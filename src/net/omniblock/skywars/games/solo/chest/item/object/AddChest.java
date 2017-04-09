package net.omniblock.skywars.games.solo.chest.item.object;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import net.omniblock.skywars.games.solo.chest.ChestManager;
import net.omniblock.skywars.games.solo.chest.ChestType;
import net.omniblock.skywars.util.ItemBuilder;

public class AddChest implements ChestType {

	private static Chest chest;
	private static AddItem Options = new AddItem();
	
	public static List<Block> ChestDiamond = new ArrayList<Block>();  //LISTA DE LOS COFRES QUE TIENEN ITEM DE DIAMANTE
	
	private static List<ItemStack> listItemN = new ArrayList<ItemStack>();
	private static List<ItemStack> listItemT = new ArrayList<ItemStack>();

	private boolean clearchest = false;
	private ItemStack[] arrayofitemN;
	private ItemStack[] arrayofitemT;


	@SuppressWarnings("static-access")
	public AddChest(ItemStack[] arrayofitemN, ItemStack[] arrayofitemT, boolean repeat) {

		this.arrayofitemN = arrayofitemN;
		this.arrayofitemT = arrayofitemT;
		Options.setRepeat_item(repeat);

	}

	@Override
	public void normalChest() {

		AddItem item = new AddItem();
		addItemToArray(listItemN, arrayofitemN);

		for (int i = 0; i < ChestManager.normalchest.size(); i++) {
			Location chestlocation = ChestManager.normalchest.get(i);
			chest = (Chest) chestlocation.getBlock().getState();

			item.setList(listItemN);
			item.setChest(chest);

			if (chest.getInventory().contains(new ItemBuilder(Material.IRON_INGOT).amount(1).build())) {
				chest.getInventory().clear();
				item.addItemN();
				ifContainsWeapon(chest);
			}
			if (chest.getInventory().contains(new ItemBuilder(Material.GOLD_INGOT).amount(1).build())) {
				chest.getInventory().clear();
				item.addItemN();
				ifContainsBlock(chest);
			}
			if (chest.getInventory().contains(new ItemBuilder(Material.DIAMOND).amount(1).build())) {
				Block block = chest.getBlock();
				ChestDiamond.add(block);
				chest.getInventory().clear();
				item.addItemN();
				ifContainsFood(chest);

			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void trappedChest() {

		AddItem item = new AddItem();
		addItemToArray(listItemT, arrayofitemT);
		ArrayList<Location> save_chest_location = new ArrayList<Location>();
		Chest chestT = null;
		
		for (int i = 0; i < ChestManager.trappedchest.size(); i++) {
			
			Location chestlocation = ChestManager.trappedchest.get(i);
			Block direction_block = chestlocation.getBlock();
			chestT = (Chest) chestlocation.getBlock().getState();
			
			/**
			 * cambiar
			 * 		TrappedChest a chest
			 * */
			byte data = direction_block.getData();
			direction_block.setType(Material.CHEST);
			direction_block.setData(data);
			save_chest_location.add(chestlocation);
		
		}
		
		for(int j = 0; j < save_chest_location.size(); j++){
			
			Location chestlocation2 = save_chest_location.get(j);
			chestT = (Chest) chestlocation2.getBlock().getState();
			
			item.setList(listItemT);
			item.setChest(chestT);
			item.addItemT();
			
		}
		
	}

	@Override
	public void numberOfItemInChest(int numberN, int numberT) {

		AddItem.numberofitemN = numberN;
		AddItem.numberofitemT = numberT;

	}

	@Override
	public void clearChest(boolean clear) {

		// TODO Auto-generated method stub

	}

	public static List<ItemStack> getListItemN() {
		return listItemN;
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

	public boolean isClearchest() {
		return clearchest;
	}

	public void setClearchest(boolean clearchest) {
		this.clearchest = clearchest;
	}

	public static void ifContainsWeapon(Chest chest) {

		AddItem item = new AddItem();
		item.setChest(chest);
		item.setList(listItemN);

		if (chest.getInventory().contains(new ItemBuilder(Material.BOW).amount(1).build())) {
			item.addItem(Material.ARROW, 16);
			item.addItem(Material.BOW, 1);

		} else if (chest.getInventory().contains(new ItemBuilder(Material.DIAMOND_SWORD).amount(1).build())) {
			item.addItem(Material.GOLDEN_APPLE, 3);
			item.addItem(Material.DIAMOND_SWORD, 1);

		} else if (chest.getInventory().contains(new ItemBuilder(Material.IRON_SWORD).amount(1).build())) {
			item.addItem(Material.GOLDEN_APPLE, 2);
			item.addItem(Material.IRON_SWORD, 1);

		} else if (chest.getInventory().contains(new ItemBuilder(Material.GOLD_SWORD).amount(1).build())) {
			item.addItem(Material.GOLDEN_APPLE, 2);
			item.addItem(Material.GOLD_SWORD, 1);

		} else if (chest.getInventory().contains(new ItemBuilder(Material.STONE_SWORD).amount(1).build())) {
			item.addItem(Material.GOLDEN_APPLE, 1);
			item.addItem(Material.STONE_SWORD, 1);

		} else if (chest.getInventory().contains(new ItemBuilder(Material.WOOD_SWORD).amount(1).build())) {
			item.addItem(Material.GOLDEN_APPLE, 1);
			item.addItem(Material.WOOD_SWORD, 1);

		} else {

			item.addItemN();
		}
	}

	public static void ifContainsBlock(Chest chest) {
		AddItem block = new AddItem();
		block.setChest(chest);

		block.addItem(Material.COBBLESTONE, 23);
		block.addItem(Material.SANDSTONE, 20);
		block.addItem(Material.STONE, 20);
		block.addItem(Material.WATER_BUCKET, 1);
		block.addItem(Material.WEB, 27);
	}

	public static void ifContainsFood(Chest chest) {
		AddItem food = new AddItem();
		food.setChest(chest);

		food.addItem(Material.COOKED_BEEF, 7);
		food.addItem(Material.APPLE, 4);
		food.addItem(Material.BREAD, 13);
		food.addItem(Material.COOKIE, 28);
	}

	public static void addItemToArray(List<ItemStack> list, ItemStack[] array) {
		for (int i = 0; i < array.length; i++) {
			list.add(array[i]);
		}
	}
}
