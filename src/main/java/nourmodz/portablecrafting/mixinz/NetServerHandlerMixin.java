package nourmodz.portablecrafting.mixinz;

import net.minecraft.core.net.ICommandListener;
import net.minecraft.core.net.handler.NetHandler;
import net.minecraft.core.net.packet.Packet250CustomPayload;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.server.net.handler.NetServerHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static nourmodz.portablecrafting.PortableCrafting.findWorkbench;

@Mixin(value = NetServerHandler.class,remap = false)
public class NetServerHandlerMixin extends NetHandler implements ICommandListener {
	@Shadow
	private EntityPlayerMP playerEntity;

	@Shadow
	public void log(String string) {
	}

	@Shadow
	public String getUsername() {
		return "";
	}

	@Shadow
	public boolean isServerHandler() {
		return false;
	}

	@Inject(method = "handleCustomPayload",at=@At("HEAD"))
	public void handleCustomPayload(Packet250CustomPayload packet, CallbackInfo ci) {
		if ("nourmodz|OW".equals(packet.channel)) {
			if (findWorkbench(playerEntity)) {
				playerEntity.displayGUIWorkbench((int) playerEntity.x, (int) playerEntity.y, (int) playerEntity.z);
			} else {
				System.out.println(playerEntity.username + " ("+playerEntity.nickname+") tried opening a workbench without having one.");
			}
		}
	}

}
