package nourmodz.portablecrafting;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiCrafting;
import net.minecraft.client.gui.options.components.KeyBindingComponent;
import net.minecraft.client.input.InputDevice;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockWorkbench;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.InventoryPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.ModVersionHelper;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;
import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Arrays;


public class PortableCrafting implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint {
    public static final String MOD_ID = "portablecrafting";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	protected static Minecraft mc = Minecraft.getMinecraft(Minecraft.class);
	public static String CFG_FILE_PATH = FabricLoader.getInstance().getGameDir().toString() + "/config/portablecrafting.cfg";
	public static TomlConfigHandler CONFIG = null;
	public static KeyBinding openWorkbench;

	public static void saveandloadConfig() { // saves and loads
		Toml toml = new Toml();
		if (openWorkbench != null) { // Just so it's not null. It'll cause a NPE without this.
			toml.addEntry("keybind", openWorkbench.getKeyCode());
		} else {
			toml.addEntry("keybind", -1);
		}
		CONFIG = new TomlConfigHandler(MOD_ID, toml);

		openWorkbench = new KeyBinding("Open workbench").setDefault(InputDevice.keyboard, CONFIG.getInt("keybind"));
		ModVersionHelper.initialize();
	}

	@Override
	public void beforeGameStart() {
		if (!new File(CFG_FILE_PATH).exists()) {
			Toml toml = new Toml();
			toml.addEntry("keybind",23);
			CONFIG = new TomlConfigHandler(MOD_ID,toml);
			openWorkbench = new KeyBinding("Open workbench").setDefault(InputDevice.keyboard,23);
			ModVersionHelper.initialize();
		} else {
			saveandloadConfig();
		}
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

	@Override
	public void onInitialize() {

	}
	public static boolean findWorkbench(EntityPlayer player) {
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
