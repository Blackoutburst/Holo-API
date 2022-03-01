package com.blackoutburst.holoapi.main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
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

	public static List<APlayer> players = new ArrayList<>();
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onMoveEvent(PlayerMoveEvent event) {
		final Player player = event.getPlayer();
		final APlayer ap = APlayer.get(player);
		if (ap == null) return;

		final double playerX = player.getLocation().getX();
		final double playerY = player.getLocation().getY();
		final double playerZ = player.getLocation().getZ();

		for (final Holo holo : ap.holos) {
			final double holoX = holo.getLocation().getX();
			final double holoY = holo.getLocation().getY();
			final double holoZ = holo.getLocation().getZ();

			final double x = playerX - holoX;
			final double y = playerY - holoY;
			final double z = playerZ - holoZ;

			final boolean distance = ((x * x) + (y * y) + (z * z)) >= 5000;
			
			if (distance && ap.holosVisible.get(holo.getUUID())) {
				HoloManager.hideHolo(event.getPlayer(), holo);
				ap.holosVisible.put(holo.getUUID(), false);
			}
			if (distance && !ap.holosVisible.get(holo.getUUID())) {
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
