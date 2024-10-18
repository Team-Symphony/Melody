package dev.symphony.melody.mixin.client.map_book;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.symphony.melody.item.map_book.MapBookItem;
import dev.symphony.melody.item.map_book.MapStateData;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.CartographyTableScreen;
import net.minecraft.component.type.MapIdComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.map.MapState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CartographyTableScreen.class)
public class CartographyTableScreenMixin {
    @ModifyExpressionValue(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/FilledMapItem;getMapState(Lnet/minecraft/component/type/MapIdComponent;Lnet/minecraft/world/World;)Lnet/minecraft/item/map/MapState;"), method = "drawBackground")
    private MapState changeMapstate(MapState original, @Local(ordinal = 1) ItemStack item0, @Local() MapIdComponent mapIdComponent) {
        if (!(item0.getItem() instanceof MapBookItem mapBookItem)) return original;

        MinecraftClient client = MinecraftClient.getInstance();
        MapStateData mapStateData = mapBookItem.getNearestMap(item0, client.world, client.player.getPos());

        if (mapStateData == null) return null;
        return mapStateData.mapState();
    }
}
