package com.blackoutburst.holoapi.core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.blackoutburst.holoapi.nms.NMSEntities;
import com.blackoutburst.holoapi.nms.NMSEntityMetadata;
import com.blackoutburst.holoapi.nms.NMSEntityTeleport;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Holo {

	protected UUID uuid;
	protected String name;
	protected Location location;
	protected NMSEntities entity;
	protected List<NMSEntities> lines;
	
	public Holo (UUID uuid, String name) {
		this.uuid = uuid;
		this.name = name;
		this.location = null;
		this.entity = null;
		this.lines = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public Holo setName(Player player, String name) {
		try {
			final Method setCustomName = this.getEntity().getClass().getMethod("setCustomName", String.class);

			setCustomName.invoke(this.getEntity(), name);

			NMSEntityMetadata.send(player, this.getEntity());
		} catch(Exception e) {
			e.printStackTrace();
		}

		this.name = name;
		return (this);
	}

	public Location getLocation() {
		return location;
	}

	public Holo setLocation(Player player, Location location) {
		NMSEntityTeleport.send(player, this.getEntity(), location.getX(), location.getY(), location.getZ());

		this.location = location;
		return (this);
	}

	public NMSEntities getEntity() {
		return entity;
	}

	public void setEntity(NMSEntities entity) {
		this.entity = entity;
	}

	public UUID getUUID() {
		return uuid;
	}

	public List<NMSEntities> getLines() {
		return lines;
	}

	public Holo addLine(String line) {
		try {
			final NMSEntities entity = new NMSEntities(this.getLocation().getWorld(), NMSEntities.EntityType.ARMOR_STAND);

			final Method setCustomName = entity.getEntity().getClass().getMethod("setCustomName", String.class);
			final Method setCustomNameVisible = entity.getEntity().getClass().getMethod("setCustomNameVisible", boolean.class);
			final Method setPosition = entity.getEntity().getClass().getMethod("setPosition", double.class, double.class, double.class);
			final Method setInvisible = entity.getEntity().getClass().getMethod("setInvisible", boolean.class);
			final Method setSmall = entity.getEntity().getClass().getMethod("setSmall", boolean.class);

			setPosition.invoke(entity.getEntity(), this.getLocation().getX(), this.getLocation().getY() - (0.25 * (this.getLines().size() + 1)), this.getLocation().getZ());
			setCustomName.invoke(entity.getEntity(), line);
			setCustomNameVisible.invoke(entity.getEntity(), true);
			setInvisible.invoke(entity.getEntity(), true);
			setSmall.invoke(entity.getEntity(), true);

			this.lines.add(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (this);
	}
	
	public Holo removeLine(int index) {
		try {
			this.lines.remove(index);
		} catch(Exception e) {
			System.err.println("The specified line: "+index+" does not exist");
			e.printStackTrace();
		}
		return (this);
	}
	
}
