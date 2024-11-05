package dev.symphony.melody;

import dev.symphony.melody.network.ClientSyncHandler;
import net.fabricmc.api.ClientModInitializer;

public class MelodyClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientSyncHandler.init();

		ModModelPredicates.registerModelPredicates();
		ModEntityModelLayers.registerEntityModelLayers();
	}
}