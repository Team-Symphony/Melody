package dev.symphony.melody.mixin.more_mob_heads.beheadable;

import dev.symphony.melody.item.ModItems;
import dev.symphony.melody.util.Beheadable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(DrownedEntity.class)
public class DrownedEntityMixin implements Beheadable<DrownedEntity> {

    @Override
    public Item getHeadItem() {
        return ModItems.DROWNED_HEAD;
    }

    @Override
    public EntityType<DrownedEntity> getEntityType() {
        return EntityType.DROWNED;
    }

}
