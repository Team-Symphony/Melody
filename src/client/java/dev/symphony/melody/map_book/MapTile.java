package dev.symphony.melody.map_book;

import dev.symphony.melody.mixin.client.map_book.DrawContextAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.render.MapRenderState;
import net.minecraft.component.type.MapIdComponent;
import net.minecraft.item.map.MapState;
import org.jetbrains.annotations.NotNull;

public final class MapTile implements Drawable {
    @NotNull
    private final MapBookScreen screen;
    private final MapIdComponent id;
    @NotNull
    private final MapState mapState;
    @NotNull
    private final MinecraftClient client;

    private MapRenderState mapRenderState;

    public MapTile(@NotNull MapBookScreen screen, MapIdComponent id, @NotNull MapState mapState, @NotNull MinecraftClient client) {
        super();
        this.screen = screen;
        this.id = id;
        this.mapState = mapState;
        this.client = client;
        this.mapRenderState = new MapRenderState();
        this.client.getMapRenderer().update(id, mapState, mapRenderState);
    }

    @Override
    public void render(@NotNull DrawContext context, int mouseX, int mouseY, float delta) {
        float mapScale = (float)(1 << this.mapState.scale);
        float offset = (float)64 * mapScale;
        context.getMatrices().push();
        context.getMatrices().translate(this.screen.getX(), this.screen.getY(), 1.0 / ((double)this.mapState.scale + 1.0) + 1.0);
        context.getMatrices().scale(this.screen.getScale(), this.screen.getScale(), -1.0F);
        context.getMatrices().translate((double)this.mapState.centerX - (double)offset + (double)this.screen.width / 2.0, (double)this.mapState.centerZ - (double)offset + (double)this.screen.height / 2.0, 0.0);
        context.getMatrices().scale(mapScale, mapScale, 1.0F);
        this.client.getMapRenderer().draw(mapRenderState, context.getMatrices(), ((DrawContextAccessor)context).getVertexConsumers(), true, 15728880);
        context.getMatrices().pop();
    }


}
