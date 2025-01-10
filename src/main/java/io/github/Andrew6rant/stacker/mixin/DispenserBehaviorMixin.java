package io.github.Andrew6rant.stacker.mixin;

import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPointer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net/minecraft/block/dispenser/DispenserBehavior$8")
public class DispenserBehaviorMixin {
    // Credit to ZoeyTheEgoist for the original code behind this mixin
    @Shadow
    @Final
    private ItemDispenserBehavior fallbackBehavior;

    @Inject(method = "dispenseSilently", at = @At(value = "RETURN", ordinal = 0), cancellable = true)
    private void dispenseSilently(BlockPointer pointer, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        if (stack.getCount() > 1) {
            ItemStack newStack = stack.copy();
            newStack.decrement(1);

            if (((DispenserBlockEntity) pointer.getBlockEntity()).addToFirstFreeSlot(Items.BUCKET.getDefaultStack()) < 0) {
                this.fallbackBehavior.dispense(pointer, Items.BUCKET.getDefaultStack());
            }

            cir.setReturnValue(newStack);
        }
    }
}
