package net.fabricmc.tiny.mixin.mixins.world;

import net.fabricmc.tiny.event.EntityEvent;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow public abstract Vec3d getPos();

    private double lastPosX = Double.NaN, lastPosY = Double.NaN, lastPosZ = Double.NaN;

    @Inject(at = @At("HEAD"), method = "tick")
    private void tick(CallbackInfo info)
    {
        Vec3d pos = getPos();
        if (pos.x != lastPosX || pos.y != lastPosY || pos.z != lastPosZ)
            EntityEvent.INSTANCE.onEntityMove((Entity) (Object) this, lastPosX, lastPosY, lastPosZ);
        else
            EntityEvent.INSTANCE.onEntityTick((Entity) (Object) this);
        lastPosX = pos.x;
        lastPosY = pos.y;
        lastPosZ = pos.z;
    }

}
