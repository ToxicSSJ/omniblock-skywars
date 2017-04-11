package net.omniblock.skywars.util;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalWorld;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.MCEditSchematicFormat;

import net.omniblock.skywars.Skywars;
import omniblock.on.OmniNetwork;

@SuppressWarnings("deprecation")
public class Schematic {	
	
	private static EditSession savedsession;
	private static boolean pasted;
	
	public static void pasteSchematic(String dir, String string, Location loc) {
    	
    	if(Skywars.getInstance().getServer().getPluginManager().isPluginEnabled("WorldEdit")){
    		
    		WorldEditPlugin we = (WorldEditPlugin)Bukkit.getPluginManager().getPlugin("WorldEdit");
            File schematic = new File(Skywars.getInstance().getDataFolder(), dir + string);
            
            if(schematic.exists()) {
                
				EditSession session = we.getWorldEdit().getEditSessionFactory().getEditSession((LocalWorld)new BukkitWorld(loc.getWorld()), 10000000);
                try {
					MCEditSchematicFormat.getFormat(schematic).load(schematic).paste(session,
																					 new Vector(loc.getX(),
																							   	loc.getY(),
																							    loc.getZ()),
																					 			false);
				} catch (MaxChangedBlocksException | DataException | IOException e) {
					e.printStackTrace();
				}
                
                setPasted(true);
                setSession(session);
                
            }
    	} else {
    		OmniNetwork.sendError("&cNo se ha detectado el plugin WorldEdit!");
    		return;
    	}
    }
    
    public static void removeSchematic(Location loc){
    	if(isPasted()){
    		if(getSession() != null){
    			
    			getSession().undo(getSession());
    			
    			Block base = loc.getBlock();
    			Block plate = loc.getBlock().getRelative(BlockFace.UP);
    			
    			base.setType(Material.AIR); plate.setType(Material.AIR);
    			
    		}
    	}
    }

	public static boolean isPasted() {
		return pasted;
	}

	public static void setPasted(boolean pasted) {
		Schematic.pasted = pasted;
	}

	public static EditSession getSavedsession() {
		return savedsession;
	}

	public static void setSavedsession(EditSession savedsession) {
		Schematic.savedsession = savedsession;
	}
	
	public static EditSession getSession() {
		return savedsession;
	}

	public static void setSession(EditSession savedsessio) {
		savedsession = savedsessio;
	}
	
}
