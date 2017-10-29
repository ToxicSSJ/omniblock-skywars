package net.omniblock.skywars.util.lib.bukkit;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;

public class FallingBlockWrapper {

	private static WrappedDataWatcher fallingblockWatcher;

	public static void setup(World world) {

		fallingblockWatcher = getDefaultWatcher(world, EntityType.FALLING_BLOCK);
		return;

	}

	@SuppressWarnings({ "deprecation" })
	public static void sendPacket(Player player, Material material, byte subid) {

		PacketContainer newPacket = new PacketContainer(24);

		newPacket.getIntegers().write(0, 500).write(1, (int) EntityType.FALLING_BLOCK.getTypeId())
				.write(2, (int) (player.getLocation().getX() * 32)).write(3, (int) (player.getLocation().getY() * 32))
				.write(4, (int) (player.getLocation().getZ() * 32)).write(5, material.getId()).write(6, (int) subid);

		newPacket.getDataWatcherModifier().write(0, fallingblockWatcher);

		try {
			ProtocolLibrary.getProtocolManager().sendServerPacket(player, newPacket);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

	}

	public static WrappedDataWatcher getDefaultWatcher(World world, EntityType type) {

		Entity entity = world.spawnEntity(new Location(world, 0, 256, 0), type);
		WrappedDataWatcher watcher = WrappedDataWatcher.getEntityWatcher(entity).deepClone();

		entity.remove();
		return watcher;

	}

}
