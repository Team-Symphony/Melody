package dev.symphony.melody.item;

import dev.symphony.melody.Melody;
import dev.symphony.melody.item.map_book.MapBookAdditionRecipe;
import dev.symphony.melody.item.map_book.MapBookCloningRecipe;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public final class ModRecipes {
    public static final SpecialCraftingRecipe.SpecialRecipeSerializer<MapBookAdditionRecipe> MAP_BOOK_ADDITION_SERIALIZER = new SpecialCraftingRecipe.SpecialRecipeSerializer<>(MapBookAdditionRecipe::new);
    public static final SpecialCraftingRecipe.SpecialRecipeSerializer<MapBookCloningRecipe> MAP_BOOK_CLONING_SERIALIZER = new SpecialCraftingRecipe.SpecialRecipeSerializer<>(MapBookCloningRecipe::new);

    public static void registerRecipes() {
        Melody.LOGGER.info("Registering Recipes for: " + Melody.MOD_ID);

        Registry.register(Registries.RECIPE_SERIALIZER, Melody.id("map_book_addition"), MAP_BOOK_ADDITION_SERIALIZER);
        Registry.register(Registries.RECIPE_SERIALIZER, Melody.id("map_book_cloning"), MAP_BOOK_CLONING_SERIALIZER);
    }
}