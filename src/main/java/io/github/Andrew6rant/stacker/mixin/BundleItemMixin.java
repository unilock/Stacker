package io.github.Andrew6rant.stacker.mixin;

import net.minecraft.item.BundleItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import static io.github.Andrew6rant.stacker.Stacker.CONFIG;

/**
 * Modifies BundleItem's magic numbers to use Stacker's configured 'maxStacker' value
 */
@Mixin(BundleItem.class)
public abstract class BundleItemMixin {
    @ModifyConstant(method = "getAmountFilled", constant = @Constant(floatValue = 64.0f))
    private static float replaceAmountFilled(float constant) {
        return (float) CONFIG.maxStacker.value();
    }

    @ModifyConstant(method = {"appendTooltip", "getItemBarStep", "onStackClicked"}, constant = @Constant(intValue = 64))
    private int replaceConstants(int constant) {
        return CONFIG.maxStacker.value();
    }

    @ModifyConstant(method = {"addToBundle", "getItemOccupancy"}, constant = @Constant(intValue = 64))
    private static int replaceStaticConstants(int constant) {
        return CONFIG.maxStacker.value();
    }
}
