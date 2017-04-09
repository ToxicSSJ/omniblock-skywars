package net.omniblock.skywars.games.solo.chest.item.z.listeners.object;

import org.bukkit.entity.Player;

public class PlayerSavedData {

	public Player player;
	
	public int food = 0;
	public int level = 0;
	public float exp = 0;
	
	public double maxhealth = 0.0;
	public double health = 0.0;
	
	public PlayerSavedData(Player player) {
		
		this.player = player;
		
		this.exp = player.getExp();
		this.level = player.getLevel();
		this.food = player.getFoodLevel();
		this.maxhealth = player.getMaxHealth();
		this.health = player.getHealth();
		
	}
	
	public void apply() {
		
		if(player.isOnline()) {
			
			player.setFoodLevel(food);
			player.setExp(exp);
			
			player.setMaxHealth(maxhealth);
			player.setHealth(health);
			
			player.setLevel(level);
			
		}
		
	}
	
}
