package uranium.util;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import uranium.Main;

import java.util.List;

public class StringUtil {

    private static final Main plugin;

    static {
        plugin = JavaPlugin.getPlugin(Main.class);
    }

    private static List<String> getBannedWords() {
        return plugin.getConfig().getStringList("chat-filter.banned-words");
    }

    public static String cc(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    private static String removeCrap(String s) {
        return s.replaceAll("[^a-zA-Z0-9]", "");
    }

    public static boolean validateUsername(String username) {
        username = removeCrap(username);
        for (String word : getBannedWords())
            if (username.toLowerCase().contains(word.toLowerCase())) return false;
        return true;
    }

    public static boolean validateMessage(String message) {
        message = removeCrap(message);
        for (String word : getBannedWords())
            if (message.toLowerCase().contains(word.toLowerCase())) return false;
        return true;
    }

    public static String capitalize(String message) {
        return Character.toUpperCase(message.charAt(0)) + message.substring(1);
    }

    public static boolean checkCaps(String message) {
        message = removeCrap(message);
        if (message.length() <= 5) return false;
        double threshold = 50;
        double upperCase = 0;
        char[] chars = message.toCharArray();

        for(char c : chars)
            if (Character.isUpperCase(c))
                upperCase++;

        double upperCasePercent = (upperCase * 100) / message.length();

        return upperCasePercent >= threshold;
    }

}