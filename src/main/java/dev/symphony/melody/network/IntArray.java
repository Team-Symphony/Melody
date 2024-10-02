package dev.symphony.melody.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.encoding.VarInts;

public class IntArray {
    //why does this not exist? surely it exists somewhere
    static PacketCodec<ByteBuf, int[]> CODEC = new PacketCodec<>() {
        public int[] decode(ByteBuf byteBuf) {
            int length = VarInts.read(byteBuf);

            int[] array = new int[length];
            for(int j = 0; j < length; j++) {
                array[j] = VarInts.read(byteBuf);
            }
            return array;
        }

        public void encode(ByteBuf byteBuf, int[] array) {
            VarInts.write(byteBuf, array.length);

            for (int i : array) {
                VarInts.write(byteBuf, i);
            }
        }
    };
}
