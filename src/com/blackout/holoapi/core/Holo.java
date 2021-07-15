package com.blackout.holoapi.core;

import java.util.UUID;

import org.bukkit.Location;

import net.minecraft.server.v1_8_R3.EntityArmorStand;

public class Holo {

	protected UUID uuid;
	protected String name;
	protected Location location;
	protected int entityId;
	protected EntityArmorStand entity;
	
	public Holo (UUID uuid, String name) {
		this.uuid = uuid;
		this.name = name;
		this.location = null;
		this.entityId = -1;
		this.entity = null;
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
}
