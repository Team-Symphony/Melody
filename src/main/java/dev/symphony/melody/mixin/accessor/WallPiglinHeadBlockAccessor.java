package dev.symphony.melody.mixin.accessor;

import net.minecraft.block.WallPiglinHeadBlock;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(WallPiglinHeadBlock.class)
public interface WallPiglinHeadBlockAccessor {

    @Accessor("SHAPES")
    static Map<Direction, VoxelShape> getShapes() {
        throw new AssertionError();
    }

}
