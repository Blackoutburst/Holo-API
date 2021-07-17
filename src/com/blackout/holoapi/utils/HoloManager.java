package com.blackout.holoapi.utils;

import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.blackout.holoapi.core.APlayer;
import com.blackout.holoapi.core.Holo;

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.WorldServer;

public class HoloManager {

	/**
	 * Destroy the Holo when the player get too far from it
	 * @param p
	 * @param holo
	 */
	public static void hideHolo(Player p, Holo holo) {
		PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
		connection.sendPacket(new PacketPlayOutEntityDestroy(holo.getEntityId()));
		
		for (EntityArmorStand line : holo.getLines())
			connection.sendPacket(new PacketPlayOutEntityDestroy(line.getId()));
	}
	
	/**
	 * Respawn the Holo when the player get close enough to it
	 * @param p
	 * @param holo
	 */
	public static void reloadHolo(Player p, Holo holo) {
		PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
		connection.sendPacket(new PacketPlayOutSpawnEntityLiving(holo.getEntity()));
		
		for (EntityArmorStand line : holo.getLines())
			connection.sendPacket(new PacketPlayOutSpawnEntityLiving(line));
	}
	
	/**
	 * Spawn the Holo when at a specific location
	 * @param holo
	 * @param p
	 */
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
		ap.holos.add(holo);
		ap.holosVisible.put(holo.getUUID(), true);
		
		PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
		connection.sendPacket(new PacketPlayOutSpawnEntityLiving(holo.getEntity()));
		
		for (EntityArmorStand line : holo.getLines())
			connection.sendPacket(new PacketPlayOutSpawnEntityLiving(line));
	}
	
	/**
	 * Remove a Holo
	 * @param p
	 * @param holo
	 */
	public static void deleteHolo(Player p, Holo holo) {
		PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
		connection.sendPacket(new PacketPlayOutEntityDestroy(holo.getEntityId()));
		
		for (EntityArmorStand line : holo.getLines())
			connection.sendPacket(new PacketPlayOutEntityDestroy(line.getId()));
		
		APlayer ap = APlayer.get(p);
		ap.holos.remove(holo);
		ap.holosVisible.remove(holo.getUUID());
	}
}
