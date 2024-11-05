package dev.symphony.melody.mixin.more_mob_heads.beheadable;

import dev.symphony.melody.item.ModItems;
import dev.symphony.melody.util.Beheadable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ZombieVillagerEntity.class)
public class ZombieVillagerEntityMixin implements Beheadable<ZombieVillagerEntity> {

    @Override
    public Item getHeadItem() {
        return ModItems.ZOMBIE_VILLAGER_HEAD;
    }

    @Override
    public EntityType<ZombieVillagerEntity> getEntityType() {
        return EntityType.ZOMBIE_VILLAGER;
    }

}
