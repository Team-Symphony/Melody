package dev.symphony.melody.item;

import dev.symphony.melody.Melody;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup MELODY_ITEM_GROUP = Registry.register(Registries.ITEM_GROUP, Identifier.of(Melody.MOD_ID, "melody_item_group"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.NETHERITE_HORSE_ARMOR))
                    .displayName(Text.translatable("itemgroup.melody.melody_item_group"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.NETHERITE_HORSE_ARMOR);

                    }).build());

    public static void registerItemGroups(){
        Melody.LOGGER.info("Registering Item Groups for:" + Melody.MOD_ID);
    }
}
