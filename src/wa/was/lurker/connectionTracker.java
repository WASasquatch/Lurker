package wa.was.lurker;

import wa.was.lurker.commands.lurkerCmd;
import wa.was.lurker.events.onJoinOrExit;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class connectionTracker extends JavaPlugin {
	
	public static JavaPlugin plugin;
	public static Server server;
	public static HashMap<UUID, Integer> lurkers = new HashMap<UUID, Integer>();
	public static FileConfiguration config;
	
	// Class constructor
	public connectionTracker() {
		plugin = this;
		server = plugin.getServer();
	}
	
	// When the plugin is enabled
    @Override
    public void onEnable() {
    	// Create the configuration file
    	createConfig();
    	// Get configuration
    	config = getConfig();
    	// Register Listeners
    	server.getPluginManager().registerEvents(new onJoinOrExit(), plugin);
    	// Register Command
    	this.getCommand("lurker").setExecutor(new lurkerCmd());
    	// Set Startup Time
    	config.set("startup-time", (int) 0);
    	System.out.print("[Lurker] Tracking connected users play time...");
    }
    
    // When the plugin is disabled
    @Override
    public void onDisable() {
    	System.out.print("[Lurker] Disabling user tracking...");
    	// Clear the tracking hashmap
    	if ( lurkers != null ) {
        	System.out.print("[Lurker] Clearing HashMap...");
	    	lurkers.clear();
	    	if ( lurkers == null ) {
	    		System.out.print("[Lurker] HashMap cleared.");
	    	} else {
	    		System.out.print("[Lurker] Failed to clear HashMap!");
	    	}
    	}
    }
    
    // Generate configuration file
    private void createConfig() {
        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }
            File file = new File(getDataFolder(), "config.yml");
            if (!file.exists()) {
                getLogger().info("Config.yml not found, creating it for you!");
                saveDefaultConfig();
            } else {
                getLogger().info("Config.yml found, loading!");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }
	

}
