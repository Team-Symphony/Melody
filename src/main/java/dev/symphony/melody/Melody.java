package dev.symphony.melody;

import dev.symphony.melody.config.MelodyConfigCondition;
import dev.symphony.melody.item.ModItemGroups;
import dev.symphony.melody.item.ModItems;
import dev.symphony.melody.item.ModRecipes;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import dev.symphony.melody.network.MapBookOpenPayload;
import dev.symphony.melody.network.MapPositionPayload;
import dev.symphony.melody.network.MapPositionRequestPayload;
import dev.symphony.melody.network.MapBookSyncPayload;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import eu.midnightdust.lib.config.MidnightConfig;
import dev.symphony.melody.config.MelodyConfig;

public class Melody implements ModInitializer {
	public static final String MOD_ID = "melody";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static Identifier id (String path) {
		return Identifier.of(Melody.MOD_ID, path);
	}

	@Override
	public void onInitialize() {
		// Config
		MidnightConfig.init(MOD_ID, MelodyConfig.class);

		MelodyConfigCondition.init();
		ResourceConditionType<MelodyConfigCondition> conditionType = ResourceConditionType.create(Identifier.of(Melody.MOD_ID, "config"), MelodyConfigCondition.CODEC);
		ResourceConditions.register(conditionType);

		// gay stuff (registry)
		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();

		ModRecipes.register();

		MapBookOpenPayload.register();
		MapBookSyncPayload.register();

		MapPositionPayload.register();
		MapPositionRequestPayload.register();
	}
}