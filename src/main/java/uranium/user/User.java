package uranium.user;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import uranium.Main;
import uranium.util.StringUtil;

// To my knowledge, there's no other way around the warnings in `sendBungeeMessage`.
@SuppressWarnings({"UnstableApiUsage"})
public class User {

    private final Player player;
    private final String name;
    private String lastMessage;
    private Long lastMessageMS;
    private String ip;
    private String domain;
    private boolean vanished;
    private boolean commandSpy;

    public User(Player player) {
        this.player = player;
        this.name = player.getName();
    }

    public Player getPlayer() {
        return player;
    }

    public String getName() {
        return name;
    }

    public boolean hasPermission(String permission) {
        return player.hasPermission(permission);
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Long getLastMessageMS() {
        return lastMessageMS;
    }

    public void setLastMessageMS(Long lastMessageMS) {
        this.lastMessageMS = lastMessageMS;
    }

    public String getIP() {
        return ip;
    }

    public void setIP(String ip) {
        this.ip = ip;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public boolean isVanished() {
        return vanished;
    }

    public void setVanished(boolean vanished) {
        this.vanished = vanished;
        if (vanished)
            UserManager.getUsers().forEach((user) -> {
                if (!user.hasPermission("uranium.vanish"))
                    user.getPlayer().hidePlayer(player);
            });
        else UserManager.getUsers().forEach((user) -> user.getPlayer().showPlayer(player));
    }

    public boolean isCommandSpy() {
        return commandSpy;
    }

    public void setCommandSpy(boolean commandSpy) {
        this.commandSpy = commandSpy;
    }

    public void sendBungeeMessage(String channel, String message) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(channel);
        out.writeUTF(message);

        player.sendPluginMessage(JavaPlugin.getPlugin(Main.class), "BungeeCord", out.toByteArray());
    }

    public void sendBungeeMessage(String channel) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(channel);

        player.sendPluginMessage(JavaPlugin.getPlugin(Main.class), "BungeeCord", out.toByteArray());
    }

    public void onJoin() {
        sendBungeeMessage("IP");
        sendBungeeMessage("EAG|GetDomain");
    }

    public void sendMessage(String message) {
        player.sendMessage(StringUtil.cc(message));
    }
}