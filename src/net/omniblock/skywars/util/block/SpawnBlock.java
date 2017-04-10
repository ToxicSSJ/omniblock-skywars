package net.omniblock.skywars.util.block;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.util.Vector;

import net.omniblock.skywars.util.NumberUtil;

public class SpawnBlock {

	//GENERA BLOQUES RANDOM
	public static List<Block> blockGeneratorInList(Block block, int xn, int yn, int zn, int rx, int ry, int rz, int numberofblock){
		List<Block> locblock = new ArrayList<Block>();
	
		for(int t = 0; t < numberofblock; t++){
			int x = NumberUtil.getRandomInt(rx, xn);
			int y = NumberUtil.getRandomInt(ry, yn);
			int z = NumberUtil.getRandomInt(rz, zn);

			String listKey = x + "," + y + "," + z;
			
			block.getRelative(x, y, z).setType(Material.PACKED_ICE);
			locblock.add(block.getRelative(x, y, z));
				
		}
		
		return locblock;
	}
	
	
	public static void blockGenerator(Block block,int xn, int yn, int zn, int rx, int ry, int rz, int numberofblock){   
		List<Block> locblock = new ArrayList<Block>();
	
		for(int t = 0; t < numberofblock; t++){
			int x = NumberUtil.getRandomInt(rx, xn);
			int y = NumberUtil.getRandomInt(ry, yn);
			int z = NumberUtil.getRandomInt(rz, zn);
			String listKey = x + "," + y + "," + z;
			
			block.getRelative(x, y, z).setType(Material.PACKED_ICE);
			
		}
	}
	
	//HACE UN CIRCULO DE BLOQUES
	public static List<Block> circle (Location loc, Integer r, Integer h, Boolean hollow, Boolean sphere, int plus_y) {
        List<Block> circleblocks = new ArrayList<Block>();
        int cx = loc.getBlockX();
        int cy = loc.getBlockY();
        int cz = loc.getBlockZ();
        for (int x = cx - r; x <= cx +r; x++)
            for (int z = cz - r; z <= cz +r; z++)
                for (int y = (sphere ? cy - r : cy); y < (sphere ? cy + r : cy + h); y++) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (dist < r*r && !(hollow && dist < (r-1)*(r-1))) {
                        Location l = new Location(loc.getWorld(), x, y + plus_y, z);
                        circleblocks.add(l.getBlock());
                        }
                    }
     
        return circleblocks;
    }	
	
	//TE CREA COLUMNAS 
	public static void columns(Block block, int xn,  int yn, int zn, int rx, int ry, int rz,  int numberofcolumns, int height, boolean Columns){
		List<String> numberplace = new ArrayList<String>();
	
		for(int t = 0; t < numberofcolumns; t++){
	
			int x = NumberUtil.getRandomInt(rx, xn);
			int y = block.getY();
			int y2 = NumberUtil.getRandomInt(ry, yn);
			int z = NumberUtil.getRandomInt(rz, zn);
			String listKey = x + "," + y + "," + z;
			
			if(Columns){
				if(numberplace.contains(listKey)){
	                continue;
	 
	            }else{
	                
	            	numberplace.add(listKey);
	            	for(int h = 0; h + y + y2 < height + y; h++){
	            		block.getRelative(x, h, z).setType(Material.PACKED_ICE);
	            	}
				}
			}else {
				block.getRelative(x, y, z).setType(Material.PACKED_ICE);
			}
		}
	}
	
	
	public static void columnsInList(Block block, int xn,  int zn, int yn, int rx, int ry, int rz,  int numberofcolumns, int height, boolean Columns){
		List<String> numberplace = new ArrayList<String>();
	
		for(int t = 0; t < numberofcolumns; t++){
	
			int x = NumberUtil.getRandomInt(rx, xn);
			int y = block.getY();
			int y2 = NumberUtil.getRandomInt(ry, yn);
			int z = NumberUtil.getRandomInt(rz, zn);
			String listKey = x + "," + y + "," + z;
			
			if(Columns){
				if(numberplace.contains(listKey)){
	                continue;
	 
	            }else{
	                
	            	numberplace.add(listKey);
	            	for(int h = 0; h + y + y2 < height + y; h++){
	            		block.getRelative(x, h, z).setType(Material.PACKED_ICE);
	            	}
				}
			}else {
				block.getRelative(x, y, z).setType(Material.PACKED_ICE);
			}
		}
	}
	
	
	//SPAWN FALLINGBLOCK
	public static void bounceBlock(Block b, float y_speed) {
        if(b == null) return;
       
        FallingBlock fb = b.getWorld().spawnFallingBlock(b.getLocation(), b.getType(), b.getData());
        fb.setDropItem(false);
        b.setType(Material.AIR);
       
        float x = (float) -0.2 + (float) (Math.random() * ((0.2 - -0.2) + 0.2));
        float y = y_speed;
        float z = (float) -0.2 + (float)(Math.random() * ((0.2 - -0.2) + 0.2));
       
        fb.setVelocity(new Vector(x, y, z));
    }

}
