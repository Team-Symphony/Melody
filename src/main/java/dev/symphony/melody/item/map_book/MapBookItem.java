package dev.symphony.melody.item.map_book;

import dev.symphony.melody.item.ModItems;
import dev.symphony.melody.network.MapBookOpenPayload;
import dev.symphony.melody.network.MapBookSyncPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.MapIdComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.map.MapState;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.packet.Packet;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public final class MapBookItem extends NetworkSyncedItem {
    public MapBookItem(Item.Settings settings) {
        super(settings);
    }

    @Override @NotNull
    public ActionResult useOnBlock(@NotNull ItemUsageContext context) {
        BlockState blockState = context.getWorld().getBlockState(context.getBlockPos());

        if (blockState.isIn(BlockTags.BANNERS)) {
            if (!context.getWorld().isClient) {
                MapStateData mapStateData = this.getNearestMap(context.getStack(), context.getWorld(), context.getBlockPos().toCenterPos());
                MapState mapState = mapStateData != null ? mapStateData.mapState() : null;
                if (mapState != null && !mapState.addBanner(context.getWorld(), context.getBlockPos())) {
                    return ActionResult.FAIL;
                }
            }

            return ActionResult.success(context.getWorld().isClient);
        } else {
            return super.useOnBlock(context);
        }
    }

    @Override @NotNull
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world != null && !world.isClient()) {
            ServerPlayerEntity player = (ServerPlayerEntity)user;
            ItemStack item = user.getStackInHand(hand);
            ItemStack otherHand = hand == Hand.MAIN_HAND ? player.getOffHandStack() : player.getMainHandStack();
            boolean openMap = true;
            if (otherHand.isOf(Items.PAPER)) {
                if (this.addNewMapAtPos(item, (ServerWorld)world, player.getPos(), 0)) {
                    if (!player.getAbilities().creativeMode) {
                        otherHand.decrement(1);
                    }

                    player.getWorld().playSoundFromEntity(null, player, SoundEvents.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, player.getSoundCategory(), 1.0F, 1.0F);
                    openMap = false;
                }
            }
            this.sendMapUpdates(player, item);
            mapBookSync(player, item);
            if (openMap && this.getMapBookId(item) != -1) {
                mapBookOpen(player, item);
            }
        }

        return super.use(world, user, hand);
    }

    public void sendMapUpdates(@NotNull ServerPlayerEntity player, @NotNull ItemStack item) {
        for (MapStateData mapStateData : this.getMapStates(item, player.getWorld())) {
            mapStateData.mapState().getPlayerSyncData(player);
            Packet<?> packet = mapStateData.mapState().getPlayerMarkerPacket(mapStateData.id(), player);
            if (packet != null) {
                player.networkHandler.sendPacket(packet);
            }
        }

    }

    @Override
    public void inventoryTick(@Nullable ItemStack stack, @Nullable World world, @Nullable Entity entity, int slot, boolean selected) {
        if (world != null && !world.isClient()) {
            if (stack != null && entity instanceof PlayerEntity) {
                if (selected || ((PlayerEntity)entity).getOffHandStack().equals(stack)) {
                    //this.applyAdditions(stack, (ServerWorld)world);

                    for (MapStateData mapStateData : this.getMapStates(stack, entity.getWorld())) {
                        mapStateData.mapState().update((PlayerEntity) entity, stack);
                        if (!mapStateData.mapState().locked) {
                            if (this.getDistanceToEdgeOfMap(mapStateData.mapState(), entity.getPos()) < 128.0) {
                                ((FilledMapItem)Items.FILLED_MAP).updateColors(world, entity, mapStateData.mapState());
                            }
                        }
                    }

                    this.sendMapUpdates((ServerPlayerEntity)entity, stack);
                    mapBookSync((ServerPlayerEntity)entity, stack);
                }
            }
        }
    }

    @NotNull
    public ArrayList<MapStateData> getMapStates(@NotNull ItemStack stack, @NotNull World world) {
        ArrayList<MapStateData> list = new ArrayList<>();
        MapBookState mapBookState = getMapBookState(stack, world);

        if (mapBookState != null) {
            for (Integer i : mapBookState.getMapIDs()) {
                MapState mapState = FilledMapItem.getMapState(new MapIdComponent(i), world);
                if (mapState != null) {
                    list.add(new MapStateData(new MapIdComponent(i), mapState));
                }
            }

        }
        return list;
    }

    @Nullable
    private MapBookState getMapBookState(@NotNull ItemStack stack, @NotNull World world) {
        int id = getMapBookId(stack);
        if (world.isClient) {
            return MapBookStateManager.INSTANCE.getClientMapBookState(id);
        } else if (world.getServer() != null){
            return MapBookStateManager.INSTANCE.getMapBookState(world.getServer(), id);
        }
        return null;
    }

    @Nullable
    public MapStateData getNearestMap(@NotNull ItemStack stack, @NotNull World world, @NotNull Vec3d pos) {
        double nearestDistance = Double.MAX_VALUE;
        byte nearestScale = 127;
        MapStateData nearestMap = null;

        Iterator<MapStateData> mapStates = this.getMapStates(stack, world).iterator();

        while(true) {
            MapStateData mapStateData;
            double distance;
            boolean roughlyEqual;
            do {
                do {
                    if (!mapStates.hasNext()) {
                        return nearestMap;
                    }

                    mapStateData = mapStates.next();
                    distance = this.getDistanceToEdgeOfMap(mapStateData.mapState(), pos);
                    if (distance < 0.0) {
                        distance = -1.0;
                    }

                    roughlyEqual = Math.abs(nearestDistance - distance) < 1.0;
                } while(!(distance < nearestDistance) && !roughlyEqual);
            } while(roughlyEqual && (!(distance < 0.0) || mapStateData.mapState().scale >= nearestScale) && (!(distance > 0.0) || mapStateData.mapState().scale <= nearestScale));

            nearestDistance = distance;
            nearestScale = mapStateData.mapState().scale;
            nearestMap = mapStateData;
        }
    }

    public double getDistanceToEdgeOfMap(@NotNull MapState mapState, @NotNull Vec3d pos) {
        return Math.max(Math.abs(pos.x - (double)mapState.centerX), Math.abs(pos.z - (double)mapState.centerZ)) - (double)(64 * (1 << mapState.scale));
    }

    public int getMapBookId(@NotNull ItemStack stack) {
        return stack.getOrDefault(DataComponentTypes.MAP_ID, new MapIdComponent(-1)).id();
    }

    private int allocateMapBookId(MinecraftServer server) {
        MapBookIdCountsState counts = server.getOverworld().getPersistentStateManager().getOrCreate(MapBookIdCountsState.getPersistentStateType(), MapBookIdCountsState.IDCOUNTS_KEY);
        int i = counts.getNextMapBookId();
        MapBookStateManager.INSTANCE.putMapBookState(server, i, new MapBookState());
        return i;
    }

    private void setMapBookId(ItemStack stack, int id) {
        stack.set(DataComponentTypes.MAP_ID, new MapIdComponent(id));
    }

    private int createMapBookState(ItemStack stack, MinecraftServer server) {
        int i = this.allocateMapBookId(server);
        this.setMapBookId(stack, i);
        return i;
    }

    private MapBookState getOrCreateMapBookState(ItemStack stack, MinecraftServer server) {
        MapBookState state = MapBookStateManager.INSTANCE.getMapBookState(server, this.getMapBookId(stack));
        if (state != null) {
            return state;
        } else {
            int i = this.createMapBookState(stack, server);
            return MapBookStateManager.INSTANCE.getMapBookState(server, i);
        }
    }

    private boolean addNewMapAtPos(ItemStack item, ServerWorld world, Vec3d pos, int scale) {
        MapBookState state = this.getOrCreateMapBookState(item, world.getServer());
        MapStateData nearestState = this.getNearestMap(item, world, pos);
        if (nearestState != null && nearestState.mapState().scale <= scale && !(this.getDistanceToEdgeOfMap(nearestState.mapState(), pos) > 0.0)) {
            return false;
        } else {
            ItemStack newMap = FilledMapItem.createMap(world, (int)Math.floor(pos.x), (int)Math.floor(pos.z), (byte)scale, true, false);
            state.addMapID(Objects.requireNonNull(newMap.get(DataComponentTypes.MAP_ID)).id());
            return true;
        }
    }

    @Override @NotNull
    public Text getName(@Nullable ItemStack stack) {
        if (stack != null && this.getMapBookId(stack) == -1) {
            if (stack.contains(ModItems.MAP_BOOK_ADDITIONS)) {
                return Text.translatable("item.melody.map_book_new");
            } else {
                return Text.translatable("item.melody.map_book_empty");
            }
        } else {
            return super.getName(stack);
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if (stack != null) {
            int id = this.getMapBookId(stack);

            int mapsCount = stack.getOrDefault(ModItems.MAP_BOOK_ADDITIONS, MapBookAdditionsComponent.DEFAULT).additions().size();
            if (id != -1) {
                //append tooltip is client-based, so its safe to get the client MapBookState
                MapBookState mapBookState = MapBookStateManager.INSTANCE.getClientMapBookState(id);

                if (mapBookState != null) {
                    mapsCount += mapBookState.getMapIDs().size();
                }

                tooltip.add(Text.translatable("item.melody.map_book_id", String.valueOf(id + 1)).formatted(Formatting.GRAY));
            }

            if (mapsCount > 0) {
                tooltip.add(Text.translatable("item.melody.map_book_maps", String.valueOf(mapsCount)).formatted(Formatting.GRAY));
            }
        }
    }

    public void setAdditions(@NotNull ItemStack stack, @NotNull List<Integer> additions) {
        stack.set(ModItems.MAP_BOOK_ADDITIONS, new MapBookAdditionsComponent(additions));
    }

    private void applyAdditions(ItemStack stack, ServerWorld world) {
        MapBookAdditionsComponent additionsComponent = stack.getOrDefault(ModItems.MAP_BOOK_ADDITIONS, null);
        if (additionsComponent == null) return;
        stack.remove(ModItems.MAP_BOOK_ADDITIONS);

        //TODO: replace maps in the same location
        List<Integer> additions = additionsComponent.additions();
        if (!additions.isEmpty()) {
            MapBookState state = this.getOrCreateMapBookState(stack, world.getServer());

            for (int id : additions) {
                state.addMapID(id);
            }
        }
    }

    public boolean hasInvalidAdditions(@NotNull ItemStack stack, @NotNull World world, List<Integer> additions) {
        MapBookState mapBookState = getMapBookState(stack, world);

        for (Integer i : additions) {
            if (mapBookState != null && mapBookState.getMapIDs().contains(i)) {
                return true;
            }

            boolean duplicate = false;
            for (Integer j : additions) {
                if (Objects.equals(i, j)) {
                    if (duplicate) return true;
                    duplicate = true;
                    }
                //} else {
                //    //TODO: ensure no two additions are the same location
                //}
            }
        }
        return false;
    }

    @Override
    public void onCraftByPlayer(ItemStack stack, World world, PlayerEntity player) {
        super.onCraftByPlayer(stack, world, player);
        if (!world.isClient) {
            mapBookSync((ServerPlayerEntity)player, stack);
        }
    }

    @Override
    public void onCraft(ItemStack stack, World world) {
        if (!world.isClient) {
            applyAdditions(stack, (ServerWorld)world);
        }
    }

    public void mapBookOpen(@NotNull ServerPlayerEntity player, @NotNull ItemStack itemStack) {
        ServerPlayNetworking.send(player, new MapBookOpenPayload(itemStack));
    }

    public void mapBookSync(@NotNull ServerPlayerEntity player, @NotNull ItemStack itemStack) {
        ServerPlayNetworking.send(player, MapBookSyncPayload.of(player, itemStack));
    }
}
