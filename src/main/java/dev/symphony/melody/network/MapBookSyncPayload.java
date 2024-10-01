package dev.symphony.melody.network;

import dev.symphony.melody.map_book.MapBookItem;
import dev.symphony.melody.map_book.MapBookState;
import dev.symphony.melody.map_book.MapBookStateManager;
import io.netty.buffer.Unpooled;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class MapBookSyncPayload implements CustomPayload {
    public static Id<MapBookSyncPayload> ID = new Id<>(Identifier.of("melody", "map_book_sync"));

    public int bookID;
    public int[] mapIDs;

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public MapBookSyncPayload(ServerPlayerEntity player, ItemStack itemStack) {
        int bookId = ((MapBookItem)itemStack.getItem()).getMapBookId(itemStack);
        MapBookState mapBookState = MapBookStateManager.INSTANCE.getMapBookState(player.server, bookId);
        if (mapBookState == null) {
            this.bookID = -1;
        } else {
            this.bookID = bookId;
            this.mapIDds = mapBookState.getMapIDs().stream().mapToInt(i -> i).toArray();
        }
    }

    //TODO: MAKE PACKETS WORK
    public PacketByteBuf encode() {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        if (bookID == -1) {
            buf.writeVarInt(-1);
        } else {
            buf.writeVarInt(bookID);
            buf.writeIntArray(mapIDs);
        }

        return buf;
    }

    public void decode(PacketByteBuf buf) {
        bookID = buf.readVarInt();
        if (bookID != -1) {
            this.mapIDs = buf.readIntArray();
        }
    }
}
