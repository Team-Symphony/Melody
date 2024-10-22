package dev.symphony.melody.map_book;

import dev.symphony.melody.item.ModItems;
import dev.symphony.melody.item.map_book.MapStateData;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.item.ItemStack;
import net.minecraft.item.map.MapDecoration;
import net.minecraft.item.map.MapDecorationTypes;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

import java.util.Optional;

public final class MapBookScreen extends Screen {
    @NotNull
    private final ItemStack item;
    private double x;
    private double y;
    private float scale;
    private float targetScale;

    public MapBookScreen(@NotNull ItemStack item) {
        super(item.getName());
        this.item = item;
        this.scale = 1.0F;
        this.targetScale = 0.5F;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public float getScale() {
        return this.scale;
    }

    @Override
    protected void init() {
        if (this.client == null || this.client.world == null) {
            return;
        }

        ClientPlayerEntity player = this.client.player;
        if (player != null) {
            this.x = -player.getX();
            this.y = -player.getZ();
        }

        this.setScale(this.targetScale, (double)this.width / 2.0, (double)this.height / 2.0);

        for (MapStateData mapStateData : ModItems.MAP_BOOK.getMapStates(this.item, this.client.world)) {
            this.addDrawable(new MapTile(this, mapStateData.id(), mapStateData.mapState(), this.client));
        }

        this.addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, (widget) -> close()).dimensions(this.width / 2 - 100, this.height / 4 + 144, 200, 20).build());
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        this.x += deltaX;
        this.y += deltaY;
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (verticalAmount != 0.0) {
            this.targetScale = this.zoom(this.scale, -((float)verticalAmount), 5.0F);
        }

        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public void render(@Nullable DrawContext context, int mouseX, int mouseY, float delta) {
        if (context != null) {
            if (this.scale != this.targetScale) {
                float newScale = MathHelper.lerp(delta, this.scale, this.targetScale);
                this.setScale(newScale, mouseX, mouseY);
            }

            super.render(context, mouseX, mouseY, delta);
            if (this.client != null) {
                ClientPlayerEntity player = this.client.player;
                if (player != null) {
                    this.renderPlayerIcon(context, (float)player.getX(), (float)player.getZ(), player.getYaw());
                }
            }

        }
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    private void renderPlayerIcon(DrawContext context, float x, float z, float rotation) {
        if (this.client == null) {
            return;
        }

        context.getMatrices().push();
        context.getMatrices().translate(this.x, this.y, 0.0);
        context.getMatrices().scale(this.scale, this.scale, 1.0F);
        context.getMatrices().translate((double)x + (double)this.width / 2.0, (double)z + (double)this.height / 2.0, 0.0);
        context.getMatrices().multiply(RotationAxis.POSITIVE_Z.rotationDegrees(rotation));
        context.getMatrices().scale(8.0F, 8.0F, -3.0F);
        context.getMatrices().translate(-0.125F, 0.125F, -10.0F);
        context.getMatrices().scale((float)1 / this.scale, (float)1 / this.scale, 1.0F);
        Sprite sprite = client.getMapDecorationsAtlasManager().getSprite(new MapDecoration(MapDecorationTypes.PLAYER, (byte)0, (byte)0, (byte)0, Optional.empty()));
        float g = sprite.getMinU();
        float h = sprite.getMinV();
        float l = sprite.getMaxU();
        float m = sprite.getMaxV();
        Matrix4f matrix4f2 = context.getMatrices().peek().getPositionMatrix();
        VertexConsumer vertexConsumer2 = context.getVertexConsumers().getBuffer(RenderLayer.getText(sprite.getAtlasId()));
        vertexConsumer2.vertex(matrix4f2, -1.0F, 1.0F, -0.1F).color(255, 255, 255, 255).texture(g, h).light(15728880);
        vertexConsumer2.vertex(matrix4f2, 1.0F, 1.0F, -0.1F).color(255, 255, 255, 255).texture(l, h).light(15728880);
        vertexConsumer2.vertex(matrix4f2, 1.0F, -1.0F, -0.1F).color(255, 255, 255, 255).texture(l, m).light(15728880);
        vertexConsumer2.vertex(matrix4f2, -1.0F, -1.0F, -0.1F).color(255, 255, 255, 255).texture(g, m).light(15728880);
        context.getMatrices().pop();
    }

    private void setScale(float newScale, double mouseX, double mouseY) {
        double offsetX = this.x - mouseX;
        double offsetY = this.y - mouseY;
        float scaleChange = newScale / this.scale;
        this.x = (double)scaleChange * offsetX + mouseX;
        this.y = (double)scaleChange * offsetY + mouseY;
        this.scale = newScale;
    }

    private float zoom(float start, float scroll, float speed) {
        float absScroll = Math.abs(scroll);
        return scroll > 0.0F ? start - start / (scroll * speed) : start * absScroll * speed / (absScroll * speed - (float)1);
    }
}

