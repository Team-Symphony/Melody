package dev.symphony.melody.item;

import dev.symphony.melody.Melody;
import dev.symphony.melody.item.map_book.MapBookAdditionRecipe;
import dev.symphony.melody.item.map_book.MapBookCloningRecipe;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class ModRecipes {
    public static final SpecialRecipeSerializer<MapBookAdditionRecipe> MAP_BOOK_ADDITION_SERIALIZER = new SpecialRecipeSerializer<>(MapBookAdditionRecipe::new);
    public static final SpecialRecipeSerializer<MapBookCloningRecipe> MAP_BOOK_CLONING_SERIALIZER = new SpecialRecipeSerializer<>(MapBookCloningRecipe::new);

    public static void registerRecipes() {
        Melody.LOGGER.info("Registering Recipes for: " + Melody.MOD_ID);

        Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of("melody", "map_book_addition"), MAP_BOOK_ADDITION_SERIALIZER);
        Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of("melody", "map_book_cloning"), MAP_BOOK_CLONING_SERIALIZER);
    }
}