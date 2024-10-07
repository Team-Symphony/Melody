package dev.symphony.melody.item;

import dev.symphony.melody.Melody;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.EnumMap;
import java.util.Enumeration;
import java.util.List;
import java.util.function.Supplier;

public class ModArmorMaterials {

    // For more info on this, compare to ArmorMaterials.NETHERITE on the vanilla sources
    public static final RegistryEntry<ArmorMaterial> NETHERITE_HORSE_ARMOR_MATERIAL = registerArmorMaterial("netherite_horse_armor_material",
            () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                // The protection the horse armor gives
                map.put(ArmorItem.Type.BODY, 15);
            // This i:15 is enchantability
            }), 15, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, () -> Ingredient.ofItems(Items.NETHERITE_INGOT),
                    // f:3 and g:0.1f are toughness and knockback resistance respectively
                    List.of(new ArmorMaterial.Layer(Identifier.of(Melody.MOD_ID, "netherite_horse_armor_material"))), 3, 0.1f));

    public static RegistryEntry<ArmorMaterial> registerArmorMaterial(String name, Supplier<ArmorMaterial> material){
        return Registry.registerReference(Registries.ARMOR_MATERIAL, Identifier.of(Melody.MOD_ID, name), material.get());
    }
}
