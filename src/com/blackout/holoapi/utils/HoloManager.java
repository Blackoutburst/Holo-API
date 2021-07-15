package com.blackout.holoapi.utils;

import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.blackout.holoapi.core.APlayer;
import com.blackout.holoapi.core.Holo;

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityHeadRotation;
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
	}
	
	/**
	 * Respawn the Holo when the player get close enough to it
	 * @param p
	 * @param holo
	 */
	public static void reloadHolo(Player p, Holo holo) {
		sendPacket(p, holo);
	}
	
	/**
	 * Spawn the Holo when at a specific location
	 * @param holo
	 * @param p
	 */
	public static void spawnHolo(Holo holo, Player p) {
		WorldServer s = ((CraftWorld) holo.getLocation().getWorld()).getHandle();
		
		EntityArmorStand holoEntity = new EntityArmorStand(s);
	
		holoEntity.setLocation(holo.getLocation().getX(), holo.getLocation().getY(), holo.getLocation().getZ(), holo.getLocation().getYaw(), holo.getLocation().getPitch());
		
		holoEntity.setCustomName(holo.getName());
		holoEntity.setCustomNameVisible(true);
		holoEntity.setInvisible(true);
		holoEntity.setSmall(true);
		
		APlayer ap = APlayer.get(p);
		
		holo.setEntityId(holoEntity.getId())
		.setEntity(holoEntity);
		ap.holos.add(holo);
		
		sendPacket(p, holo);
		
		ap.holosVisible.put(holo.getUUID(), true);
	}
	
	/**
	 * Remove a Holo
	 * @param p
	 * @param holo
	 */
	public static void deleteHolo(Player p, Holo holo) {
		PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
		
		connection.sendPacket(new PacketPlayOutEntityDestroy(holo.getEntityId()));
		
		APlayer ap = APlayer.get(p);
		
		ap.holos.remove(holo);
		ap.holosVisible.remove(holo.getUUID());
	}
	
	/**
	 * Send packet used to spawn a Holo
	 * @param p
	 * @param watcher
	 * @param holo
	 */
	private static void sendPacket(Player p, Holo holo) {
		PacketPlayOutEntityHeadRotation headRotationPacket = new PacketPlayOutEntityHeadRotation(holo.getEntity(), (byte) ((holo.getLocation().getYaw() * 256.0F) / 360.0F));
		PacketPlayOutSpawnEntityLiving spawnPacket = new PacketPlayOutSpawnEntityLiving(holo.getEntity());
		
		PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
		connection.sendPacket(spawnPacket);
		connection.sendPacket(headRotationPacket);
	}
}
