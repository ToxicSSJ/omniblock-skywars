package net.omniblock.skywars.games.solo.chest.item.object;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import net.omniblock.skywars.games.solo.chest.ChestManager;
import net.omniblock.skywars.util.ItemBuilder;
import net.omniblock.skywars.util.NumberUtil;
import net.omniblock.skywars.util.Scan;

public class AddChest {
	private AddItem item = new AddItem();
	private ItemStack[] arrayofitemN;
	private ItemStack[] arrayofitemT;
	private int numberofitemN;
	private int numberofitemT;
	private int CHEST_TYPE; 
	
	public static List<ItemStack> itemN = new ArrayList<ItemStack>();
	public static List<ItemStack> itemT = new  ArrayList<ItemStack>();
	public static List<Block> ChestDiamond = new ArrayList<Block>();  
	
	public AddChest(ItemStack[] arrayofitemN, ItemStack[] arrayofitemT,  final int N, final int T) {
		
		this.arrayofitemN = arrayofitemN;
		this.arrayofitemT = arrayofitemT;
		this.numberofitemN = N;
		this.numberofitemT = T;
		addItemToArray(this.itemN, this.arrayofitemN);
		addItemToArray(this.itemT, this.arrayofitemT);
	}
	
	@SuppressWarnings({ "static-access" })
	public void normalChest() {
		
		for (int i = 0; i < ChestManager.normalchest.size(); i++) {
			Location chestlocation = ChestManager.normalchest.get(i);
			Chest chest = (Chest) chestlocation.getBlock().getState();
			CHEST_TYPE = NumberUtil.getRandomInt(1, 3);
			
			if(chest.getInventory().contains(new ItemBuilder(Material.IRON_INGOT).amount(1).build())
				||chest.getInventory().contains(new ItemBuilder(Material.GOLD_INGOT).amount(1).build()) 
				|| chest.getInventory().contains(new ItemBuilder(Material.DIAMOND).amount(1).build())){
				
				containDiamond(chest);
				chest.getInventory().clear();
				item.add(chest, this.numberofitemN, this.itemN);
				
				switch(CHEST_TYPE){
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				
				
				}
			}
		}
	}
	
	@SuppressWarnings({ "deprecation", "static-access", "unused" })
	public void trappedChest() {
		ArrayList<Location> save_chest_location = new ArrayList<Location>();
		for (int i = 0; i < ChestManager.trappedchest.size(); i++) {
			
			Location chestlocation = ChestManager.trappedchest.get(i);
			Block direction_block = chestlocation.getBlock();
			Chest chest = (Chest) chestlocation.getBlock().getState();
			byte data = direction_block.getData();
			direction_block.setType(Material.CHEST);
			direction_block.setData(data);
			save_chest_location.add(chestlocation);
		}
		
		for(int j = 0; j < save_chest_location.size(); j++){
			
			Location chestlocation2 = save_chest_location.get(j);
			Chest chest = (Chest) chestlocation2.getBlock().getState();
			item.add(chest, this.numberofitemT, this.itemT);
		}
	}
	
	public static ReFillChest makeReFill(ItemStack[] array, final int N){
		return new ReFillChest(array, N);
	}

	public static void addKeyForFillChest(String string, ItemStack[] array, final int n){
		Map<String, ReFillChest> fillChest = new HashMap<String, ReFillChest>();
		fillChest.put(string, new ReFillChest(array, n));
	}
	
	
	public ItemStack[] getArrayofitemN() {
		return arrayofitemN;
	}

	public void setArrayofitemN(ItemStack[] arrayofitemN) {
		this.arrayofitemN = arrayofitemN;
	}

	public ItemStack[] getArrayofitemT() {
		return arrayofitemT;
	}
	

	public void setArrayofitemT(ItemStack[] arrayofitemT) {
		this.arrayofitemT = arrayofitemT;
	}

	public int getNumberofitemN() {
		return numberofitemN;
	}

	public void setNumberofitemN(int numberofitemN) {
		this.numberofitemN = numberofitemN;
	}

	public int getNumberofitemT() {
		return numberofitemT;
	}

	public void setNumberofitemT(int numberofitemT) {
		this.numberofitemT = numberofitemT;
	}

	public static List<ItemStack> getItemN() {
		return itemN;
	}

	public static void setItemN(List<ItemStack> itemN) {
		AddChest.itemN = itemN;
	}

	public static List<ItemStack> getItemT() {
		return itemT;
	}
	public static void setItemT(List<ItemStack> itemT) {
		AddChest.itemT = itemT;
	}

	
	/*
	 * 
	 * - AddItem - Añade item a los cofres
	 * 
	 */
	
	public static class AddItem {
		private static Random random = new Random();
		
		public static void add(Chest chest, final int n, List<ItemStack> list) {
			
			List<Integer> LIST_ITEM = new ArrayList<Integer>();
			List<Integer> LIST_SLOT = new ArrayList<Integer>();
			LIST_ITEM.clear();
			LIST_SLOT.clear();
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
		}
	}
	
	/*
	 * 
	 * - ReFillChest - Rellena nuevamente los cofres
	 * 
	 */
	
	public static class ReFillChest{
		private AddItem additem = new AddItem();
		private ItemStack[] arrayofitem;
		private int numberofitem;
		
		public List<Location> chestLoc = Scan.oneMaterial(Material.CHEST);
		private List<ItemStack> item = new ArrayList<ItemStack>();
		
		public ReFillChest(ItemStack[] array, final int NUMBER){
			
			this.arrayofitem = array;
			this.numberofitem = NUMBER;
			addItemToArray(item, this.arrayofitem);
			fillChest();
		}
		
		public void fillChest(){
		
			for(int i = 0; i < chestLoc.size(); i++){
				
				Location chestlocation = chestLoc.get(i);
				Chest chest = (Chest) chestlocation.getBlock();
				chest.getInventory().clear();
				additem.add(chest, this.numberofitem, this.item);
				
			}
		}
		
		public ItemStack[] getArrayofitem() {
			return arrayofitem;
		}

		public void setArrayofitem(ItemStack[] arrayofitem) {
			this.arrayofitem = arrayofitem;
		}

		public int getNumberofitem() {
			return numberofitem;
		}

		public void setNumberofitem(int numberofitem) {
			this.numberofitem = numberofitem;
		}

		public List<ItemStack> getItem() {
			return item;
		}

		public void setItem(List<ItemStack> item) {
			this.item = item;
		}
		
	}
	
	public void containDiamond(Chest chest){
		if(chest.getInventory().contains(new ItemBuilder(Material.DIAMOND).amount(1).build())){
			Block block = chest.getBlock();
			ChestDiamond.add(block);
		}else{
			return;
		}
	}
	
	public static void addItemToArray(List<ItemStack> list, ItemStack[] array) {
		for (int i = 0; i < array.length; i++) {
			list.add(array[i]);
		}
	}
}
