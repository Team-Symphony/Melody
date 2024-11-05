package dev.symphony.melody.item;

import dev.symphony.melody.Melody;
import dev.symphony.melody.block.ModBlocks;
import dev.symphony.melody.item.map_book.MapBookAdditionsComponent;
import dev.symphony.melody.item.map_book.MapBookItem;
import net.minecraft.block.Block;
import net.minecraft.component.ComponentType;
import net.minecraft.item.AnimalArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.VerticallyAttachableBlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;


public class ModItems {

    public static final List<Item> SKULLS = new ArrayList<>();

    // FEATURE: Netherite horse armor
    // AUTHORS: Flatkat
    // ARTISTS: Eleanor
    public static final Item NETHERITE_HORSE_ARMOR = registerItem(
            // Uses custom armor material in order to change the protection from 11 to 15 (since horse armor takes the chestplate protection from the material)
            "netherite_horse_armor", new AnimalArmorItem(ModArmorMaterials.NETHERITE_HORSE_ARMOR_MATERIAL, AnimalArmorItem.Type.EQUESTRIAN, false, new Item.Settings().maxCount(1).fireproof())
    );

    // FEATURE: Map Book
    // AUTHORS: Nettakrim
    // ARTISTS: Eleanor
    public static final MapBookItem MAP_BOOK = (MapBookItem)registerItem("map_book", new MapBookItem(new Item.Settings().maxCount(16)));
    public static final ComponentType<MapBookAdditionsComponent> MAP_BOOK_ADDITIONS = registerComponent("melody_map_book_additions", (builder) -> builder.codec(MapBookAdditionsComponent.CODEC).packetCodec(MapBookAdditionsComponent.PACKET_CODEC).cache());

    public static final Item BLAZE_HEAD = registerSkullItem("blaze_head", ModBlocks.BLAZE_HEAD, ModBlocks.BLAZE_WALL_HEAD);
    public static final Item BOGGED_SKULL = registerSkullItem("bogged_skull", ModBlocks.BOGGED_SKULL, ModBlocks.BOGGED_WALL_SKULL);
    public static final Item BREEZE_HEAD = registerSkullItem("breeze_head", ModBlocks.BREEZE_HEAD, ModBlocks.BREEZE_WALL_HEAD);
    public static final Item DROWNED_HEAD = registerSkullItem("drowned_head", ModBlocks.DROWNED_HEAD, ModBlocks.DROWNED_WALL_HEAD);
    public static final Item ENDERMAN_HEAD = registerSkullItem("enderman_head", ModBlocks.ENDERMAN_HEAD, ModBlocks.ENDERMAN_WALL_HEAD);
    public static final Item EVOKER_HEAD = registerSkullItem("evoker_head", ModBlocks.EVOKER_HEAD, ModBlocks.EVOKER_WALL_HEAD);
    public static final Item HOGLIN_HEAD = registerSkullItem("hoglin_head", ModBlocks.HOGLIN_HEAD, ModBlocks.HOGLIN_WALL_HEAD);
    public static final Item HUSK_HEAD = registerSkullItem("husk_head", ModBlocks.HUSK_HEAD, ModBlocks.HUSK_WALL_HEAD);
    public static final Item PIGLIN_BRUTE_HEAD = registerSkullItem("piglin_brute_head", ModBlocks.PIGLIN_BRUTE_HEAD, ModBlocks.PIGLIN_BRUTE_WALL_HEAD);
    public static final Item PILLAGER_HEAD = registerSkullItem("pillager_head", ModBlocks.PILLAGER_HEAD, ModBlocks.PILLAGER_WALL_HEAD);
    public static final Item STRAY_SKULL = registerSkullItem("stray_skull", ModBlocks.STRAY_SKULL, ModBlocks.STRAY_WALL_SKULL);
    public static final Item VINDICATOR_HEAD = registerSkullItem("vindicator_head", ModBlocks.VINDICATOR_HEAD, ModBlocks.VINDICATOR_WALL_HEAD);
    public static final Item WITCH_HEAD = registerSkullItem("witch_head", ModBlocks.WITCH_HEAD, ModBlocks.WITCH_WALL_HEAD);
    public static final Item ZOGLIN_HEAD = registerSkullItem("zoglin_head", ModBlocks.ZOGLIN_HEAD, ModBlocks.ZOGLIN_WALL_HEAD);
    public static final Item ZOMBIE_VILLAGER_HEAD = registerSkullItem("zombie_villager_head", ModBlocks.ZOMBIE_VILLAGER_HEAD, ModBlocks.ZOMBIE_VILLAGER_WALL_HEAD);
    public static final Item ZOMBIFIED_PIGLIN_HEAD = registerSkullItem("zombified_piglin_head", ModBlocks.ZOMBIFIED_PIGLIN_HEAD, ModBlocks.ZOMBIFIED_PIGLIN_WALL_HEAD);

    private static Item registerSkullItem(String name, Block standing, Block wall) {
        Item item = new VerticallyAttachableBlockItem(standing, wall, new Item.Settings().rarity(Rarity.UNCOMMON), Direction.DOWN);
        SKULLS.add(item);

        return registerItem(name, item);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Melody.id(name), item);
    }

    private static <T> ComponentType<T> registerComponent(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, id, builderOperator.apply(ComponentType.builder()).build());
    }

    public static void registerItems() {
        Melody.LOGGER.info("Registering Items for: " + Melody.MOD_ID);
    }
}
