package dev.symphony.melody.item;

import dev.symphony.melody.Melody;
import dev.symphony.melody.item.map_book.MapBookAdditionsComponent;
import dev.symphony.melody.item.map_book.MapBookItem;
import net.minecraft.component.ComponentType;
import net.minecraft.item.AnimalArmorItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.UnaryOperator;


public class ModItems {

    // FEATURE: Netherite horse armor
    // AUTHORS: Flatkat
    // ARTISTS: Eleanor
    public static final Item NETHERITE_HORSE_ARMOR = registerItem(
            // Uses custom armor material in order to change the protection from 11 to 15 (since horse armor takes the chestplate protection from the material)
            "netherite_horse_armor", new AnimalArmorItem(ModArmorMaterials.NETHERITE_HORSE_ARMOR_MATERIAL, AnimalArmorItem.Type.EQUESTRIAN, false, new Item.Settings().maxCount(1).fireproof())
    );

    // FEATURE: Map Book
    // AUTHORS: Nettakrim
    // ARTISTS: Eleanor
    public static final MapBookItem MAP_BOOK = (MapBookItem)registerItem("map_book", new MapBookItem(new Item.Settings().maxCount(1)));
    public static final ComponentType<MapBookAdditionsComponent> MAP_BOOK_ADDITIONS = registerComponent("melody_map_book_additions", (builder) -> builder.codec(MapBookAdditionsComponent.CODEC).packetCodec(MapBookAdditionsComponent.PACKET_CODEC).cache());



    public static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, Identifier.of(Melody.MOD_ID, name), item);
    }

    private static <T> ComponentType<T> registerComponent(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, id, builderOperator.apply(ComponentType.builder()).build());
    }

    public static void registerModItems(){
        Melody.LOGGER.info("Registering Mod Items for" + Melody.MOD_ID);
    }
}
