package dev.symphony.melody.mixin.more_mob_heads.beheadable;

import dev.symphony.melody.item.ModItems;
import dev.symphony.melody.util.Beheadable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PiglinBruteEntity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PiglinBruteEntity.class)
public class PiglinBruteEntityMixin implements Beheadable<PiglinBruteEntity> {

    @Override
    public Item getHeadItem() {
        return ModItems.PIGLIN_BRUTE_HEAD;
    }

    @Override
    public EntityType<PiglinBruteEntity> getEntityType() {
        return EntityType.PIGLIN_BRUTE;
    }

}
