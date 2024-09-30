package nourmodz.portablecrafting.mixinz;

import net.minecraft.client.gui.options.components.*;
import net.minecraft.client.gui.options.data.OptionsPage;
import net.minecraft.client.gui.options.data.OptionsPages;
import net.minecraft.core.item.Item;
import nourmodz.portablecrafting.PortableCrafting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = OptionsPages.class,remap = false)
public class OptionsPagesMixin {
	@Inject(method = "register",at=@At("TAIL"))
	private static void register(OptionsPage page, CallbackInfoReturnable cir) {
		if (page.getIcon().itemID == Item.boat.id) {
			page.withComponent(new OptionsCategory("Portable Workbenches")).withComponent(new KeyBindingComponent(PortableCrafting.openWorkbench));
		}
	}
}
