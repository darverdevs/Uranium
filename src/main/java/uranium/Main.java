package uranium;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import uranium.commands.*;
import uranium.listeners.*;
import uranium.tasks.IPCheck;
import uranium.user.*;

import java.io.File;

public class Main extends JavaPlugin implements PluginMessageListener {

    public void onEnable() {
        setupConfig();
        setupListeners();
        setupCommands();
        setupTasks();
        setupPluginChannels();
        getLogger().info("Initialization is complete.");
    }

    public void onDisable() {
        getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        getServer().getMessenger().unregisterIncomingPluginChannel(this);
    }

    private void setupListeners() {
        getLogger().info("Initializing events...");
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        getServer().getPluginManager().registerEvents(new CommandListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockListener(), this);
    }

    private void setupCommands() {
        getLogger().info("Initializing commands...");
        getCommand("ubanip").setExecutor(new BanIP(this));
        getCommand("ip").setExecutor(new IP(this));
        getCommand("domain").setExecutor(new Domain(this));
        getCommand("vanish").setExecutor(new Vanish(this));
        getCommand("uranium").setExecutor(new Uranium(this));
        getCommand("commandspy").setExecutor(new CommandSpy(this));
    }

    private void setupTasks() {
        getLogger().info("Initializing tasks...");
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new IPCheck(this), 30, 30); // 1.5 seconds
    }

    private void setupPluginChannels() {
        getLogger().info("Initializing plugin channels...");
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
    }

    private void setupConfig() {
        getLogger().info("Initializing config...");
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
    }

    @SuppressWarnings("UnstableApiUsage")
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) return;

        User user = UserManager.getUser(player);
        if (user == null) return;

        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subChannel = in.readUTF();
        if (subChannel.equals("IP")) {
            String ip = in.readUTF();
            user.setIP(ip);
        } else if (subChannel.equals("EAG|GetDomain")) {
            boolean domainRecognized = in.readBoolean();
            String domain = in.readUTF();
            user.setDomain(domainRecognized ? domain : "null");
        }
    }

}