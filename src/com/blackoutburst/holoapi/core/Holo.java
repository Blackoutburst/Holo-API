package com.blackoutburst.holoapi.core;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.WorldServer;

public class Holo {

	protected UUID uuid;
	protected String name;
	protected Location location;
	protected int entityId;
	protected EntityArmorStand entity;
	protected List<EntityArmorStand> lines;
	
	public Holo (UUID uuid, String name) {
		this.uuid = uuid;
		this.name = name;
		this.location = null;
		this.entityId = -1;
		this.entity = null;
		this.lines = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public Holo setName(String name) {
		this.name = name;
		return (this);
	}

	public Location getLocation() {
		return location;
	}

	public Holo setLocation(Location location) {
		this.location = location;
		return (this);
	}

	public int getEntityId() {
		return entityId;
	}

	public Holo setEntityId(int entityId) {
		this.entityId = entityId;
		return (this);
	}

	public EntityArmorStand getEntity() {
		return entity;
	}

	public Holo setEntity(EntityArmorStand entity) {
		this.entity = entity;
		return (this);
	}

	public UUID getUUID() {
		return uuid;
	}

	public List<EntityArmorStand> getLines() {
		return lines;
	}

	public Holo setLines(List<EntityArmorStand> lines) {
		this.lines = lines;
		return (this);
	}
	
	public Holo addLine(String line) {
		WorldServer s = ((CraftWorld) this.getLocation().getWorld()).getHandle();
		
		EntityArmorStand holoEntity = new EntityArmorStand(s);
		holoEntity.setLocation(this.getLocation().getX(), this.getLocation().getY() - (0.25 * (this.getLines().size() + 1)), this.getLocation().getZ(), 0, 0);
		holoEntity.setCustomName(line);
		holoEntity.setCustomNameVisible(true);
		holoEntity.setInvisible(true);
		holoEntity.setSmall(true);
		
		this.lines.add(holoEntity);
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
