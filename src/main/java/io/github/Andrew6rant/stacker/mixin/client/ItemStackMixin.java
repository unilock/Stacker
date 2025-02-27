package io.github.Andrew6rant.stacker.mixin.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;

@Environment(EnvType.CLIENT)
@Mixin(Item.Settings.class)
public class ItemStackMixin {
    @WrapMethod(method = "maxCount")
    private Item.Settings forceCount(int maxCount, Operation<Item.Settings> original) {
        return RenderSystem.isOnRenderThread() ? original.call(Integer.MAX_VALUE) : original.call(maxCount);
    }
}
