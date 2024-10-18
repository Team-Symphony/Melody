package dev.symphony.melody.item.map_book;

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
    private static final Map<String, MapBookState> clientMapBooks = new HashMap<>();

    private MapBookStateManager() {
    }

    @NotNull
    public PersistentState.Type<MapBookState> getPersistentStateType() {
        return new PersistentState.Type<>(
                () -> {throw new IllegalStateException("Should never create an empty map saved data - but for map books");},
                (nbt,lookup) -> INSTANCE.createMapBookState(nbt),
                DataFixTypes.SAVED_DATA_MAP_DATA);
    }

    @NotNull
    public MapBookState createMapBookState(@NotNull NbtCompound nbt) {
        return new MapBookState().fromNbt(nbt);
    }

    @Nullable
    public MapBookState getMapBookState(@NotNull MinecraftServer server, int id) {
        return id == -1 ? null : server.getOverworld().getPersistentStateManager().get(this.getPersistentStateType(), this.getMapBookName(id));
    }

    public void putMapBookState(@NotNull MinecraftServer server, int id, @Nullable MapBookState state) {
        if (id != -1) {
            server.getOverworld().getPersistentStateManager().set(this.getMapBookName(id), state);
        }
    }

    @Nullable
    public MapBookState getClientMapBookState(int id) {
        return id == -1 ? null : clientMapBooks.get(this.getMapBookName(id));
    }

    public void putClientMapBookState(int id, @Nullable MapBookState state) {
        if (id != -1 && state != null) {
            clientMapBooks.put(this.getMapBookName(id), state);
        }
    }

    @NotNull
    public String getMapBookName(int mapId) {
        return "melody_map_book_" + mapId;
    }
}
