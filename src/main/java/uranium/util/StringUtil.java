package uranium.util;

import org.bukkit.ChatColor;

public class StringUtil {

    public static String cc(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}