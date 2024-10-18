package dev.symphony.melody.mixin.map_book;

import dev.symphony.melody.config.MelodyConfig;
import dev.symphony.melody.item.ModItems;
import dev.symphony.melody.item.map_book.MapBookItem;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.MapIdComponent;
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

import java.util.ArrayList;
import java.util.List;

@Mixin(CartographyTableScreenHandler.class)
public abstract class CartographyRecipesMixin extends ScreenHandler {
    @Shadow @Final private CraftingResultInventory resultInventory;

    protected CartographyRecipesMixin(@Nullable ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }

    @Inject(at = @At("HEAD"), method = "method_17382", cancellable = true)
    private void updateResult(ItemStack map, ItemStack item, ItemStack oldResult, World world, BlockPos pos, CallbackInfo ci) {
        if (!MelodyConfig.mapBook) return;

        if (map.isOf(Items.BOOK) && item.isOf(Items.MAP)) {
            resultInventory.setStack(2, new ItemStack(ModItems.MAP_BOOK, 1));
            sendContentUpdates();
        } else if (map.isOf(ModItems.MAP_BOOK) && item.isOf(Items.FILLED_MAP)) {
            List<Integer> additions = new ArrayList<>();
            MapIdComponent mapIdComponent = item.get(DataComponentTypes.MAP_ID);
            if (mapIdComponent != null) {
                additions.add(mapIdComponent.id());
            }

            if (ModItems.MAP_BOOK.hasInvalidAdditions(map, world, additions)) {
                resultInventory.removeStack(2);
            } else {
                ItemStack newItem = map.copy();
                ((MapBookItem) newItem.getItem()).setAdditions(newItem, additions);
                resultInventory.setStack(2, newItem);
            }
            sendContentUpdates();
        } else if (map.isOf(ModItems.MAP_BOOK) && item.isOf(ModItems.MAP_BOOK) && ModItems.MAP_BOOK.hasMapBookId(map) && !ModItems.MAP_BOOK.hasMapBookId(item)) {
            resultInventory.setStack(2, map.copyWithCount(2));
        } else {
            resultInventory.removeStack(2);
        }

        //vanillas code will interpret the map book as a map and cause issues (like allowing map books to be cloned with empty maps)
        if (map.isOf(ModItems.MAP_BOOK)) {
            ci.cancel();
        }
    }

    //TODO: add to quickMove to allow shift clicking map books etc into the cartography table
}


