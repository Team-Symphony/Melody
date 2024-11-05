package dev.symphony.melody.block;

import dev.symphony.melody.Melody;
import dev.symphony.melody.block.more_mob_heads.*;
import dev.symphony.melody.util.MelodySkullType;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class ModBlocks {

    public static final List<Block> SKULLS = new ArrayList<>();

    public static final Block BLAZE_HEAD = registerStandingSkullBlock("blaze_head", AbstractBlock.Settings.create().luminance(state -> 15), MelodySkullType.BLAZE);
    public static final Block BLAZE_WALL_HEAD = registerWallSkullBlock("blaze_wall_head", MelodySkullType.BLAZE, BLAZE_HEAD);
    public static final Block BOGGED_SKULL = registerStandingSkullBlock("bogged_skull", MelodySkullType.BOGGED);
    public static final Block BOGGED_WALL_SKULL = registerWallSkullBlock("bogged_wall_skull", MelodySkullType.BOGGED, BOGGED_SKULL);
    public static final Block BREEZE_HEAD = registerStandingSkullBlock("breeze_head", MelodySkullType.BREEZE);
    public static final Block BREEZE_WALL_HEAD = registerWallSkullBlock("breeze_wall_head", MelodySkullType.BREEZE, BREEZE_HEAD);
    public static final Block DROWNED_HEAD = registerStandingSkullBlock("drowned_head", MelodySkullType.DROWNED);
    public static final Block DROWNED_WALL_HEAD = registerWallSkullBlock("drowned_wall_head", MelodySkullType.DROWNED, DROWNED_HEAD);
    public static final Block ENDERMAN_HEAD = registerStandingSkullBlock("enderman_head", MelodySkullType.ENDERMAN);
    public static final Block ENDERMAN_WALL_HEAD = registerWallSkullBlock("enderman_wall_head", MelodySkullType.ENDERMAN, ENDERMAN_HEAD);
    public static final Block EVOKER_HEAD = registerStandingSkullBlock("evoker_head", MelodySkullType.EVOKER, VillagerResemblingHeadBlock::new);
    public static final Block EVOKER_WALL_HEAD = registerWallSkullBlock("evoker_wall_head", MelodySkullType.EVOKER, EVOKER_HEAD, WallVillagerResemblingHeadBlock::new);
    public static final Block HOGLIN_HEAD = registerStandingSkullBlock("hoglin_head", MelodySkullType.HOGLIN, HoglinHeadBlock::new);
    public static final Block HOGLIN_WALL_HEAD = registerWallSkullBlock("hoglin_wall_head", MelodySkullType.HOGLIN, HOGLIN_HEAD, WallHoglinHeadBlock::new);
    public static final Block HUSK_HEAD = registerStandingSkullBlock("husk_head", MelodySkullType.HUSK);
    public static final Block HUSK_WALL_HEAD = registerWallSkullBlock("husk_wall_head", MelodySkullType.HUSK, HUSK_HEAD);
    public static final Block PIGLIN_BRUTE_HEAD = registerStandingSkullBlock("piglin_brute_head", MelodySkullType.PIGLIN_BRUTE);
    public static final Block PIGLIN_BRUTE_WALL_HEAD = registerWallSkullBlock("piglin_brute_wall_head", MelodySkullType.PIGLIN_BRUTE, PIGLIN_BRUTE_HEAD, WallPiglinTypeHeadBlock::new);
    public static final Block PILLAGER_HEAD = registerStandingSkullBlock("pillager_head", MelodySkullType.PILLAGER, VillagerResemblingHeadBlock::new);
    public static final Block PILLAGER_WALL_HEAD = registerWallSkullBlock("pillager_wall_head", MelodySkullType.PILLAGER, PILLAGER_HEAD, WallVillagerResemblingHeadBlock::new);
    public static final Block STRAY_SKULL = registerStandingSkullBlock("stray_skull", MelodySkullType.STRAY);
    public static final Block STRAY_WALL_SKULL = registerWallSkullBlock("stray_wall_skull", MelodySkullType.STRAY, STRAY_SKULL);
    public static final Block VINDICATOR_HEAD = registerStandingSkullBlock("vindicator_head", MelodySkullType.VINDICATOR, VillagerResemblingHeadBlock::new);
    public static final Block VINDICATOR_WALL_HEAD = registerWallSkullBlock("vindicator_wall_head", MelodySkullType.VINDICATOR, VINDICATOR_HEAD, WallVillagerResemblingHeadBlock::new);
    public static final Block WITCH_HEAD = registerStandingSkullBlock("witch_head", MelodySkullType.WITCH, VillagerResemblingHeadBlock::new);
    public static final Block WITCH_WALL_HEAD = registerWallSkullBlock("witch_wall_head", MelodySkullType.WITCH, WITCH_HEAD, WallVillagerResemblingHeadBlock::new);
    public static final Block ZOGLIN_HEAD = registerStandingSkullBlock("zoglin_head", MelodySkullType.ZOGLIN, HoglinHeadBlock::new);
    public static final Block ZOGLIN_WALL_HEAD = registerWallSkullBlock("zoglin_wall_head", MelodySkullType.ZOGLIN, ZOGLIN_HEAD, WallHoglinHeadBlock::new);
    public static final Block ZOMBIE_VILLAGER_HEAD = registerStandingSkullBlock("zombie_villager_head", MelodySkullType.ZOMBIE_VILLAGER, VillagerResemblingHeadBlock::new);
    public static final Block ZOMBIE_VILLAGER_WALL_HEAD = registerWallSkullBlock("zombie_villager_wall_head", MelodySkullType.ZOMBIE_VILLAGER, ZOMBIE_VILLAGER_HEAD, WallVillagerResemblingHeadBlock::new);
    public static final Block ZOMBIFIED_PIGLIN_HEAD = registerStandingSkullBlock("zombified_piglin_head", MelodySkullType.ZOMBIFIED_PIGLIN);
    public static final Block ZOMBIFIED_PIGLIN_WALL_HEAD = registerWallSkullBlock("zombified_piglin_wall_head", MelodySkullType.ZOMBIFIED_PIGLIN, ZOMBIFIED_PIGLIN_HEAD, WallPiglinTypeHeadBlock::new);

    private static Block registerStandingSkullBlock(String name, SkullBlock.SkullType skullType) {
        return registerStandingSkullBlock(name, AbstractBlock.Settings.create(), skullType, SkullBlock::new);
    }

    private static Block registerStandingSkullBlock(String name, AbstractBlock.Settings settings, SkullBlock.SkullType skullType) {
        return registerStandingSkullBlock(name, settings, skullType, SkullBlock::new);
    }

    private static Block registerStandingSkullBlock(String name, SkullBlock.SkullType skullType, BiFunction<SkullBlock.SkullType, AbstractBlock.Settings, ? extends SkullBlock> factory) {
        NoteBlockInstrument instrument = skullType instanceof MelodySkullType melodySkullType ? NoteBlockInstrument.valueOf(melodySkullType.name()) : NoteBlockInstrument.CUSTOM_HEAD;
        return registerSkullBlock(name, factory.apply(skullType, getStandingSkullSettings(AbstractBlock.Settings.create(), instrument)));
    }

    private static Block registerStandingSkullBlock(String name, AbstractBlock.Settings settings, SkullBlock.SkullType skullType, BiFunction<SkullBlock.SkullType, AbstractBlock.Settings, ? extends SkullBlock> factory) {
        NoteBlockInstrument instrument = skullType instanceof MelodySkullType melodySkullType ? NoteBlockInstrument.valueOf(melodySkullType.name()) : NoteBlockInstrument.CUSTOM_HEAD;
        return registerSkullBlock(name, factory.apply(skullType, getStandingSkullSettings(settings, instrument)));
    }

    private static Block registerWallSkullBlock(String name, SkullBlock.SkullType skullType, Block standing) {
        return registerWallSkullBlock(name, skullType, standing, WallSkullBlock::new);
    }

    private static Block registerWallSkullBlock(String name, SkullBlock.SkullType skullType, Block standing, BiFunction<SkullBlock.SkullType, AbstractBlock.Settings, ? extends WallSkullBlock> factory) {
        return registerSkullBlock(name, factory.apply(skullType, getWallSkullSettings(standing)));
    }

    private static Block registerSkullBlock(String name, Block block) {
        SKULLS.add(block);
        return registerBlock(name, block);
    }

    private static AbstractBlock.Settings getStandingSkullSettings(AbstractBlock.Settings settings, NoteBlockInstrument instrument) {
        return settings.instrument(instrument).strength(1.0F).pistonBehavior(PistonBehavior.DESTROY);
    }

    private static AbstractBlock.Settings getWallSkullSettings(Block standing) {
        return AbstractBlock.Settings.copy(standing).dropsLike(standing);
    }

    private static Block registerBlock(String name, Block block) {
        return Registry.register(Registries.BLOCK, Melody.id(name), block);
    }

    public static void registerBlocks() {
        Melody.LOGGER.info("Registering Blocks for: " + Melody.MOD_ID);
    }

    static {
        List<BlockState> list = new ArrayList<>();

        for (Block block : SKULLS)
            list.addAll(block.getStateManager().getStates());

        for (BlockState blockState : list) {
            Block.STATE_IDS.add(blockState);
            blockState.initShapeCache();
        }
    }

}
