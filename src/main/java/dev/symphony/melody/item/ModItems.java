package dev.symphony.melody.item;

import dev.symphony.melody.Melody;
import net.minecraft.item.AnimalArmorItem;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;


public class ModItems {

    // public static final Item NETHERITE_HORSE_ARMOR
    public static final Item NETHERITE_HORSE_ARMOR = registerItem(
            "netherite_horse_armor", new AnimalArmorItem(ArmorMaterials.NETHERITE, AnimalArmorItem.Type.EQUESTRIAN, false, new Item.Settings().maxCount(1))
    );

    public static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, Identifier.of(Melody.MOD_ID, name), item);
    }

    public static void registerModItems(){
        Melody.LOGGER.info("Registering Mod Items for" + Melody.MOD_ID);

        //TODO: Add Melody tag and creative inventory tag
    }
}
