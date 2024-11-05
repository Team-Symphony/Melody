package dev.symphony.melody.mixin.client.more_mob_heads;

import com.google.common.collect.ImmutableMap;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import dev.symphony.melody.ModEntityModelLayers;
import dev.symphony.melody.more_mob_heads.model.*;
import dev.symphony.melody.more_mob_heads.renderer.HeadFeatureRenderer;
import dev.symphony.melody.util.MelodySkullType;
import net.minecraft.block.SkullBlock;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.SkullBlockEntityModel;
import net.minecraft.client.render.block.entity.SkullBlockEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.PiglinHeadEntityModel;
import net.minecraft.client.render.entity.model.SkullEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

@Mixin(SkullBlockEntityRenderer.class)
public abstract class SkullBlockEntityRendererMixin {

    @Shadow @Final private static Map<SkullBlock.SkullType, Identifier> TEXTURES;

    @Unique private static final RenderLayer BOGGED_OVERLAY = RenderLayer.getEntityCutoutNoCull(Identifier.ofVanilla("textures/entity/skeleton/bogged_overlay.png"));
    @Unique private static final RenderLayer DROWNED_OVERLAY = RenderLayer.getEntityCutoutNoCull(Identifier.ofVanilla("textures/entity/zombie/drowned_outer_layer.png"));
    @Unique private static final RenderLayer STRAY_OVERLAY = RenderLayer.getEntityCutoutNoCull(Identifier.ofVanilla("textures/entity/skeleton/stray_overlay.png"));

    @Inject(method = "method_3580", at = @At(value = "INVOKE", target = "Ljava/util/HashMap;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", ordinal = 0))
    private static void addTextureIds(HashMap<SkullBlock.SkullType, Identifier> map, CallbackInfo ci) {
        map.put(MelodySkullType.BLAZE, Identifier.ofVanilla("textures/entity/blaze.png"));
        map.put(MelodySkullType.BOGGED, Identifier.ofVanilla("textures/entity/skeleton/bogged.png"));
        map.put(MelodySkullType.BREEZE, Identifier.ofVanilla("textures/entity/breeze/breeze.png"));
        map.put(MelodySkullType.DROWNED, Identifier.ofVanilla("textures/entity/zombie/drowned.png"));
        map.put(MelodySkullType.ENDERMAN, Identifier.ofVanilla("textures/entity/enderman/enderman.png"));
        map.put(MelodySkullType.EVOKER, Identifier.ofVanilla("textures/entity/illager/evoker.png"));
        map.put(MelodySkullType.HOGLIN, Identifier.ofVanilla("textures/entity/hoglin/hoglin.png"));
        map.put(MelodySkullType.HUSK, Identifier.ofVanilla("textures/entity/zombie/husk.png"));
        map.put(MelodySkullType.PIGLIN_BRUTE, Identifier.ofVanilla("textures/entity/piglin/piglin_brute.png"));
        map.put(MelodySkullType.PILLAGER, Identifier.ofVanilla("textures/entity/illager/pillager.png"));
        map.put(MelodySkullType.STRAY, Identifier.ofVanilla("textures/entity/skeleton/stray.png"));
        map.put(MelodySkullType.VINDICATOR, Identifier.ofVanilla("textures/entity/illager/vindicator.png"));
        map.put(MelodySkullType.WITCH, Identifier.ofVanilla("textures/entity/witch.png"));
        map.put(MelodySkullType.ZOGLIN, Identifier.ofVanilla("textures/entity/hoglin/zoglin.png"));
        map.put(MelodySkullType.ZOMBIE_VILLAGER, Identifier.ofVanilla("textures/entity/zombie_villager/zombie_villager.png"));
        map.put(MelodySkullType.ZOMBIFIED_PIGLIN, Identifier.ofVanilla("textures/entity/piglin/zombified_piglin.png"));
    }

    @Inject(method = "getModels", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableMap$Builder;put(Ljava/lang/Object;Ljava/lang/Object;)Lcom/google/common/collect/ImmutableMap$Builder;", ordinal = 0))
    private static void addModels(EntityModelLoader modelLoader, CallbackInfoReturnable<Map<SkullBlock.SkullType, SkullBlockEntityModel>> cir, @Local ImmutableMap.Builder<SkullBlock.SkullType, SkullBlockEntityModel> builder) {
        builder.put(MelodySkullType.BLAZE, new SkullEntityModel(modelLoader.getModelPart(ModEntityModelLayers.BLAZE_HEAD)));
        builder.put(MelodySkullType.BOGGED, new OverlaySkullEntityModel(modelLoader.getModelPart(ModEntityModelLayers.BOGGED_SKULL), BOGGED_OVERLAY));
        builder.put(MelodySkullType.BREEZE, new BreezeHeadEntityModel(modelLoader.getModelPart(ModEntityModelLayers.BREEZE_HEAD)));
        builder.put(MelodySkullType.DROWNED, new OverlaySkullEntityModel(modelLoader.getModelPart(ModEntityModelLayers.DROWNED_HEAD), DROWNED_OVERLAY));
        builder.put(MelodySkullType.ENDERMAN, new EndermanHeadEntityModel(modelLoader.getModelPart(ModEntityModelLayers.ENDERMAN_HEAD)));
        builder.put(MelodySkullType.EVOKER, new VillagerResemblingHeadEntityModel(modelLoader.getModelPart(ModEntityModelLayers.EVOKER_HEAD)));
        builder.put(MelodySkullType.HOGLIN, new HoglinHeadEntityModel(modelLoader.getModelPart(ModEntityModelLayers.HOGLIN_HEAD)));
        builder.put(MelodySkullType.HUSK, new SkullEntityModel(modelLoader.getModelPart(ModEntityModelLayers.HUSK_HEAD)));
        builder.put(MelodySkullType.PIGLIN_BRUTE, new PiglinHeadEntityModel(modelLoader.getModelPart(ModEntityModelLayers.PIGLIN_BRUTE_HEAD)));
        builder.put(MelodySkullType.PILLAGER, new VillagerResemblingHeadEntityModel(modelLoader.getModelPart(ModEntityModelLayers.PILLAGER_HEAD)));
        builder.put(MelodySkullType.STRAY, new OverlaySkullEntityModel(modelLoader.getModelPart(ModEntityModelLayers.STRAY_SKULL), STRAY_OVERLAY));
        builder.put(MelodySkullType.VINDICATOR, new VillagerResemblingHeadEntityModel(modelLoader.getModelPart(ModEntityModelLayers.VINDICATOR_HEAD)));
        builder.put(MelodySkullType.WITCH, new WitchHeadEntityModel(modelLoader.getModelPart(ModEntityModelLayers.WITCH_HEAD)));
        builder.put(MelodySkullType.ZOGLIN, new HoglinHeadEntityModel(modelLoader.getModelPart(ModEntityModelLayers.ZOGLIN_HEAD)));
        builder.put(MelodySkullType.ZOMBIE_VILLAGER, new VillagerResemblingHeadEntityModel(modelLoader.getModelPart(ModEntityModelLayers.ZOMBIE_VILLAGER_HEAD)));
        builder.put(MelodySkullType.ZOMBIFIED_PIGLIN, new PiglinHeadEntityModel(modelLoader.getModelPart(ModEntityModelLayers.ZOMBIFIED_PIGLIN_HEAD)));
    }

    @WrapOperation(method = "renderSkull", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;translate(FFF)V", ordinal = 1))
    private static void applyCustomTransformations(MatrixStack instance, float x, float y, float z, Operation<Void> original, @Local(argsOnly = true) @Nullable Direction direction, @Local(argsOnly = true, ordinal = 0) float yaw, @Local(argsOnly = true, ordinal = 1) float animationProgress, @Local(argsOnly = true) SkullBlockEntityModel model) {
        if (!(model instanceof MelodySkullBlockEntityModel featuredModel)) {
            original.call(instance, x, y, z);
            return;
        }

        if (direction != null)
            featuredModel.transformWall(instance, x, y, z, direction, yaw, animationProgress);
    }

    @Inject(method = "renderSkull", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;pop()V"))
    private static void renderFeatures(Direction direction, float yaw, float animationProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, SkullBlockEntityModel model, RenderLayer renderLayer, CallbackInfo ci) {
        if (!(model instanceof MelodySkullBlockEntityModel featuredModel))
            return;

        for (HeadFeatureRenderer<?> feature : featuredModel.getFeatures())
            feature.render(matrices, vertexConsumers, light, animationProgress);
    }

    @ModifyArg(method = "render(Lnet/minecraft/block/entity/SkullBlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/block/entity/SkullBlockEntityRenderer;renderSkull(Lnet/minecraft/util/math/Direction;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/block/entity/SkullBlockEntityModel;Lnet/minecraft/client/render/RenderLayer;)V"))
    private RenderLayer modifyBlockRenderLayer(RenderLayer original, @Local SkullBlock.SkullType skullType, @Local SkullBlockEntityModel skullBlockEntityModel) {
        if (!(skullBlockEntityModel instanceof MelodySkullBlockEntityModel featuredModel))
            return original;

        return featuredModel.getRenderLayer(TEXTURES.get(skullType));
    }

}
