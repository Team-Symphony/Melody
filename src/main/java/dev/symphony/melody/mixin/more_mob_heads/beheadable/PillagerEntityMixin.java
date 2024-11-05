package dev.symphony.melody.mixin.more_mob_heads.beheadable;

import dev.symphony.melody.item.ModItems;
import dev.symphony.melody.util.Beheadable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PillagerEntity.class)
public class PillagerEntityMixin implements Beheadable<PillagerEntity> {

    @Override
    public Item getHeadItem() {
        return ModItems.PILLAGER_HEAD;
    }

    @Override
    public EntityType<PillagerEntity> getEntityType() {
        return EntityType.PILLAGER;
    }

}
