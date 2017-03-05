package wa.was.lurker;

import wa.was.lurker.commands.lurkerCmd;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class connectionTracker extends JavaPlugin implements Listener {
	
	public static JavaPlugin plugin;
	public static Server server;
	public static HashMap<UUID, Integer> lurkers;
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
    	server.getPluginManager().registerEvents(new connectionTracker(), plugin);
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
    	lurkers.clear();
    }
	
	// When a player joins, lets add them to the HashMap with the current timestamp
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
    	lurkers.put(event.getPlayer().getUniqueId(), (int) System.currentTimeMillis());
    }
    
    // When a player leaves, let's remove them from the HashMap
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
    	lurkers.remove(event.getPlayer().getUniqueId());
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
