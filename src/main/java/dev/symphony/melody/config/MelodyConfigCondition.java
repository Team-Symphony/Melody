package dev.symphony.melody.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.symphony.melody.Melody;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;

// FEATURE: Configurable Data-Driven Resources (ported over from Harmony)
// AUTHORS: Flatkat, WheatFlour
public record MelodyConfigCondition(String config_name) implements ResourceCondition {

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD})
    public @interface ResourceConfigName {
        String config_name();
    }

    public static void init() {
        Field[] fields = MelodyConfig.class.getDeclaredFields();
        HashMap<String, Boolean> map = new HashMap<>();
        for (Field field : fields) {
            if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                if (field.isAnnotationPresent(ResourceConfigName.class)) {

                    ResourceConfigName configName = field.getAnnotation(ResourceConfigName.class);

                    try {
                        map.put(configName.config_name(), (Boolean) field.get(null));
                    } catch (IllegalAccessException e) {
                        Melody.LOGGER.error("Failed to get resource config condition value for field {}", field.getName());
                        Melody.LOGGER.error(Arrays.toString(e.getStackTrace()));
                    }
                }
            }
        }
        resourceMap = map;
    }

    public static HashMap<String, Boolean> resourceMap = null;

    public static final MapCodec<MelodyConfigCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.fieldOf("config_name").forGetter(condition -> condition.config_name)
    ).apply(instance, MelodyConfigCondition::new));




    @Override
    public ResourceConditionType<?> getType() {
        return ResourceConditionType.create(Identifier.of(Melody.MOD_ID, "config"), CODEC);
    }


    @Override
    public boolean test(@Nullable RegistryWrapper.WrapperLookup registryLookup) {
        return resourceMap.getOrDefault(config_name, false);
    }


}