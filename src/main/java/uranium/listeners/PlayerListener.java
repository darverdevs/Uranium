package uranium.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import uranium.Main;
import uranium.user.User;
import uranium.user.UserManager;

public class PlayerListener implements Listener {

    private final Main plugin;

    public PlayerListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        if (!plugin.getConfig().getBoolean("chat-filter.enabled")) return;
        String username = event.getPlayer().getName();


    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        User user = UserManager.addUser(event.getPlayer());

        UserManager.getUsers().forEach((u) -> {
            if (u.isVanished())
                if (!user.hasPermission("uranium.vanish"))
                    user.getPlayer().hidePlayer(u.getPlayer());
        });
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        UserManager.removeUser(event.getPlayer());
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        UserManager.removeUser(event.getPlayer());
    }

}