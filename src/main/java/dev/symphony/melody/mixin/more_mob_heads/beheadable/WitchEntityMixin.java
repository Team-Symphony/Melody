package dev.symphony.melody.mixin.more_mob_heads.beheadable;

import dev.symphony.melody.item.ModItems;
import dev.symphony.melody.util.Beheadable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(WitchEntity.class)
public class WitchEntityMixin implements Beheadable<WitchEntity> {

    @Override
    public Item getHeadItem() {
        return ModItems.WITCH_HEAD;
    }

    @Override
    public EntityType<WitchEntity> getEntityType() {
        return EntityType.WITCH;
    }

}
