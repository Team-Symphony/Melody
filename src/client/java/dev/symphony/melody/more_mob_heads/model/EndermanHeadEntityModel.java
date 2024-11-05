package dev.symphony.melody.more_mob_heads.model;

import dev.symphony.melody.more_mob_heads.renderer.HeadOverlayFeatureRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class EndermanHeadEntityModel extends MelodySkullBlockEntityModel {

    private final ModelPart head;
    private static final RenderLayer EYES = RenderLayer.getEyes(Identifier.ofVanilla("textures/entity/enderman/enderman_eyes.png"));

    public EndermanHeadEntityModel(ModelPart root) {
        this.head = root.getChild(EntityModelPartNames.HEAD);
        this.addFeature(new HeadOverlayFeatureRenderer<>(this, EYES));
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData root = modelData.getRoot();

        ModelPartData head = root.addChild(
            EntityModelPartNames.HEAD,
            ModelPartBuilder.create()
                .uv(0, 0)
                .cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F),
            ModelTransform.NONE
        );

        head.addChild(
            EntityModelPartNames.HAT,
            ModelPartBuilder.create()
                .uv(0, 16)
                .cuboid(-4.0F, -7.5F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(-0.5F)),
            ModelTransform.NONE
        );

        return TexturedModelData.of(modelData, 64, 32);
    }

    @Override
    public void setHeadRotation(float animationProgress, float yaw, float pitch) {
        this.head.yaw = (float) Math.toRadians(yaw);
        this.head.pitch = (float) Math.toRadians(pitch);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        this.head.render(matrices, vertices, light, overlay, color);
    }

    @Override
    public RenderLayer getRenderLayer(Identifier texture) {
        return RenderLayer.getEntityTranslucent(texture);
    }

}
