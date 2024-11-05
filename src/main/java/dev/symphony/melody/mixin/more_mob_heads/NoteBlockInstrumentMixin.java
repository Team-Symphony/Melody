package dev.symphony.melody.mixin.more_mob_heads;

import dev.symphony.melody.util.MelodySkullType;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(NoteBlockInstrument.class)
public class NoteBlockInstrumentMixin {

	@Mutable @Shadow @Final private static NoteBlockInstrument[] field_12652;

	@Invoker("<init>")
	private static NoteBlockInstrument invokeInit(String name, int ordinal, String id, RegistryEntry<SoundEvent> sound, NoteBlockInstrument.Type type) {
		throw new AssertionError();
	}

	@Unique
	private static NoteBlockInstrument create(String name, int ordinal, String id, RegistryEntry<SoundEvent> sound) {
		return invokeInit(name, ordinal, id, sound, NoteBlockInstrument.Type.MOB_HEAD);
	}

    @Inject(method = "<clinit>", at = @At("TAIL"))
	private static void addInstruments(CallbackInfo ci) {
        List<NoteBlockInstrument> list = new ArrayList<>(List.of(field_12652));

		for (MelodySkullType skullType : MelodySkullType.values())
			list.add(create(skullType.name(), field_12652.length + skullType.ordinal(), skullType.asString(), skullType.soundEvent));

		field_12652 = list.toArray(NoteBlockInstrument[]::new);
	}

}