package nourmodz.portablecrafting.mixinz;

import net.minecraft.client.gui.GuiContainer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.core.net.packet.Packet101CloseWindow;
import nourmodz.portablecrafting.PortableCrafting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GuiContainer.class,remap = false)
public class GuiContainerMixin extends GuiScreen {

	@Inject(method = "keyTyped",at=@At("HEAD"))
	public void keyTyped(char c, int i, int mouseX, int mouseY, CallbackInfo ci) {
		if (i == 1 || PortableCrafting.openWorkbench.isKeyboardKey(i) || i == 14) {
			if (mc.isMultiplayerWorld()) {
				mc.getSendQueue().addToSendQueue(new Packet101CloseWindow(mc.thePlayer.craftingInventory.windowId));
			}
			this.mc.thePlayer.closeScreen();
		}
	}

}
