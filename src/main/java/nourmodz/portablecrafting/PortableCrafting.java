package nourmodz.portablecrafting;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockWorkbench;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.InventoryPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;

import javax.annotation.Nullable;
import java.util.Arrays;


public class PortableCrafting implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint {
    public static final String MOD_ID = "portablecrafting";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	protected static Minecraft mc = Minecraft.getMinecraft(Minecraft.class);

    @Override
    public void onInitialize() {
        LOGGER.info("ExampleMod initialized.");
    }

	@Override
	public void beforeGameStart() {

	}

	@Override
	public void afterGameStart() {

	}

	@Override
	public void onRecipesReady() {

	}

	@Override
	public void initNamespaces() {

	}

	public static boolean findWorkbench(EntityPlayer player) {
		//if (player.inventorySlots.inventoryItemStacks.contains(BlockWorkbench.workbench.getDefaultStack())) return true;
		//if (mc.thePlayer.craftingInventory.inventoryItemStacks.contains(BlockWorkbench.workbench.getDefaultStack())) return true;
		for (int i = 0; i < player.craftingInventory.inventoryItemStacks.size(); i++) {
			if (!player.craftingInventory.getSlot(i).hasStack()) {
				continue;
			}
			if (player.craftingInventory.getSlot(i).getStack().itemID == BlockWorkbench.workbench.id) {
				return true;
			}
		}
		return player.inventory.getHeldItemStack() != null && player.inventory.getHeldItemStack().itemID == Block.workbench.id;
	}

}
