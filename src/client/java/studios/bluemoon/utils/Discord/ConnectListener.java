package studios.bluemoon.utils.Discord;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerConnectionListener;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.server.network.ServerPlayerConnection;
import studios.bluemoon.BlueMoonClientClient;

public class ConnectListener implements  ServerPlayConnectionEvents.Init, ServerPlayConnectionEvents.Disconnect {
    @Override
    public void onPlayDisconnect(ServerGamePacketListenerImpl handler, MinecraftServer server) {

    }

    @Override
    public void onPlayInit(ServerGamePacketListenerImpl handler, MinecraftServer server) {

    }
}
