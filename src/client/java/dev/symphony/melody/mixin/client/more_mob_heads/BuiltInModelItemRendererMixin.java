package dev.symphony.melody.mixin.client.more_mob_heads;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import dev.symphony.melody.mixin.client.accessor.SkullBlockEntityRendererAccessor;
import dev.symphony.melody.more_mob_heads.model.MelodySkullBlockEntityModel;
import net.minecraft.block.SkullBlock;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.SkullBlockEntityModel;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.component.type.ProfileComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BuiltinModelItemRenderer.class)
public class BuiltInModelItemRendererMixin {

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/block/entity/SkullBlockEntityRenderer;getRenderLayer(Lnet/minecraft/block/SkullBlock$SkullType;Lnet/minecraft/component/type/ProfileComponent;)Lnet/minecraft/client/render/RenderLayer;"))
    private RenderLayer modifyItemRenderLayer(SkullBlock.SkullType type, ProfileComponent profile, Operation<RenderLayer> original, @Local SkullBlockEntityModel skullBlockEntityModel) {
        if (!(skullBlockEntityModel instanceof MelodySkullBlockEntityModel featuredModel))
            return original.call(type, profile);

        return featuredModel.getRenderLayer(SkullBlockEntityRendererAccessor.getTextures().get(type));
    }

}
