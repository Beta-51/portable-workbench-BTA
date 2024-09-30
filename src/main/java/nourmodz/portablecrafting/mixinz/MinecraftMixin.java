package nourmodz.portablecrafting.mixinz;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.ClientSkinVariantList;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.client.gui.*;
import net.minecraft.client.input.InputDevice;
import net.minecraft.client.input.InputType;
import net.minecraft.client.net.thread.ThreadSleepForeverClient;
import net.minecraft.client.render.LoadingScreenRenderer;
import net.minecraft.client.render.PostProcessingManager;
import net.minecraft.client.render.model.ModelBiped;
import net.minecraft.client.render.window.GameWindow;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.util.helper.ScreenshotHelper;
import net.minecraft.core.Global;
import net.minecraft.core.MinecraftAccessor;
import net.minecraft.core.Timer;
import net.minecraft.core.entity.SkinVariantList;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.net.packet.Packet101CloseWindow;
import net.minecraft.core.net.packet.Packet250CustomPayload;
import net.minecraft.core.util.helper.LogPrintStream;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.IChunkLoader;
import net.minecraft.core.world.chunk.provider.IChunkProvider;
import nourmodz.portablecrafting.PortableCrafting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;

import static nourmodz.portablecrafting.PortableCrafting.findWorkbench;

@Mixin(value = Minecraft.class,remap = false)
public class MinecraftMixin implements Runnable, MinecraftAccessor {//she licked me like a lollipop
	private static Minecraft INSTANCE;

	@Inject(method = "shutdown",at=@At("HEAD"))
	public void shutdown(CallbackInfo ci) {
		if (new File(PortableCrafting.CFG_FILE_PATH).delete()) {
			PortableCrafting.saveandloadConfig();
		}
	}

	@Inject(method="checkBoundInputs",at=@At("HEAD"))
	private void checkBoundInputs(InputDevice currentInputDevice, CallbackInfoReturnable cir) {
		if (PortableCrafting.openWorkbench.isPressEvent(currentInputDevice)) {
			if (!INSTANCE.isMultiplayerWorld()) {
				if (INSTANCE.currentScreen == null && findWorkbench(INSTANCE.thePlayer)) {
					INSTANCE.thePlayer.displayGUIWorkbench((int) INSTANCE.thePlayer.x, (int) INSTANCE.thePlayer.y, (int) INSTANCE.thePlayer.z);
				} else if (!findWorkbench(INSTANCE.thePlayer)) {
					INSTANCE.thePlayer.sendMessage("You do not have a crafting table in your inventory!");
				}
			} else {
				if (INSTANCE.currentScreen == null && findWorkbench(INSTANCE.thePlayer)) {
					INSTANCE.getSendQueue().addToSendQueue(new Packet250CustomPayload("nourmodz|OW",new byte[0])); //shortened because the limit is 20 characters
				} else if (!findWorkbench(INSTANCE.thePlayer)) {
					INSTANCE.thePlayer.sendMessage("You do not have a crafting table in your inventory!");
				}
			}
		}
	}

	@Shadow
	public void run() {

	}

	@Shadow
	public File getMinecraftDir() {
		return null;
	}

	@Shadow
	public IChunkProvider createChunkProvider(World world, IChunkLoader iChunkLoader) {
		return null;
	}

	@Shadow
	public int getAutosaveTimer() {
		return 0;
	}

	@Shadow
	public SkinVariantList getSkinVariantList() {
		return null;
	}

	@Shadow
	public String getMinecraftVersion() {
		return "";
	}
}
