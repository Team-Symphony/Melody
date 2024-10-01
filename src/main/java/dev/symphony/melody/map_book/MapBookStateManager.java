package dev.symphony.melody.map_book;

import com.google.common.collect.Maps;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public final class MapBookStateManager {
    @NotNull
    public static final MapBookStateManager INSTANCE = new MapBookStateManager();
    @NotNull
    private static final Map<String, MapBookState> clientMapBooks;

    private MapBookStateManager() {
    }

    @NotNull
    public final PersistentState.Type<MapBookState> getPersistentStateType() {
        return new PersistentState.Type<MapBookState>(
                () -> {throw new IllegalStateException("Should never create an empty map saved data - but for map books");},
                (nbt,lookup) -> INSTANCE.createMapBookState(nbt),
                DataFixTypes.SAVED_DATA_MAP_DATA);
    }

    @NotNull
    public final MapBookState createMapBookState(@NotNull NbtCompound nbt) {
        MapBookState state = new MapBookState();
        state.fromNbt(nbt);
        return state;
    }

    @Nullable
    public final MapBookState getMapBookState(@NotNull MinecraftServer server, @Nullable Integer id) {
        return id == null ? null : (MapBookState)server.getOverworld().getPersistentStateManager().get(this.getPersistentStateType(), this.getMapBookName(id));
    }

    public final void putMapBookState(@NotNull MinecraftServer server, @Nullable Integer id, @Nullable MapBookState state) {
        if (id != null) {
            server.getOverworld().getPersistentStateManager().set(this.getMapBookName(id), (PersistentState)state);
        }
    }

    @Nullable
    public final MapBookState getClientMapBookState(@Nullable Integer id) {
        return id == null ? null : clientMapBooks.get(this.getMapBookName(id));
    }

    public final void putClientMapBookState(@Nullable Integer id, @Nullable MapBookState state) {
        if (id != null && state != null) {
            clientMapBooks.put(this.getMapBookName(id), state);
        }
    }

    @NotNull
    public final String getMapBookName(int mapId) {
        return "melody_map_book_" + mapId;
    }

    static {
        HashMap var0 = Maps.newHashMap();
        clientMapBooks = (Map)var0;
    }
}
