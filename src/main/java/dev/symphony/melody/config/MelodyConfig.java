package dev.symphony.melody.config;

import eu.midnightdust.lib.config.MidnightConfig;

public class MelodyConfig extends MidnightConfig {
    @Comment(category = "melody", centered = true) public static Comment reloadWarning;

    public static final String ACCESSIBILITY = "accessibility";
    @Entry(category = ACCESSIBILITY) public static boolean accessibleCreepers = true;
    @Entry(category = ACCESSIBILITY) public static boolean creeperIgnitionRequiresSight = true;
    @Entry(category = ACCESSIBILITY) public static boolean creepersPreserveContainers = true;
    @Entry(category = ACCESSIBILITY, isSlider = true, min = 0.0, max = 10.0) public static double creeperDefuseDistance = 4.0;
    @Entry(category = ACCESSIBILITY, isSlider = true, min = 0, max = 10) public static int creeperExplosionPower = 2;
    @Entry(category = ACCESSIBILITY, isSlider = true, min = 1.0F, max = 5.0F) public static float creeperEntityDamageMultiplier = 1.5F;

    // Transportation
    public static final String TRANS = "transportation";
    @Entry(category = TRANS) public static boolean vehiclesMoveThroughLeaves = true;
    @Entry(category = TRANS, isSlider = true, min = 0f, max = 1f) public static float leafSpeedFactor = 0.85f;
    @MelodyConfigCondition.ResourceConfigName(config_name = "item/netherite_horse_armor") @Entry(category = TRANS) public static boolean netheriteHorseArmor = true;

    public static final String EXPLORATION = "exploration";
    @MelodyConfigCondition.ResourceConfigName(config_name = "item/map_book") @Entry(category = EXPLORATION) public static boolean mapBook = true;
}
