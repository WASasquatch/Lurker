package wa.was.lurker.commands;

import wa.was.lurker.connectionTracker;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class lurkerCmd implements CommandExecutor {

	public static JavaPlugin plugin;
	
	// Setup ptime constructor
	public lurkerCmd() {
		plugin = connectionTracker.plugin;
	}

	// Execute Lurker Tracking Command
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String tag, String[] args) {
		// Check for too many arguments
		if ( args.length >= 2 ) {
			// Invalid arguments
			String msg = connectionTracker.config.getString("invalid-argument-format");
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
			// Return command as FALSE
			return false;
		}
		// Send to player or console
		if ( sender instanceof Player && ( sender.hasPermission("lurker.cmd") || sender.isOp() ) ) {
			// Assume player casting command
			// Check if casting on self or other
			if ( args.length == 0 ) {
				// Casting on self
				Player ps = (Player) sender;
				// Valid entry in HashMap
				if ( connectionTracker.lurkers.get(ps.getUniqueId()) != null ) {
					// Get the login time of player
					int to = ( (int) System.currentTimeMillis() - connectionTracker.lurkers.get(ps.getUniqueId()) );
					// Format Message
					String msg = connectionTracker.config.getString("self-time-format");
					String fmsg = formatString(msg, formatTime(to), null);
					// Send message to player
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', fmsg));
					// Return command as TRUE
					return true;
				}
			// Checking others play time
			} else if ( sender.hasPermission("lurker.cmd.others") || sender.isOp() && ! ( args[0].toString().toLowerCase().contentEquals("server") ) ) {
				Player p = connectionTracker.server.getPlayer(args[0].toString());
				// Check if valid player...
				if ( p != null && connectionTracker.lurkers.containsKey(p.getUniqueId()) ) {
					// Get the login time of player
					int to = ( (int) System.currentTimeMillis() - connectionTracker.lurkers.get(p.getUniqueId()) );
					// Format Message
					String msg = connectionTracker.config.getString("player-time-format");
					String fmsg = formatString(msg, formatTime(to), args[0].toString());
					// Send message to player
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', fmsg));	
					// Return command as TRUE
					return true;
				} else {
					// Target is offline
					// Format Message
					String msg = connectionTracker.config.getString("target-offline-format");
					String fmsg = formatString(msg, null, args[0]);
					// Send message to player
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', fmsg));
				}
			} else if ( sender.hasPermission("lurker.cmd.uptime") || sender.isOp() && args[0].toString().toLowerCase().contentEquals("server") ) {
				// Asking for server uptime
				// Get the startup time of the server
				int to = ( (int) System.currentTimeMillis() - connectionTracker.startupTime );
				// Format Message
				String msg = connectionTracker.config.getString("server-time-format");
				String fmsg = formatString(msg, formatTime(to), null);

				// Send message to player
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', fmsg));	
				// Return command as TRUE
				return true;
			} else {
				// User has no permission for this command. 
				String msg = connectionTracker.config.getString("no-permission-format");
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
			}
		} else if ( sender instanceof ConsoleCommandSender ) {
			if ( args.length == 0 ) {
				// Get the startup time of the server
				int to = ( (int) System.currentTimeMillis() - connectionTracker.startupTime );
				// Format Message
				String msg = connectionTracker.config.getString("server-time-format");
				String fmsg = formatString(msg, formatTime(to), null);
	
				// Send message to player
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', fmsg));	
				// Return command as TRUE
			} else {
				// Server casting on player
				Player p = connectionTracker.server.getPlayer(args[0].toString());
				// Check if valid player...
				if ( p != null && connectionTracker.lurkers.containsKey(p.getUniqueId()) ) {
					// Get the login time of player
					int to = ( (int) System.currentTimeMillis() - connectionTracker.lurkers.get(p.getUniqueId()) );
					// Format Message
					String msg = connectionTracker.config.getString("player-time-format");
					String fmsg = formatString(msg, formatTime(to), args[0].toString());
					// Send message to player
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', fmsg));	
					// Return command as TRUE
					return true;
				} else {
					// Target is offline
					// Format Message
					String msg = connectionTracker.config.getString("target-offline-format");
					String fmsg = formatString(msg, null, args[0]);
					// Send message to player
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', fmsg));
				}
			}
			return true;
		}
		// Return command as FALSE
		return true;
	}
	
	public static String formatString(String string, int[] ft, String player) {
		String ns;
		// If there is no time array lets just do username
		if ( ft == null ) {
			if ( player != null ) {
				ns = string
						.replace("{USERNAME}", player);
			} else {
				ns = string;
			}
		} else {
			ns = string
					.replace("{WEEKS}", Integer.toString(ft[0]))
					.replace("{DAYS}", Integer.toString(ft[1]))
					.replace("{HOURS}", Integer.toString(ft[2]))
					.replace("{MINUTES}", Integer.toString(ft[3]))
					.replace("{SECONDS}", Integer.toString(ft[4]));
			if ( player != null ) {
				ns = ns.replace("{USERNAME}", player);
			}
		}
		return ns;
	}
	
	// Return formated time in array from hour to second
	public static int[] formatTime(int time) {
		int[] at = {
				(int)(time / (1000*60*60*24*7)), 
				(int)(time / (1000*60*60*24) % 7), 
				(int)((time / (1000*60*60)) % 24), 
				(int)((time / (1000*60)) % 60), 
				(int)(time / 1000) % 60
				};
		return at;
	}
	

}
