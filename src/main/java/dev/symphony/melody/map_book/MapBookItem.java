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
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
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
        ActionResult var3;
        if (blockState.isIn(BlockTags.BANNERS)) {
            if (!context.getWorld().isClient) {
                ItemStack var5 = context.getStack();
                ItemStack var10001 = var5;
                World var7 = context.getWorld();
                World var10002 = var7;
                Vec3d var8 = context.getBlockPos().toCenterPos();
                MapStateData var4 = this.getNearestMap(var10001, var10002, var8);
                MapState mapState = var4 != null ? var4.getMapState() : null;
                if (mapState != null && !mapState.addBanner((WorldAccess)context.getWorld(), context.getBlockPos())) {
                    return ActionResult.FAIL;
                }
            }

            var3 = ActionResult.success(context.getWorld().isClient);
            return var3;
        } else {
            var3 = super.useOnBlock(context);
            return var3;
        }
    }

    @NotNull
    public TypedActionResult use(@Nullable World world, @Nullable PlayerEntity user, @Nullable Hand hand) {
        if (world != null && !world.isClient()) {
            ServerPlayerEntity player = (ServerPlayerEntity)user;
            ItemStack item = user.getStackInHand(hand);
            ItemStack otherHand = hand == Hand.MAIN_HAND ? player.getOffHandStack() : player.getMainHandStack();
            boolean openMap = true;
            if (otherHand.isOf(Items.PAPER)) {
                ServerWorld var10002 = (ServerWorld)world;
                Vec3d var8 = player.getPos();
                if (this.addNewMapAtPos(item, var10002, var8, 0)) {
                    if (!player.getAbilities().creativeMode) {
                        otherHand.decrement(1);
                    }

                    player.getWorld().playSoundFromEntity((PlayerEntity)null, (Entity)player, SoundEvents.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, player.getSoundCategory(), 1.0F, 1.0F);
                    openMap = false;
                }
            }
            this.sendMapUpdates(player, item);
            SyncHandler.INSTANCE.mapBookSync(player, item);
            if (openMap && this.getMapBookId(item) != null) {
                SyncHandler.INSTANCE.onOpenMapBook(player, item);
            }
        }

        TypedActionResult var9 = super.use(world, user, hand);
        return var9;
    }

    public final void sendMapUpdates(@NotNull ServerPlayerEntity player, @NotNull ItemStack item) {
        for (MapStateData mapStateData : this.getMapStates(item, player.getWorld())) {
            mapStateData.getMapState().getPlayerSyncData((PlayerEntity) player);
            Packet packet = mapStateData.getMapState().getPlayerMarkerPacket(mapStateData.getId(), (PlayerEntity) player);
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
                    Iterator var6 = this.getMapStates(stack, ((PlayerEntity)entity).getWorld()).iterator();

                    while(var6.hasNext()) {
                        MapStateData mapStateData = (MapStateData)var6.next();
                        mapStateData.getMapState().update((PlayerEntity)entity, stack);
                        if (!mapStateData.getMapState().locked) {
                            MapState var10001 = mapStateData.getMapState();
                            Vec3d var8 = ((PlayerEntity)entity).getPos();
                            if (this.getDistanceToEdgeOfMap(var10001, var8) < 128.0) {
                                Item var9 = Items.FILLED_MAP;
                                ((FilledMapItem)var9).updateColors(world, entity, mapStateData.getMapState());
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
    public final ArrayList<MapStateData> getMapStates(@NotNull ItemStack stack, @Nullable World world) {
        ArrayList<MapStateData> list = new ArrayList<>();
        if (world != null) {
            MapBookState mapBookState;
            if (world.isClient) {
                mapBookState = MapBookStateManager.INSTANCE.getClientMapBookState(this.getMapBookId(stack));
            } else {
                MapBookStateManager var8 = MapBookStateManager.INSTANCE;
                mapBookState = var8.getMapBookState(world.getServer(), this.getMapBookId(stack));
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
    public final MapStateData getNearestMap(@NotNull ItemStack stack, @NotNull World world, @NotNull Vec3d pos) {
        double nearestDistance = Double.MAX_VALUE;
        byte nearestScale = 127;
        MapStateData nearestMap = null;
        Iterator<MapStateData> var8 = this.getMapStates(stack, world).iterator();

        while(true) {
            MapStateData mapStateData;
            double distance;
            boolean roughlyEqual;
            do {
                do {
                    if (!var8.hasNext()) {
                        return nearestMap;
                    }

                    mapStateData = (MapStateData)var8.next();
                    distance = this.getDistanceToEdgeOfMap(mapStateData.getMapState(), pos);
                    if (distance < 0.0) {
                        distance = -1.0;
                    }

                    roughlyEqual = Math.abs(nearestDistance - distance) < 1.0;
                } while(!(distance < nearestDistance) && !roughlyEqual);
            } while(roughlyEqual && (!(distance < 0.0) || mapStateData.getMapState().scale >= nearestScale) && (!(distance > 0.0) || mapStateData.getMapState().scale <= nearestScale));

            nearestDistance = distance;
            nearestScale = mapStateData.getMapState().scale;
            nearestMap = mapStateData;
        }
    }

    public final double getDistanceToEdgeOfMap(@NotNull MapState mapState, @NotNull Vec3d pos) {
        return Math.max(Math.abs(pos.x - (double)mapState.centerX), Math.abs(pos.z - (double)mapState.centerZ)) - (double)(64 * (1 << mapState.scale));
    }

    @Nullable
    public final Integer getMapBookId(@NotNull ItemStack stack) {
        if (stack.contains(DataComponentTypes.MAP_ID)) {
            return stack.get(DataComponentTypes.MAP_ID).id();
        }
        return null;
    }

    private final int allocateMapBookId(MinecraftServer server) {
        MapBookIdCountsState var10000;
        MapBookState mapBookState;
        label12: {
            mapBookState = new MapBookState();
            ServerWorld var4 = server.getOverworld();
            if (var4 != null) {
                PersistentStateManager var5 = var4.getPersistentStateManager();
                if (var5 != null) {
                    var10000 = var5.getOrCreate(MapBookIdCountsState.getPersistentStateType(), MapBookIdCountsState.IDCOUNTS_KEY);
                    break label12;
                }
            }

            var10000 = null;
        }

        MapBookIdCountsState counts = var10000;
        int i = counts.getNextMapBookId();
        MapBookStateManager.INSTANCE.putMapBookState(server, i, mapBookState);
        return i;
    }

    private final void setMapBookId(ItemStack stack, int id) {
        stack.set(DataComponentTypes.MAP_ID, new MapIdComponent(id));
    }

    private final int createMapBookState(ItemStack stack, MinecraftServer server) {
        int i = this.allocateMapBookId(server);
        this.setMapBookId(stack, i);
        return i;
    }

    private final MapBookState getOrCreateMapBookState(ItemStack stack, MinecraftServer server) {
        MapBookState state = MapBookStateManager.INSTANCE.getMapBookState(server, this.getMapBookId(stack));
        if (state != null) {
            return state;
        } else {
            int i = this.createMapBookState(stack, server);
            return MapBookStateManager.INSTANCE.getMapBookState(server, i);
        }
    }

    private final boolean addNewMapAtPos(ItemStack item, ServerWorld world, Vec3d pos, int scale) {
        MinecraftServer var6 = world.getServer();
        MapBookState state = this.getOrCreateMapBookState(item, var6);
        MapStateData nearestState = this.getNearestMap(item, (World)world, pos);
        if (nearestState != null && nearestState.getMapState().scale <= scale && !(this.getDistanceToEdgeOfMap(nearestState.getMapState(), pos) > 0.0)) {
            return false;
        } else {
            ItemStack newMap = FilledMapItem.createMap((World)world, (int)Math.floor(pos.x), (int)Math.floor(pos.z), (byte)scale, true, false);
            state.addMapID(newMap.get(DataComponentTypes.MAP_ID).id());
            return true;
        }
    }

    @NotNull
    public Text getName(@Nullable ItemStack stack) {
        if (stack != null && this.getMapBookId(stack) == null) {
            MutableText var3;
            if (stack.contains(ItemRegistry.MAP_BOOK_ADDITIONS)) {
                var3 = Text.translatable("item.melody.map_book_new");
                return (Text)var3;
            } else {
                var3 = Text.translatable("item.melody.map_book_empty");
                return (Text)var3;
            }
        } else {
            Text var2 = super.getName(stack);
            return var2;
        }
    }

    public void appendTooltip(@Nullable ItemStack stack, @Nullable World world, @Nullable List<Text> tooltip, @Nullable TooltipContext context) {
        if (stack != null) {
            Integer id = this.getMapBookId(stack);

            int mapsCount = stack.getOrDefault(ItemRegistry.MAP_BOOK_ADDITIONS, MapBookAdditionsComponent.DEFAULT).additions().size();
            if (id != null) {
                MapBookState var10000;
                if (world != null && !world.isClient) {
                    MapBookStateManager var10 = MapBookStateManager.INSTANCE;
                    MinecraftServer var10001 = world.getServer();
                    var10000 = var10.getMapBookState(var10001, id);
                } else {
                    var10000 = MapBookStateManager.INSTANCE.getClientMapBookState(id);
                }

                int var11;
                label25: {
                    MapBookState mapBookState = var10000;
                    if (mapBookState != null) {
                        ArrayList var8 = mapBookState.getMapIDs();
                        if (var8 != null) {
                            int var9 = ((Collection)var8).size();
                            var11 = var9;
                            break label25;
                        }
                    }

                    var11 = 0;
                }

                mapsCount += var11;
                MutableText var13 = Text.translatable("item.melody.map_book_id").append(ScreenTexts.SPACE).append(String.valueOf(id + 1)).formatted(Formatting.GRAY);
                tooltip.add(var13);
            }

            if (mapsCount > 0) {
                MutableText var12 = Text.translatable("item.melody.map_book_maps").append(ScreenTexts.SPACE).append(String.valueOf(mapsCount)).formatted(Formatting.GRAY);
                tooltip.add(var12);
            }

        }
    }

    public final void setAdditions(@NotNull ItemStack stack, @NotNull ArrayList<Integer> additions) {
        stack.set(ItemRegistry.MAP_BOOK_ADDITIONS, new MapBookAdditionsComponent(additions));
    }

    private final void applyAdditions(ItemStack stack, ServerWorld world) {
        MapBookAdditionsComponent additions = stack.getOrDefault(ItemRegistry.MAP_BOOK_ADDITIONS, null);
        if (additions == null) return;
        stack.remove(ItemRegistry.MAP_BOOK_ADDITIONS);

        if (!additions.additions().isEmpty()) {
            MinecraftServer var6 = world.getServer();
            MapBookState state = this.getOrCreateMapBookState(stack, var6);
            ArrayList<Integer> var10 = additions.additions();

            int var7 = 0;
            for(int var8 = additions.additions().size(); var7 < var8; ++var7) {
                int id = var10.get(var7);
                state.addMapID(id);
            }
        }
    }
}
