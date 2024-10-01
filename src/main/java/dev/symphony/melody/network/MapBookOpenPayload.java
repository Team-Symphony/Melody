package dev.symphony.melody.network;

import io.netty.buffer.Unpooled;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class MapBookOpenPayload implements CustomPayload {
    public static Id<MapBookOpenPayload> ID = new Id<>(Identifier.of("melody", "map_book_open"));

    public ItemStack itemStack;

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public MapBookOpenPayload(ServerPlayerEntity player, ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    //TODO: MAKE PACKETS WORK (ItemStack.PACKET_CODEC exists and should make it easy)
    public PacketByteBuf encode() {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeItemStack(itemStack);
        return buf;
    }

    public void decode(PacketByteBuf buf) {
        itemStack = buf.readItemStack();
    }
}
