package dev.symphony.melody.more_mob_heads.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

@Environment(EnvType.CLIENT)
public class HoglinHeadEntityModel extends MelodySkullBlockEntityModel {

    private final ModelPart head;
    private final ModelPart rightEar;
    private final ModelPart leftEar;

    private static final float SCALE_FACTOR = 0.84210526315F;

    public HoglinHeadEntityModel(ModelPart root) {
        this.head = root.getChild(EntityModelPartNames.HEAD);
        this.rightEar = this.head.getChild(EntityModelPartNames.RIGHT_EAR);
        this.leftEar = this.head.getChild(EntityModelPartNames.LEFT_EAR);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData root = modelData.getRoot();

        ModelPartData head = root.addChild(
            EntityModelPartNames.HEAD,
            ModelPartBuilder.create()
                .uv(61, 1)
                .cuboid(-7.0F, -6.0F, -9.5F, 14.0F, 6.0F, 19.0F),
            ModelTransform.NONE
        );

        head.addChild(
            EntityModelPartNames.RIGHT_EAR,
            ModelPartBuilder.create()
                .uv(1, 1)
                .cuboid(-6.0F, -1.0F, 7.5F, 6.0F, 1.0F, 4.0F),
            ModelTransform.of(-6.0F, -5.0F, -3.0F, 0.0F, 0.0F, MathHelper.PI * 1.5F)
        );

        head.addChild(
            EntityModelPartNames.LEFT_EAR,
            ModelPartBuilder.create()
                .uv(1, 6)
                .cuboid(0.0F, -1.0F, 7.5F, 6.0F, 1.0F, 4.0F),
            ModelTransform.of(6.0F, -5.0F, -3.0F, 0.0F, 0.0F, -MathHelper.PI * 1.5F)
        );

        head.addChild(
            EntityModelPartNames.RIGHT_HORN,
            ModelPartBuilder.create()
                .uv(10, 13)
                .cuboid(-1.0F, -14.0F, 8.5F, 2.0F, 11.0F, 2.0F),
            ModelTransform.pivot(-7.0F, 2.0F, -12.0F)
        );

        head.addChild(
            EntityModelPartNames.LEFT_HORN,
            ModelPartBuilder.create()
                .uv(1, 13)
                .cuboid(-1.0F, -14.0F, 8.5F, 2.0F, 11.0F, 2.0F),
            ModelTransform.pivot(7.0F, 2.0F, -12.0F)
        );

        return TexturedModelData.of(modelData, 128, 64);
    }

    @Override
    public void setHeadRotation(float animationProgress, float yaw, float pitch) {
        this.head.yaw = (float) Math.toRadians(yaw);
        this.head.pitch = (float) Math.toRadians(pitch);

        this.leftEar.roll = -MathHelper.sin(animationProgress * MathHelper.PI * 0.24F) * 0.2F + MathHelper.PI / 4.0F;
        this.rightEar.roll = MathHelper.sin(animationProgress * MathHelper.PI * 0.2F) * 0.2F - MathHelper.PI / 4.0F;
    }

    @Override
    public void transformWall(MatrixStack matrices, float x, float y, float z, Direction direction, float yaw, float animationProgress) {
        RotationAxis axis = switch (direction) {
            case NORTH -> RotationAxis.NEGATIVE_X;
            case SOUTH -> RotationAxis.POSITIVE_X;
            case EAST -> RotationAxis.NEGATIVE_Z;
            case WEST -> RotationAxis.POSITIVE_Z;
            default -> throw new MatchException(null, null);
        };

        matrices.translate(0.5F - 0.3F * direction.getOffsetX(), 0.45F, 0.5F - 0.3F * direction.getOffsetZ());
        matrices.multiply(axis.rotationDegrees(60));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        matrices.push();

        matrices.scale(SCALE_FACTOR, SCALE_FACTOR, SCALE_FACTOR);
        this.head.render(matrices, vertices, light, overlay, color);

        matrices.pop();
    }

}
