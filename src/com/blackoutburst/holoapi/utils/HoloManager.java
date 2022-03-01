package com.blackoutburst.holoapi.utils;

import com.blackoutburst.holoapi.nms.NMSEntities;
import com.blackoutburst.holoapi.nms.NMSSpawnEntityLiving;
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

import java.lang.reflect.Method;

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
		try {
			final NMSEntities entity = new NMSEntities(p.getWorld(), NMSEntities.EntityType.ARMOR_STAND);

			final Method setCustomName = entity.getEntity().getClass().getMethod("setCustomName", String.class);
			final Method setCustomNameVisible = entity.getEntity().getClass().getMethod("setCustomNameVisible", boolean.class);
			final Method setPosition = entity.getEntity().getClass().getMethod("setPosition", double.class, double.class, double.class);
			final Method setInvisible = entity.getEntity().getClass().getMethod("setInvisible", boolean.class);
			final Method setSmall = entity.getEntity().getClass().getMethod("setSmall", boolean.class);

			setPosition.invoke(entity.getEntity(), holo.getLocation().getX(), holo.getLocation().getY(), holo.getLocation().getZ());
			setCustomName.invoke(entity.getEntity(), holo.getName());
			setCustomNameVisible.invoke(entity.getEntity(), true);
			setInvisible.invoke(entity.getEntity(), true);
			setSmall.invoke(entity.getEntity(), true);

			holo.setEntityId(entity.getID())
				.setEntity(entity);


			APlayer ap = APlayer.get(p);
			if (ap != null) {
				ap.holos.add(holo);
				ap.holosVisible.put(holo.getUUID(), true);
			}

			NMSSpawnEntityLiving.send(p, entity);

			for (NMSEntities line : holo.getLines())
				NMSSpawnEntityLiving.send(p, line);

		} catch (Exception e) {
			e.printStackTrace();
		}
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
