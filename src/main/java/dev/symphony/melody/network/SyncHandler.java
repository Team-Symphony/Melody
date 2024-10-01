package dev.symphony.melody.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;

public final class SyncHandler {
    @NotNull
    public static final SyncHandler INSTANCE = new SyncHandler();

    private SyncHandler() {
    }

    public void onOpenMapBook(@NotNull ServerPlayerEntity player, @NotNull ItemStack itemStack) {
        ServerPlayNetworking.send(player, new MapBookOpenPayload(player, itemStack));
    }

    public void mapBookSync(@NotNull ServerPlayerEntity player, @NotNull ItemStack itemStack) {
        ServerPlayNetworking.send(player, new MapBookSyncPayload(player, itemStack));
    }
}