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
		System.out.print("lurkerCmd() started...");
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
			// Checking others play time
			} else if ( sender.hasPermission("lurker.cmd.others") || sender.isOp() ) {
				Player p = connectionTracker.server.getPlayer(args[0]);
				// Check if valid player...
				if ( p != null && p instanceof Player && connectionTracker.lurkers.containsKey(args[0]) ) {
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
									.replace("{USERNAME}", args[0]);
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
			String msg = connectionTracker.config.getString("server-time-format")
							.replace("{HOURS}", Integer.toString(ft[0]))
							.replace("{MINUTES}", Integer.toString(ft[1]))
							.replace("{SECONDS}", Integer.toString(ft[2]));
			// Send message to player
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));	
			// Return command as TRUE
			return true;
		}
		// Return command as FALSE
		return true;
	}
	
	// Return formated time in array from hour to second
	public static int[] formatTime(int time) {
		int[] at = {(int)((time / (1000*60*60)) % 24), (int)((time / (1000*60)) % 60), (int)(time / 1000) % 60};
		return at;
	}
	

}
