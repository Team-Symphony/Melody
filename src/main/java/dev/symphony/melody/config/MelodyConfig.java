package dev.symphony.melody.config;

import eu.midnightdust.lib.config.MidnightConfig;

public class MelodyConfig extends MidnightConfig {
    @Comment(category = "melody", centered = true) public static Comment reloadWarning;

    // Transportation
    public static final String TRANS = "transportation";
    @Entry(category = TRANS) public static boolean vehiclesMoveThroughLeaves = true;
    @Entry(category = TRANS, isSlider = true, min = 0f, max = 1f) public static float leafSpeedFactor = 0.85f;
    @MelodyConfigCondition.ResourceConfigName(config_name = "item/netherite_horse_armor") @Entry(category = TRANS) public static boolean netheriteHorseArmor = true;
    @Entry(category = TRANS) public static int netheriteHorseArmorDefense = 15;

    public static final String EXPLORATION = "exploration";
    @MelodyConfigCondition.ResourceConfigName(config_name = "item/map_book") @Entry(category = EXPLORATION) public static boolean mapBook = true;
}
