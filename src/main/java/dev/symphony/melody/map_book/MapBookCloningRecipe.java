package dev.symphony.melody.map_book;

import dev.symphony.melody.ItemRegistry;
import dev.symphony.melody.RecipeRegistry;
import net.minecraft.inventory.RecipeInputInventory;
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

import java.util.Collection;
import java.util.Iterator;

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
        ItemStack var4;
        if (itemStack != null && !itemStack.isEmpty()) {
            var4 = itemStack.copy();
            return var4;
        } else {
            var4 = ItemStack.EMPTY;
            return var4;
        }
    }

    private final ItemStack getResult(CraftingRecipeInput craftingRecipeInput) {
        ItemStack filledMap = null;
        boolean emptyMap = false;
        Iterator var4 = craftingRecipeInput.getStacks().iterator();

        while(var4.hasNext()) {
            ItemStack itemStack = (ItemStack)var4.next();
            if (!itemStack.isEmpty()) {
                if (!itemStack.isOf(ItemRegistry.MapBook)) {
                    return null;
                }

                Item var7 = itemStack.getItem();
                MapBookItem var10000 = (MapBookItem)var7;
                boolean isEmpty = var10000.getMapBookId(itemStack) == null;
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

    @NotNull
    public DefaultedList getRemainder(@Nullable RecipeInputInventory inventory) {
        DefaultedList var3 = DefaultedList.ofSize(inventory.size(), ItemStack.EMPTY);
        DefaultedList result = var3;
        int i = 0;

        for(int var4 = ((Collection)result).size(); i < var4; ++i) {
            ItemStack stack = inventory.getStack(i);
            if (stack != null) {
                Item item = stack.getItem();
                if (item != null) {
                    if (item.getRecipeRemainder() != null) {
                        result.set(i, stack.getRecipeRemainder());
                    } else if (item instanceof MapBookItem) {
                        Item var7 = stack.getItem();
                        if (((MapBookItem)var7).getMapBookId(stack) != null) {
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
