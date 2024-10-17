package dev.symphony.melody.mixin.map_book;

import dev.symphony.melody.item.ModItems;
import dev.symphony.melody.item.map_book.MapBookItem;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.CartographyTableScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(CartographyTableScreenHandler.class)
public abstract class CartographyRecipesMixin extends ScreenHandler {
    @Shadow @Final private CraftingResultInventory resultInventory;

    protected CartographyRecipesMixin(@Nullable ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }

    @Inject(at = @At("HEAD"), method = "method_17382")
    private void updateResult(ItemStack map, ItemStack item, ItemStack oldResult, World world, BlockPos pos, CallbackInfo ci) {
        if (map.isOf(Items.BOOK) && item.isOf(Items.MAP)) {
            resultInventory.setStack(2, new ItemStack(ModItems.MAP_BOOK, 1));
            sendContentUpdates();
        } else if (map.isOf(ModItems.MAP_BOOK) && item.isOf(Items.FILLED_MAP)) {
            ItemStack newItem = map.copy();
            ((MapBookItem)map.getItem()).setAdditions(map, List.of(item.get(DataComponentTypes.MAP_ID).id()));
            resultInventory.setStack(2, newItem);
            sendContentUpdates();
        }
    }
}


