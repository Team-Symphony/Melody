package dev.symphony.melody.item.map_book;

import dev.symphony.melody.item.ModRecipes;
import dev.symphony.melody.item.ModItems;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.MapIdComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public final class MapBookAdditionRecipe extends SpecialCraftingRecipe {
    public MapBookAdditionRecipe(CraftingRecipeCategory craftingRecipeCategory) {
        super(craftingRecipeCategory);
    }

    @Override
    public boolean matches(CraftingRecipeInput craftingRecipeInput, @NotNull World world) {
        return this.getResult(craftingRecipeInput, world) != null;
    }

    @Override @NotNull
    public ItemStack craft(CraftingRecipeInput craftingRecipeInput, RegistryWrapper.WrapperLookup wrapperLookup) {
        AdditionResult result = this.getResult(craftingRecipeInput, null);
        if (result != null) {
            ItemStack item = result.mapBook().copyWithCount(1);
            ((MapBookItem)item.getItem()).setAdditions(item, result.maps());
            return item;
        } else {
            return ItemStack.EMPTY;
        }
    }

    private AdditionResult getResult(CraftingRecipeInput craftingRecipeInput, World world) {
        ItemStack mapBook = null;
        ArrayList<Integer> maps = new ArrayList<>();

        for (ItemStack itemStack : craftingRecipeInput.getStacks()) {
            if (itemStack.isEmpty()) continue;

            // due to applying the additions it gets confused when theres more than one item in the grid and ends up duplicating things
            if (itemStack.getCount() > 1) return null;

            if (!itemStack.isOf(ModItems.MAP_BOOK)) {
                if (!itemStack.isOf(Items.FILLED_MAP)) {
                    return null;
                }

                MapIdComponent mapId = itemStack.get(DataComponentTypes.MAP_ID);
                if (mapId == null) {
                    return null;
                }
                maps.add(mapId.id());
            } else {
                if (mapBook != null) {
                    return null;
                }

                mapBook = itemStack;
            }
        }
        if (mapBook == null || maps.isEmpty()) {
            return null;
        }

        if (world != null && ModItems.MAP_BOOK.hasInvalidAdditions(mapBook, world, maps)) {
            return null;
        }

        return new AdditionResult(mapBook, maps);
    }

    @Override @NotNull
    public RecipeSerializer<MapBookAdditionRecipe> getSerializer() {
        return ModRecipes.MAP_BOOK_ADDITION_SERIALIZER;
    }

    private record AdditionResult(@NotNull ItemStack mapBook, @NotNull ArrayList<Integer> maps) {
    }
}
