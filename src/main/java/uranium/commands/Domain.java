package uranium.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uranium.Main;
import uranium.user.User;
import uranium.user.UserManager;
import uranium.util.StringUtil;

public class Domain implements CommandExecutor {

    private final Main plugin;

    public Domain(Main plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(StringUtil.cc(plugin.getConfig().getString("prefix") + "&cThis command can only be used in-game."));
            return true;
        }
        User user = UserManager.getUser((Player) sender);

        if (!user.hasPermission("uranium.domain")) {
            user.sendMessage(plugin.getConfig().getString("prefix") + "&cUsage: /domain <player>");
            return true;
        }

        Player target = plugin.getServer().getPlayer(args[0]);
        User targetUser;
        if (target == null) {
            user.sendMessage(plugin.getConfig().getString("prefix") + "&cPlayer " + args[0] + " is not online.");
            return true;
        }
        targetUser = UserManager.getUser(target);

        String domain = targetUser.getDomain();
        if (domain == null) {
            user.sendMessage(plugin.getConfig().getString("prefix") + "&c" + targetUser.getName() + "'s domain has not been detected yet.");
            return true;
        }

        user.sendMessage(plugin.getConfig().getString("prefix") + "&a" + targetUser.getName() + " &7" + (
                domain.equals("null")
                ? "is using an offline download."
                : domain.equals("")
                ? "is using the debug runtime."
                : "'s domain is &a" + domain + "&7."));
        return true;
    }

}