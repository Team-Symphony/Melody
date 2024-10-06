package dev.symphony.melody.config;

import eu.midnightdust.lib.config.MidnightConfig;

public class MelodyConfig extends MidnightConfig {
    @Comment(category = "melody", centered = true) public static Comment reloadWarning;

    // Transportation
    public static final String TRANS = "transportation";
    @MelodyConfigCondition.ResourceConfigName(config_name = "item/netherite_horse_armor") @Entry(category = TRANS) public static boolean netheriteHorseArmor = true;
}
