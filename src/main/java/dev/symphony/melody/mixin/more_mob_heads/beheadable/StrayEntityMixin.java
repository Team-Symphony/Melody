package dev.symphony.melody.mixin.more_mob_heads.beheadable;

import dev.symphony.melody.item.ModItems;
import dev.symphony.melody.util.Beheadable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.StrayEntity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(StrayEntity.class)
public class StrayEntityMixin implements Beheadable<StrayEntity> {

    @Override
    public Item getHeadItem() {
        return ModItems.STRAY_SKULL;
    }

    @Override
    public EntityType<StrayEntity> getEntityType() {
        return EntityType.STRAY;
    }

}
