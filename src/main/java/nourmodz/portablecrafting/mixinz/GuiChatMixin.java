package nourmodz.portablecrafting.mixinz;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiChatEmotePicker;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSleepMP;
import net.minecraft.client.gui.text.ITextField;
import net.minecraft.client.gui.text.TextFieldEditor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = GuiChat.class,remap = false)
public class GuiChatMixin extends GuiScreen implements ITextField {
	@Shadow
	protected String message;
	@Shadow
	private int currentIndex;
	@Shadow
	private String originalMessage;
	@Shadow
	private int updateCounter;
	@Shadow
	private final TextFieldEditor editor;
	@Shadow
	protected GuiChatEmotePicker emotePicker;
	@Shadow
	protected boolean showEmotePicker;

	public GuiChatMixin(TextFieldEditor editor) {
		this.editor = editor;
	}


	@Overwrite
	public void keyTyped(char c, int key, int mouseX, int mouseY) {
		if (key == 200 && this.mc.thePlayer.messageHistory.size() > 0) {
			if (this.currentIndex == 0) {
				this.originalMessage = this.getText();
			}

			if (this.currentIndex < this.mc.thePlayer.messageHistory.size()) {
				++this.currentIndex;
				this.setText(this.getMessage(this.currentIndex));
				this.editor.setCursor(10000);
			}

			this.editor.setCursor(10000);
		} else if (key == 208 && this.currentIndex > 0) {
			if (this.currentIndex - 1 == 0) {
				--this.currentIndex;
				this.setText(this.originalMessage);
				this.editor.setCursor(10000);
			} else {
				--this.currentIndex;
				this.setText(this.getMessage(this.currentIndex));
				this.editor.setCursor(10000);
			}

			this.editor.setCursor(10000);
		} else if (key == 1 && !(mc.currentScreen instanceof GuiSleepMP)) {
			this.mc.displayGuiScreen((GuiScreen)null);
		} else if (key == 28) {
			String s = this.message.trim();
			if (s.length() > 0) {
				String s1 = this.message.trim();
				this.mc.thePlayer.sendChatMessage(s1);
			}
			if (mc.currentScreen instanceof GuiChat) { // this is very important. do not remove this ever unless you don't want /craft to work in singleplayer
				this.mc.displayGuiScreen((GuiScreen) null);
			}
		} else {
			this.editor.handleInput(key, c);
		}
	}

	@Shadow
	public void setText(String string) {

	}

	@Shadow
	public String getText() {
		return "";
	}

	@Shadow
	public int maxLength() {
		return 0;
	}

	@Shadow
	private String getMessage(int currentIndex) {
		return (String)this.mc.thePlayer.messageHistory.get(this.mc.thePlayer.messageHistory.size() - currentIndex);
	}
}
