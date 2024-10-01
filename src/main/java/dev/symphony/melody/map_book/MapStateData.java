package dev.symphony.melody.map_book;

import net.minecraft.component.type.MapIdComponent;
import net.minecraft.item.map.MapState;
import org.jetbrains.annotations.NotNull;

public record MapStateData(MapIdComponent id, @NotNull MapState mapState) {
}

