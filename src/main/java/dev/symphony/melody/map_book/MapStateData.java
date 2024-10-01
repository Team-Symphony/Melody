package dev.symphony.melody.map_book;

import net.minecraft.component.type.MapIdComponent;
import net.minecraft.item.map.MapState;
import org.jetbrains.annotations.NotNull;

public final class MapStateData {
    private final MapIdComponent id;
    @NotNull
    private final MapState mapState;

    public MapStateData(MapIdComponent id, @NotNull MapState mapState) {
        super();
        this.id = id;
        this.mapState = mapState;
    }

    public final MapIdComponent getId() {
        return this.id;
    }

    @NotNull
    public final MapState getMapState() {
        return this.mapState;
    }
}

