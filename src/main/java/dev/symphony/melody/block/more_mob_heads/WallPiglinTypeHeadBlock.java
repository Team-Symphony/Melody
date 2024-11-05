package dev.symphony.melody.block.more_mob_heads;

import dev.symphony.melody.mixin.accessor.WallPiglinHeadBlockAccessor;
import dev.symphony.melody.util.MelodySkullType;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

/**
 * An alternative implementation of {@link WallPiglinHeadBlock} that allows one to supply a custom {@link net.minecraft.block.SkullBlock.SkullType} instance instead of always using {@link SkullBlock.Type#PIGLIN}.
 * @implNote Standing piglin head variants are determined using a mixin into {@link SkullBlock} and a call to {@link MelodySkullType#isPiglin()}.
 */
public class WallPiglinTypeHeadBlock extends WallSkullBlock {

    public WallPiglinTypeHeadBlock(SkullBlock.SkullType skullType, Settings settings) {
        super(skullType, settings);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return WallPiglinHeadBlockAccessor.getShapes().get(state.get(FACING));
    }

}
