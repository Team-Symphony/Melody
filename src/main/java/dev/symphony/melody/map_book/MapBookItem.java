package dev.symphony.melody.map_book;

import dev.symphony.melody.ItemRegistry;
import dev.symphony.melody.network.SyncHandler;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.MapIdComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.map.MapState;
import net.minecraft.network.packet.Packet;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.screen.ScreenTexts;
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

public final class MapBookItem extends NetworkSyncedItem {
    @NotNull
    private final String MAP_BOOK_KEY = "melody:map_book";
    @NotNull
    private final String ADDITIONS_KEY = "melody:additions";

    public MapBookItem(@Nullable Item.Settings settings) {
        super(settings);
    }

    @NotNull
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

    @NotNull
    public TypedActionResult<ItemStack> use(@Nullable World world, @Nullable PlayerEntity user, @Nullable Hand hand) {
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
            SyncHandler.INSTANCE.mapBookSync(player, item);
            if (openMap && this.getMapBookId(item) != null) {
                SyncHandler.INSTANCE.onOpenMapBook(player, item);
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

    public void inventoryTick(@Nullable ItemStack stack, @Nullable World world, @Nullable Entity entity, int slot, boolean selected) {
        if (world != null && !world.isClient()) {
            if (stack != null && entity instanceof PlayerEntity) {
                if (selected || ((PlayerEntity)entity).getOffHandStack().equals(stack)) {
                    this.applyAdditions(stack, (ServerWorld)world);

                    for (MapStateData mapStateData : this.getMapStates(stack, entity.getWorld())) {
                        mapStateData.mapState().update((PlayerEntity) entity, stack);
                        if (!mapStateData.mapState().locked) {
                            if (this.getDistanceToEdgeOfMap(mapStateData.mapState(), entity.getPos()) < 128.0) {
                                ((FilledMapItem)Items.FILLED_MAP).updateColors(world, entity, mapStateData.mapState());
                            }
                        }
                    }

                    this.sendMapUpdates((ServerPlayerEntity)entity, stack);
                    SyncHandler.INSTANCE.mapBookSync((ServerPlayerEntity)entity, stack);
                }
            }
        }
    }

    @NotNull
    public ArrayList<MapStateData> getMapStates(@NotNull ItemStack stack, @Nullable World world) {
        ArrayList<MapStateData> list = new ArrayList<>();
        if (world != null) {
            MapBookState mapBookState;
            if (world.isClient) {
                mapBookState = MapBookStateManager.INSTANCE.getClientMapBookState(this.getMapBookId(stack));
            } else {
                mapBookState = MapBookStateManager.INSTANCE.getMapBookState(world.getServer(), this.getMapBookId(stack));
            }

            if (mapBookState != null) {
                for (Integer i : mapBookState.getMapIDs()) {
                    MapState mapState = FilledMapItem.getMapState(new MapIdComponent(i), world);
                    if (mapState != null) {
                        list.add(new MapStateData(new MapIdComponent(i), mapState));
                    }
                }

            }
        }
        return list;
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

    @Nullable
    public Integer getMapBookId(@NotNull ItemStack stack) {
        if (stack.contains(DataComponentTypes.MAP_ID)) {
            return stack.get(DataComponentTypes.MAP_ID).id();
        }
        return null;
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
            state.addMapID(newMap.get(DataComponentTypes.MAP_ID).id());
            return true;
        }
    }

    @NotNull
    public Text getName(@Nullable ItemStack stack) {
        if (stack != null && this.getMapBookId(stack) == null) {
            if (stack.contains(ItemRegistry.MAP_BOOK_ADDITIONS)) {
                return Text.translatable("item.melody.map_book_new");
            } else {
                return Text.translatable("item.melody.map_book_empty");
            }
        } else {
            return super.getName(stack);
        }
    }

    public void appendTooltip(@Nullable ItemStack stack, @Nullable World world, @Nullable List<Text> tooltip, @Nullable TooltipContext context) {
        if (stack != null) {
            Integer id = this.getMapBookId(stack);

            int mapsCount = stack.getOrDefault(ItemRegistry.MAP_BOOK_ADDITIONS, MapBookAdditionsComponent.DEFAULT).additions().size();
            if (id != null) {
                MapBookState mapBookState;
                if (world != null && !world.isClient) {
                    mapBookState = MapBookStateManager.INSTANCE.getMapBookState(world.getServer(), id);
                } else {
                    mapBookState = MapBookStateManager.INSTANCE.getClientMapBookState(id);
                }

                if (mapBookState != null) {
                    mapsCount += mapBookState.getMapIDs().size();
                }

                tooltip.add(Text.translatable("item.melody.map_book_id").append(ScreenTexts.SPACE).append(String.valueOf(id + 1)).formatted(Formatting.GRAY));
            }

            if (mapsCount > 0) {
                tooltip.add(Text.translatable("item.melody.map_book_maps").append(ScreenTexts.SPACE).append(String.valueOf(mapsCount)).formatted(Formatting.GRAY));
            }
        }
    }

    public void setAdditions(@NotNull ItemStack stack, @NotNull ArrayList<Integer> additions) {
        stack.set(ItemRegistry.MAP_BOOK_ADDITIONS, new MapBookAdditionsComponent(additions));
    }

    private void applyAdditions(ItemStack stack, ServerWorld world) {
        MapBookAdditionsComponent additionsComponent = stack.getOrDefault(ItemRegistry.MAP_BOOK_ADDITIONS, null);
        if (additionsComponent == null) return;
        stack.remove(ItemRegistry.MAP_BOOK_ADDITIONS);

        ArrayList<Integer> additions = additionsComponent.additions();
        if (!additions.isEmpty()) {
            MapBookState state = this.getOrCreateMapBookState(stack, world.getServer());

            for (int id : additions) {
                state.addMapID(id);
            }
        }
    }
}
