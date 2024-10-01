package dev.symphony.melody.network;

import dev.symphony.melody.map_book.MapBookScreen;
import dev.symphony.melody.map_book.MapBookState;
import dev.symphony.melody.map_book.MapBookStateManager;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class ClientSyncHandler {
    @NotNull
    public static final ClientSyncHandler INSTANCE = new ClientSyncHandler();

    private ClientSyncHandler() {
    }

    public final void init() {
        ClientPlayNetworking.registerGlobalReceiver(MapBookOpenPayload.ID, ClientSyncHandler::mapBookOpen);
        ClientPlayNetworking.registerGlobalReceiver(MapBookSyncPayload.ID, ClientSyncHandler::mapBookSync);
    }

    private static final void mapBookOpen(MapBookOpenPayload payload, ClientPlayNetworking.Context context) {
        payload.decode();

        context.client().execute(() -> {
            context.client().setScreen(new MapBookScreen(payload.itemStack));
        });
    }

    private static final void mapBookSync(MapBookSyncPayload payload, ClientPlayNetworking.Context context) {
        payload.decode();

        if (payload.mapIDs.length > 0) {
            context.client().execute(() -> {
                MapBookStateManager var10000 = MapBookStateManager.INSTANCE;
                Integer var10001 = payload.bookID;
                var10000.putClientMapBookState(var10001, new MapBookState(payload.mapIDs));
            });
        }
    }
}