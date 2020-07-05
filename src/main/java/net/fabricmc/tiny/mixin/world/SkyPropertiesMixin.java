package net.fabricmc.tiny.mixin.world;

import net.fabricmc.tiny.Config;
import net.fabricmc.tiny.utils.property.properties.IntProperty;
import net.minecraft.client.render.SkyProperties;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SkyProperties.class)
public class SkyPropertiesMixin {

    @Final @Shadow private float cloudsHeight;

    @Inject(at = @At("HEAD"), method = "getCloudsHeight", cancellable = true)
    private void getCloudsHeight(CallbackInfoReturnable<Float> info)
    {
        IntProperty height = Config.getInt("cloudHeight");
        if (height.get() != 100)
        {
            float m = (float) height.get() / 100.0F;
            info.setReturnValue(cloudsHeight * m);
        }
    }

}
