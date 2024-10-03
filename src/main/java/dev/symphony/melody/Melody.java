package dev.symphony.melody;

import dev.symphony.melody.network.MapBookOpenPayload;
import dev.symphony.melody.network.MapPositionPayload;
import dev.symphony.melody.network.MapPositionRequestPayload;
import dev.symphony.melody.network.MapBookSyncPayload;
import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;
import dev.symphony.melody.config.MelodyConfig;

public class Melody implements ModInitializer {
	public static final String MOD_ID = "melody";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static Identifier id (String path) {
		return Identifier.of(Melody.MOD_ID, path);
	}

	@Override
	public void onInitialize() {
		//Config
		MidnightConfig.init(MOD_ID, MelodyConfig.class);

        //Registry
		ItemRegistry.register();
		RecipeRegistry.register();

		MapBookOpenPayload.register();
		MapBookSyncPayload.register();

		MapPositionPayload.register();
		MapPositionRequestPayload.register();
	}
}