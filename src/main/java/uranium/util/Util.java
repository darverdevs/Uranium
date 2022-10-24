package uranium.util;

import org.bukkit.Bukkit;
import uranium.user.UserManager;

import java.util.concurrent.atomic.AtomicInteger;

public class Util {

    public static boolean multiplePlayersOnIP(String ip) {
        AtomicInteger count = new AtomicInteger(0);
        UserManager.getUsers().forEach((user) -> {
            if (user.getIP().equalsIgnoreCase(ip)) count.getAndAdd(1);
        });
        return count.get() > 1;
    }

    public static void sendAll(String message) {
        UserManager.getUsers().forEach((user) -> user.sendMessage(message));
        Bukkit.getConsoleSender().sendMessage(StringUtil.cc(message));
    }

}