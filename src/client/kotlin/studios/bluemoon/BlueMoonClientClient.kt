package studios.bluemoon

import net.fabricmc.api.ClientModInitializer
import studios.bluemoon.keybindings.KeyInputRegistry

object BlueMoonClientClient : ClientModInitializer {
	override fun onInitializeClient() {
		KeyInputRegistry.register()
	}
}