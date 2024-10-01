package dev.symphony.melody.network;

import dev.symphony.melody.map_book.MapBookItem;
import dev.symphony.melody.map_book.MapBookState;
import dev.symphony.melody.map_book.MapBookStateManager;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;

public final class SyncHandler {
    @NotNull
    public static final SyncHandler INSTANCE = new SyncHandler();

    private SyncHandler() {
    }

    public final void onOpenMapBook(@NotNull ServerPlayerEntity player, @NotNull ItemStack itemStack) {
        ServerPlayNetworking.send(player, new MapBookOpenPayload(player, itemStack));
    }

    public final void mapBookSync(@NotNull ServerPlayerEntity player, @NotNull ItemStack itemStack) {
        ServerPlayNetworking.send(player, new MapBookSyncPayload(player, itemStack));
    }
}