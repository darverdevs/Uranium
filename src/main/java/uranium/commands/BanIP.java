package uranium.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uranium.Main;
import uranium.user.User;
import uranium.user.UserManager;
import uranium.util.StringUtil;
import uranium.util.Util;

public class BanIP implements CommandExecutor {

    private final Main plugin;

    public BanIP(Main plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(StringUtil.cc(plugin.getConfig().getString("prefix") + "&cPlease use the Bungee console to ban IPs."));
            return true;
        }

        User user = UserManager.getUser((Player) sender);
        if (!user.hasPermission("uranium.banip")) {
            user.sendMessage(plugin.getConfig().getString("prefix") + "&cYou do not have permission to use this command.");
            return true;
        }

        if (args.length < 1) {
            user.sendMessage(plugin.getConfig().getString("prefix") + "&cUsage: /ubanip <player>");
            return true;
        }

        Player target = plugin.getServer().getPlayer(args[0]);
        User targetUser;
        if (target == null) {
            user.sendMessage(plugin.getConfig().getString("prefix") + "&cThat player is not online.");
            return true;
        }
        targetUser = UserManager.getUser(target);
        if (targetUser.hasPermission("uranium.banip.exempt")) {
            user.sendMessage(plugin.getConfig().getString("prefix") + "&cYou cannot IP ban this user!");
            return true;
        }
        if (targetUser.getIP() == null) {
            user.sendMessage(plugin.getConfig().getString("prefix") + "&cThis user's IP address has not been detected yet. Please wait a moment.");
            return true;
        }

        user.sendBungeeMessage("EAG|ConsoleCommand", "eag-ban-ip " + targetUser.getIP());
        if (Util.multiplePlayersOnIP(targetUser.getIP()))
            user.sendBungeeMessage("EAG|ConsoleCommand", "eag-ban-ip confirm");
        user.sendMessage(plugin.getConfig().getString("prefix") + "This user has been IP banned. Please note that if anyone else had the same IP, they were also kicked.");
        Util.sendAll(plugin.getConfig().getString("prefix") + "&c" + targetUser.getName() + " &7was IP banned by &c" + user.getName() + "&7.");
        return true;
    }

}