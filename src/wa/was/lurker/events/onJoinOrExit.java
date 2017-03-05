package wa.was.lurker.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import wa.was.lurker.connectionTracker;

public class onJoinOrExit implements Listener {
	
	// Setup Constructor
	public onJoinOrExit() {
		// nothing to do...
	}
	
	// When a player joins, lets add them to the HashMap with the current timestamp
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
    	if ( event.getPlayer().getUniqueId() != null ) {
    		connectionTracker.lurkers.put(event.getPlayer().getUniqueId(), (int) System.currentTimeMillis());
    		
    	}
    }
    
    // When a player leaves, let's remove them from the HashMap
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
    	connectionTracker.lurkers.remove(event.getPlayer().getUniqueId());
    }

}
