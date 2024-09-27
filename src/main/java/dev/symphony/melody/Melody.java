package dev.symphony.melody;

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

	}
}