package commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import main.Main;
import net.md_5.bungee.api.ChatColor;

public class ClearReports implements CommandExecutor {
	
	FileConfiguration config = Main.plugin.getConfig();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	
		// console command
		
		if(!(sender.hasPermission("report.clearreports"))) {
			sender.sendMessage(ChatColor.RED + "You do not have permission to run this command!");
			return true;
		}
		
		try {
			
			Player target_player = Bukkit.getPlayer(args[0]);
			
			if(!(target_player.getName().equals(args[0]))) {
				sender.sendMessage(ChatColor.RED + "This player doesn't exist!");
				return true;
			}
			
			
			// good to go
			
			config.set("reports." + target_player.getUniqueId().toString(), null);
			Main.plugin.saveConfig();
			
			sender.sendMessage(ChatColor.GREEN + "Players reports were cleared!");
			
			
			
		} catch(Exception e) {
			sender.sendMessage(ChatColor.RED + "This player doesn't exist!");
			return true;
		}
		
		return true;
	}

}
