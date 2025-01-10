package io.github.Andrew6rant.stacker.mixin;

import net.minecraft.block.entity.JukeboxBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MusicDiscItem.class)
public class MusicDiscItemMixin {
    @Redirect(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/JukeboxBlockEntity;setStack(Lnet/minecraft/item/ItemStack;)V"))
    private void setCountToOne(JukeboxBlockEntity jukeboxBlockEntity, ItemStack stack) {
        jukeboxBlockEntity.setStack(stack.copyWithCount(1));
    }
}
