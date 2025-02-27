package io.github.Andrew6rant.stacker.mixin;

import net.minecraft.server.command.GiveCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(GiveCommand.class)
public class GiveCommandMixin {
	@ModifyVariable(method = "execute", at = @At(value = "INVOKE", target = "Lnet/minecraft/command/argument/ItemStackArgument;createStack(IZ)Lnet/minecraft/item/ItemStack;", ordinal = 0), index = 5)
	private static int j(int value) {
		return Integer.MAX_VALUE;
	}
}
