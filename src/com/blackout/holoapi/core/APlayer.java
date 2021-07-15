package com.blackout.holoapi.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.blackout.holoapi.main.Main;

public class APlayer {

	public Player player;
	public Map<UUID, Boolean> holosVisible;
	public List<Holo> holos;
	
	public APlayer (Player player) {
		this.player = player;
		this.holosVisible = new HashMap<UUID, Boolean>();
		this.holos = new ArrayList<Holo>();
	}
	
	public static APlayer get(Player player) {
		for (APlayer p : Main.players)
			if (p.player.getUniqueId().equals(player.getUniqueId()))
				return (p);
		return (null);
	}
}
