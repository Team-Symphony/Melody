package dev.symphony.melody.more_mob_heads.renderer;

import dev.symphony.melody.more_mob_heads.model.MelodySkullBlockEntityModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public abstract class HeadFeatureRenderer<T extends MelodySkullBlockEntityModel> {

    private final T model;

    public HeadFeatureRenderer(T model) {
        this.model = model;
    }

    public T getModel() {
        return this.model;
    }

    public abstract void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, float animationProgress);

}