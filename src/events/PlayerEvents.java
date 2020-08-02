package events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import main.Main;

public class PlayerEvents implements Listener {


	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		e.getPlayer().sendMessage("You were injected.");
		Main.injectPlayer(e.getPlayer());
	}
	
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		Main.removePlayer(e.getPlayer());
	}
	
}
