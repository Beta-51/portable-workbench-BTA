package nourmodz.portablecrafting.mixinz;

import net.minecraft.client.Minecraft;
import net.minecraft.core.InventoryAction;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockWorkbench;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.packet.Packet100OpenWindow;
import net.minecraft.core.net.packet.Packet101CloseWindow;
import net.minecraft.core.player.inventory.Container;
import net.minecraft.core.player.inventory.ContainerWorkbench;
import net.minecraft.core.player.inventory.InventoryCrafting;
import net.minecraft.core.player.inventory.slot.Slot;
import net.minecraft.core.world.World;
import net.minecraft.server.MinecraftServer;
import nourmodz.portablecrafting.PortableCrafting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = ContainerWorkbench.class,remap=false)
public class ContainerWorkbenchMixin extends Container {@Shadow
	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
	@Shadow
	private World field_20133_c;
	@Shadow
	private int field_20132_h;
	@Shadow
	private int field_20131_i;
	@Shadow
	private int field_20130_j;
	@Shadow
	public List<Integer> getMoveSlots(InventoryAction inventoryAction, Slot slot, int i, EntityPlayer entityPlayer) {
		return null;
	}

	@Shadow
	public List<Integer> getTargetSlots(InventoryAction inventoryAction, Slot slot, int i, EntityPlayer entityPlayer) {
		return null;
	}

	@Overwrite
	public boolean isUsableByPlayer(EntityPlayer entityplayer) {
		if (entityplayer == null) return false;
		if (this.field_20133_c.getBlockId(this.field_20132_h, this.field_20131_i, this.field_20130_j) != Block.workbench.id) {
			if (!PortableCrafting.findWorkbench(entityplayer)) {
				if (MinecraftServer.getInstance() != null) {
					MinecraftServer.getInstance().playerList.sendPacketToPlayer(entityplayer.username,new Packet100OpenWindow(-1, 0, "", 0));
					MinecraftServer.getInstance().playerList.sendPacketToPlayer(entityplayer.username,new Packet101CloseWindow(-1));
				}
				onCraftGuiClosed(entityplayer);
				return false;
			}
			return true;
		} else {
			return entityplayer.distanceToSqr((double)this.field_20132_h + 0.5, (double)this.field_20131_i + 0.5, (double)this.field_20130_j + 0.5) <= 64.0;
		}
	}
}
