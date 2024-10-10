package dev.symphony.melody.item.map_book;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.PersistentState;
import org.jetbrains.annotations.NotNull;

public final class MapBookIdCountsState extends PersistentState {
    @NotNull
    private final Object2IntMap<String> idCounts = new Object2IntOpenHashMap<>();
    @NotNull
    public static final String IDCOUNTS_KEY = "melody_idcounts";

    public MapBookIdCountsState() {
        this.idCounts.defaultReturnValue(-1);
    }

    public static PersistentState.Type<MapBookIdCountsState> getPersistentStateType() {
        return new PersistentState.Type<>(MapBookIdCountsState::new, MapBookIdCountsState::fromNbt, DataFixTypes.SAVED_DATA_MAP_INDEX);
    }

    public static MapBookIdCountsState fromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        MapBookIdCountsState idCountsState = new MapBookIdCountsState();

        for (String string : nbt.getKeys()) {
            if (nbt.contains(string, 99)) {
                idCountsState.idCounts.put(string, nbt.getInt(string));
            }
        }

        return idCountsState;
    }

    @NotNull
    public NbtCompound writeNbt(@NotNull NbtCompound nbt, RegistryWrapper.WrapperLookup wrapperLookup) {

        for (Object2IntMap.Entry<String> entry : this.idCounts.object2IntEntrySet()) {
            nbt.putInt(entry.getKey(), entry.getIntValue());
        }

        return nbt;
    }

    public int getNextMapBookId() {
        int i = this.idCounts.getInt("melody:map_book") + 1;
        this.idCounts.put("melody:map_book", i);
        this.markDirty();
        return i;
    }
}
