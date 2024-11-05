package dev.symphony.melody.more_mob_heads.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.entity.SkullBlockEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class VillagerResemblingHeadEntityModel extends SkullBlockEntityModel {

    private final ModelPart head;

    public VillagerResemblingHeadEntityModel(ModelPart root) {
        this.head = root.getChild(EntityModelPartNames.HEAD);
    }

    public static ModelData getModelData() {
        ModelData modelData = new ModelData();
        ModelPartData root = modelData.getRoot();

        ModelPartData head = root.addChild(
            EntityModelPartNames.HEAD,
            ModelPartBuilder.create()
                .uv(0, 0)
                .cuboid(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F),
            ModelTransform.NONE
        );

        head.addChild(
            EntityModelPartNames.HAT,
            ModelPartBuilder.create()
                .uv(32, 0)
                .cuboid(-4.0F, -10.0F, -4.0F, 8.0F, 12.0F, 8.0F, new Dilation(0.45F)),
            ModelTransform.NONE
        );

        head.addChild(
            EntityModelPartNames.NOSE,
            ModelPartBuilder.create()
                .uv(24, 0)
                .cuboid(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F),
            ModelTransform.pivot(0.0F, -2.0F, 0.0F)
        );

        return modelData;
    }

    public static TexturedModelData getTexturedModelData() {
        return TexturedModelData.of(getModelData(), 64, 64);
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
