package net.omniblock.skywars.games.solo.chest.item.object;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

public class AddItem {

	public static int numberofitemN;
	public static int numberofitemT;
	private static Random random = new Random();
	public static List<ItemStack> itemN = new ArrayList<ItemStack>();
	public static List<ItemStack> itemT = new  ArrayList<ItemStack>();
	
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
	public AddItem(){}
}
