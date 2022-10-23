package darverdevs;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public void onEnable() {
        setupConfig();
    }

    private void setupConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

}