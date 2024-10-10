package dev.symphony.melody.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.encoding.VarInts;

import java.util.ArrayList;
import java.util.List;

public class IntArray {
    //why does this not exist? surely it exists somewhere
    public static PacketCodec<ByteBuf, int[]> ARRAY_CODEC = new PacketCodec<>() {
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

    public static PacketCodec<ByteBuf, List<Integer>> LIST_CODEC = new PacketCodec<>() {
        public List<Integer> decode(ByteBuf byteBuf) {
            int length = VarInts.read(byteBuf);

            List<Integer> list = new ArrayList<>(length);
            for(int j = 0; j < length; j++) {
                list.add(VarInts.read(byteBuf));
            }
            return list;
        }

        public void encode(ByteBuf byteBuf, List<Integer> list) {
            VarInts.write(byteBuf, list.size());

            for (int i : list) {
                VarInts.write(byteBuf, i);
            }
        }
    };
}