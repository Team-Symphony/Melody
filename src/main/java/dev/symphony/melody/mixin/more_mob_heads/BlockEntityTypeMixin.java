package dev.symphony.melody.mixin.more_mob_heads;

import dev.symphony.melody.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.ArrayList;
import java.util.List;

@Mixin(BlockEntityType.class)
public class BlockEntityTypeMixin {

    @ModifyArg(method = "<clinit>", slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=skull")), at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/BlockEntityType$Builder;create(Lnet/minecraft/block/entity/BlockEntityType$BlockEntityFactory;[Lnet/minecraft/block/Block;)Lnet/minecraft/block/entity/BlockEntityType$Builder;", ordinal = 0), index = 1)
    private static Block[] addBlockEntityTypes(Block[] original) {
        List<Block> list = new ArrayList<>(List.of(original));
        list.addAll(ModBlocks.SKULLS);

        return list.toArray(Block[]::new);
    }

}
