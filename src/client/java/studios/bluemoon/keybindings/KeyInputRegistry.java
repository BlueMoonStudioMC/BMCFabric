package studios.bluemoon.keybindings;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.impl.client.keybinding.KeyBindingRegistryImpl;
import net.minecraft.client.InputType;
import net.minecraft.client.KeyMapping;
import studios.bluemoon.keybindings.impl.GuiKey;
import studios.bluemoon.keybindings.impl.ZoomKey;

import javax.swing.text.JTextComponent;
import java.util.ArrayList;
import java.util.List;

public class KeyInputRegistry {
    public static final String KEY_CATEGORY = "BMC";
    public static List<Key> keys = new ArrayList<>();

    public static void register() {
        ZoomKey zoomKey = new ZoomKey();
        GuiKey guiKey = new GuiKey();
        registerKey(zoomKey, guiKey);
    }
    public static void registerKey(Key... keyArray) {
        keys.addAll(List.of(keyArray));
        for (Key key : keyArray) {
            KeyMapping binding = KeyBindingRegistryImpl.registerKeyBinding(new KeyMapping(key.getName(), InputConstants.Type.KEYSYM, key.getKey(), KEY_CATEGORY));

            registerKeyInput(binding,key);
        }
    }

    private static void registerKeyInput(KeyMapping binding, Key key) {
        final boolean[] wasPressed = {false};
        ClientTickEvents.END_CLIENT_TICK.register(tick -> {
            if (binding.consumeClick()) {
                key.pressAction();
            }
            boolean isPressed =  binding.isDown();
            if (isPressed) {
                key.holdAction();
            }else if (wasPressed[0]) {
                key.releaseAction();
            }
            wasPressed[0] = isPressed;

        });
    }
}
