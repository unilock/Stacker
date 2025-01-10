package io.github.Andrew6rant.stacker.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.StewItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StewItem.class)
public class StewItemMixin { // This mixin works for soups as well
    @Inject(method = "finishUsing", at = @At(value = "NEW", target = "(Lnet/minecraft/item/ItemConvertible;)Lnet/minecraft/item/ItemStack;"), cancellable = true)
    private void stackableStew(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir){
        ((PlayerEntity) user).getInventory().offerOrDrop(Items.BOWL.getDefaultStack());
        cir.setReturnValue(stack);
    }
}
