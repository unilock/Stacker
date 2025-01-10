package io.github.Andrew6rant.stacker.mixin;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.FurnaceFuelSlot;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FurnaceFuelSlot.class)
public abstract class LimitFurnaceStackMixin extends Slot {
    public LimitFurnaceStackMixin(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        throw new AssertionError("LimitFurnaceStackMixin wrongly instantiated!");
    }

    @Inject(method = "isBucket", at = @At("TAIL"), cancellable = true)
    private static void isLavaBucket(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValueZ() && stack.isOf(Items.LAVA_BUCKET)) {
            cir.setReturnValue(true);
        }
    }
}
