package commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import main.Main;
import main.NPC;
import net.md_5.bungee.api.ChatColor;

public class AuraBot implements CommandExecutor {
	FileConfiguration config = Main.plugin.getConfig();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		Plugin plugin = Main.getPlugin(Main.class);
		
		
		try {
			
			Player player = Bukkit.getPlayerExact(args[0]);
			
			sender.sendMessage(ChatColor.GOLD + "Aura Bot summoned for: " + ChatColor.RED + player.getName());
			
			
			NPC aurabot = new NPC(player.getWorld(), "");
			
			Main.aurabotting.put(player, aurabot);
			Main.aurabot_hits.put(player, 0);
			
		
			
			aurabot.spawn(player.getLocation());
			aurabot.show(player);
			
			
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

				@Override
				public void run() {
					
					Main.aurabotting.remove(player);
					
					aurabot.hide(player);
					
				}
					
			}, 100);
				
		} catch(Exception e1) {
			
			if(args[0].equals("threshold")) {
				
				if(!(sender.hasPermission("aurabot.managethreshold"))) {
					sender.sendMessage(ChatColor.RED + "You do not have permission to run this command!");
					return true;
				}
				
				
				int threshold = Integer.parseInt(args[1]);
				
				config.set("aura-bot-threshold", threshold);
				Main.plugin.saveConfig();
				
				sender.sendMessage(ChatColor.GOLD + "Aura Bot threshold was updated to " +ChatColor.RED+ threshold);
				return true;
				
			}
			
			sender.sendMessage(ChatColor.RED + "Player is not online or doesn't exist!");
			return true;
		}
		
		
		return true;
	}

}
