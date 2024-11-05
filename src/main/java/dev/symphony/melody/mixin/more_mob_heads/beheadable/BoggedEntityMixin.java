package dev.symphony.melody.mixin.more_mob_heads.beheadable;

import dev.symphony.melody.item.ModItems;
import dev.symphony.melody.util.Beheadable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.BoggedEntity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BoggedEntity.class)
public class BoggedEntityMixin implements Beheadable<BoggedEntity> {

    @Override
    public Item getHeadItem() {
        return ModItems.BOGGED_SKULL;
    }

    @Override
    public EntityType<BoggedEntity> getEntityType() {
        return EntityType.BOGGED;
    }

}
