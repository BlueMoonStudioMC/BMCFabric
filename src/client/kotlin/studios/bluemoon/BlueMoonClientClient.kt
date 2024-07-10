package studios.bluemoon

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import studios.bluemoon.events.ConnectionListener
import studios.bluemoon.keybindings.KeyInputRegistry

object BlueMoonClientClient : ClientModInitializer {

	fun onInitialize() {

	}

	override fun onInitializeClient() {
		ClientLifecycleEvents.CLIENT_STARTED.register {
			RPC.start()
		}

		KeyInputRegistry.register()

		ServerPlayConnectionEvents.INIT.register(ConnectionListener())
		ServerPlayConnectionEvents.DISCONNECT.register(ConnectionListener())
	}

	fun resetDiscord() {
		RPC.updateRPC("Im HauptmenÃ¼", "Schaut den TitleScreen an")
	}
}