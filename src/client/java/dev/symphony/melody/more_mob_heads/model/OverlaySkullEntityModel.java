package dev.symphony.melody.more_mob_heads.model;

import dev.symphony.melody.more_mob_heads.renderer.HeadOverlayFeatureRenderer;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;

public class OverlaySkullEntityModel extends MelodySkullBlockEntityModel {

    private final ModelPart head;

    public OverlaySkullEntityModel(ModelPart root, RenderLayer overlay) {
        this.head = root.getChild(EntityModelPartNames.HEAD);
        this.addFeature(new HeadOverlayFeatureRenderer<>(this, overlay, 1 + 0.5F / 16.0F));
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

}
