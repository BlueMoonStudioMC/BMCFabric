package studios.bluemoon.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import studios.bluemoon.utils.syntaxx.GlShaderUtils;

import java.io.IOException;


@Mixin(TitleScreen.class)

public class TitleScreenMixin extends Screen {
    private double time = 0.0D;
    private GlShaderUtils glShaderUtils;
    @Unique
    private GlShaderUtils backgroundShader = new GlShaderUtils();

    protected TitleScreenMixin(Component component) throws IOException {
        super(component);
    }

@Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/TitleScreen;renderPanorama(Lnet/minecraft/client/gui/GuiGraphics;F)V", shift = At.Shift.AFTER), cancellable = true)
    public void nightly$renderGLShaderBackground(GuiGraphics guiGraphics, int i, int j, float f, CallbackInfo ci) {
        this.time += f;
        this.backgroundShader.setupUniforms((float) this.time / 60);
        this.backgroundShader.drawShader(width, height);
    }
@Inject(method = "init", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
    try {
        glShaderUtils = new GlShaderUtils();
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}


}
