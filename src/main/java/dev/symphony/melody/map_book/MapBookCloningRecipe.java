package dev.symphony.melody.map_book;

import dev.symphony.melody.ItemRegistry;
import dev.symphony.melody.RecipeRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class MapBookCloningRecipe extends SpecialCraftingRecipe {
    public MapBookCloningRecipe(@Nullable CraftingRecipeCategory craftingRecipeCategory) {
        super(craftingRecipeCategory);
    }

    public boolean matches(CraftingRecipeInput craftingRecipeInput, @NotNull World world) {
        return this.getResult(craftingRecipeInput) != null;
    }

    @NotNull
    public ItemStack craft(CraftingRecipeInput craftingRecipeInput, RegistryWrapper.WrapperLookup wrapperLookup) {
        ItemStack itemStack = this.getResult(craftingRecipeInput);
        if (itemStack != null && !itemStack.isEmpty()) {
            return itemStack.copy();
        } else {
            return ItemStack.EMPTY;
        }
    }

    private ItemStack getResult(CraftingRecipeInput craftingRecipeInput) {
        ItemStack filledMap = null;
        boolean emptyMap = false;

        for (ItemStack itemStack : craftingRecipeInput.getStacks()) {
            if (!itemStack.isEmpty()) {
                if (!itemStack.isOf(ItemRegistry.MapBook)) {
                    return null;
                }

                boolean isEmpty = ((MapBookItem)itemStack.getItem()).getMapBookId(itemStack) == -1;
                if (isEmpty) {
                    if (emptyMap) {
                        return null;
                    }

                    emptyMap = true;
                } else {
                    if (filledMap != null) {
                        return null;
                    }

                    filledMap = itemStack;
                }
            }
        }

        if (!emptyMap) {
            return null;
        } else {
            return filledMap;
        }
    }

    @Override
    public DefaultedList<ItemStack> getRemainder(CraftingRecipeInput craftingRecipeInput) {
        DefaultedList<ItemStack> result = DefaultedList.ofSize(craftingRecipeInput.getSize(), ItemStack.EMPTY);

        for(int i = 0; i < result.size(); i++) {
            ItemStack stack = craftingRecipeInput.getStacks().get(i);
            if (stack != null) {
                Item item = stack.getItem();
                if (item != null) {
                    if (item.getRecipeRemainder() != null) {
                        result.set(i, stack.getRecipeRemainder());
                    } else if (item instanceof MapBookItem) {
                        if (((MapBookItem)stack.getItem()).getMapBookId(stack) != -1) {
                            result.set(i, stack.copyWithCount(1));
                        }
                    }
                }
            }
        }

        return result;
    }

    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @NotNull
    public RecipeSerializer<MapBookCloningRecipe> getSerializer() {
        return RecipeRegistry.MAP_BOOK_CLONING_SERIALIZER;
    }
}
