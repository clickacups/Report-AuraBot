package commands;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import main.Main;
import main.NPC;
import net.md_5.bungee.api.ChatColor;

public class Report implements CommandExecutor {

	FileConfiguration config = Main.plugin.getConfig();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	
		if(!(sender instanceof Player)) {
			sender.sendMessage("Only users can execute this command!");
			return true;
		}
		
		Player player = (Player) sender;
		
		if(!(player.hasPermission("report.sendreport"))) {
			sender.sendMessage(ChatColor.RED + "You do not have permission to run this command!");
			return true;
		}
		
		if(args.length<2) {
			player.sendMessage(ChatColor.RED + "Invalid arguments. Usage: /report <player> <reason>");
			return true;
		}
		
		try {
			
			Player reported_player = Bukkit.getPlayer(args[0]);
			
			if(!(reported_player.getName().equals(args[0]))) {
				player.sendMessage(ChatColor.RED + "This player doesn't exist!");
				return true;
			}
			
			
		// report went through
			
		
			
		String report_reason = "";
		ArrayList<String> report_array = new ArrayList<String>();
		
		for(String arg: args) {
			report_array.add(arg);
		}
		
		for(String arg: report_array) {
			
			if(report_array.indexOf(arg)>0) {
				report_reason += arg + " ";
			}
			
		}
		
		if(Main.reports.get(player)!=null) {
			
			if(Main.reports.get(player).contains(reported_player)) {
				
				player.sendMessage(ChatColor.RED + "You've already reported this player! Please do not submit more than one report.");
				return true;
				
				
			}
			
		}
		
		player.sendMessage(ChatColor.GREEN + "Your report has been submitted!");
		
		int reports = config.getInt("reports." + reported_player.getUniqueId() + ".report_int");
		int bot_reports = config.getInt("reports." + reported_player.getUniqueId() + ".bot_report_int");

		reports = reports+1;
		bot_reports = bot_reports+1;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd : HH.mm.ss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		
		config.set("reports." + reported_player.getUniqueId() + ".report_int", reports);
		config.set("reports." + reported_player.getUniqueId() + ".bot_report_int", bot_reports);
		config.set("reports." + reported_player.getUniqueId() + ".report" +reports+".reason", report_reason);
		config.set("reports." + reported_player.getUniqueId() + ".report" +reports+ ".reporter", player.getUniqueId().toString());
		config.set("reports." + reported_player.getUniqueId() + ".report" +reports+ ".timestamp", sdf.format(timestamp));
		
		// save config
		Main.plugin.saveConfig();
		
		if(Main.reports.get(player)==null) {
			ArrayList<Player> reported_players = new ArrayList<Player>();
			reported_players.add(reported_player);
			Main.reports.put(player, reported_players);
		} else {
			Main.reports.get(player).add(reported_player);
		}
			
		
		for(Player p: Bukkit.getOnlinePlayers()) {
			
			if(p.hasPermission("report.recievenotis")) {
				
				p.sendMessage(ChatColor.BLUE + "[Report] [" + reported_player.getWorld().getName() + "] " +ChatColor.WHITE+ reported_player.getName() + ChatColor.GRAY + " reported by " +ChatColor.WHITE + player.getName() + ChatColor.GRAY + " for:");
				p.sendMessage(ChatColor.BLUE + "    - " + ChatColor.GRAY + report_reason);
				
			}
			
		}
		
		int aurabot_report_int = config.getInt("aurabot-report-int");
		
		System.out.println("Reports: " + reports);
		System.out.println("Report-int: " + aurabot_report_int);
		
		if(aurabot_report_int==0) {
			
			config.set("aurabot-report-int", 3);
			
		} else {
			
			if(bot_reports==aurabot_report_int || bot_reports>aurabot_report_int) {
				
				// summon aurabot
				
				Player target = Bukkit.getPlayerExact(args[0]);
				
		
				NPC aurabot = new NPC(target.getWorld(), "");
				
				Main.aurabotting.put(target, aurabot);
				Main.aurabot_hits.put(target, 0);
				
			
				
				aurabot.spawn(target.getLocation());
				aurabot.show(target);
				
				config.set("reports." + reported_player.getUniqueId() + ".bot_report_int", 0);
				Main.plugin.saveConfig();
				
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable() {

					@Override
					public void run() {
						
						Main.aurabotting.remove(target);
						
						aurabot.hide(target);
						
					}
						
				}, 100);
				
				
			}
			
		}
		
		
		
		
		
		
		
		
		
		
		
		} catch(Exception e) {
			player.sendMessage(ChatColor.RED + "This player doesn't exist!");
			return true;
		}
		
		
		
		return true;
	}

}
