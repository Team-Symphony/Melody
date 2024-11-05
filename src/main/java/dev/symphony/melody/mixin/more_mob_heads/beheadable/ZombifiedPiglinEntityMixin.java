package dev.symphony.melody.mixin.more_mob_heads.beheadable;

import dev.symphony.melody.item.ModItems;
import dev.symphony.melody.util.Beheadable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ZombifiedPiglinEntity.class)
public class ZombifiedPiglinEntityMixin implements Beheadable<ZombifiedPiglinEntity> {

    @Override
    public Item getHeadItem() {
        return ModItems.ZOMBIFIED_PIGLIN_HEAD;
    }

    @Override
    public EntityType<ZombifiedPiglinEntity> getEntityType() {
        return EntityType.ZOMBIFIED_PIGLIN;
    }

}
