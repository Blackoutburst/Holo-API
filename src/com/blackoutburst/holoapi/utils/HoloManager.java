package com.blackoutburst.holoapi.utils;

import com.blackoutburst.holoapi.nms.NMSEntities;
import com.blackoutburst.holoapi.nms.NMSEntityDestroy;
import com.blackoutburst.holoapi.nms.NMSSpawnEntityLiving;
import org.bukkit.entity.Player;

import com.blackoutburst.holoapi.core.APlayer;
import com.blackoutburst.holoapi.core.Holo;

import java.lang.reflect.Method;

public class HoloManager {

	/**
	 * Make the holo and all his lines disappear
	 *
	 * @param p the player that will receive the packets
	 * @param holo the holo you wish to hide
	 */
	public static void hideHolo(Player p, Holo holo) {
		NMSEntityDestroy.send(p, holo.getEntity().getID());

		for (NMSEntities line : holo.getLines())
			NMSEntityDestroy.send(p, line.getID());
	}

	/**
	 * Make a holo and all his lines appear
	 *
	 * @param p the player that will receive the packet
	 * @param holo the holo you want to make appear
	 */
	public static void reloadHolo(Player p, Holo holo) {
		NMSSpawnEntityLiving.send(p, holo.getEntity());

		for (NMSEntities line : holo.getLines())
			NMSSpawnEntityLiving.send(p, line);
	}

	/**
	 * Create a new Holo
	 *
	 * @param p the player that will receive the packets
	 * @param holo the holo configuration
	 */
	public static void spawnHolo(Player p, Holo holo) {
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

			holo.setEntity(entity);

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

	/**
	 * Delete a holo
	 *
	 * @param p the player that will receive the packet
	 * @param holo the holo you wish to delete
	 */
	public static void deleteHolo(Player p, Holo holo) {
		NMSEntityDestroy.send(p, holo.getEntity().getID());

		for (NMSEntities line : holo.getLines())
			NMSEntityDestroy.send(p, line.getID());
		
		APlayer ap = APlayer.get(p);
		if (ap != null) {
			ap.holos.remove(holo);
			ap.holosVisible.remove(holo.getUUID());
		}
	}
}
