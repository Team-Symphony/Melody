package dev.symphony.melody.mixin.more_mob_heads.beheadable;

import dev.symphony.melody.item.ModItems;
import dev.symphony.melody.util.Beheadable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.ZoglinEntity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ZoglinEntity.class)
public class ZoglinEntityMixin implements Beheadable<ZoglinEntity> {

    @Override
    public Item getHeadItem() {
        return ModItems.ZOGLIN_HEAD;
    }

    @Override
    public EntityType<ZoglinEntity> getEntityType() {
        return EntityType.ZOGLIN;
    }

}
