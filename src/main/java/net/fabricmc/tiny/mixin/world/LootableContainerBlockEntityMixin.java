package net.fabricmc.tiny.mixin.world;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(LootableContainerBlockEntity.class)
public abstract class LootableContainerBlockEntityMixin extends LockableContainerBlockEntity {

    private boolean empty = false, needsUpdate = true;

    protected LootableContainerBlockEntityMixin(BlockEntityType<?> blockEntityType)
    {
        super(blockEntityType);
    }

    @Shadow
    public void checkLootInteraction(@Nullable PlayerEntity player)
    {

    }

    @Shadow
    protected abstract DefaultedList<ItemStack> getInvStackList();

    @Inject(at = @At("HEAD"), method = "isEmpty", cancellable = true)
    private void isEmpty(CallbackInfoReturnable<Boolean> info)
    {
        /*
        if (Config.getBoolean("optimizedInventory").get())
        {
            if (!needsUpdate)
                info.setReturnValue(empty);
            this.checkLootInteraction(null);
            empty = this.getInvStackList().stream().allMatch(ItemStack::isEmpty);
            needsUpdate = false;
            info.setReturnValue(empty);
        }
         */
    }

    @Inject(at = @At("RETURN"), method = "removeStack(II)Lnet/minecraft/item/ItemStack;")
    private void removeStack(int slot, int amount, CallbackInfoReturnable<ItemStack> info)
    {
        needsUpdate = true;
    }

    @Inject(at = @At("RETURN"), method = "removeStack(I)Lnet/minecraft/item/ItemStack;")
    private void removeStack(int slot, CallbackInfoReturnable<ItemStack> info)
    {
        needsUpdate = true;
    }

    @Inject(at = @At("RETURN"), method = "setStack")
    private void setStack(int slot, ItemStack stack, CallbackInfo info)
    {
        needsUpdate = true;
    }

    @Inject(at = @At("RETURN"), method = "clear")
    private void clear(CallbackInfo info)
    {
        needsUpdate = true;
    }

}
