package com.blackoutburst.holoapi.nms;

import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class NMSEntityTeleport {


    /**
     * Teleport an entity to a new location
     *
     * @param player the player that will receive the packet
     * @param entity the entity that will be teleported
     * @param x the new x position
     * @param y the new y position
     * @param z the new z position
     */
    public static void send(Player player, NMSEntities entity, double x, double y, double z) {
        try {
            final Class<?> packetClass = NMS.getClass("PacketPlayOutEntityTeleport");
            final Class<?> entityClass = NMS.getClass("Entity");

            final Constructor<?> packetConstructor = packetClass.getConstructor(entityClass);

            final Method setPosition = entity.entity.getClass().getMethod("setPosition", double.class, double.class, double.class);
            setPosition.invoke(entity.entity, x, y, z);

            final Object packet = packetConstructor.newInstance(entity.entity);

            NMS.sendPacket(player, packet);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
