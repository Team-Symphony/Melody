package dev.symphony.melody.network;

import dev.symphony.melody.map_book.MapBookScreen;
import dev.symphony.melody.item.map_book.MapBookState;
import dev.symphony.melody.item.map_book.MapBookStateManager;
import dev.symphony.melody.item.map_book.MapStateAccessor;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.map.MapState;

public final class ClientSyncHandler {
    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(MapBookOpenPayload.PACKET_ID, ClientSyncHandler::mapBookOpen);
        ClientPlayNetworking.registerGlobalReceiver(MapBookSyncPayload.PACKET_ID, ClientSyncHandler::mapBookSync);
        ClientPlayNetworking.registerGlobalReceiver(MapPositionPayload.PACKET_ID, ClientSyncHandler::mapPosition);
    }

    private static void mapBookOpen(MapBookOpenPayload payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> context.client().setScreen(new MapBookScreen(payload.itemStack())));
    }

    private static void mapBookSync(MapBookSyncPayload payload, ClientPlayNetworking.Context context) {
        if (payload.mapIDs().length > 0) {
            context.client().execute(() -> MapBookStateManager.INSTANCE.putClientMapBookState(payload.bookID(), new MapBookState(payload.mapIDs())));
        }
    }

    private static void mapPosition(MapPositionPayload payload, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            ClientWorld world = context.client().world;
            MapState mapstate = world.getMapState(payload.mapIdComponent());
            if (mapstate != null) {
                ((MapStateAccessor)mapstate).melody$setPosition(payload.centerX(), payload.centerZ());
            }
        });
    }
}