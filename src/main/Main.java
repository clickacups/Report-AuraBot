package main;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.base.Preconditions;

import commands.AuraBot;
import commands.ClearReports;
import commands.GetReports;
import commands.Report;
import events.EntityHit;
import events.PlayerDisconnect;
import events.PlayerEvents;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.util.internal.ThreadLocalRandom;
import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin {
	
	public static Main plugin;
	
	public static ArrayList<String> report_key_words = new ArrayList<String>();
	// complicated shit :o
	public static HashMap<Player, ArrayList<Player>> reports = new HashMap<Player, ArrayList<Player>>();
	public static HashMap<Player, Integer> aurabot_hits = new HashMap<Player, Integer>();
	public static HashMap<Player, NPC> aurabotting = new HashMap<Player, NPC>();
	
	@SuppressWarnings("deprecation")
	@Override
	public void onEnable() {
		
		plugin = this;
		
		loadConfig();
		
		
		System.out.println("Report plugin has started up!");
		
		getCommand("report").setExecutor(new Report());
		getCommand("clearreports").setExecutor(new ClearReports());
		getCommand("reports").setExecutor(new GetReports());
		
		getCommand("aurabot").setExecutor(new AuraBot());
		
		Bukkit.getPluginManager().registerEvents(new PlayerDisconnect(), this);
		Bukkit.getPluginManager().registerEvents(new EntityHit(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerEvents(), this);
		
		if(plugin.getConfig().getInt("aura-bot-threshold")==0) {
			plugin.getConfig().set("aura-bot-threshold", 35);
			plugin.saveConfig();
		}
		if(plugin.getConfig().getInt("aurabot-report-int")==0) {
			plugin.getConfig().set("aurabot-report-int", 3);
			plugin.saveConfig();
		}
		
		
		for(Player player: Bukkit.getOnlinePlayers()) {
			
			try {
				injectPlayer(player);
			} catch(Exception e) {
				// player was already injected
			}
			
		}
		
		
		plugin.getConfig().options().header("#DaneelsReports");
		plugin.getConfig().options().copyHeader(true);
	
		
		Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {

			@Override
			public void run() {
				
				for(Player player: Bukkit.getOnlinePlayers()) {
				
					if(Main.aurabotting.containsKey(player)) {
						
						// spawn aurabot
						
						NPC bot = Main.aurabotting.get(player);
						
						
						
						Block block = player.getLocation().getBlock();
						
						Block block1 = player.getWorld().getBlockAt(block.getX()+1, block.getY()+2, block.getZ()-1);
						Block block2 = player.getWorld().getBlockAt(block.getX()-1, block.getY()-2, block.getZ()+1);
						
						Location l = getRandomLocation(block1.getLocation(), block2.getLocation());
						
						bot.setLocation(l);
						bot.show(player);
						
						
					}
					
				}
				
				
				
			}
			
			
			
		}, 4, 4);
		
		
	}
	
	public Location getRandomLocation(Location loc1, Location loc2) {
        Preconditions.checkArgument(loc1.getWorld() == loc2.getWorld());
        double minX = Math.min(loc1.getX(), loc2.getX());
        double minY = Math.min(loc1.getY(), loc2.getY());
        double minZ = Math.min(loc1.getZ(), loc2.getZ());

        double maxX = Math.max(loc1.getX(), loc2.getX());
        double maxY = Math.max(loc1.getY(), loc2.getY());
        double maxZ = Math.max(loc1.getZ(), loc2.getZ());

        return new Location(loc1.getWorld(), randomDouble(minX, maxX), randomDouble(minY, maxY), randomDouble(minZ, maxZ));
    }

    public double randomDouble(double min, double max) {
    	return min + ThreadLocalRandom.current().nextDouble(Math.abs(max - min + 1));
    }
	
	
	public static void removePlayer(Player player) {
		Channel channel = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel;
		channel.eventLoop().submit(() -> {
			channel.pipeline().remove(player.getName());
			return null;
		});
	}
	
	public static void injectPlayer(Player player) {
		ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler() {
			
			@Override
			public void channelRead(ChannelHandlerContext channelHandlerContext, Object packet) throws Exception {
				
				//Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "Packet read: " +ChatColor.RED + packet.toString());
	
				super.channelRead(channelHandlerContext, packet);
				
	
				
				if(packet.toString().contains("PacketPlayInUseEntity")) {
					
					// entity is being hit
					
					if(Main.aurabotting.containsKey(player)) {
						
						// if the player is currently getting surrounded
						
						
					
						
						if(plugin.getConfig().getInt("aura-bot-threshold")==0) {
							
							plugin.getConfig().set("aura-bot-threshold", 35);
							plugin.saveConfig();
							
						} else {
							
							
							
							Main.aurabot_hits.put(player, Main.aurabot_hits.get(player)+1);
							
							int threshold = plugin.getConfig().getInt("aura-bot-threshold");
							
						

							if(Main.aurabot_hits.get(player)>threshold) {
								
								// PLAYER GETS HIGH CLICKS ON BOT
								// = BAN
								
								
								// ban code
								
								for(Player p: Bukkit.getOnlinePlayers()) {
									if(p.hasPermission("aurabot.recievenotis")) {
										p.sendMessage(ChatColor.DARK_RED + "AuraBot Failed - " + ChatColor.RED + player.getName() + ChatColor.GOLD + " went over threshold (" +ChatColor.RED + Main.plugin.getConfig().getString("aura-bot-threshold") + ChatColor.GOLD + ")");
									}
								}
								
								
								
								
								
		
							}
							
							
							
						}
						
						
						
					}
					
					
				}
				
				
			}
			
			@Override
			public void write(ChannelHandlerContext channelHandlerContext, Object packet, ChannelPromise channelPromise) throws Exception {
				//Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "Packet write: " +ChatColor.GREEN + packet.toString());
				super.write(channelHandlerContext, packet, channelPromise);
			}
			 
			
			
			
			
		};
		
		ChannelPipeline pipeline = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel.pipeline();
		pipeline.addBefore("packet_handler", player.getName(), channelDuplexHandler);
		
	}
	
	public void loadConfig() {
	
		// copy the defaults
		getConfig().options().copyDefaults(true);
		saveConfig();
	
	
	}
	
	public void circlePlayer(Player player, NPC aurabot) {
		
		
		
	}
	

	
}
