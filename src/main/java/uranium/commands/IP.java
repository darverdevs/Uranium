package uranium.commands;

import com.google.common.base.Strings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uranium.Main;
import uranium.user.User;
import uranium.user.UserManager;
import uranium.util.StringUtil;

public class IP implements CommandExecutor {

    private final Main plugin;

    public IP(Main plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(StringUtil.cc(plugin.getConfig().getString("prefix") + "&cThis command can only be used in-game."));
            return true;
        }
        User user = UserManager.getUser((Player) sender);

        if (!user.hasPermission("uranium.ip")) {
            user.sendMessage(plugin.getConfig().getString("prefix") + "&cYou do not have permission to use this command.");
            return true;
        }

        if (args.length < 1) {
            user.sendMessage(plugin.getConfig().getString("prefix") + "&cUsage: /ip <player>");
            return true;
        }

        Player target = plugin.getServer().getPlayer(args[0]);
        User targetUser;
        if (target == null) {
            user.sendMessage(plugin.getConfig().getString("prefix") + "&cPlayer " + args[0] + " is not online.");
            return true;
        }
        targetUser = UserManager.getUser(target);

        String ip = targetUser.getIP();
        if (ip == null) {
            user.sendMessage(plugin.getConfig().getString("prefix") + "&c" + target.getName() + "'s IP has not been detected yet.");
            return true;
        }

        ip = (user.hasPermission("uranium.ip.full")
                ? ip
                : ip.substring(0, ip.length() / 2) + Strings.repeat("*", Math.round((float) ip.length() / 2)));
        user.sendMessage(plugin.getConfig().getString("prefix") + "&a" + target.getName() + "&7's IP is &a" + ip + "&7.");
        return true;
    }
}