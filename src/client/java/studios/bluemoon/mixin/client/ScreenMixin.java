package studios.bluemoon.mixin.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import studios.bluemoon.utils.syntaxx.GlShaderUtils;

import java.io.IOException;

@Mixin(Screen.class)
public abstract class ScreenMixin {

    @Unique
    private double time = 0.0D;
    @Unique private GlShaderUtils backgroundShader = new GlShaderUtils();

    @Shadow
    public int width;
    @Shadow public int height;

    protected ScreenMixin() throws IOException {
    }

    @Inject(method = "renderBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;renderPanorama(Lnet/minecraft/client/gui/GuiGraphics;F)V", shift = At.Shift.AFTER), cancellable = true)
    public void nightly$renderGLShaderBackground(GuiGraphics guiGraphics, int i, int j, float f, CallbackInfo ci) {
        this.time += f;
        this.backgroundShader.setupUniforms((float) this.time / 60);
        this.backgroundShader.drawShader(width, height);
        ci.cancel();
    }
}