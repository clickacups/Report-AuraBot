package commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import main.Main;
import net.md_5.bungee.api.ChatColor;

public class GetReports implements CommandExecutor {
	
	FileConfiguration config = Main.plugin.getConfig();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender.hasPermission("report.getreports"))) {
			sender.sendMessage(ChatColor.RED + "You do not have permission to run this command!");
			return true;
		}
		
		try {
			
			Player player = Bukkit.getPlayer(args[0]);
			
			if(!(player.getName().equals(args[0]))) {
				
				sender.sendMessage(ChatColor.RED + "Player has no reports.");
				return true;
			}
			
			String uuid = player.getUniqueId().toString();
			
			
			sender.sendMessage(ChatColor.BLUE + "Reports: ");
			
			try {
				
				int report_int = config.getInt("reports." + uuid + ".report_int");
				
				sender.sendMessage("Total reports: " + report_int);
				
				for(int i=1; i<report_int+1;i++) {
					
					
					Player reporter = Bukkit.getPlayer(UUID.fromString(config.getString("reports." + uuid + ".report" + i + ".reporter")));
					String reason = config.getString("reports." + uuid + ".report" + i + ".reason");
					String timestamp = config.getString("reports." + uuid + ".report" + i + ".timestamp");
					
					sender.sendMessage(ChatColor.BLUE + "[Report "+i+"]");
					sender.sendMessage(ChatColor.BLUE + "    - " + ChatColor.WHITE + reason);
					sender.sendMessage(ChatColor.BLUE + "    - at " + ChatColor.GRAY + timestamp);
					sender.sendMessage(ChatColor.BLUE + "    - by " + ChatColor.GRAY + reporter.getName());
					
					
					
				}
				
				
				
			} catch(Exception ex) {
				ex.printStackTrace();
			}
			
			
		} catch(Exception e) {
		 
			for(OfflinePlayer op: Bukkit.getOfflinePlayers()) {
				
				if(op.getName().equals(args[0])) {
					
					String uuid = op.getUniqueId().toString();
					
					String player_name = Bukkit.getPlayer(UUID.fromString(uuid)).getName();
					
					sender.sendMessage(ChatColor.BLUE + "Reports: ");
					
					try {
						
						int report_int = config.getInt("reports." + uuid + ".report_int");
						
						for(int i=1; i<report_int+1;i++) {
							
							Player reporter = Bukkit.getPlayer(config.getString("reports." + uuid + ".report" + i + ".reporter"));
							String reason = config.getString("reports." + uuid + ".report" + i + ".reason");
							String timestamp = config.getString("reports." + uuid + ".report" + i + ".timestamp");
							
							sender.sendMessage(ChatColor.BLUE + "[Report]" +ChatColor.WHITE+ player_name + ChatColor.GRAY + " reported by " +ChatColor.WHITE + reporter.getName() + ChatColor.GRAY + " for:");
							
							sender.sendMessage(ChatColor.BLUE + "    - " + ChatColor.GRAY + reason);
							sender.sendMessage(ChatColor.BLUE + "    - at " + ChatColor.GRAY + timestamp);
							
							
							
						}
						
						
						
					} catch(Exception ex) {
						sender.sendMessage(ChatColor.RED + "Player has no reports");
						return true;
					}
				
				
				}
				
			}
			
		}
		
		return true;
	}

}
