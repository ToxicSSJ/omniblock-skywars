package net.omniblock.skywars.patch.managers;

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
import net.omniblock.skywars.patch.managers.MapManager.MapType;
import net.omniblock.skywars.util.Scan;
import omniblock.on.OmniNetwork;
import omniblock.on.util.TextUtil;

@SuppressWarnings("deprecation")
public class LobbySchematic {

	private MapType currentMapType = MapType.UNKNOWN;
	private EditSession savedsession;
	
	private Location lobbyschematic;
	private boolean pasted = false;
	
	private String path;
	private String pathZ;
	
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
				pasteLobbySchematic(path);
				break;
			case Z: 
				pasteLobbySchematic(pathZ);
				break;
			case UNKNOWN: break; // si el lugar es desconocido no pasará nada
		}
	}
	
    public void pasteLobbySchematic(String string) {
    	
    	if(Skywars.getInstance().getServer().getPluginManager().isPluginEnabled("WorldEdit")){
    		
    		WorldEditPlugin we = (WorldEditPlugin)Bukkit.getPluginManager().getPlugin("WorldEdit");
            File schematic = new File(Skywars.getInstance().getDataFolder(), "/data/schems/" + string);
            
            if(schematic.exists()) {
            	
                Bukkit.getConsoleSender().sendMessage(TextUtil.format("&a---------------------------------"));
                Bukkit.getConsoleSender().sendMessage("La schematic existe en " + string );
                Bukkit.getConsoleSender().sendMessage("La schematic se pegara en " + lobbyschematic.getWorld().getName());
                Bukkit.getConsoleSender().sendMessage(TextUtil.format("&a---------------------------------"));
                
				EditSession session = we.getWorldEdit().getEditSessionFactory().getEditSession((LocalWorld)new BukkitWorld(lobbyschematic.getWorld()), 10000000);
                try {
					MCEditSchematicFormat.getFormat(schematic).load(schematic).paste(session,
																					 new Vector(lobbyschematic.getX(),
																							    lobbyschematic.getY(),
																							    lobbyschematic.getZ()),
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
    
    public void removeSchematic(){
    	if(isPasted()){
    		if(getSession() != null){
    			
    			getSession().undo(getSession());
    			
    			Block base = lobbyschematic.getBlock();
    			Block plate = lobbyschematic.getBlock().getRelative(BlockFace.UP);
    			
    			base.setType(Material.AIR); plate.setType(Material.AIR);
    			
    		}
    	}
    }

	public boolean isPasted() {
		return pasted;
	}

	public void setPasted(boolean pasted) {
		this.pasted = pasted;
	}

	public EditSession getSession() {
		return savedsession;
	}

	public Location getLocation() {
		return lobbyschematic;
	}
	
	public void setSession(EditSession savedsession) {
		this.savedsession = savedsession;
	}
    
}
