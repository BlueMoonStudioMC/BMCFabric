package studios.bluemoon.utils.syntaxx;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.systems.RenderSystem;

import com.mojang.blaze3d.vertex.VertexBuffer;
import net.minecraft.client.Minecraft;


import java.io.Closeable;


public class ScalableCanvas implements Closeable {

    public final RenderTarget input;
    public final RenderTarget output = Minecraft.getInstance().getMainRenderTarget();

    public ScalableCanvas() {
        this.input = new TextureTarget(this.output.width, this.output.height, false, false);
    }

    public void blit(VertexBuffer buffer, float alpha) {
        this.output.bindWrite(true);
        RenderSystem.setShaderTexture(0, this.input.getColorTextureId());
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
        RenderSystem.enableBlend();
        buffer.draw();
    }

    public void resize(int width, int height, int quality) {
        if (this.input.width != width && this.input.height != height && width > 0 && height > 0)
            this.input.resize(width * quality, height * quality, Minecraft.ON_OSX);

    }

    public void close() {
        this.input.destroyBuffers();
    }
}