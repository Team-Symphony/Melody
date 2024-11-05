package dev.symphony.melody.block.more_mob_heads;

import com.google.common.collect.Maps;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

import java.util.Map;

public class WallHoglinHeadBlock extends WallSkullBlock {

    private static final Map<Direction, VoxelShape> SHAPES = Maps.immutableEnumMap(Map.of(
        Direction.NORTH, Block.createCuboidShape(2, 0, 11, 14, 16, 16),
        Direction.SOUTH, Block.createCuboidShape(2, 0, 0, 14, 16, 5),
        Direction.EAST,  Block.createCuboidShape(0, 0, 2, 5, 16, 14),
        Direction.WEST,  Block.createCuboidShape(11, 0, 2, 16, 16, 14)
    ));

    public WallHoglinHeadBlock(SkullBlock.SkullType skullType, Settings settings) {
        super(skullType, settings);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPES.get(state.get(FACING));
    }

}
