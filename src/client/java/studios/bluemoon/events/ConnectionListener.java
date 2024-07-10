package studios.bluemoon.events;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import studios.bluemoon.BlueMoonClient;
import studios.bluemoon.BlueMoonClientClient;
import studios.bluemoon.RPC;

public class ConnectionListener implements ServerPlayConnectionEvents.Init, ServerPlayConnectionEvents.Disconnect {

    @Override
    public void onPlayInit(ServerGamePacketListenerImpl handler, MinecraftServer server) {
        if (server.isSingleplayer()) {
            RPC.updateRPC("Singleplayer", "Spielt BMC-1.20.1");
        } else {
            RPC.updateRPC("Multiplayer", "Spielt BMC-1.20.1");
        }
    }

    @Override
    public void onPlayDisconnect(ServerGamePacketListenerImpl handler, MinecraftServer server) {
        BlueMoonClientClient.INSTANCE.resetDiscord();
    }
}

