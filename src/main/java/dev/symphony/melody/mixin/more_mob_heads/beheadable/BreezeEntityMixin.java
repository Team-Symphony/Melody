package dev.symphony.melody.mixin.more_mob_heads.beheadable;

import dev.symphony.melody.item.ModItems;
import dev.symphony.melody.util.Beheadable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.BreezeEntity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BreezeEntity.class)
public class BreezeEntityMixin implements Beheadable<BreezeEntity> {

    @Override
    public Item getHeadItem() {
        return ModItems.BREEZE_HEAD;
    }

    @Override
    public EntityType<BreezeEntity> getEntityType() {
        return EntityType.BREEZE;
    }

}
