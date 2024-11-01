package dev.symphony.melody.item;

import dev.symphony.melody.Melody;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentModels;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Util;

import java.util.EnumMap;
import java.util.function.Supplier;

public class ModArmorMaterials {

    // For more info on this, compare to ArmorMaterials.NETHERITE on the vanilla sources
    public static final ArmorMaterial NETHERITE_HORSE_ARMOR_MATERIAL = new ArmorMaterial(37, Util.make(new EnumMap(EquipmentType.class), map -> {
        map.put(EquipmentType.CHESTPLATE, 15);
    // This j:15 is enchantability, while f:3 and g:0.1f are toughness and knockback resistance respectively
    }), 15, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 3.0F, 0.1F, ItemTags.REPAIRS_NETHERITE_ARMOR, EquipmentModels.NETHERITE);

    //public static RegistryEntry<ArmorMaterial> registerArmorMaterial(String name, Supplier<ArmorMaterial> material){
    //    return Registry.registerReference(Registries.ARMOR_MATERIAL, Melody.id(name), material.get());
    //}
}
