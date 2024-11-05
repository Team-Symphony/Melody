package dev.symphony.melody.mixin.more_mob_heads.beheadable;

import dev.symphony.melody.item.ModItems;
import dev.symphony.melody.util.Beheadable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.VindicatorEntity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(VindicatorEntity.class)
public class VindicatorEntityMixin implements Beheadable<VindicatorEntity> {

    @Override
    public Item getHeadItem() {
        return ModItems.VINDICATOR_HEAD;
    }

    @Override
    public EntityType<VindicatorEntity> getEntityType() {
        return EntityType.VINDICATOR;
    }

}
