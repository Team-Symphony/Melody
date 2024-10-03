package dev.symphony.melody.mixin.map_book;

import dev.symphony.melody.map_book.MapStateAccessor;
import net.minecraft.item.map.MapState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MapState.class)
public class MapStateMixin implements MapStateAccessor {
    @Final @Shadow @Mutable
    public int centerX;

    @Final @Shadow @Mutable
    public int centerZ;


    @Override
    public void melody$setPosition(int centerX, int centerZ) {
        this.centerX = centerX;
        this.centerZ = centerZ;
    }
}
