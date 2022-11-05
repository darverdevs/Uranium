package uranium.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;
import uranium.Main;
import uranium.user.User;
import uranium.user.UserManager;
import uranium.util.StringUtil;

public class PlayerListener implements Listener {

    private final Main plugin;

    public PlayerListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        if (!plugin.getConfig().getBoolean("chat-filter.enabled")) return;
        String username = event.getPlayer().getName();

        if (!StringUtil.validateUsername(username)) {
            event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
            event.setKickMessage(StringUtil.cc("&cYour username contains blacklisted words."));
        }
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

    @EventHandler(ignoreCancelled = true)
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        User user = UserManager.getUser(event.getPlayer());
        if (user == null) return; // Somehow this can occur
        if (!user.isVanished()) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        User user = UserManager.getUser(event.getPlayer());
        if (user == null) return; // Somehow this can occur
        if (!user.isVanished()) return;
        event.setCancelled(true);
        user.sendMessage("&cYou can't do that while vanished.");
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!(event.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
        User user = UserManager.getUser(event.getPlayer());
        if (user == null) return;
        if (!user.isVanished()) return;

        if (event.getClickedBlock().getType() == Material.CHEST
            || event.getClickedBlock().getType() == Material.TRAPPED_CHEST) {
            event.setCancelled(true);
            Chest chest = (Chest) event.getClickedBlock().getState();
            Inventory inv = Bukkit.createInventory(null, chest.getInventory() instanceof DoubleChestInventory ? 54 : 27, chest.getInventory().getTitle());
            inv.setContents(chest.getInventory().getContents());
            user.getPlayer().openInventory(inv);
            user.sendMessage(plugin.getConfig().getString("prefix") + "Silently opening this chest.");
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        User user = UserManager.getUser((Player) event.getWhoClicked());
        if (user == null) return;
        if (!user.isVanished()) return;

        if (event.getInventory().getType() == InventoryType.CHEST)
            event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDamageEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player)) return;
        User user = UserManager.getUser((Player) event.getDamager());
        if (user == null) return;

        if (user.isVanished()) {
            event.setCancelled(true);
            user.sendMessage("&cYou can't do that while vanished.");
        }
    }

}