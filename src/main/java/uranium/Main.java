package uranium;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import uranium.listeners.*;
import uranium.user.*;

public class Main extends JavaPlugin implements PluginMessageListener {

    public void onEnable() {
        setupConfig();
        setupListeners();
        setupCommands();
        setupTasks();
        setupPluginChannels();
    }

    public void onDisable() {
        getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        getServer().getMessenger().unregisterIncomingPluginChannel(this);
    }

    private void setupListeners() {
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        getServer().getPluginManager().registerEvents(new CommandListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockListener(), this);
    }

    private void setupCommands() {

    }

    private void setupTasks() {

    }

    private void setupPluginChannels() {
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
    }

    private void setupConfig() {
        getLogger().info("Initializing config...");
        getConfig().options().copyDefaults(true);
        saveConfig();
        getLogger().info("Done!");
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