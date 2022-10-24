package uranium.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uranium.Main;
import uranium.user.User;
import uranium.user.UserManager;
import uranium.util.StringUtil;

public class Vanish implements CommandExecutor {

    private final Main plugin;

    public Vanish(Main plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(StringUtil.cc(plugin.getConfig().getString("prefix") + "&cThis command can only be used in-game."));
            return true;
        }

        User user = UserManager.getUser((Player) sender);

        if (!user.hasPermission("uranium.vanish")) {
            user.sendMessage(plugin.getConfig().getString("prefix") + "&cYou do not have permission to use this command.");
            return true;
        }

        user.setVanished(!user.isVanished());
        user.sendMessage(plugin.getConfig().getString("prefix") + "You have " + (user.isVanished() ? "enabled" : "disabled") + " &aVanish&7.");
        return true;
    }

}