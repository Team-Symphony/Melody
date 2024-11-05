package dev.symphony.melody.more_mob_heads.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.util.math.MathHelper;

public class WitchHeadEntityModel extends VillagerResemblingHeadEntityModel {

    private final ModelPart nose;

    public WitchHeadEntityModel(ModelPart root) {
        super(root);
        this.nose = root.getChild(EntityModelPartNames.HEAD).getChild(EntityModelPartNames.NOSE);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = VillagerResemblingHeadEntityModel.getModelData();

        ModelPartData root = modelData.getRoot();
        ModelPartData head = root.getChild(EntityModelPartNames.HEAD);

        ModelPartData hat = head.addChild(
            EntityModelPartNames.HAT,
            ModelPartBuilder.create()
                .uv(0, 64)
                .cuboid(0.0F, 0.0F, 0.0F, 10.0F, 2.0F, 10.0F),
            ModelTransform.pivot(-5.0F, -10.03125F, -5.0F)
        );

        ModelPartData hat2 = hat.addChild(
            "hat2",
            ModelPartBuilder.create()
                .uv(0, 76)
                .cuboid(0.0F, 0.0F, 0.0F, 7.0F, 4.0F, 7.0F),
            ModelTransform.of(1.75F, -4.0F, 2.0F, -0.05235988F, 0.0F, 0.02617994F)
        );

        ModelPartData hat3 = hat2.addChild(
            "hat3",
            ModelPartBuilder.create()
                .uv(0, 87)
                .cuboid(0.0F, 0.0F, 0.0F, 4.0F, 4.0F, 4.0F),
            ModelTransform.of(1.75F, -4.0F, 2.0F, -0.10471976F, 0.0F, 0.05235988F)
        );

        hat3.addChild(
            "hat4",
            ModelPartBuilder.create()
                .uv(0, 95)
                .cuboid(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.25F)),
            ModelTransform.of(1.75F, -2.0F, 2.0F, (float) (-Math.PI / 15), 0.0F, 0.10471976F)
        );

        ModelPartData nose = head.getChild(EntityModelPartNames.NOSE);

        nose.addChild(
            "mole",
            ModelPartBuilder.create()
                .uv(0, 0)
                .cuboid(0.0F, 3.0F, -6.75F, 1.0F, 1.0F, 1.0F, new Dilation(-0.25F)),
            ModelTransform.pivot(0.0F, -2.0F, 0.0F)
        );

        return TexturedModelData.of(modelData, 64, 128);
    }

    @Override
    public void setHeadRotation(float animationProgress, float yaw, float pitch) {
        super.setHeadRotation(animationProgress, yaw, pitch);

        this.nose.setPivot(0.0F, -2.0F, 0.0F);

        this.nose.pitch = MathHelper.sin(animationProgress * 0.2F) * 2.0F * MathHelper.PI / 180.0F;
        this.nose.roll = MathHelper.sin(animationProgress * 0.2F) * MathHelper.PI / 180.0F;
    }

}
