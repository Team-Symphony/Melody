package dev.symphony.melody.sound;

import dev.symphony.melody.Melody;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSoundEvents {

    public static final RegistryEntry.Reference<SoundEvent> BLOCK_NOTE_BLOCK_IMITATE_BLAZE = registerReference("block.note_block.imitate.blaze");
    public static final RegistryEntry.Reference<SoundEvent> BLOCK_NOTE_BLOCK_IMITATE_BOGGED = registerReference("block.note_block.imitate.bogged");
    public static final RegistryEntry.Reference<SoundEvent> BLOCK_NOTE_BLOCK_IMITATE_BREEZE = registerReference("block.note_block.imitate.breeze");
    public static final RegistryEntry.Reference<SoundEvent> BLOCK_NOTE_BLOCK_IMITATE_DROWNED = registerReference("block.note_block.imitate.drowned");
    public static final RegistryEntry.Reference<SoundEvent> BLOCK_NOTE_BLOCK_IMITATE_ENDERMAN = registerReference("block.note_block.imitate.enderman");
    public static final RegistryEntry.Reference<SoundEvent> BLOCK_NOTE_BLOCK_IMITATE_EVOKER = registerReference("block.note_block.imitate.evoker");
    public static final RegistryEntry.Reference<SoundEvent> BLOCK_NOTE_BLOCK_IMITATE_HOGLIN = registerReference("block.note_block.imitate.hoglin");
    public static final RegistryEntry.Reference<SoundEvent> BLOCK_NOTE_BLOCK_IMITATE_HUSK = registerReference("block.note_block.imitate.husk");
    public static final RegistryEntry.Reference<SoundEvent> BLOCK_NOTE_BLOCK_IMITATE_PIGLIN_BRUTE = registerReference("block.note_block.imitate.piglin_brute");
    public static final RegistryEntry.Reference<SoundEvent> BLOCK_NOTE_BLOCK_IMITATE_PILLAGER = registerReference("block.note_block.imitate.pillager");
    public static final RegistryEntry.Reference<SoundEvent> BLOCK_NOTE_BLOCK_IMITATE_STRAY = registerReference("block.note_block.imitate.stray");
    public static final RegistryEntry.Reference<SoundEvent> BLOCK_NOTE_BLOCK_IMITATE_VINDICATOR = registerReference("block.note_block.imitate.vindicator");
    public static final RegistryEntry.Reference<SoundEvent> BLOCK_NOTE_BLOCK_IMITATE_WITCH = registerReference("block.note_block.imitate.witch");
    public static final RegistryEntry.Reference<SoundEvent> BLOCK_NOTE_BLOCK_IMITATE_ZOGLIN = registerReference("block.note_block.imitate.zoglin");
    public static final RegistryEntry.Reference<SoundEvent> BLOCK_NOTE_BLOCK_IMITATE_ZOMBIFIED_PIGLIN = registerReference("block.note_block.imitate.zombified_piglin");
    public static final RegistryEntry.Reference<SoundEvent> BLOCK_NOTE_BLOCK_IMITATE_ZOMBIE_VILLAGER = registerReference("block.note_block.imitate.zombie_villager");

    private static RegistryEntry.Reference<SoundEvent> registerReference(String name) {
        Identifier id = Melody.id(name);
        return Registry.registerReference(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSoundEvents() {
        Melody.LOGGER.info("Registering SoundEvents for: " + Melody.MOD_ID);
    }

}
