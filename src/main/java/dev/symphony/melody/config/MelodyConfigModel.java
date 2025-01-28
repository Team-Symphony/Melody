package dev.symphony.melody.config;


import dev.symphony.melody.Melody;
import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.*;

@Modmenu(modId = Melody.MOD_ID)
@Config(name = "melody", wrapperName = "MelodyConfig")
public class MelodyConfigModel {


    // Transportation 🏳️‍⚧️
    @SectionHeader("Trans")

    @Nest @Expanded public TransSaddledCat transSaddledCat = new TransSaddledCat();
    public static class TransSaddledCat {
        @Sync(Option.SyncMode.OVERRIDE_CLIENT) public boolean vehiclesMoveThroughLeaves = true;

        @Nest public ThroughLeavesCat throughLeavesCat = new ThroughLeavesCat();
        public static class ThroughLeavesCat {
            @RangeConstraint(min = 0f, max = 1f) @Sync(Option.SyncMode.OVERRIDE_CLIENT) public float leafSpeedFactor = 0.85f;
        }

        @MelodyConfigCondition.ResourceConfigName(config_name = "item/netherite_horse_armor")  public boolean netheriteHorseArmor = true;

        @Nest public NethHorseArmorCat nethHorseArmorCat = new NethHorseArmorCat();
        public static class NethHorseArmorCat {
            public int netheriteHorseArmorDefense = 15;
        }
    }




    // Exploration
    @SectionHeader("Exploration")
    @MelodyConfigCondition.ResourceConfigName(config_name = "item/map_book")  public boolean mapBook = true;
}
