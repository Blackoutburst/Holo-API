package com.blackoutburst.holoapi.utils;

import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.blackoutburst.holoapi.core.APlayer;
import com.blackoutburst.holoapi.core.Holo;

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.WorldServer;

public class HoloManager {

	public static void hideHolo(Player p, Holo holo) {
		PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
		connection.sendPacket(new PacketPlayOutEntityDestroy(holo.getEntityId()));
		
		for (EntityArmorStand line : holo.getLines())
			connection.sendPacket(new PacketPlayOutEntityDestroy(line.getId()));
	}
	
	public static void reloadHolo(Player p, Holo holo) {
		PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
		connection.sendPacket(new PacketPlayOutSpawnEntityLiving(holo.getEntity()));
		
		for (EntityArmorStand line : holo.getLines())
			connection.sendPacket(new PacketPlayOutSpawnEntityLiving(line));
	}
	
	public static void spawnHolo(Holo holo, Player p) {
		WorldServer s = ((CraftWorld) holo.getLocation().getWorld()).getHandle();
		
		EntityArmorStand holoEntity = new EntityArmorStand(s);
		holoEntity.setLocation(holo.getLocation().getX(), holo.getLocation().getY(), holo.getLocation().getZ(), 0, 0);
		holoEntity.setCustomName(holo.getName());
		holoEntity.setCustomNameVisible(true);
		holoEntity.setInvisible(true);
		holoEntity.setSmall(true);
		
		holo.setEntityId(holoEntity.getId())
		.setEntity(holoEntity);
		
		APlayer ap = APlayer.get(p);
		if (ap != null) {
			ap.holos.add(holo);
			ap.holosVisible.put(holo.getUUID(), true);
		}

		PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
		connection.sendPacket(new PacketPlayOutSpawnEntityLiving(holo.getEntity()));
		
		for (EntityArmorStand line : holo.getLines())
			connection.sendPacket(new PacketPlayOutSpawnEntityLiving(line));
	}
	
	public static void deleteHolo(Player p, Holo holo) {
		PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
		connection.sendPacket(new PacketPlayOutEntityDestroy(holo.getEntityId()));
		
		for (EntityArmorStand line : holo.getLines())
			connection.sendPacket(new PacketPlayOutEntityDestroy(line.getId()));
		
		APlayer ap = APlayer.get(p);
		if (ap != null) {
			ap.holos.remove(holo);
			ap.holosVisible.remove(holo.getUUID());
		}
	}
}
