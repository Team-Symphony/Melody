package dev.symphony.melody.mixin.more_mob_heads.beheadable;

import dev.symphony.melody.item.ModItems;
import dev.symphony.melody.util.Beheadable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HuskEntity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(HuskEntity.class)
public class HuskEntityMixin implements Beheadable<HuskEntity> {

    @Override
    public Item getHeadItem() {
        return ModItems.HUSK_HEAD;
    }

    @Override
    public EntityType<HuskEntity> getEntityType() {
        return EntityType.HUSK;
    }

}
