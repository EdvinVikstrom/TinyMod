package net.fabricmc.tiny.mixin.render;

import net.fabricmc.tiny.Config;
import net.fabricmc.tiny.utils.Maths;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Quaternion;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ItemEntityRenderer.class)
public abstract class ItemEntityRenderingMixin extends EntityRenderer<ItemEntity> {

    protected ItemEntityRenderingMixin(EntityRenderDispatcher dispatcher)
    {
        super(dispatcher);
    }

    private float count = 0;
    @Final @Shadow private Random random;

    @Inject(at = @At(value = "INVOKE",
            shift = At.Shift.AFTER,
            target = "Lnet/minecraft/client/util/math/MatrixStack;multiply(Lnet/minecraft/util/math/Quaternion;)V"),
            method = "render")
    private void rotateItemX(ItemEntity itemEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo info)
    {
        if (Config.DROPPED_ITEM_RENDERING.get() != 0)
            matrixStack.multiply(Vector3f.POSITIVE_X.getRadialQuaternion(itemEntity.isOnGround() ? Maths.PI05F : count * 0.25F));
    }

    @Redirect(at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/util/math/MatrixStack;multiply(Lnet/minecraft/util/math/Quaternion;)V"),
            method = "render")
    private void removeItemRotation(MatrixStack matrixStack, Quaternion quaternion)
    {
        if (Config.DROPPED_ITEM_RENDERING.get() == 0)
            matrixStack.multiply(quaternion);
    }

    @Redirect(at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/util/math/MatrixStack;translate(DDD)V"),
            method = "render")
    private void removeItemFloating(MatrixStack matrixStack, double x, double y, double z)
    {
        count+=0.005F;
        count = count % Maths.PI2F;
        if (Config.DROPPED_ITEM_RENDERING.get() == 0)
            matrixStack.translate(x, y, z);
        else
            matrixStack.translate(0.0D, 0.025D, 0.0D);
    }

    @Redirect(at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/render/item/ItemRenderer;renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V"),
            method = "render")
    private void renderItem(ItemRenderer itemRenderer, ItemStack stack, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model)
    {
        if (Config.DROPPED_ITEM_RENDERING.get() == 2)
        {
            double offset = model.hasDepth() ? 0.25D : 0.03D;
            double x = 0, y = 0;
            for (int i = 0; i < stack.getCount(); i++)
            {
                itemRenderer.renderItem(stack, renderMode, leftHanded, matrices, vertexConsumers, light, overlay, model);
                double offX = random.nextDouble() * 0.015D;
                double offY = random.nextDouble() * 0.015D;
                if (random.nextBoolean()) offX*=-1;
                if (random.nextBoolean()) offY*=-1;
                matrices.translate(offX, offY, -offset);
                x+=offX;
                y+=offY;
            }
        }else
            itemRenderer.renderItem(stack, renderMode, leftHanded, matrices, vertexConsumers, light, overlay, model);
    }

    @Inject(at = @At("HEAD"), method = "getRenderedAmount", cancellable = true)
    private void getRenderedAmount(ItemStack itemStack, CallbackInfoReturnable<Integer> info)
    {
        if (Config.DROPPED_ITEM_RENDERING.get() == 2)
            info.setReturnValue(1);
    }

}
