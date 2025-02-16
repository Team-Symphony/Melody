package dev.symphony.melody.explosion;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.inventory.Inventory;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;

import java.util.Optional;

public class CreeperExplosionBehavior extends ExplosionBehavior {

    private final float entityDamageMultiplier;
    private final Optional<RegistryEntryList<Block>> immuneBlocks;

    public CreeperExplosionBehavior(float entityDamageMultiplier, Optional<RegistryEntryList<Block>> immuneBlocks) {
        this.entityDamageMultiplier = entityDamageMultiplier;
        this.immuneBlocks = immuneBlocks;
    }

    public CreeperExplosionBehavior(float entityDamageMultiplier) {
        this(entityDamageMultiplier, Optional.empty());
    }

    @Override
    public Optional<Float> getBlastResistance(Explosion explosion, BlockView world, BlockPos pos, BlockState blockState, FluidState fluidState) {
        Optional<Float> bedrockBlastResistance = Optional.of(Blocks.BEDROCK.getBlastResistance());

        if (isContainer(blockState, pos))
            return bedrockBlastResistance;

        return this.immuneBlocks.<Optional<Float>>map(registryEntries ->
            blockState.isIn(registryEntries) ? bedrockBlastResistance : Optional.empty()
        ).orElseGet(
            () -> super.getBlastResistance(explosion, world, pos, blockState, fluidState)
        );
    }

    private static boolean isContainer(BlockState state, BlockPos pos) {
        return state.getBlock() instanceof BlockWithEntity blockWithEntity && blockWithEntity.createBlockEntity(pos, state) instanceof Inventory;
    }

    @Override
    public float calculateDamage(Explosion explosion, Entity entity) {
        Vec3d pos = explosion.getPosition();
        double power = explosion.getPower() * this.entityDamageMultiplier * 2.0F;

        double d = Math.sqrt(entity.squaredDistanceTo(pos)) / power;
        double exposure = (1.0 - d) * (double) Explosion.getExposure(pos, entity);

        return (float) ((exposure * exposure + exposure) * 3.5 * power + 1.0);
    }

}
