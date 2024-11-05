package dev.symphony.melody;

import dev.symphony.melody.more_mob_heads.model.*;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.PiglinHeadEntityModel;
import net.minecraft.client.render.entity.model.SkullEntityModel;

public class ModEntityModelLayers {

    public static final EntityModelLayer BLAZE_HEAD = register("blaze_head", SkullEntityModel::getSkullTexturedModelData);
    public static final EntityModelLayer BOGGED_SKULL = register("bogged_skull", SkullEntityModel::getSkullTexturedModelData);
    public static final EntityModelLayer BREEZE_HEAD = register("breeze_head", BreezeHeadEntityModel::getTexturedModelData);
    public static final EntityModelLayer DROWNED_HEAD = register("drowned_head", SkullEntityModel::getHeadTexturedModelData);
    public static final EntityModelLayer ENDERMAN_HEAD = register("enderman_head", EndermanHeadEntityModel::getTexturedModelData);
    public static final EntityModelLayer EVOKER_HEAD = register("evoker_head", VillagerResemblingHeadEntityModel::getTexturedModelData);
    public static final EntityModelLayer HOGLIN_HEAD = register("hoglin_head", HoglinHeadEntityModel::getTexturedModelData);
    public static final EntityModelLayer HUSK_HEAD = register("husk_head", SkullEntityModel::getHeadTexturedModelData);
    public static final EntityModelLayer PIGLIN_BRUTE_HEAD = register("piglin_brute_head", () -> TexturedModelData.of(PiglinHeadEntityModel.getModelData(), 64, 64));
    public static final EntityModelLayer PILLAGER_HEAD = register("pillager_head", VillagerResemblingHeadEntityModel::getTexturedModelData);
    public static final EntityModelLayer STRAY_SKULL = register("stray_skull", SkullEntityModel::getSkullTexturedModelData);
    public static final EntityModelLayer VINDICATOR_HEAD = register("vindicator_head", VillagerResemblingHeadEntityModel::getTexturedModelData);
    public static final EntityModelLayer WITCH_HEAD = register("witch_head", WitchHeadEntityModel::getTexturedModelData);
    public static final EntityModelLayer ZOGLIN_HEAD = register("zoglin_head", HoglinHeadEntityModel::getTexturedModelData);
    public static final EntityModelLayer ZOMBIE_VILLAGER_HEAD = register("zombie_villager_head", VillagerResemblingHeadEntityModel::getTexturedModelData);
    public static final EntityModelLayer ZOMBIFIED_PIGLIN_HEAD = register("zombified_piglin_head", () -> TexturedModelData.of(PiglinHeadEntityModel.getModelData(), 64, 64));

    private static EntityModelLayer register(String name, EntityModelLayerRegistry.TexturedModelDataProvider provider) {
        EntityModelLayer layer = new EntityModelLayer(Melody.id(name), "main");
        EntityModelLayerRegistry.registerModelLayer(layer, provider);

        return layer;
    }

    public static void registerEntityModelLayers() {
        Melody.LOGGER.info("Registering Entity Model Layers for: " + Melody.MOD_ID);
    }

}
