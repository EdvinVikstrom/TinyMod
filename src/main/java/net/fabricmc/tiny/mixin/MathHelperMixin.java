package net.fabricmc.tiny.mixin;

import net.fabricmc.tiny.Config;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MathHelper.class)
public class MathHelperMixin {

    private static final int FAST_MATH_ABLE_SIZE = Short.MAX_VALUE / 4;

    private static final float[] SIN_TABLE;
    private static final float[] COS_TABLE;

    @Inject(at = @At("HEAD"), method = "sin", cancellable = true)
    private static void sin(float f, CallbackInfoReturnable<Float> info) {
        if (Config.getBoolean("fastMath").get())
            info.setReturnValue(SIN_TABLE[(int) ((Math.abs(f) % (Math.PI * 2)) / (Math.PI * 2)) * FAST_MATH_ABLE_SIZE]);
    }

    @Inject(at = @At("HEAD"), method = "cos", cancellable = true)
    private static void cos(float f, CallbackInfoReturnable<Float> info) {
        if (Config.getBoolean("fastMath").get())
            info.setReturnValue(COS_TABLE[(int) ((Math.abs(f) % (Math.PI * 2)) / (Math.PI * 2)) * FAST_MATH_ABLE_SIZE]);
    }

    static {
        int sineTableSize = Config.getBoolean("fastMath").get() ? FAST_MATH_ABLE_SIZE : 65536;
        SIN_TABLE = new float[sineTableSize];
        COS_TABLE = new float[sineTableSize];
        for(int i = 0; i < sineTableSize; i++)
        {
            double value = ((double) i / (double) sineTableSize) * (Math.PI * 2);
            SIN_TABLE[i] = (float) Math.sin(value);
            COS_TABLE[i] = (float) Math.cos(value);
        }
    }

}
