package nourmodz.portablecrafting.mixinz;

import net.minecraft.core.net.command.Command;
import net.minecraft.core.net.command.Commands;
import nourmodz.portablecrafting.CraftCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(value = Commands.class, remap = false)
public class CommandsMixin {
	@Shadow
	public static List<Command> commands = new ArrayList();

	@Inject(method = "initCommands", at = @At("TAIL"))
	private static void nourCommands(CallbackInfo ci) {
		commands.add(new CraftCommand());
	}

}
