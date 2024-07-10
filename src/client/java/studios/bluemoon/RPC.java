package studios.bluemoon;

import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import de.jcm.discordgamesdk.activity.Activity;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import studios.bluemoon.utils.lists.BooleanList;

import java.time.Instant;

public class RPC {

    private static final Activity ACTIVITY = new Activity();

    private static long lastActivityUpdate = 0;

    public static void start() {
        new Thread(() -> {
            final CreateParams params = new CreateParams();
            params.setClientID(1259424712644366416L);
            params.setFlags(CreateParams.Flags.NO_REQUIRE_DISCORD);

            try (final Core core = new Core(params)) {

                core.runCallbacks();
                if (BooleanList.rpc == true) {
                    while (true) {
                        if (updateRPC("Playing", "BlueMoonClient") && lastActivityUpdate == 0 || System.currentTimeMillis() - lastActivityUpdate < 20_000) {
                            core.activityManager().updateActivity(ACTIVITY);

                            lastActivityUpdate = System.currentTimeMillis();
                        }

                        try {
                            Thread.sleep(16);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

            } catch (RuntimeException e) {
                e.printStackTrace();
            }

    }).start();
}

    public static boolean updateRPC(String detail, String state) {
        boolean activityChanged = false;

        ACTIVITY.assets().setLargeImage("large");
        updatePlayerHead();

        ACTIVITY.assets().setLargeText("BlueMoon-Client | Fabric 1.21");
        ACTIVITY.assets().setSmallText(Minecraft.getInstance().getGameProfile().getName());

        if (lastActivityUpdate == 0) {
            ACTIVITY.timestamps().setStart(Instant.now());

            activityChanged = true;
        }

        ACTIVITY.timestamps().setStart(Instant.now());

        if (!detail.equals(ACTIVITY.getDetails())) {
            ACTIVITY.setDetails(detail);

            activityChanged = true;
        }

        if (!state.equals(ACTIVITY.getState())) {
            ACTIVITY.setState(state);

            activityChanged = true;
        }

        return activityChanged;
    }

    private static void updatePlayerHead() {
        String uuid = Minecraft.getInstance().getGameProfile().getId().toString();
        String playerHeadImage = getPlayerHeadURL(uuid, "face", 3);
        ACTIVITY.assets().setSmallImage(playerHeadImage);
    }

    @Contract(pure = true)
    private static @NotNull String getPlayerHeadURL(String uuid, String type, int size) {
        return "https://api.mineatar.io/" + type + "/" + uuid + "?scale=" + size;
    }
}
