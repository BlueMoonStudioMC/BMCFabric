package studios.bluemoon.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class DiscordWebhookSender {
    private static final String WEBHOOK_URL = "https://discord.com/api/webhooks/1260252981979516970/Am7hY3SnomNVwXcATDWttAvhIHe8BD0wXM7h_Bnc7jtUdk_QpzPXwZUP5GRM8yeQSsOC";

    public static void send(String message, boolean codeBlock) {

        if (codeBlock) {
            message = "```" + message + "```";
        }
        String jsonPayload = "{ \"content\": \"" + message + "\" }";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(WEBHOOK_URL);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(jsonPayload));

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                int statusCode = response.getStatusLine().getStatusCode();
                String responseBody = EntityUtils.toString(response.getEntity());

                System.out.println("Response status: " + statusCode);
                System.out.println("Response body: " + responseBody);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DiscordWebhookSender.send("Hello, this is a plain message!", false);
        DiscordWebhookSender.send("Hello, this is a code block message!", true);
    }
}
