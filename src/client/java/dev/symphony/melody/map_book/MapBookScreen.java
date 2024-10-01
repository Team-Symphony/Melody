package dev.symphony.melody.map_book;

import dev.symphony.melody.ItemRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.map.MapState;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

public final class MapBookScreen extends Screen {
    @NotNull
    private final ItemStack item;
    private double x;
    private double y;
    private float scale;
    private float targetScale;
    @NotNull
    private final RenderLayer MAP_ICONS_RENDER_LAYER;

    public MapBookScreen(@NotNull ItemStack item) {
        super(item.getName());
        this.item = item;
        this.scale = 1.0F;
        this.targetScale = 0.5F;
        this.MAP_ICONS_RENDER_LAYER = RenderLayer.getText(Identifier.of("textures/map/map_icons.png"));
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

    protected void init() {
        if (this.client != null) {
            ClientPlayerEntity player = this.client.player;
            if (player != null) {
                this.x = -player.getX();
                this.y = -player.getZ();
            }
        }

        this.setScale(this.targetScale, (double)this.width / 2.0, (double)this.height / 2.0);

        for (MapStateData mapStateData : ItemRegistry.MapBook.getMapStates(this.item, (this.client != null ? this.client.world : null))) {
            this.addDrawable(new MapTile(this, mapStateData.getId(), mapStateData.getMapState(), this.client));
        }

        this.addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, (widget) -> close()).dimensions(this.width / 2 - 100, this.height / 4 + 144, 200, 20).build());
    }

    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        this.x += deltaX;
        this.y += deltaY;
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (verticalAmount != 0.0) {
            this.targetScale = this.zoom(this.scale, -((float)verticalAmount), 5.0F);
        }

        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    public void render(@Nullable DrawContext context, int mouseX, int mouseY, float delta) {
        if (context != null) {
            if (this.scale != this.targetScale) {
                float newScale = MathHelper.lerp(delta, this.scale, this.targetScale);
                this.setScale(newScale, (double)mouseX, (double)mouseY);
            }

            super.render(context, mouseX, mouseY, delta);
            MinecraftClient var7 = this.client;
            if (var7 != null) {
                ClientPlayerEntity var8 = var7.player;
                if (var8 != null) {
                    this.renderPlayerIcon(context, (float)var8.getX(), (float)var8.getZ(), var8.getYaw());
                }
            }

        }
    }

    private void renderPlayerIcon(DrawContext context, float x, float z, float rotation) {
        //TODO: change to using MapRenderer.this.mapDecorationsAtlasManager.getSprite(mapDecoration)

        //context.getMatrices().push();
        //context.getMatrices().translate(this.x, this.y, 0.0);
        //context.getMatrices().scale(this.scale, this.scale, 1.0F);
        //context.getMatrices().translate((double)x + (double)this.width / 2.0, (double)z + (double)this.height / 2.0, 0.0);
        //context.getMatrices().multiply(RotationAxis.POSITIVE_Z.rotationDegrees(rotation));
        //context.getMatrices().scale(8.0F, 8.0F, -3.0F);
        //context.getMatrices().translate(-0.125F, 0.125F, -10.0F);
        //context.getMatrices().scale((float)1 / this.scale, (float)1 / this.scale, 1.0F);
        //byte b = MapIcon.Type.PLAYER.getId();
        //float g = (float)(b % 16 + 0) / 16.0F;
        //float h = (float)(b / 16 + 0) / 16.0F;
        //float l = (float)(b % 16 + 1) / 16.0F;
        //float m = (float)(b / 16 + 1) / 16.0F;
        //Matrix4f var11 = context.getMatrices().peek().getPositionMatrix();
        //Matrix4f matrix4f2 = var11;
        //VertexConsumer var12 = context.getVertexConsumers().getBuffer(this.MAP_ICONS_RENDER_LAYER);
        //VertexConsumer vertexConsumer2 = var12;
        //vertexConsumer2.vertex(matrix4f2, -1.0F, 1.0F, -0.1F).color(255, 255, 255, 255).texture(g, h).light(15728880).next();
        //vertexConsumer2.vertex(matrix4f2, 1.0F, 1.0F, -0.1F).color(255, 255, 255, 255).texture(l, h).light(15728880).next();
        //vertexConsumer2.vertex(matrix4f2, 1.0F, -1.0F, -0.1F).color(255, 255, 255, 255).texture(l, m).light(15728880).next();
        //vertexConsumer2.vertex(matrix4f2, -1.0F, -1.0F, -0.1F).color(255, 255, 255, 255).texture(g, m).light(15728880).next();
        //context.getMatrices().pop();
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

