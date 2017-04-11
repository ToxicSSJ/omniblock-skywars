package net.omniblock.skywars.patch.managers;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.sk89q.worldedit.EditSession;

import net.omniblock.skywars.patch.managers.MapManager.MapType;
import net.omniblock.skywars.util.Scan;
import net.omniblock.skywars.util.Schematic;

public class LobbySchematic {

	private MapType currentMapType = MapType.UNKNOWN;
	private static EditSession savedsession;

	private static Location lobbyschematic;
	private static boolean pasted;
	
	private static String path;
	private static String pathZ;
	
	public LobbySchematic(MapType currentMapType){
		
		this.currentMapType = currentMapType;
		this.path = "SWWL.schematic";
		this.pathZ = "SWZWL.schematic";
		
		Location matchedBlocks = Scan.singleBlock(Material.BEDROCK);
		Block bl = matchedBlocks.getBlock();
		
		if(bl.getRelative(0,1,0).getType() == Material.WOOD_PLATE){
			lobbyschematic = matchedBlocks;
			bl.getRelative(0,1,0).setType(Material.AIR);
		}
		
	}
	
	public void pasteLobbySchematic() {
		switch(currentMapType){
			case NORMAL:
				Schematic.pasteSchematic("/data/schems/", path, lobbyschematic);
				break;
			case Z: 
				Schematic.pasteSchematic("/data/schems/", pathZ, lobbyschematic);
				break;
			case UNKNOWN: break; // si el lugar es desconocido no pasará nada
		}
	}
	
   
	public Location getLocation() {
		return lobbyschematic;
	}

}

