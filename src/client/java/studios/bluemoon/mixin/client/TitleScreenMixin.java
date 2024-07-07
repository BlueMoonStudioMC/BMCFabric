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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import studios.bluemoon.utils.syntaxx.GlShaderUtils;

import java.io.IOException;

import static com.ibm.icu.text.PluralRules.Operand.f;

@Mixin(TitleScreen.class)

public class TitleScreenMixin extends Screen {
    private double time = 0.0D;
    private GlShaderUtils glShaderUtils;

    protected TitleScreenMixin(Component component) {
        super(component);
    }

    @Inject(method = "renderPanorama", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/PanoramaRenderer;render(Lnet/minecraft/client/gui/GuiGraphics;IIFF)V"))
    private void renderPanorama(GuiGraphics guiGraphics, float f, CallbackInfo ci) {
    //BM
    this.time += f;
    this.glShaderUtils.setupUniforms((float) this.time / 60);
    this.glShaderUtils.drawShader(width, height);
    //BM END
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
