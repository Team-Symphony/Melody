package dev.symphony.melody.mixin.more_mob_heads.beheadable;

import dev.symphony.melody.item.ModItems;
import dev.symphony.melody.util.Beheadable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HoglinEntity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(HoglinEntity.class)
public class HoglinEntityMixin implements Beheadable<HoglinEntity> {

    @Override
    public Item getHeadItem() {
        return ModItems.HOGLIN_HEAD;
    }

    @Override
    public EntityType<HoglinEntity> getEntityType() {
        return EntityType.HOGLIN;
    }

}
