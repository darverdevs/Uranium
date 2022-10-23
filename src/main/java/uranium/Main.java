package uranium;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public void onEnable() {
        setupConfig();
    }

    private void setupConfig() {
        getLogger().info("Initializing config...");
        getConfig().options().copyDefaults(true);
        saveConfig();
        getLogger().info("Done!");
    }

}