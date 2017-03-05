package wa.was.lurker.commands;

import wa.was.lurker.connectionTracker;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class ptime implements CommandExecutor {

	public static JavaPlugin plugin;
	
	// Setup ptime constructor
	public ptime() {
		plugin = connectionTracker.plugin;
	}

	// Execute Lurker Tracking Command
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String tag, String[] args) {
		// Send to player or console
		if ( sender instanceof Player && sender.hasPermission("lurker.cmd") ) {
			// Assume player casting command
			// Check if casting on self or other
			if ( args == null ) {
				// Casting on self
				Player ps = (Player) sender;
				if ( connectionTracker.lurkers.get(ps.getUniqueId()) != null ) {
					int to = ( (int) System.currentTimeMillis() - connectionTracker.lurkers.get(ps.getUniqueId()) );
					int[] ft = formatTime(to);
					// Format Message
					String msg = connectionTracker.config.getString("self-time-format")
									.replace("{HOURS}", Integer.toString(ft[0]))
									.replace("{MINUTES}", Integer.toString(ft[1]))
									.replace("{SECONDS}", Integer.toString(ft[2]));
					// Send message to player
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
					// Return command as TRUE
					return true;
				}
			} else if ( sender.hasPermission("lurker.cmd.others") ) {
				Player p = connectionTracker.server.getPlayer(args[0]);
				// Check if destination player is online and in hashmap
				if ( p.isOnline() && connectionTracker.lurkers.get(p.getUniqueId()) != null ) {
					// Get the login time of player
					int to = ( (int) System.currentTimeMillis() - connectionTracker.lurkers.get(p.getUniqueId()) );
					int[] ft = formatTime(to);
					// Format Message
					String msg = connectionTracker.config.getString("player-time-format")
									.replace("{USERNAME}", p.getDisplayName())
									.replace("{HOURS}", Integer.toString(ft[0]))
									.replace("{MINUTES}", Integer.toString(ft[1]))
									.replace("{SECONDS}", Integer.toString(ft[2]));
					// Send message to player
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));	
					// Return command as TRUE
					return true;
				} else {
					// Target is offline
					String msg = connectionTracker.config.getString("target-offline-format")
									.replace("{USERNAME}", p.getDisplayName());
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
				}
			} else {
				// User has no permission for this command. 
				String msg = connectionTracker.config.getString("no-permission-format");
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
			}
		} else if ( sender instanceof ConsoleCommandSender ) {
			// Assume console casting command
			int[] ft = formatTime(connectionTracker.config.getInt("startup-time-format"));
			// Format Message
			String msg = connectionTracker.config.getString("server-online")
							.replace("{HOURS}", Integer.toString(ft[0]))
							.replace("{MINUTES}", Integer.toString(ft[1]))
							.replace("{SECONDS}", Integer.toString(ft[2]));
			// Send message to player
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));	
			// Return command as TRUE
			return true;
		}
		
		// Return command as FALSE
		return false;
	}
	
	// Return formated time in array from hour to second
	public static int[] formatTime(int time) {
		int[] at = {(int)((time / (1000*60*60)) % 24), (int)((time / (1000*60)) % 60), (int)(time / 1000) % 60};
		return at;
	}
	

}
