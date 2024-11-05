package dev.symphony.melody.more_mob_heads.model;

import dev.symphony.melody.more_mob_heads.renderer.HeadFeatureRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.SkullBlockEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public abstract class MelodySkullBlockEntityModel extends SkullBlockEntityModel {

    private final List<HeadFeatureRenderer<?>> features = new ArrayList<>();

    public List<HeadFeatureRenderer<?>> getFeatures() {
        return this.features;
    }

    public RenderLayer getRenderLayer(Identifier texture) {
        return RenderLayer.getEntityCutoutNoCullZOffset(texture);
    }

    public void transformWall(MatrixStack matrices, float x, float y, float z, Direction direction, float yaw, float animationProgress) {
        matrices.translate(0.5F - direction.getOffsetX() * 0.25F, 0.25F, 0.5F - direction.getOffsetZ() * 0.25F);
    }

    public void addFeature(HeadFeatureRenderer<?> feature) {
        this.features.add(feature);
    }

}
