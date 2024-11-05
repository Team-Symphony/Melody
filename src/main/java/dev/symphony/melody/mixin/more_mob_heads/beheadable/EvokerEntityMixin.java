package dev.symphony.melody.mixin.more_mob_heads.beheadable;

import dev.symphony.melody.item.ModItems;
import dev.symphony.melody.util.Beheadable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.EvokerEntity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EvokerEntity.class)
public class EvokerEntityMixin implements Beheadable<EvokerEntity> {

    @Override
    public Item getHeadItem() {
        return ModItems.EVOKER_HEAD;
    }

    @Override
    public EntityType<EvokerEntity> getEntityType() {
        return EntityType.EVOKER;
    }

}
