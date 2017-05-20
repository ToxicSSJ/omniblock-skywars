package net.omniblock.skywars.patch.managers.chest.item.object;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.patch.managers.MapManager;
import net.omniblock.skywars.patch.managers.chest.ChestManager;
import net.omniblock.skywars.util.ItemBuilder;
import net.omniblock.skywars.util.Scan;

public class FillChest {
	
	private Chest oneChest = null;
	
	public static List<Block> ChestDiamond = new ArrayList<Block>();
	
	private List<ItemStack> getItem = new ArrayList<ItemStack>();
	private List<ItemStack> getItemChest = new ArrayList<ItemStack>();
	private List<ItemStack> getItemTrappedChest = new ArrayList<ItemStack>();
	
	private final int NUMBER_OF_ITEM = 13;
	private final int NUMBER_OF_ITEM_CHEST = 8;
	private final int NUMBER_OF_ITEM_TRAPPEDCHEST = 10;
	
	public FillChest(List<ItemStack> itemN, List<ItemStack> itemT) {
		
		this.getItemChest = itemN;
		this.getItemTrappedChest = itemT;
	}
	
	public FillChest(List<ItemStack> item, Block block){
		
		this.getItem = item;
		this.oneChest = (Chest) block.getState();
		
	}
	
	public FillChest startFilled(FilledType filled){
		
		final List<Chest> CHEST_NORMAL = getChest(ChestManager.normalchest);
		final List<Chest> CHEST_TRAPPED = getChest(ChestManager.trappedchest);
		
		@SuppressWarnings("serial")
		final List<ItemStack> SUPPORT_ITEM_BLOCKS = new ArrayList<ItemStack>(){{
			
			add(new ItemBuilder(Material.STONE).amount(40).build());
			add(new ItemBuilder(Material.SANDSTONE).amount(60).build());
			add(new ItemBuilder(Material.COBBLESTONE).amount(50).build());
			add(new ItemBuilder(Material.WOOD).amount(48).build());
			add(new ItemBuilder(Material.IRON_PICKAXE).amount(1).build());
			add(new ItemBuilder(Material.IRON_AXE).amount(1).build());
			add(new ItemBuilder(Material.GOLDEN_APPLE).amount(3).build());
			add(new ItemBuilder(Material.WATER_BUCKET).amount(1).build());
			add(new ItemBuilder(Material.WEB).amount(23).build());
			add(new ItemBuilder(Material.LAVA_BUCKET).amount(1).build());
			add(new ItemBuilder(Material.ENDER_PEARL).amount(2).build());
					
		}};

		@SuppressWarnings("serial")
		final List<ItemStack> SUPPORT_ITEM_FOOD = new ArrayList<ItemStack>(){{
			
			add(new ItemBuilder(Material.GOLDEN_APPLE).amount(4).build());
			add(new ItemBuilder(Material.ARROW).amount(23).build());
			add(new ItemBuilder(Material.DIAMOND).amount(2).build());
			add(new ItemBuilder(Material.STICK).amount(4).build());
			add(new ItemBuilder(Material.COOKED_BEEF).amount(10).build());
			add(new ItemBuilder(Material.COOKED_CHICKEN).amount(8).build());
			add(new ItemBuilder(Material.COOKIE).amount(14).build());
			add(new ItemBuilder(Material.COOKED_FISH).amount(6).build());
					
		}};			
		
		switch (filled){
		
		case FILLED:
			
			for(Chest GET_NORMAL_CHEST : CHEST_NORMAL){
				
				if(GET_NORMAL_CHEST.getInventory().contains(new ItemBuilder(Material.IRON_INGOT).amount(1).build())){
					
					GET_NORMAL_CHEST.getInventory().clear();
					addItemInChest(GET_NORMAL_CHEST, 4, SUPPORT_ITEM_BLOCKS, ItemType.BLOCK);
				}
				
				if(GET_NORMAL_CHEST.getInventory().contains(new ItemBuilder(Material.GOLD_INGOT).amount(1).build())){
					
					GET_NORMAL_CHEST.getInventory().clear();
					addItemInChest(GET_NORMAL_CHEST, 4, SUPPORT_ITEM_FOOD, ItemType.FOOD);
				}
				
				if(GET_NORMAL_CHEST.getInventory().contains(new ItemBuilder(Material.DIAMOND).amount(1).build())){
					
					GET_NORMAL_CHEST.getInventory().clear();
					addItemInChest(GET_NORMAL_CHEST, NUMBER_OF_ITEM_CHEST, getItemChest, ItemType.WEAPON);
					ChestDiamond.add(GET_NORMAL_CHEST.getBlock());
				}			
			}
			
			for(Chest GET_TRAPPED_CHEST : CHEST_TRAPPED){
				
				Block block = GET_TRAPPED_CHEST.getBlock();
				byte data = block.getData();
				
				block.setType(Material.CHEST);
				block.setData(data);
				
				block.getLocation().getChunk().load(true);
				
				Chest CHEST = (Chest) block.getState();
				
				new BukkitRunnable() {
					@Override
					public void run() {
						addItemInChest(CHEST, NUMBER_OF_ITEM_TRAPPEDCHEST , getItemTrappedChest, ItemType.WEAPON);
					}
				}.runTaskLater(Skywars.getInstance(), 3L);
				
			}
			
			break;
		
		case MORE_ITEM_Z:
			
			for(Chest GET_NORMAL_CHEST : CHEST_NORMAL){
				
				if(GET_NORMAL_CHEST.getInventory().contains(new ItemBuilder(Material.IRON_INGOT).amount(1).build())){
					
					GET_NORMAL_CHEST.getInventory().clear();
					addItemInChest(GET_NORMAL_CHEST, NUMBER_OF_ITEM_CHEST, getItemChest, ItemType.WEAPON);
					addItemInChest(GET_NORMAL_CHEST, 4, SUPPORT_ITEM_BLOCKS, ItemType.BLOCK);
				}
				
				if(GET_NORMAL_CHEST.getInventory().contains(new ItemBuilder(Material.GOLD_INGOT).amount(1).build())){
					
					GET_NORMAL_CHEST.getInventory().clear();
					addItemInChest(GET_NORMAL_CHEST, NUMBER_OF_ITEM_CHEST, getItemChest, ItemType.WEAPON);
					addItemInChest(GET_NORMAL_CHEST, 4, SUPPORT_ITEM_FOOD, ItemType.FOOD);
				}
				
				if(GET_NORMAL_CHEST.getInventory().contains(new ItemBuilder(Material.DIAMOND).amount(1).build())){
					
					GET_NORMAL_CHEST.getInventory().clear();
					addItemInChest(GET_NORMAL_CHEST, NUMBER_OF_ITEM_CHEST, getItemChest, ItemType.WEAPON);
					ChestDiamond.add(GET_NORMAL_CHEST.getBlock());
				}			
			}
			
			for(Chest GET_TRAPPED_CHEST : CHEST_TRAPPED){
				
				Block block = GET_TRAPPED_CHEST.getBlock();
				byte data = block.getData();
				
				block.setType(Material.CHEST);
				block.setData(data);
				
				block.getLocation().getChunk().load(true);
				
				Chest CHEST = (Chest) block.getState();
				
				new BukkitRunnable() {
					@Override
					public void run() {
						addItemInChest(CHEST, NUMBER_OF_ITEM_TRAPPEDCHEST , getItemTrappedChest, ItemType.WEAPON);
					}
				}.runTaskLater(Skywars.getInstance(), 3L);
				
			}
			
			break;
				
		
		case ONE_CHEST:
				addItemInChest(oneChest, NUMBER_OF_ITEM , getItem, ItemType.WEAPON);
			break;
			
		case RE_FILLED:
				
			List<Location> getLocChest = Scan.oneMaterial(MapManager.CURRENT_MAP, Material.CHEST);
			
			for(Location loc : getLocChest){
				
				Chest chest = (Chest) loc.getBlock().getState();
				chest.getInventory().clear();
				addItemInChest(chest, 12, getItemTrappedChest, ItemType.WEAPON);
			}
			
			break;
			
		case CLEAR:
			
			for(Location loc : Scan.oneMaterial(MapManager.CURRENT_MAP, Material.CHEST)){
				
				Chest chest = (Chest) loc.getBlock().getState();
				chest.getInventory().clear();
			
			}
			
			break;
		
		default:
			break;
		
		}
		
		return this;
	}
	
	public void addItemInChest(Chest chest, final int n, List<ItemStack> list, ItemType itemtype){

		Random random = new Random();
		
		List<Integer> LIST_ITEM = new ArrayList<Integer>();
		List<Integer> LIST_SLOT = new ArrayList<Integer>();
		
		Collections.shuffle(list);
		
		int item = 0;
		int slot = 0;
		
		List<String> ID = new ArrayList<String>();
		ID.clear();

		switch (itemtype){
		
		case WEAPON: 
				
			for (int x = 0; x < n; x++) {
				
				item = random.nextInt(list.size());
				slot = random.nextInt(27);
				
				if(!LIST_ITEM.contains(item) && !LIST_SLOT.contains(slot)){

					if(		 list.get(item).getType() == Material.DIAMOND_CHESTPLATE 
							|| list.get(item).getType() == Material.GOLD_CHESTPLATE
							|| list.get(item).getType() == Material.IRON_CHESTPLATE
							|| list.get(item).getType() == Material.LEATHER_CHESTPLATE){
							
						if(!ID.contains("PT")){
							
							ID.add("PT");
							LIST_ITEM.add(item);
							LIST_SLOT.add(slot);
							chest.getInventory().setItem(slot, list.get(item));
						}
						else{
							x--;
						}
					}else if(	list.get(item).getType() == Material.DIAMOND_LEGGINGS
								|| list.get(item).getType() == Material.GOLD_LEGGINGS
								|| list.get(item).getType() == Material.IRON_LEGGINGS
								|| list.get(item).getType() == Material.LEATHER_LEGGINGS){
						
						if(!ID.contains("PA")){
							
							ID.add("PA");
							LIST_ITEM.add(item);
							LIST_SLOT.add(slot);
							chest.getInventory().setItem(slot, list.get(item));
						
						}else{
							x--;
						}
					}else if(	list.get(item).getType() == Material.DIAMOND_HELMET
								|| list.get(item).getType() == Material.GOLD_HELMET
								|| list.get(item).getType() == Material.IRON_HELMET
								|| list.get(item).getType() == Material.LEATHER_HELMET){
						
						if(!ID.contains("CS")){
							
							ID.add("CS");
							LIST_ITEM.add(item);
							LIST_SLOT.add(slot);
							chest.getInventory().setItem(slot, list.get(item));
						}else{
							x--;
						}
					}else if(	list.get(item).getType() == Material.DIAMOND_BOOTS
								|| list.get(item).getType() == Material.GOLD_BOOTS
								|| list.get(item).getType() == Material.IRON_BOOTS
								|| list.get(item).getType() == Material.LEATHER_BOOTS){
							
						if(!ID.contains("BO")){
							
							ID.add("BO");
							LIST_ITEM.add(item);
							LIST_SLOT.add(slot);
							chest.getInventory().setItem(slot, list.get(item));
							
						}else{
							x--;
						}
					}else if(list.get(item).getType() == Material.DIAMOND_SWORD
							|| list.get(item).getType() == Material.GOLD_SWORD
							|| list.get(item).getType() == Material.IRON_SWORD
							|| list.get(item).getType() == Material.STONE_SWORD
							|| list.get(item).getType() == Material.WOOD_SWORD
							|| list.get(item).getType() == Material.DIAMOND_AXE
							|| list.get(item).getType() == Material.GOLD_AXE
							|| list.get(item).getType() == Material.IRON_AXE
							|| list.get(item).getType() == Material.STONE_AXE
							|| list.get(item).getType() == Material.WOOD_AXE){
						
						if(!ID.contains("SW")){
							
							ID.add("SW");
							LIST_ITEM.add(item);
							LIST_SLOT.add(slot);
							chest.getInventory().setItem(slot, list.get(item));
							
						}else{
							
							x--;
						}
					}else if(list.get(item).getType() == Material.BOW){
						
						if(!ID.contains("BOW")){
							ID.add("BOW");
							
							LIST_ITEM.add(item);
							LIST_SLOT.add(slot);
							chest.getInventory().setItem(slot, list.get(item));
						}else{
							
							x--;
						}
					}else{
						
						LIST_ITEM.add(item);
						LIST_SLOT.add(slot);
						chest.getInventory().setItem(slot, list.get(item));
					}
									
				} else {
					
					x--;
					
				}
			}
			
			break;
		
		case BLOCK:
			
			int b = 0;
			
			for(int x = 0; x < n; x++){
					
				item = random.nextInt(list.size());
				slot = random.nextInt(27);
				
				
				if(!LIST_ITEM.contains(item) && !LIST_SLOT.contains(slot)){
					
					if(!(chest.getInventory().contains(Material.STONE) 
							|| chest.getInventory().contains(Material.SANDSTONE))
							|| chest.getInventory().contains(Material.COBBLESTONE)
							|| chest.getInventory().contains(Material.WOOD)){
						
						if(		list.get(item).getType() == Material.STONE
								|| list.get(item).getType() == Material.SANDSTONE
								|| list.get(item).getType() == Material.COBBLESTONE
								|| list.get(item).getType() == Material.WOOD){
								
								LIST_ITEM.add(item);
								LIST_SLOT.add(slot);
								chest.getInventory().setItem(slot, list.get(item));
							
							
						}else{
							
							x--;
						
						}
						
					}else{
						
						LIST_ITEM.add(item);
						LIST_SLOT.add(slot);
						chest.getInventory().setItem(slot, list.get(item));
						
					}
				
				}else{
					
					x--;
				}
			}
				
			
			break;
			
		case FOOD:
			
			for(int x = 0; x < n; x++){
				
				item = random.nextInt(list.size());
				slot = random.nextInt(27);
				
				if(!LIST_ITEM.contains(item) && !LIST_SLOT.contains(slot)){
					
					if(!(chest.getInventory().contains(Material.COOKED_FISH) 
							|| chest.getInventory().contains(Material.COOKED_BEEF))
							|| chest.getInventory().contains(Material.COOKED_CHICKEN)){
						
						if(		list.get(item).getType() == Material.COOKED_FISH
								|| list.get(item).getType() == Material.COOKED_BEEF
								|| list.get(item).getType() == Material.COOKED_CHICKEN){
							
							LIST_ITEM.add(item);
							LIST_SLOT.add(slot);
							chest.getInventory().setItem(slot, list.get(item));
							
						}else{
							
							x--;
							
						}
						
					}else{
						
						LIST_ITEM.add(item);
						LIST_SLOT.add(slot);
						chest.getInventory().setItem(slot, list.get(item));
						
					}
				}else{
					
					x--;
				}
			}
			
			break;
		}
		
		chest.update(true);
		
	}
	
	public List<Chest> getChest(List<Location> list) {
		
		List<Chest> ChestBlock = new ArrayList<Chest>();
		
		for (Location loc : list) {

			Block block = loc.getBlock();
			
			if(		   block.getType() == Material.CHEST 
					|| block.getType() == Material.TRAPPED_CHEST 
					|| block.getType() == Material.ENDER_CHEST){
				
				Chest chest = (Chest) block.getState();
				ChestBlock.add(chest);
				
			}
		}
		
		return ChestBlock;
	}
	
	public enum FilledType{
		
		CLEAR,
		FILLED,
		RE_FILLED,
		ONE_CHEST,
		MORE_ITEM_Z;

	}
	
	public enum ItemType{
		
		BLOCK,
		FOOD,
		WEAPON;
		
	}
}
