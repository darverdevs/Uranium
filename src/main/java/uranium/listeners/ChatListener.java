package uranium.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import uranium.Main;
import uranium.user.User;
import uranium.user.UserManager;
import uranium.util.StringUtil;

public class ChatListener implements Listener {

    private final Main plugin;

    public ChatListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (!plugin.getConfig().getBoolean("chat-filter.enabled")) return;
        User user = UserManager.getUser(event.getPlayer());
        String message = event.getMessage();
        if (user.hasPermission("uranium.bypass")) return;

        if (plugin.getConfig().getBoolean("chat-filter.double-posting")
            && user.getLastMessage() != null
            && user.getLastMessage().equalsIgnoreCase(message)) {
            user.sendMessage("&cPlease do not double post.");
            event.setCancelled(true);
            return;
        }

        if (plugin.getConfig().getBoolean("message-delay")
            && user.getLastMessageMS() != null
            && (System.currentTimeMillis() - user.getLastMessageMS()) < plugin.getConfig().getInt("chat-filter.message-delay")) {
            user.sendMessage("&cYou're sending messages too fast! Slow down.");
            event.setCancelled(true);
            return;
        }

        user.setLastMessage(message);
        user.setLastMessageMS(System.currentTimeMillis());
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onPlayerChat2(AsyncPlayerChatEvent event) {
        if (!plugin.getConfig().getBoolean("chat-filter.enabled")) return;
        User user = UserManager.getUser(event.getPlayer());
        String message = event.getMessage();

        if (!StringUtil.validateMessage(message)) {
            event.setCancelled(true);
            user.sendMessage("&cYour message contains blacklisted words.");
            return;
        }

        if (StringUtil.checkCaps(message)) {
            event.setMessage(StringUtil.capitalize(message.toLowerCase()));
            user.sendMessage("&cDon't use so many caps!");
        }
    }

}