package uranium.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uranium.Main;
import uranium.user.User;
import uranium.user.UserManager;
import uranium.util.StringUtil;

public class CommandSpy implements CommandExecutor {

    private final Main plugin;

    public CommandSpy(Main plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(StringUtil.cc(plugin.getConfig().getString("prefix") + "&cThis command can only be used in-game."));
            return true;
        }
        User user = UserManager.getUser((Player) sender);

        if (!user.hasPermission("uranium.commandspy")) {
            user.sendMessage(plugin.getConfig().getString("prefix") + "&cYou do not have permission to use this command.");
            return true;
        }

        user.setCommandSpy(!user.isCommandSpy());
        user.sendMessage(plugin.getConfig().getString("prefix") + "You have " + (user.isCommandSpy() ? "enabled" : "disabled") + " &aCommand Spy&7.");
        return true;
    }
}