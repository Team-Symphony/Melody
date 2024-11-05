package dev.symphony.melody.mixin.more_mob_heads;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.symphony.melody.util.MelodySkullType;
import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractSkullBlock.class)
public class AbstractSkullBlockMixin {

    @ModifyExpressionValue(method = "getTicker", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z", ordinal = 0))
    private boolean shouldTick(boolean original, @Local(argsOnly = true) BlockState state) {
        return original || state.getBlock() instanceof AbstractSkullBlock skullBlock && skullBlock.getSkullType() instanceof MelodySkullType skullType && skullType.shouldTick();
    }

}
