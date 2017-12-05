package net.omniblock.skywars.patch.managers.chest.defaults.events.stuff;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class PlayerSavedData {

	public Player player;

	public LinkedList<ItemStack> inventoryItems = new LinkedList<ItemStack>();
	public Map<EquipmentSlot, ItemStack> equipmentItems = new HashMap<EquipmentSlot, ItemStack>();
	
	public int food = 0;
	public int level = 0;
	public float exp = 0;

	public double maxhealth = 0.0;
	public double health = 0.0;

	@SuppressWarnings("deprecation")
	public PlayerSavedData(Player player) {
		
		this.player = player;

		this.exp = player.getExp();
		this.level = player.getLevel();
		this.food = player.getFoodLevel();
		this.maxhealth = player.getMaxHealth();
		this.health = player.getHealth();
		
		for(ItemStack item : player.getInventory().getContents()) {
			
			inventoryItems.add(item);
			continue;
			
		}
		
		this.equipmentItems.put(EquipmentSlot.HEAD, player.getInventory().getHelmet());
		this.equipmentItems.put(EquipmentSlot.CHEST, player.getInventory().getChestplate());
		this.equipmentItems.put(EquipmentSlot.LEGS, player.getInventory().getLeggings());
		this.equipmentItems.put(EquipmentSlot.FEET, player.getInventory().getBoots());
		
	}

	@SuppressWarnings("deprecation")
	public void apply() {
		
		if (player.isOnline()) {
			
			player.setFoodLevel(food);
			player.setExp(exp);

			player.setMaxHealth(maxhealth);
			player.setHealth(health);

			player.setLevel(level);

			player.getInventory().setHelmet(this.equipmentItems.get(EquipmentSlot.HEAD));
			player.getInventory().setHelmet(this.equipmentItems.get(EquipmentSlot.CHEST));
			player.getInventory().setHelmet(this.equipmentItems.get(EquipmentSlot.LEGS));
			player.getInventory().setHelmet(this.equipmentItems.get(EquipmentSlot.FEET));
			
			int x = 0;
			
			for(ItemStack item : inventoryItems) {
				
				player.getInventory().setItem(x, item);
				x++;
				
			}
			
			return;
			
		}
		
	}

}
