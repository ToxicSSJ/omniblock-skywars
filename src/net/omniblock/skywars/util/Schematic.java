package net.omniblock.skywars.util;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.MCEditSchematicFormat;

import net.omniblock.skywars.Skywars;

@SuppressWarnings("deprecation")
public class Schematic {

	protected WorldEditPlugin we = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
	
	public EditSession session;
	public CuboidClipboard cc;
	
	public boolean pasted;

	public void pasteSchematic(String dir, String string, Location loc) {

		if(pasted)
			return;
		
		File schematic = new File(Skywars.getInstance().getDataFolder(), dir + string);

		if (schematic.exists()) {

			try {

				session = we.getWorldEdit().getEditSessionFactory().getEditSession((LocalWorld) new BukkitWorld(loc.getWorld()), 5000);
				cc = MCEditSchematicFormat.getFormat(schematic).load(schematic);

				cc.paste(session, new com.sk89q.worldedit.Vector(loc.getX(), loc.getY(), loc.getZ()), false);
				return;
				
			} catch(MaxChangedBlocksException | DataException | IOException e) {
				e.printStackTrace();
			}

			pasted = true;

		}

	}

	public void removeSchematic() {

		if(session != null) {
			
			if(session.getBlockBag() != null)
				session.getBlockBag().flushChanges();
			
			session.undo(session);
			
			session = null;
			pasted = false;
			cc = null;
			
		}

	}

}
