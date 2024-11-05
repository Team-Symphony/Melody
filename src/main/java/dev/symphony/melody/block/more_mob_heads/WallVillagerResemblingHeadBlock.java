package dev.symphony.melody.block.more_mob_heads;

import com.google.common.collect.Maps;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

import java.util.Map;

public class WallVillagerResemblingHeadBlock extends WallSkullBlock {

    private static final Map<Direction, VoxelShape> SHAPES = Maps.immutableEnumMap(Map.of(
        Direction.NORTH, Block.createCuboidShape(4, 4, 8, 12, 14, 16),
        Direction.SOUTH, Block.createCuboidShape(4, 4, 0, 12, 14,  8),
        Direction.EAST,  Block.createCuboidShape(0, 4, 4,  8, 14, 12),
        Direction.WEST,  Block.createCuboidShape(8, 4, 4, 16, 14, 12)
    ));

    public WallVillagerResemblingHeadBlock(SkullBlock.SkullType skullType, Settings settings) {
        super(skullType, settings);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPES.get(state.get(FACING));
    }

}
