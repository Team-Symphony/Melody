package dev.symphony.melody.mixin.more_mob_heads;

import dev.symphony.melody.util.MelodySkullType;
import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SkullBlock.class)
public abstract class SkullBlockMixin extends AbstractSkullBlock {

    @Shadow @Final protected static VoxelShape PIGLIN_SHAPE;

    public SkullBlockMixin(SkullBlock.SkullType type, Settings settings) {
        super(type, settings);
    }

    @Inject(method = "getOutlineShape", at = @At("HEAD"), cancellable = true)
    private void melody$getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if (this.getSkullType() instanceof MelodySkullType skullType && skullType.isPiglin())
            cir.setReturnValue(PIGLIN_SHAPE);
    }

}
