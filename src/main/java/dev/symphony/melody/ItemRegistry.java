package dev.symphony.melody;

import dev.symphony.melody.map_book.MapBookAdditionsComponent;
import dev.symphony.melody.map_book.MapBookItem;
import net.minecraft.component.ComponentType;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.function.UnaryOperator;

public class ItemRegistry {
    public static final MapBookItem MapBook = new MapBookItem(new Item.Settings().maxCount(1));



    public static final ComponentType<MapBookAdditionsComponent> MAP_BOOK_ADDITIONS = registerComponent("melody_map_book_additions", (builder) -> builder.codec(MapBookAdditionsComponent.CODEC).packetCodec(MapBookAdditionsComponent.PACKET_CODEC).cache());

    private static <T> ComponentType<T> registerComponent(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, id, builderOperator.apply(ComponentType.builder()).build());
    }

}
