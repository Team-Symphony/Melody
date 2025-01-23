package dev.symphony.melody.config;


import dev.symphony.melody.Melody;
import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;
import io.wispforest.owo.config.annotation.RangeConstraint;
import io.wispforest.owo.config.annotation.SectionHeader;

@Modmenu(modId = Melody.MOD_ID)
@Config(name = "melody", wrapperName = "MelodyConfig")
public class MelodyConfigModel {

    // Transportation 🏳️‍⚧️
    @SectionHeader("Transportation")
    public boolean vehiclesMoveThroughLeaves = true;
    @RangeConstraint(min = 0f, max = 1f) public float leafSpeedFactor = 0.85f;
    @MelodyConfigCondition.ResourceConfigName(config_name = "item/netherite_horse_armor")  public boolean netheriteHorseArmor = true;
    public int netheriteHorseArmorDefense = 15;

    // Exploration
    @SectionHeader("Exploration")
    @MelodyConfigCondition.ResourceConfigName(config_name = "item/map_book")  public boolean mapBook = true;
}
