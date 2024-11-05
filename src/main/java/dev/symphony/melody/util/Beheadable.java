package dev.symphony.melody.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;

public interface Beheadable<T extends Entity> {

    Item getHeadItem();

    EntityType<T> getEntityType();

}
