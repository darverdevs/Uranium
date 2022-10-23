package uranium.user;

import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;

public class UserManager {

    private static final HashMap<String, User> users;

    static {
        users = new HashMap<>();
    }

    public static User addUser(Player player) {
        users.put(player.getName().toLowerCase(), new User(player));
        return users.get(player.getName().toLowerCase());
    }

    public static User getUser(Player player) {
        return users.get(player.getName().toLowerCase());
    }

    public static void removeUser(Player player) {
        users.remove(player.getName().toLowerCase());
    }

    public static Collection<User> getUsers() {
        return users.values();
    }

}