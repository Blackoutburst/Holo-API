package com.blackoutburst.holoapi.main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.blackoutburst.holoapi.core.APlayer;
import com.blackoutburst.holoapi.core.Holo;
import com.blackoutburst.holoapi.utils.HoloManager;

public class Main extends JavaPlugin implements Listener {

	public static List<APlayer> players = new ArrayList<APlayer>();
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onMoveEvent(PlayerMoveEvent event) {
		APlayer ap = APlayer.get(event.getPlayer());
		for (Holo holo : ap.holos) {
			double distance = Math.sqrt(
					Math.pow(event.getPlayer().getLocation().getX() - holo.getLocation().getX(), 2) +
					Math.pow(event.getPlayer().getLocation().getY() - holo.getLocation().getY(), 2) +
					Math.pow(event.getPlayer().getLocation().getZ() - holo.getLocation().getZ(), 2));
			
			if (distance > 70 && ap.holosVisible.get(holo.getUUID())) {
				HoloManager.hideHolo(event.getPlayer(), holo);
				ap.holosVisible.put(holo.getUUID(), false);
			}
			if (distance < 70 && !ap.holosVisible.get(holo.getUUID())) {
				HoloManager.reloadHolo(event.getPlayer(), holo);
				ap.holosVisible.put(holo.getUUID(), true);
			}
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
		players.add(new APlayer(event.getPlayer()));
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		players.remove(APlayer.get(event.getPlayer()));
	}
}
