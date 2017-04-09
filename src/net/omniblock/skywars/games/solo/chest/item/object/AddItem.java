package net.omniblock.skywars.games.solo.chest.item.object;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import net.omniblock.skywars.util.ItemBuilder;

public class AddItem {

	public static int numberofitemN = 0;
	public static int numberofitemT = 0;
	
	private Random random = new Random();

	private Chest chest;
	private Material material;
	private List<ItemStack> list;

	private boolean repeat_item = false;
	
	public AddItem() {

	}

	public Chest getChest() {
		return chest;
	}

	public void setChest(Chest chest) {
		this.chest = chest;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public List<ItemStack> getList() {
		return list;
	}

	public void setList(List<ItemStack> list) {
		this.list = list;
	}

	public int getNumberofitemN() {
		return numberofitemN;
	}

	public int getNumberofitemT() {
		return numberofitemT;
	}
	
	public boolean isRepeat_item() {
		return repeat_item;
	}

	public void setRepeat_item(boolean repeat_item) {
		this.repeat_item = repeat_item;
	}

	
	public void addItemN() {
		
		List<Integer> listposition = new ArrayList<Integer>();
		listposition.clear();
		for (int x = 0; x < numberofitemN; x++) {
	
			int item = random.nextInt(list.size());
			
			if(!this.repeat_item){
				if(listposition.contains(item)){
					continue;
				
				}else{
					
					listposition.add(item);
					chest.getInventory().setItem(randomSlot(), list.get(item));
				}
				
			}else{
				
				chest.getInventory().setItem(randomSlot(), list.get(random.nextInt(list.size())));
			}	
		}
	}

	public void addItemT() {
		
		List<Integer> listposition = new ArrayList<Integer>();
		listposition.clear();
		for (int x = 0; x < numberofitemT; x++) {
	
			int item = random.nextInt(list.size());
			
			if(!this.repeat_item){
				if(listposition.contains(item)){
					continue;
				
				}else{
					
					listposition.add(item);
					chest.getInventory().setItem(randomSlot(), list.get(item));
				}
				
			}else{
				
				chest.getInventory().setItem(randomSlot(), list.get(random.nextInt(list.size())));
			}	
		}
	}

	public void addItem(Material m, int amount) {
		chest.getInventory().setItem(randomSlot(), new ItemBuilder(m).amount(amount).build());
	}

	public int randomSlot() {
		int r = random.nextInt(27);
		return r;
	}
}
