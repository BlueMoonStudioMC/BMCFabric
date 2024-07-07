package studios.bluemoon.utils;

public class GetPlayerHead {

    public static String getPlayerHeadURL(String uuid, String type, int size) {
        return "https://api.mineatar.io/" + type + "/" + uuid + "?scale" + size;
    }

}
