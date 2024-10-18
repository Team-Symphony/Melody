package dev.symphony.melody;

import dev.symphony.melody.item.ModItems;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.util.Identifier;

public class ModModelPredicates {
    static {
        ModelPredicateProviderRegistry.register(ModItems.MAP_BOOK, Identifier.ofVanilla("empty"), (stack, world, entity, seed) -> (stack.contains(DataComponentTypes.MAP_ID) || stack.contains(ModItems.MAP_BOOK_ADDITIONS)) ? 0 : 1);
    }

    public static void registerModelPredicates() {
        Melody.LOGGER.info("Registering Model Predicates for: " + Melody.MOD_ID);
    }
}
