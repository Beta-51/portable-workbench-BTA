package nourmodz.portablecrafting.mixinz;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.Container;
import net.minecraft.core.player.inventory.InventoryPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = Container.class,remap = false)
public class ContainerMixin {


	@Overwrite
	public void onCraftGuiClosed(EntityPlayer player) { // casually leaks crash exploit (only server to client crash)
		if (player == null) return;
		InventoryPlayer inventory = player.inventory;
		if (inventory.getHeldItemStack() != null) {
			ItemStack stack = inventory.getHeldItemStack();
			inventory.setHeldItemStack((ItemStack)null);
			this.storeOrDropItem(player, stack);
		}

	}
	@Shadow
	public void storeOrDropItem(EntityPlayer player, ItemStack stack) {}

}
