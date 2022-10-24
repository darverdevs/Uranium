package uranium.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uranium.Main;
import uranium.user.User;
import uranium.user.UserManager;
import uranium.util.StringUtil;

public class Uranium implements CommandExecutor {

    private final Main plugin;

    public Uranium(Main plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            plugin.reloadConfig();
            sender.sendMessage(StringUtil.cc(plugin.getConfig().getString("prefix") + "The configuration file has been reloaded."));
            return true;
        }

        User user = UserManager.getUser((Player) sender);
        if (!user.hasPermission("uranium.reload")) {
            user.sendMessage(plugin.getConfig().getString("prefix") + "&cYou do not have permission to use this command.");
            return true;
        }

        plugin.reloadConfig();
        user.sendMessage(plugin.getConfig().getString("prefix") + "The configuration file has been reloaded.");
        return true;
    }

}