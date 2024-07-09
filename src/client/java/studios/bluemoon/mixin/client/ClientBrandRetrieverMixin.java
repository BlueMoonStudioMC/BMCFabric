package studios.bluemoon.mixin.client;

import net.minecraft.client.ClientBrandRetriever;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ClientBrandRetriever.class, remap = false)
public abstract class ClientBrandRetrieverMixin {

    @Unique
    private static final String BRANDING_VANILLA = "vanilla";
    @Unique
    private static final String BRANDING_BMC = "BlueMoon-Client";

    @Inject(method = "getClientModName", at = @At("RETURN"), cancellable = true)
    private static void appendBMCBranding(CallbackInfoReturnable<String> CBackInfoReturnable)
    {
        String branding = CBackInfoReturnable.getReturnValue();
        if (ClientBrandRetrieverMixin.BRANDING_VANILLA.equals(branding))
        {
            CBackInfoReturnable.setReturnValue(ClientBrandRetrieverMixin.BRANDING_BMC);
        }
        else
        {
            CBackInfoReturnable.setReturnValue(ClientBrandRetrieverMixin.BRANDING_BMC);
        }
    }
}