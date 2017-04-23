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
import net.omniblock.skywars.patch.managers.chest.ChestManager;
import net.omniblock.skywars.util.ItemBuilder;

public class FillChest {
	
	public List<Block> ChestDiamond = new ArrayList<Block>();
	
	private ItemStack[] arrayofitemN;
	private ItemStack[] arrayofitemT;
	
	private int numberofitemN;
	private int numberofitemT;
	
	public FillChest(ItemStack[] arrayofitemN, ItemStack[] arrayofitemT,  final int N, final int T) {
		
		this.arrayofitemN = arrayofitemN;
		this.arrayofitemT = arrayofitemT;
		this.numberofitemN = N;
		this.numberofitemT = T;
		
	}

	
	public void fillChest(){
		
		final List<Chest> NORMAL = getChest(ChestManager.normalchest);
		final List<Chest> TRAPPED = getChest(ChestManager.trappedchest);
		
		List<ItemStack> ITEMS_NORMAL = new ArrayList<ItemStack>();
		List<ItemStack> ITEMS_TRAPPED = new  ArrayList<ItemStack>();
		addItemToArray(ITEMS_NORMAL, arrayofitemN);
		addItemToArray(ITEMS_TRAPPED, arrayofitemT);
		
		startFilled(NORMAL, TRAPPED, ITEMS_NORMAL, ITEMS_TRAPPED);
			
	}
	
	
	@SuppressWarnings("deprecation")
	public void startFilled(final List<Chest> CHEST_NORMAL, final List<Chest> CHEST_TRAPPED, final List<ItemStack> ITEM_NORMAL, final List<ItemStack> ITEM_TRAPPED) {
		
		@SuppressWarnings("serial")
		final List<ItemStack> SUPPORT_ITEM_BLOCKS = new ArrayList<ItemStack>(){{
			
			add(new ItemBuilder(Material.STONE).amount(40).build());
			add(new ItemBuilder(Material.SANDSTONE).amount(60).build());
			add(new ItemBuilder(Material.COBBLESTONE).amount(35).build());
			add(new ItemBuilder(Material.GOLDEN_APPLE).amount(3).build());
			add(new ItemBuilder(Material.WATER_BUCKET).amount(1).build());
			add(new ItemBuilder(Material.WEB).amount(23).build());
			add(new ItemBuilder(Material.LAVA_BUCKET).amount(1).build());
					
		}};

		@SuppressWarnings("serial")
		final List<ItemStack> SUPPORT_ITEM_FOOD = new ArrayList<ItemStack>(){{
			
			add(new ItemBuilder(Material.GOLDEN_APPLE).amount(4).build());
			add(new ItemBuilder(Material.COOKED_BEEF).amount(20).build());
			add(new ItemBuilder(Material.COOKED_CHICKEN).amount(30).build());
			add(new ItemBuilder(Material.COOKIE).amount(60).build());
			add(new ItemBuilder(Material.COOKED_FISH).amount(23).build());
					
		}};
		

		@SuppressWarnings("serial")
		final List<ItemStack> SUPPORT_ITEM_WEAPONS = new ArrayList<ItemStack>(){{
			
			add(new ItemBuilder(Material.DIAMOND_SWORD).amount(1).build());
			add(new ItemBuilder(Material.IRON_SWORD).amount(1).build());
			add(new ItemBuilder(Material.FISHING_ROD).amount(1).build());
			add(new ItemBuilder(Material.BOW).amount(1).build());
			add(new ItemBuilder(Material.ARROW).amount(35).build());
							
		}};
					
		
		for(Chest GET_NORMAL_CHEST : CHEST_NORMAL){
			
			if(GET_NORMAL_CHEST.getInventory().contains(new ItemBuilder(Material.IRON_INGOT).amount(1).build())){
				
				GET_NORMAL_CHEST.getInventory().clear();
				addItemInChest(GET_NORMAL_CHEST, numberofitemN, ITEM_NORMAL);
				addItemInChest(GET_NORMAL_CHEST, 5, SUPPORT_ITEM_BLOCKS);
			
			}
			
			if(GET_NORMAL_CHEST.getInventory().contains(new ItemBuilder(Material.GOLD_INGOT).amount(1).build())){
				
				GET_NORMAL_CHEST.getInventory().clear();
				addItemInChest(GET_NORMAL_CHEST, numberofitemN, ITEM_NORMAL);
				addItemInChest(GET_NORMAL_CHEST, 5, SUPPORT_ITEM_FOOD);
			
			}
			
			if(GET_NORMAL_CHEST.getInventory().contains(new ItemBuilder(Material.DIAMOND).amount(1).build())){
				
				GET_NORMAL_CHEST.getInventory().clear();
				addItemInChest(GET_NORMAL_CHEST, numberofitemN, ITEM_NORMAL);
				addItemInChest(GET_NORMAL_CHEST, 5, SUPPORT_ITEM_WEAPONS);
				containDiamond(GET_NORMAL_CHEST);
			
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
					addItemInChest(CHEST, numberofitemT, ITEM_TRAPPED);
				}
			}.runTaskLater(Skywars.getInstance(), 3L);
			
		}
	}
	
	public void addItemInChest(Chest chest, final int n, List<ItemStack> list){

		Random random = new Random();
		
		List<Integer> LIST_ITEM = new ArrayList<Integer>();
		List<Integer> LIST_SLOT = new ArrayList<Integer>();
		
		Collections.shuffle(list);
		
		int item = 0;
		int slot = 0;
			
		for (int x = 0; x < n; x++) {
		
			item = random.nextInt(list.size());
			slot = random.nextInt(27);
			
			if(!LIST_ITEM.contains(item) && !LIST_SLOT.contains(slot)){
				
				LIST_ITEM.add(item);
				LIST_SLOT.add(slot);
				
				chest.getInventory().setItem(slot, list.get(item));
				
			} else {
				
				continue;
				
			}
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
	
	public void containDiamond(Chest chest){
		
		if(chest.getInventory().contains(new ItemBuilder(Material.DIAMOND).amount(1).build())){
			
			Block block = chest.getBlock();
			ChestDiamond.add(block);
			
		}
		
	}
	
	public void addItemToArray(List<ItemStack> list, ItemStack[] array) {
		for (int i = 0; i < array.length; i++) {
			list.add(array[i]);
		}
	}
}
