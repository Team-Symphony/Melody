package dev.symphony.melody.map_book;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.PersistentState;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

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
        Iterator var3 = nbt.getKeys().iterator();

        while(var3.hasNext()) {
            String string = (String)var3.next();
            if (nbt.contains(string, 99)) {
                idCountsState.idCounts.put(string, nbt.getInt(string));
            }
        }

        return idCountsState;
    }

    @NotNull
    public NbtCompound writeNbt(@NotNull NbtCompound nbt, RegistryWrapper.WrapperLookup wrapperLookup) {
        ObjectIterator var2 = this.idCounts.object2IntEntrySet().iterator();

        while(var2.hasNext()) {
            Object2IntMap.Entry entry = (Object2IntMap.Entry)var2.next();
            Object var4 = entry.getKey();
            nbt.putInt((String)var4, entry.getIntValue());
        }

        return nbt;
    }

    public final int getNextMapBookId() {
        int i = this.idCounts.getInt("melody:map_book") + 1;
        this.idCounts.put("melody:map_book", i);
        this.markDirty();
        return i;
    }
}
