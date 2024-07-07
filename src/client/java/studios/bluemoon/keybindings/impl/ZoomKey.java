package studios.bluemoon.keybindings.impl;

import org.lwjgl.glfw.GLFW;
import studios.bluemoon.keybindings.Key;

public class ZoomKey implements Key {
    @Override
    public int getKey() {
        return GLFW.GLFW_KEY_C;
    }

    @Override
    public String getName() {
        return "ZoomKey";
    }

    @Override
    public void releaseAction() {
        System.out.println("Releasing ZoomKey");
    }

    @Override
    public void pressAction() {
        System.out.println("Pressing ZoomKey");
    }

    @Override
    public void holdAction() {
        System.out.println("Holding ZoomKey");
    }

    @Override
    public void onScrollUp() {
    }

    @Override
    public void onScrollDown() {
    }
}
