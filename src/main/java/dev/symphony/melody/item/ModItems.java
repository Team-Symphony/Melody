package dev.symphony.melody.item;

import dev.symphony.melody.Melody;
import net.minecraft.item.AnimalArmorItem;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;


public class ModItems {

    // FEATURE: Netherite horse armor
    // AUTHORS: Flatkat
    // ARTISTS: Eleanor
    public static final Item NETHERITE_HORSE_ARMOR = registerItem(
            // Uses custom armor material in order to change the protection from 11 to 15 (since horse armor takes the chestplate protection from the material)
            "netherite_horse_armor", new AnimalArmorItem(ModArmorMaterials.NETHERITE_HORSE_ARMOR_MATERIAL, AnimalArmorItem.Type.EQUESTRIAN, false, new Item.Settings().maxCount(1).fireproof())
    );

    public static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, Identifier.of(Melody.MOD_ID, name), item);
    }

    public static void registerModItems(){
        Melody.LOGGER.info("Registering Mod Items for" + Melody.MOD_ID);
    }
}
