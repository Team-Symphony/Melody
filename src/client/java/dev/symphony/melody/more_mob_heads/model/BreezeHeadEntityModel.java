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
public class BreezeHeadEntityModel extends MelodySkullBlockEntityModel {

    private final ModelPart head;
    private static final RenderLayer EYES = RenderLayer.getEntityTranslucentEmissiveNoOutline(Identifier.ofVanilla("textures/entity/breeze/breeze_eyes.png"));

    public BreezeHeadEntityModel(ModelPart root) {
        this.head = root.getChild(EntityModelPartNames.HEAD);
        this.addFeature(new HeadOverlayFeatureRenderer<>(this, EYES));
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData root = modelData.getRoot();

        ModelPartData head = root.addChild(
            EntityModelPartNames.HEAD,
            ModelPartBuilder.create()
                .uv(4, 24)
                .cuboid(-5.0F, -5.0F, -4.2F, 10.0F, 3.0F, 4.0F, new Dilation(0.0F))
                .uv(0, 0)
                .cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)),
            ModelTransform.NONE
        );

        head.addChild(
            EntityModelPartNames.EYES,
            ModelPartBuilder.create()
                .uv(4, 24)
                .cuboid(-5.0F, -5.0F, -4.2F, 10.0F, 3.0F, 4.0F, new Dilation(0.0F))
                .uv(0, 0)
                .cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)),
            ModelTransform.NONE
        );

        return TexturedModelData.of(modelData, 32, 32);
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
