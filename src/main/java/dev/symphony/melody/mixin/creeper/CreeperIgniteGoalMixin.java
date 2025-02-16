package dev.symphony.melody.mixin.creeper;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.symphony.melody.config.MelodyConfig;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.CreeperIgniteGoal;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CreeperIgniteGoal.class)
public class CreeperIgniteGoalMixin {

    @Unique
    private static final double TOLERANCE = 0.025;

    @Shadow @Final private CreeperEntity creeper;

    @ModifyReturnValue(method = "canStart", at = @At(value = "RETURN"))
    private boolean canStart$melody(boolean original) {
        if (!MelodyConfig.accessibleCreepers || !MelodyConfig.creeperIgnitionRequiresSight)
            return original;

        return original && this.isTargetLookingAtCreeper();
    }

    @ModifyExpressionValue(method = "tick", at = @At(value = "CONSTANT", args = "doubleValue=49.0"))
    private double modifySquaredCancelDistance(double original) {
        return MelodyConfig.accessibleCreepers ? MathHelper.square(MelodyConfig.creeperDefuseDistance) : original;
    }

    @Unique
    private boolean isTargetLookingAtCreeper() {
        LivingEntity target = this.creeper.getTarget();

        if (target == null)
            return false;

        Vec3d rotationVec = target.getRotationVec(1.0F).normalize();

        double[] checkedYs = new double[]{
            this.creeper.getEyeY(),
            this.creeper.getY() + 0.5 * this.creeper.getScale(),
            (this.creeper.getEyeY() + this.creeper.getY()) / 2.0
        };

        return this.isEntityLookingAtCreeper(target, rotationVec, TOLERANCE, checkedYs);
    }

    @Unique
    private boolean isEntityLookingAtCreeper(@NotNull LivingEntity entity, Vec3d rotationVec, double tolerance, double... checkedYs) {
        for (double y : checkedYs) {
            Vec3d vec3d = new Vec3d(this.creeper.getX() - entity.getX(), y - entity.getEyeY(), this.creeper.getZ() - entity.getZ());
            double g = rotationVec.dotProduct(vec3d.normalize());

            if (g > 1.0 - tolerance && entity.canSee(this.creeper))
                return true;
        }

        return false;
    }

}
