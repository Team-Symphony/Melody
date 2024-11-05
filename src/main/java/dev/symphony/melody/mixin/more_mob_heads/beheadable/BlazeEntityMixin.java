package dev.symphony.melody.mixin.more_mob_heads.beheadable;

import dev.symphony.melody.item.ModItems;
import dev.symphony.melody.util.Beheadable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlazeEntity.class)
public class BlazeEntityMixin implements Beheadable<BlazeEntity> {

    @Override
    public Item getHeadItem() {
        return ModItems.BLAZE_HEAD;
    }

    @Override
    public EntityType<BlazeEntity> getEntityType() {
        return EntityType.BLAZE;
    }

}
