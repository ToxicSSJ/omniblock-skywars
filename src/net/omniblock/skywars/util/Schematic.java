package net.omniblock.skywars.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;

import net.omniblock.skywars.Skywars;

public class Schematic {
	
	private static List<Block> savedsession;
	private static boolean pasted;
	
	public Schematic() {
		
	}
	
	public void pasteSchematic(String dir, String string, Location loc) {
    		
        File schematic = new File(Skywars.getInstance().getDataFolder(), dir + string);
            
        if(schematic.exists()) {
               
            List<Block> session = new ArrayList<Block>();
			try {
				session = StructureAPI.pasteSchematic(loc.getWorld(), loc, StructureAPI.loadSchematic(schematic));
			} catch (IOException e) {
				e.printStackTrace();
			}
            	
            pasted = true;
            savedsession = session;
            
        }
            
    }
	
    public void removeSchematic(){
    	
    	if(pasted){
    		if(savedsession != null){
    			StructureAPI.removeSchematic(savedsession);
    		}
    	}
    	
    }
	
}
