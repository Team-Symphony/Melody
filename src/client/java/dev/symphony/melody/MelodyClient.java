package dev.symphony.melody;

import dev.symphony.melody.map_book.MapBookFilledProperty;
import dev.symphony.melody.network.ClientSyncHandler;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.item.property.bool.BooleanProperties;

public class MelodyClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientSyncHandler.init();

		BooleanProperties.ID_MAPPER.put(Melody.id("map_book/filled"), MapBookFilledProperty.CODEC);
	}
}