package uranium.util;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import org.bukkit.plugin.java.JavaPlugin;
import uranium.Main;

public class WebhookUtil {

    private static final Main plugin;

    static {
        plugin = JavaPlugin.getPlugin(Main.class);
    }

    private static String filterForDiscord(String message) {
        return message
            .replace("_", "\\_")
            .replace("*", "\\*")
            .replace("~", "\\~")
            .replace("`", "\\`")
            .replace("|", "\\|")
            .replace("> ", "\\> ")
            .replace("@everyone", "")
            .replace("@here", "");
    }

    public static void sendSusDomain(String username, String domain) {
        String webhookURI = plugin.getConfig().getString("suspicious-domains.webhook-url");
        if (webhookURI.equalsIgnoreCase("")) {
            plugin.getLogger().warning("The webhook URL for suspicious domains has been incorrectly configured.");
            return;
        }
        WebhookClientBuilder builder = new WebhookClientBuilder(webhookURI);
        builder.setThreadFactory((job) -> {
            Thread thread = new Thread(job);
            thread.setName("Webhook");
            thread.setDaemon(true);
            return thread;
        });
        builder.setWait(true);
        try (WebhookClient client = builder.build()) {
            WebhookEmbed embed = new WebhookEmbedBuilder()
                .setColor(0xFF0000)
                .setDescription("A user has logged onto the server using a suspicious domain.")
                .addField(new WebhookEmbed.EmbedField(true, "Username", filterForDiscord(username)))
                .addField(new WebhookEmbed.EmbedField(true, "Domain", "http://" + filterForDiscord(domain)))
                .build();

            client.send(embed);
        } catch(Exception ex) {
            plugin.getLogger().severe("An error occurred while attempting to deliver the webhook. Please report the following stacktrace to Cold.");
            plugin.getLogger().severe("Webhook URL: " + webhookURI);
            ex.printStackTrace();
        }
    }

}