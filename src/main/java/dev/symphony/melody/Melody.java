package dev.symphony.melody;

import dev.symphony.melody.network.MapBookOpenPayload;
import dev.symphony.melody.network.MapBookSyncPayload;
import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Melody implements ModInitializer {
	public static final String MOD_ID = "melody";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static Identifier id (String path) {
		return Identifier.of(Melody.MOD_ID, path);
	}

	@Override
	public void onInitialize() {
		//TODO: initialise midnight config (also add it)

		ItemRegistry.register();
		RecipeRegistry.register();
		MapBookOpenPayload.register();
		MapBookSyncPayload.register();
	}
}