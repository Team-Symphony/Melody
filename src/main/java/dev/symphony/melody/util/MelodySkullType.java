package dev.symphony.melody.util;

import dev.symphony.melody.sound.ModSoundEvents;
import net.minecraft.block.SkullBlock;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;

public enum MelodySkullType implements SkullBlock.SkullType {

    BLAZE("blaze", ModSoundEvents.BLOCK_NOTE_BLOCK_IMITATE_BLAZE),
    BOGGED("bogged", ModSoundEvents.BLOCK_NOTE_BLOCK_IMITATE_BOGGED),
    BREEZE("breeze", ModSoundEvents.BLOCK_NOTE_BLOCK_IMITATE_BREEZE),
    DROWNED("drowned", ModSoundEvents.BLOCK_NOTE_BLOCK_IMITATE_DROWNED),
    ENDERMAN("enderman", ModSoundEvents.BLOCK_NOTE_BLOCK_IMITATE_ENDERMAN),
    EVOKER("evoker", ModSoundEvents.BLOCK_NOTE_BLOCK_IMITATE_EVOKER),
    HOGLIN("hoglin", ModSoundEvents.BLOCK_NOTE_BLOCK_IMITATE_HOGLIN, true),
    HUSK("husk", ModSoundEvents.BLOCK_NOTE_BLOCK_IMITATE_HUSK),
    PIGLIN_BRUTE("piglin_brute", ModSoundEvents.BLOCK_NOTE_BLOCK_IMITATE_PIGLIN_BRUTE, true),
    PILLAGER("pillager", ModSoundEvents.BLOCK_NOTE_BLOCK_IMITATE_PILLAGER),
    STRAY("stray", ModSoundEvents.BLOCK_NOTE_BLOCK_IMITATE_STRAY),
    VINDICATOR("vindicator", ModSoundEvents.BLOCK_NOTE_BLOCK_IMITATE_VINDICATOR),
    WITCH("witch", ModSoundEvents.BLOCK_NOTE_BLOCK_IMITATE_WITCH, true),
    ZOGLIN("zoglin", ModSoundEvents.BLOCK_NOTE_BLOCK_IMITATE_ZOGLIN, true),
    ZOMBIE_VILLAGER("zombie_villager", ModSoundEvents.BLOCK_NOTE_BLOCK_IMITATE_ZOMBIE_VILLAGER),
    ZOMBIFIED_PIGLIN("zombified_piglin", ModSoundEvents.BLOCK_NOTE_BLOCK_IMITATE_ZOMBIFIED_PIGLIN, true);

    private final String id;
    public final RegistryEntry.Reference<SoundEvent> soundEvent;
    private final boolean tick;

    MelodySkullType(String id, RegistryEntry.Reference<SoundEvent> soundEvent) {
        this(id, soundEvent, false);
    }

    MelodySkullType(String id, RegistryEntry.Reference<SoundEvent> soundEvent, boolean tick) {
        this.id = id;
        this.soundEvent = soundEvent;
        this.tick = tick;

        TYPES.put(id, this);
    }

    @Override
    public String asString() {
        return this.id;
    }

    public boolean shouldTick() {
        return this.tick;
    }

    public boolean isPiglin() {
        return this == PIGLIN_BRUTE || this == ZOMBIFIED_PIGLIN;
    }

}
