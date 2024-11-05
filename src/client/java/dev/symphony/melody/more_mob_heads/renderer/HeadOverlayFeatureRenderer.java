package dev.symphony.melody.more_mob_heads.renderer;

import dev.symphony.melody.more_mob_heads.model.MelodySkullBlockEntityModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class HeadOverlayFeatureRenderer<T extends MelodySkullBlockEntityModel> extends HeadFeatureRenderer<T> {

    private final RenderLayer texture;
    private final float scale;

    public HeadOverlayFeatureRenderer(T model, RenderLayer texture) {
        this(model, texture, 1.0F);
    }

    public HeadOverlayFeatureRenderer(T model, RenderLayer texture, float scale) {
        super(model);

        this.texture = texture;
        this.scale = scale;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, float animationProgress) {
        matrices.translate(0.0F, -(1 - this.scale) / 4, 0.0F);
        matrices.scale(this.scale, this.scale, this.scale);

        this.getModel().render(matrices, vertexConsumers.getBuffer(this.texture), light, OverlayTexture.DEFAULT_UV);
    }

}
