package studios.bluemoon.mixin.client;
import net.minecraft.client.ClientBrandRetriever;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;


@Mixin(ClientBrandRetriever.class)
public class ClientBrandRetrieverMixin {

    @Unique
    public String getClientModName() {
        return "BlueMoonClient";
    }
}