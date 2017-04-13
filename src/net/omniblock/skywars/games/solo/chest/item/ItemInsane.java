package net.omniblock.skywars.games.solo.chest.item;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import net.omniblock.skywars.util.ItemBuilder;

public class ItemInsane {
	
	
	public static ItemStack[] normalChest(){
		ItemStack[] item = new ItemStack[]{
				
				/* ARMADURA */ new ItemBuilder(Material.LEATHER_BOOTS).amount(1).build(),
				/* ARMADURA */ new ItemBuilder(Material.IRON_CHESTPLATE).amount(1).build(),
				/* ARMADURA */ new ItemBuilder(Material.IRON_LEGGINGS).amount(1).build(),
				/* ARMADURA */ new ItemBuilder(Material.IRON_BOOTS).amount(1).build(),
				/* ARMADURA */ new ItemBuilder(Material.DIAMOND_HELMET).amount(1).build(),
				/* ARMADURA */ new ItemBuilder(Material.DIAMOND_LEGGINGS).amount(1).build(),
				
				/* ARMA */ new ItemBuilder(Material.DIAMOND_SWORD).amount(1).build(),
				/* ARMA */ new ItemBuilder(Material.GOLD_SWORD).amount(1).build(),
				/* ARMA */ new ItemBuilder(Material.IRON_SWORD).amount(1).build(),
				/* ARMA */ new ItemBuilder(Material.STONE_SWORD).amount(1).build(),
				/* ARMA */ new ItemBuilder(Material.WOOD_SWORD).amount(1).build(),
				/* ARMA */  new ItemBuilder(Material.BOW).amount(1).build(),
				
				/* ARMADURA ENC */ new ItemBuilder(Material.IRON_CHESTPLATE).amount(1).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build(),
				/* ARMADURA ENC */ new ItemBuilder(Material.IRON_CHESTPLATE).amount(1).enchant(Enchantment.PROTECTION_PROJECTILE, 2).build(),
				/* ARMADURA ENC */ new ItemBuilder(Material.DIAMOND_BOOTS).amount(1).enchant(Enchantment.DURABILITY, 1).build(),
				/* ARMADURA ENC */ new ItemBuilder(Material.IRON_LEGGINGS).amount(1).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build(),
				
				
				/* ARMA ENC */ new ItemBuilder(Material.IRON_SWORD).amount(1).enchant(Enchantment.DAMAGE_ALL, 2).build(),
				/* ARMA ENC */ new ItemBuilder(Material.STONE_SWORD).amount(1).enchant(Enchantment.KNOCKBACK, 2).build(),
				/* ARMA ENC */ new ItemBuilder(Material.WOOD_AXE).amount(1).enchant(Enchantment.DAMAGE_ALL, 3).build(),
				/* ARMA ENC */ new ItemBuilder(Material.BOW).amount(1).enchant(Enchantment.ARROW_DAMAGE, 1).build(),
				/* ARMA ENC */ new ItemBuilder(Material.BOW).amount(1).enchant(Enchantment.ARROW_KNOCKBACK, 1).build(),
				

				/* ITEMS */ new ItemBuilder(Material.WATER_BUCKET).amount(1).build(),
				/* ITEMS */ new ItemBuilder(Material.LAVA_BUCKET).amount(1).build(),
				/* ITEMS */ new ItemBuilder(Material.FISHING_ROD).amount(1).build(),
				/* ITEMS */ new ItemBuilder(Material.WEB).amount(4).build(),
				/* ITEMS */ new ItemBuilder(Material.WATER_BUCKET).amount(1).build(),
				/* ITEMS */ new ItemBuilder(Material.SNOW_BALL).amount(20).build(),
				/* ITEMS */ new ItemBuilder(Material.LAPIS_ORE).amount(3).build()
				
		};
		return item;
	} 
	
	public static ItemStack[] trappedChest(){
		ItemStack[] item = new ItemStack[]{
				
				/* ARMADURA */ new ItemBuilder(Material.LEATHER_BOOTS).amount(1).build(),
				/* ARMADURA */ new ItemBuilder(Material.IRON_LEGGINGS).amount(1).build(),
				/* ARMADURA */ new ItemBuilder(Material.IRON_BOOTS).amount(1).build(),
				/* ARMADURA */ new ItemBuilder(Material.DIAMOND_HELMET).amount(1).build(),
				/* ARMADURA */ new ItemBuilder(Material.DIAMOND_BOOTS).amount(1).build(),
			
				/* ARMADURA ENC */ new ItemBuilder(Material.IRON_CHESTPLATE).amount(1).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build(),
				/* ARMADURA ENC */ new ItemBuilder(Material.IRON_CHESTPLATE).amount(1).enchant(Enchantment.THORNS, 2).build(),
				/* ARMADURA ENC */ new ItemBuilder(Material.LEATHER_HELMET).amount(1).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build(),
				/* ARMADURA ENC */ new ItemBuilder(Material.IRON_BOOTS).amount(1).enchant(Enchantment.PROTECTION_FALL, 1).build(),
				/* ARMADURA ENC */ new ItemBuilder(Material.LEATHER_LEGGINGS).amount(1).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build(),
				/* ARMADURA ENC */ new ItemBuilder(Material.IRON_BOOTS).amount(1).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build(),
				/* ARMADURA ENC */ new ItemBuilder(Material.IRON_CHESTPLATE).amount(1).enchant(Enchantment.PROTECTION_EXPLOSIONS, 2).build(),
				/* ARMADURA ENC */ new ItemBuilder(Material.LEATHER_CHESTPLATE).amount(1).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build(),
				/* ARMADURA ENC */ new ItemBuilder(Material.IRON_HELMET).amount(1).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build(),
				/* ARMADURA ENC */ new ItemBuilder(Material.IRON_HELMET).amount(1).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build(),
				/* ARMADURA ENC */ new ItemBuilder(Material.DIAMOND_HELMET).amount(1).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build(),
				/* ARMADURA ENC */ new ItemBuilder(Material.DIAMOND_BOOTS).amount(1).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build(),
				/* ARMADURA ENC */ new ItemBuilder(Material.DIAMOND_CHESTPLATE).amount(1).enchant(Enchantment.PROTECTION_EXPLOSIONS, 2).build(),
				/* ARMADURA ENC */ new ItemBuilder(Material.DIAMOND_CHESTPLATE).amount(1).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).enchant(Enchantment.THORNS, 1).build(),
			
				/* ARMA */	new ItemBuilder(Material.IRON_SWORD).amount(1).build(),
				/* ARMA */  new ItemBuilder(Material.DIAMOND_SWORD).amount(1).build(),
				/* ARMA */ 	new ItemBuilder(Material.BOW).amount(1).build(),
				/* ARMA */	new ItemBuilder(Material.IRON_PICKAXE).amount(1).build(),
			
				/* ARMA ENC */ new ItemBuilder(Material.IRON_PICKAXE).amount(1).enchant(Enchantment.DURABILITY, 1).build(),
				/* ARMA ENC */ new ItemBuilder(Material.DIAMOND_SWORD).amount(1).enchant(Enchantment.DAMAGE_ALL, 1).build(),
				/* ARMA ENC */ new ItemBuilder(Material.DIAMOND_HOE).amount(1).enchant(Enchantment.DAMAGE_ALL, 4).enchant(Enchantment.KNOCKBACK, 1).build(),
				/* ARMA ENC */ new ItemBuilder(Material.WOOD_AXE).amount(1).enchant(Enchantment.DAMAGE_ALL, 2).enchant(Enchantment.FIRE_ASPECT, 1).build(),
				/* ARMA ENC */ new ItemBuilder(Material.SHEARS).amount(1).enchant(Enchantment.KNOCKBACK, 3).enchant(Enchantment.FIRE_ASPECT, 1).build(),
				/* ARMA ENC */ new ItemBuilder(Material.IRON_SWORD).amount(1).enchant(Enchantment.DAMAGE_ALL, 1).build(),
				/* ARMA ENC */ new ItemBuilder(Material.STONE_SWORD).amount(1).enchant(Enchantment.KNOCKBACK, 2).build(),
				/* ARMA ENC */ new ItemBuilder(Material.BOW).amount(1).enchant(Enchantment.ARROW_DAMAGE, 1).build(),
				/* ARMA ENC */ new ItemBuilder(Material.BOW).amount(1).enchant(Enchantment.FIRE_ASPECT, 1).build(),
				/* ARMA ENC */ new ItemBuilder(Material.BOW).amount(1).enchant(Enchantment.DAMAGE_ALL, 1).enchant(Enchantment.ARROW_KNOCKBACK, 1).build(),
				/* ARMA ENC */ new ItemBuilder(Material.BOW).amount(1).enchant(Enchantment.ARROW_DAMAGE, 2).enchant(Enchantment.ARROW_FIRE, 1).build(),
				/* ARMA ENC */ new ItemBuilder(Material.STONE_SWORD).amount(1).enchant(Enchantment.DAMAGE_ARTHROPODS, 3).enchant(Enchantment.FIRE_ASPECT, 1).build(),
				/* ARMA ENC */ new ItemBuilder(Material.DIAMOND_SWORD).amount(1).enchant(Enchantment.KNOCKBACK, 1).enchant(Enchantment.FIRE_ASPECT, 1).build(),
				/* ARMA ENC */ new ItemBuilder(Material.BLAZE_POWDER).amount(1).enchant(Enchantment.FIRE_ASPECT, 3).build(),
				/* ARMA ENC */ new ItemBuilder(Material.FEATHER).amount(1).enchant(Enchantment.KNOCKBACK, 3).build(),
			
				/* ALIMENTOS */ new ItemBuilder(Material.APPLE).amount(4).build(),
				/* ALIMENTOS */ new ItemBuilder(Material.APPLE).amount(8).build(),
				/* ALIMENTOS */ new ItemBuilder(Material.COOKED_BEEF).amount(10).build(),
				/* ALIMENTOS */ new ItemBuilder(Material.COOKED_CHICKEN).amount(6).build(),
			
				/* BLOQUES */ new ItemBuilder(Material.COBBLESTONE).amount(23).build(),
				/* BLOQUES */ new ItemBuilder(Material.COBBLESTONE).amount(46).build(),
				/* BLOQUES */ new ItemBuilder(Material.SANDSTONE).amount(20).build(),
				/* BLOQUES */ new ItemBuilder(Material.STONE).amount(10).build(),
				/* BLOQUES */ new ItemBuilder(Material.STONE).amount(20).build(),
			

				/* ITEMS */ new ItemBuilder(Material.WATER_BUCKET).amount(1).build(),
				/* ITEMS */ new ItemBuilder(Material.ARROW).amount(8).build(),
				/* ITEMS */ new ItemBuilder(Material.ARROW).amount(16).build(),
				/* ITEMS */ new ItemBuilder(Material.FISHING_ROD).amount(1).build(),
				/* ITEMS */ new ItemBuilder(Material.WEB).amount(4).build(),
				/* ITEMS */ new ItemBuilder(Material.FLINT).amount(1).build(),
				/* ITEMS */ new ItemBuilder(Material.LAVA_BUCKET).amount(1).build(),
				/* ITEMS */ new ItemBuilder(Material.SNOW_BALL).amount(20).build(),
				/* ITEMS */ new ItemBuilder(Material.GOLD_INGOT).amount(8).build(),
				/* ITEMS */ new ItemBuilder(Material.GOLD_INGOT).amount(16).build(),
				/* ITEMS */ new ItemBuilder(Material.GOLD_INGOT).amount(12).build(),
				/* ITEMS */ new ItemBuilder(Material.LAPIS_ORE).amount(3).build()
		};
		return item;
	}
}
