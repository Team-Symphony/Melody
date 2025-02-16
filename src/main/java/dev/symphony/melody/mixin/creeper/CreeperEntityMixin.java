package dev.symphony.melody.mixin.creeper;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.symphony.melody.explosion.CreeperExplosionBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreeperEntity.class)
public class CreeperEntityMixin {

    @Shadow private int explosionRadius;

    @WrapOperation(method = "explode", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/world/World$ExplosionSourceType;)Lnet/minecraft/world/explosion/Explosion;"))
    private Explosion createExplosionWithCustomBehavior(World instance, Entity entity, double x, double y, double z, float power, World.ExplosionSourceType explosionSourceType, Operation<Explosion> original) {
        DamageSource source = Explosion.createDamageSource(instance, entity);
        ExplosionBehavior explosionBehavior = new CreeperExplosionBehavior(1.5F);

        return instance.createExplosion(entity, source, explosionBehavior, x, y, z, power, false, explosionSourceType);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void modifyExplosionRadius(EntityType<CreeperEntity> entityType, World world, CallbackInfo ci) {
        this.explosionRadius = 2;
    }

    @WrapWithCondition(method = "setTarget", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/HostileEntity;setTarget(Lnet/minecraft/entity/LivingEntity;)V"))
    private boolean canSetTarget(HostileEntity instance, LivingEntity entity) {
        return entity instanceof PlayerEntity;
    }

}
