package dev.symphony.melody.map_book;

import com.mojang.serialization.MapCodec;
import dev.symphony.melody.item.ModItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.item.property.bool.BooleanProperty;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class MapBookFilledProperty implements BooleanProperty {

    public static final MapCodec<MapBookFilledProperty> CODEC = MapCodec.unit(new MapBookFilledProperty());

    @Override
    public boolean getValue(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity user, int seed, ModelTransformationMode modelTransformationMode) {
        return stack.contains(DataComponentTypes.MAP_ID) || stack.contains(ModItems.MAP_BOOK_ADDITIONS);
    }

    @Override
    public MapCodec<MapBookFilledProperty> getCodec() {
        return CODEC;
    }

}
