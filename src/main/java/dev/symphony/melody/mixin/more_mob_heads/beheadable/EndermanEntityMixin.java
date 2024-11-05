package dev.symphony.melody.mixin.more_mob_heads.beheadable;

import dev.symphony.melody.item.ModItems;
import dev.symphony.melody.util.Beheadable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EndermanEntity.class)
public class EndermanEntityMixin implements Beheadable<EndermanEntity> {

    @Override
    public Item getHeadItem() {
        return ModItems.ENDERMAN_HEAD;
    }

    @Override
    public EntityType<EndermanEntity> getEntityType() {
        return EntityType.ENDERMAN;
    }

}
