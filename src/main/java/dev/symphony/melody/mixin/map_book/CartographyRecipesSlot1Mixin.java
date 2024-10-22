package dev.symphony.melody.mixin.map_book;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.symphony.melody.config.MelodyConfig;
import dev.symphony.melody.item.ModItems;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net/minecraft/screen/CartographyTableScreenHandler$4")
public class CartographyRecipesSlot1Mixin {
    @ModifyReturnValue(at = @At("RETURN"), method = "canInsert")
    private boolean addCanInsert(boolean original, ItemStack stack) {
        return original || (MelodyConfig.mapBook && (stack.isOf(Items.FILLED_MAP) || (stack.isOf(ModItems.MAP_BOOK) && !stack.contains(DataComponentTypes.MAP_ID))));
    }
}
