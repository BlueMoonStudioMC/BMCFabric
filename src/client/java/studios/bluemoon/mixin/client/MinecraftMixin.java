package studios.bluemoon.mixin.client;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import studios.bluemoon.utils.DiscordWebhookSender;

@Mixin(Minecraft.class)
public class MinecraftMixin {
	@Inject(at = @At("HEAD"), method = "run")
	private void init(CallbackInfo info) {

		DiscordWebhookSender.send("The Player " + Minecraft.getInstance().getUser().getName() + " Started the Client", true);
	}
}
