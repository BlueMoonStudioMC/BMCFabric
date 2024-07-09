package studios.bluemoon.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.util.RandomSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import static net.minecraft.client.gui.components.LogoRenderer.*;

@Mixin(LogoRenderer.class)
public class LogoRendererMixin {

    private final boolean showEasterEgg = (double) RandomSource.create().nextFloat() < 1.0E-4;
    private final boolean keepLogoThroughFade;

    public LogoRendererMixin(boolean keepLogoThroughFade) {
        this.keepLogoThroughFade = keepLogoThroughFade;
    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    public void renderLogo(GuiGraphics guiGraphics, int i, float f, int j) {
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, this.keepLogoThroughFade ? 1.0F : f);
        RenderSystem.enableBlend();
        int k = i / 2 - 128;
        guiGraphics.blit(this.showEasterEgg ? EASTER_EGG_LOGO : MINECRAFT_LOGO, k, j, 0.0F, 0.0F, 256, 44, 256, 64);
        int l = i / 2 - 64;
        int m = j + 44 - 7;
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
    }

}
