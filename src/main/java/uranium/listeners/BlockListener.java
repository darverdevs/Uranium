package uranium.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import uranium.user.User;
import uranium.user.UserManager;

public class BlockListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        User user = UserManager.getUser(event.getPlayer());
        if (!user.isVanished()) return;
        event.setCancelled(true);
        user.sendMessage("&cYou can't do that while vanished.");
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        User user = UserManager.getUser(event.getPlayer());
        if (!user.isVanished()) return;
        event.setCancelled(true);
        user.sendMessage("&cYou can't do that while vanished.");
    }

}