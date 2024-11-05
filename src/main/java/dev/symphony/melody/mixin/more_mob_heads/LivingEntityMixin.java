package dev.symphony.melody.mixin.more_mob_heads;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.symphony.melody.util.Beheadable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "dropEquipment", at = @At("HEAD"))
    private void dropHeadOnChargedCreeperExplode(ServerWorld world, DamageSource source, boolean causedByPlayer, CallbackInfo ci) {
        if (!(this instanceof Beheadable<?> beheadable))
            return;

        if (source.getAttacker() instanceof CreeperEntity creeperEntity && creeperEntity.shouldDropHead()) {
            ItemStack itemStack = new ItemStack(beheadable.getHeadItem());
            creeperEntity.onHeadDropped();
            this.dropStack(itemStack);
        }
    }

    @ModifyExpressionValue(method = "getAttackDistanceScalingFactor", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 0))
    private boolean shouldDecreaseAggroRange(boolean original, @Local ItemStack itemStack, @Local EntityType<?> entityType) {
        if (!(this instanceof Beheadable<?> beheadable))
            return original;

        return original || entityType == beheadable.getEntityType() && itemStack.isOf(beheadable.getHeadItem());
    }

}
