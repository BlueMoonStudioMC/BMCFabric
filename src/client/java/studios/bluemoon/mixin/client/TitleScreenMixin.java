package studios.bluemoon.mixin.client;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.realmsclient.gui.screens.RealmsNotificationsScreen;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.CreditsAndAttributionScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.options.AccessibilityOptionsScreen;
import net.minecraft.client.gui.screens.options.LanguageSelectScreen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import studios.bluemoon.utils.syntaxx.GlShaderUtils;

import java.io.IOException;
import java.util.Iterator;


@Mixin(TitleScreen.class)

public class TitleScreenMixin extends Screen {
    private double time = 0.0D;
    private GlShaderUtils glShaderUtils;
    @Unique
    private GlShaderUtils backgroundShader = new GlShaderUtils();

    protected TitleScreenMixin(Component component, LogoRenderer logoRenderer) throws IOException {
        super(component);
        this.logoRenderer = logoRenderer;
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

/**
 * @author
 * @reason
 */
@Nullable
private SplashRenderer splash;
    private Button resetDemoButton;
    @Nullable
    private RealmsNotificationsScreen realmsNotificationsScreen;
    private float panoramaFade;
    private boolean fading;
    private long fadeInStart;
    private final LogoRenderer logoRenderer;
    private boolean realmsNotificationsEnabled() {
        return this.realmsNotificationsScreen != null;
    }
/**
 * @author
 * @reason
 */
@Overwrite
public void render(GuiGraphics guiGraphics, int i, int j, float f) {
    if (this.fadeInStart == 0L && this.fading) {
        this.fadeInStart = Util.getMillis();
    }

    float g = 1.0F;
    if (this.fading) {
        float h = (float)(Util.getMillis() - this.fadeInStart) / 2000.0F;
        if (h > 1.0F) {
            this.fading = false;
            this.panoramaFade = 1.0F;
        } else {
            h = Mth.clamp(h, 0.0F, 1.0F);
            g = Mth.clampedMap(h, 0.5F, 1.0F, 0.0F, 1.0F);
            this.panoramaFade = Mth.clampedMap(h, 0.0F, 0.5F, 0.0F, 1.0F);
        }

        this.fadeWidgets(g);
    }

    this.renderPanorama(guiGraphics, f);
    int k = Mth.ceil(g * 255.0F) << 24;
    if ((k & -67108864) != 0) {
        super.render(guiGraphics, i, j, f);
        this.logoRenderer.renderLogo(guiGraphics, this.width, g);
        if (this.splash != null && !(Boolean)this.minecraft.options.hideSplashTexts().get()) {
            this.splash.render(guiGraphics, this.width, this.font, k);
        }

        String string = ChatFormatting.BLUE + "BM || " + SharedConstants.getCurrentVersion().getName();

        guiGraphics.drawString(this.font, string, 2, this.height - 10, 16777215 | k);
        if (this.realmsNotificationsEnabled() && g >= 1.0F) {
            RenderSystem.enableDepthTest();
            this.realmsNotificationsScreen.render(guiGraphics, i, j, f);
        }

    }
}

    private void fadeWidgets(float f) {
        Iterator var2 = this.children().iterator();

        while(var2.hasNext()) {
            GuiEventListener guiEventListener = (GuiEventListener)var2.next();
            if (guiEventListener instanceof AbstractWidget abstractWidget) {
                abstractWidget.setAlpha(f);
            }
        }

    }
@Unique
Window window;



}
