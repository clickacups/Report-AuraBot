package events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import main.Main;

public class PlayerDisconnect implements Listener {
	
	@EventHandler
	public void onPlayerDisconnect(PlayerQuitEvent e) {
		
		if(Main.reports.containsKey(e.getPlayer())) {
			
			Main.reports.remove(e.getPlayer());
			
		}
		
	}

}
