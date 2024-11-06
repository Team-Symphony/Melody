package dev.symphony.melody.mixin.transportation;

import dev.symphony.melody.config.MelodyConfig;
import net.minecraft.item.equipment.ArmorMaterials;
import net.minecraft.item.equipment.EquipmentType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorMaterials.class)
public interface ArmorMaterialsMixin {
    // Changes Netherite Horse Armor protection from vanilla value to a custom one
    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void modifyNetheriteBodyProtection(CallbackInfo info) {
        ArmorMaterials.NETHERITE.defense().put(EquipmentType.BODY, MelodyConfig.netheriteHorseArmorDefense);
    }
}
