package dev.symphony.melody.mixin.map_book;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.symphony.melody.item.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net/minecraft/screen/CartographyTableScreenHandler$3")
public class CartographyRecipesSlot0Mixin {
    @ModifyReturnValue(at = @At("RETURN"), method = "canInsert")
    private boolean addCanInsert(boolean original, ItemStack stack) {
        return original || stack.isOf(Items.BOOK) || stack.isOf(ModItems.MAP_BOOK);
    }
}
