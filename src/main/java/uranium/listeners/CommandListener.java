package uranium.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import uranium.Main;
import uranium.user.*;
import uranium.util.StringUtil;

public class CommandListener implements Listener {

    private final Main plugin;

    public CommandListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPlayerPreprocessCommand(PlayerCommandPreprocessEvent event) {
        if (!plugin.getConfig().getBoolean("override-ipban")) return;
        String message = event.getMessage().substring(1);
        String[] args = message.split("\\s+");
        String command = args[0];

        if (command.equalsIgnoreCase("ipban")
            || command.equalsIgnoreCase("banip")
            || command.equalsIgnoreCase("ban-ip")) {
            args[0] = "ubanip";
            event.setMessage("/" + String.join(" ", args));
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onPlayerPreprocessCommand2(PlayerCommandPreprocessEvent event) {
        User user = UserManager.getUser(event.getPlayer());
        String message = event.getMessage();

        if (!StringUtil.validateMessage(message)) {
            event.setCancelled(true);
            user.sendMessage("&cYour command contained blacklisted words.");
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onPlayerPreprocessCommand3(PlayerCommandPreprocessEvent event) {
        User user = UserManager.getUser(event.getPlayer());
        String message = event.getMessage();

        if (user.hasPermission("uranium.commandspy")) return;
        UserManager.getUsers().forEach((u) -> {
            if (u.isCommandSpy())
                u.sendMessage(plugin.getConfig().getString("prefix") + "&e" + user.getName() + ": " + message);
        });
    }

}