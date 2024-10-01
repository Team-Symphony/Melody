package dev.symphony.melody.map_book;

import com.mojang.serialization.Codec;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;

import java.util.ArrayList;

public record MapBookAdditionsComponent(ArrayList<Integer> additions) {
    public static final MapBookAdditionsComponent DEFAULT = new MapBookAdditionsComponent(new ArrayList<>());

    //TODO: MAKE CODECS WORK
    public static final Codec<MapBookAdditionsComponent> CODEC = null;
    public static final PacketCodec<RegistryByteBuf, MapBookAdditionsComponent> PACKET_CODEC = null;

    public ArrayList<Integer> additions() {
        return this.additions;
    }
}
