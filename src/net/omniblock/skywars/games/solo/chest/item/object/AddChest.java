package net.omniblock.skywars.games.solo.chest.item.object;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import net.omniblock.skywars.games.solo.chest.ChestManager;
import net.omniblock.skywars.util.ItemBuilder;
import net.omniblock.skywars.util.NumberUtil;
public class AddChest {
	
	private static AddItem additem;
	private ItemStack[] arrayofitemN;
	private ItemStack[] arrayofitemT;
	private int CHEST_TYPE; // int 1 = IRON; 2 = GOLD; 3 = DIAMOND.
	public static List<Block> ChestDiamond = new ArrayList<Block>();  //LISTA DE LOS COFRES QUE TIENEN ITEM DE DIAMANTE
	public AddChest(ItemStack[] arrayofitemN, ItemStack[] arrayofitemT,  final int N, final int T) {
		
		this.arrayofitemN = arrayofitemN;
		this.arrayofitemT = arrayofitemT;
		AddItem.numberofitemN = N;
		AddItem.numberofitemT = T;
		addItemToArray(AddItem.itemN, this.arrayofitemN);
		addItemToArray(AddItem.itemT, this.arrayofitemT);
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
				additem.add(chest, additem.numberofitemN, additem.itemN);
				
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
			additem.add(chest, additem.numberofitemT, additem.itemT);
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
