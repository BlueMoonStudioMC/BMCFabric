package studios.bluemoon.keybindings.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;
import studios.bluemoon.FirstScreen;
import studios.bluemoon.keybindings.Key;

public class GuiKey implements Key {
    @Override
    public int getKey() {
        return GLFW.GLFW_KEY_RIGHT_SHIFT;
    }

    @Override
    public String getName() {
        return "Gui";
    }

    @Override
    public void releaseAction() {

    }

    @Override
    public void pressAction() {

        Minecraft.getInstance().setScreen(new FirstScreen());

    }

    @Override
    public void holdAction() {

    }
}
