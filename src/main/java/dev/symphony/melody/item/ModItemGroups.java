package dev.symphony.melody.item;

import dev.symphony.melody.Melody;
import dev.symphony.melody.item.map_book.MapBookItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

import java.util.List;

public class ModItemGroups {
    public static final ItemGroup MELODY_ITEM_GROUP = Registry.register(Registries.ITEM_GROUP, Melody.id("melody_item_group"),
            FabricItemGroup.builder().icon(() -> {
                ItemStack itemStack = new ItemStack(ModItems.MAP_BOOK);
                ((MapBookItem) itemStack.getItem()).setAdditions(itemStack, List.of(0));
                return itemStack;
            })
                    .displayName(Text.translatable("itemgroup.melody.melody_item_group"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.NETHERITE_HORSE_ARMOR);
                        entries.add(ModItems.MAP_BOOK);

                        for (Item item : ModItems.SKULLS)
                            entries.add(item);

                    }).build());

    public static void registerItemGroups() {
        Melody.LOGGER.info("Registering Item Groups for: " + Melody.MOD_ID);
    }
}
