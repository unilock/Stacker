package io.github.Andrew6rant.stacker.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static io.github.Andrew6rant.stacker.Stacker.CONFIG;

@Mixin(targets = "net.devtech.stacc.ItemCountRenderHandler")
@Pseudo
@Environment(EnvType.CLIENT)
public class ItemRenderScaleMixin {
    @Inject(method = "scale", at = @At("RETURN"), cancellable = true, remap = false)
    private void forceScale(CallbackInfoReturnable<Float> cir) {
        if (!CONFIG.fontOverride.value()) {
            cir.setReturnValue((float) ((CONFIG.itemCountScaleInt.value() + 50) / 100));
        }
    }
}
