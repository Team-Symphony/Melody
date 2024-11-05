package dev.symphony.melody.block.more_mob_heads;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.SkullBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class HoglinHeadBlock extends SkullBlock {

    private static final VoxelShape SHAPE = Block.createCuboidShape(0, 0, 0, 16, 5, 16);

    public HoglinHeadBlock(SkullType skullType, Settings settings) {
        super(skullType, settings);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

}
