package nourmodz.portablecrafting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.client.gui.GuiCrafting;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.net.command.Command;
import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Shadow;

import static nourmodz.portablecrafting.PortableCrafting.mc;

public class CraftCommand extends Command {
	public CraftCommand() {
		super("craft");
	}


	@Override
	public boolean execute(CommandHandler commandHandler, CommandSender commandSender, String[] strings) {
		if (commandSender.isConsole()) {
			commandSender.sendMessage("You may only use this command in-game!");
			return true;
		}
		EntityPlayer player = commandSender.getPlayer();
		boolean workbenchFound = PortableCrafting.findWorkbench(player);
		if (workbenchFound) {
			if (!commandHandler.isServer()) {
				//mc.displayGuiScreen(null);
				//mc.thePlayer.closeScreen();
				//mc.isGamePaused = false;
				mc.thePlayer.displayGUIWorkbench((int) player.x,(int) player.y, (int) player.z);
				//mc.displayGuiScreen(new GuiCrafting(mc.thePlayer.inventory, mc.theWorld, (int) player.x,(int) player.y, (int) player.z));
				//mc.thePlayer.displayGUIWorkbench((int) player.x,(int) player.y, (int) player.z);
				//mc.displayGuiScreen(new GuiCrafting(player.inventory, mc.theWorld, (int) player.x,(int) player.y, (int) player.z));
				//player.displayGUIWorkbench((int) player.x, (int) player.y, (int) player.z);
				//player.displayGUIWorkbench((int) player.x, (int) player.y, (int) player.z);
				//player.sendMessage("wb found");
				return true;
			} else {
				player.displayGUIWorkbench((int) player.x, (int) player.y, (int) player.z);
				return true;
			}
		} else {
			commandSender.sendMessage("You do not have a crafting table in your inventory!");
		}
		return true;
	}

	@Override
	public boolean opRequired(String[] strings) {
		return false;
	}

	@Override
	public void sendCommandSyntax(CommandHandler commandHandler, CommandSender commandSender) {
		commandSender.sendMessage("/craft");
	}
}
