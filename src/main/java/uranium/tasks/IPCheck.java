package uranium.tasks;

import org.bukkit.entity.Player;
import uranium.Main;
import uranium.user.UserManager;
import uranium.util.Util;

import java.util.List;

public class IPCheck implements Runnable {

    private final Main plugin;

    public IPCheck(Main plugin) {
        this.plugin = plugin;
    }

    public void run() {
        UserManager.getUsers().forEach((user) -> {
            if (user.getIP() == null || user.getDomain() == null) {
                Player player = user.getPlayer();
                user.onJoin();

                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    if (UserManager.getUser(player) == null) return;
                    if (!plugin.getConfig().getBoolean("suspicious-domains.enabled")) return;
                    List<String> trustedDomains = plugin.getConfig().getStringList("suspicious-domains.trusted-domains");
                    String prefix = plugin.getConfig().getString("prefix");
                    boolean offlineDownload = plugin.getConfig().getBoolean("suspicious-domains.offline-download");
                    boolean debugRuntime = plugin.getConfig().getBoolean("suspicious-domains.debug-runtime");
                    if (user.getDomain() == null) return;
                    if (trustedDomains.contains(user.getDomain())) return;

                    if (offlineDownload && user.getDomain().equalsIgnoreCase("null"))
                        Util.sendStaff(prefix + "&c" + user.getName() + "&7 joined using an offline download.");
                    else if (debugRuntime && user.getDomain().equalsIgnoreCase(""))
                        Util.sendStaff(prefix + "&c" + user.getName() + "&7 joined using the debug runtime.");
                    else
                        Util.sendStaff(prefix + "&c" + user.getName() + "&7 joined using a strange domain: &c" + user.getDomain());
                });
            }
        });
    }

}