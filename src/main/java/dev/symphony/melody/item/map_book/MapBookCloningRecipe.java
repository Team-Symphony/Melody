package dev.symphony.melody.item.map_book;

import dev.symphony.melody.item.ModRecipes;
import dev.symphony.melody.item.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class MapBookCloningRecipe extends SpecialCraftingRecipe {
    public MapBookCloningRecipe(@Nullable CraftingRecipeCategory craftingRecipeCategory) {
        super(craftingRecipeCategory);
    }

    @Override
    public boolean matches(CraftingRecipeInput craftingRecipeInput, @NotNull World world) {
        return this.getResult(craftingRecipeInput) != null;
    }

    @Override @NotNull
    public ItemStack craft(CraftingRecipeInput craftingRecipeInput, RegistryWrapper.WrapperLookup wrapperLookup) {
        return Objects.requireNonNullElse(this.getResult(craftingRecipeInput), ItemStack.EMPTY);
    }

    private ItemStack getResult(CraftingRecipeInput craftingRecipeInput) {
        ItemStack filledMap = null;
        int emptyCount = 0;

        for (ItemStack itemStack : craftingRecipeInput.getStacks()) {
            if (!itemStack.isEmpty()) {
                if (!itemStack.isOf(ModItems.MAP_BOOK)) {
                    return null;
                }

                if (!((MapBookItem)itemStack.getItem()).hasMapBookId(itemStack)) {
                    emptyCount++;
                } else {
                    if (filledMap != null) {
                        return null;
                    }

                    filledMap = itemStack;
                }
            }
        }

        if (emptyCount == 0 || filledMap == null) {
            return null;
        } else {
            return filledMap.copyWithCount(emptyCount+1);
        }
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override @NotNull
    public RecipeSerializer<MapBookCloningRecipe> getSerializer() {
        return ModRecipes.MAP_BOOK_CLONING_SERIALIZER;
    }
}
