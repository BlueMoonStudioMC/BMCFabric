package studios.bluemoon.utils;

import com.mojang.blaze3d.pipeline.RenderTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.player.Player;

public interface GlobalConstants {

    Minecraft minecraft = Minecraft.getInstance();
    Player player = Minecraft.getInstance().player;
    Font textRenderer = Minecraft.getInstance().font;
    RenderTarget framebuffer = Minecraft.getInstance().getMainRenderTarget();
    ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();


}