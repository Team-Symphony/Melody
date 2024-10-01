package dev.symphony.melody;

import dev.symphony.melody.map_book.MapBookAdditionRecipe;
import dev.symphony.melody.map_book.MapBookCloningRecipe;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class RecipeRegistry {
    public static final SpecialRecipeSerializer<MapBookAdditionRecipe> MAP_BOOK_ADDITION_SERIALIZER = new SpecialRecipeSerializer<>(RecipeRegistry::MAP_BOOK_ADDITION_SERIALIZER$lambda$0);
    public static final SpecialRecipeSerializer<MapBookCloningRecipe> MAP_BOOK_CLONING_SERIALIZER = new SpecialRecipeSerializer<>(RecipeRegistry::MAP_BOOK_CLONING_SERIALIZER$lambda$1);

    public void register() {
        Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of("melody", "map_book_addition"), MAP_BOOK_ADDITION_SERIALIZER);
        Registry.register(Registries.RECIPE_SERIALIZER, Identifier.of("melody", "map_book_cloning"), MAP_BOOK_CLONING_SERIALIZER);
    }

    private static MapBookAdditionRecipe MAP_BOOK_ADDITION_SERIALIZER$lambda$0(CraftingRecipeCategory craftingRecipeCategory) {
        return new MapBookAdditionRecipe(craftingRecipeCategory);
    }

    private static MapBookCloningRecipe MAP_BOOK_CLONING_SERIALIZER$lambda$1(CraftingRecipeCategory craftingRecipeCategory) {
        return new MapBookCloningRecipe(craftingRecipeCategory);
    }
}