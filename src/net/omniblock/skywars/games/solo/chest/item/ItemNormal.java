package net.omniblock.skywars.games.solo.chest.item;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import net.omniblock.skywars.util.ItemBuilder;

public class ItemNormal {

	public static ItemStack[] normalChest(){
		ItemStack[] itemN = new ItemStack[]{
				
				/* ARMADURA */ new ItemBuilder(Material.LEATHER_LEGGINGS).amount(1).build(),
				/* ARMADURA */ new ItemBuilder(Material.IRON_HELMET).amount(1).build(),
				/* ARMADURA */ new ItemBuilder(Material.IRON_BOOTS).amount(1).build(),
				
				/* ARMA */ new ItemBuilder(Material.STONE_SWORD).amount(1).build(),
				/* ARMA */ new ItemBuilder(Material.WOOD_SWORD).amount(1).build(),
				/* ARMA */ new ItemBuilder(Material.IRON_SWORD).amount(1).build(),
				/* ARMA */  new ItemBuilder(Material.BOW).amount(1).build(),
				
				/* ARMADURA ENC */ new ItemBuilder(Material.IRON_HELMET).amount(1).enchant(Enchantment.PROTECTION_PROJECTILE, 1).build(),
				/* ARMADURA ENC */ new ItemBuilder(Material.IRON_CHESTPLATE).amount(1).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build(),
				
				/* ARMA ENC */ new ItemBuilder(Material.BOW).amount(1).enchant(Enchantment.ARROW_KNOCKBACK, 1).build(),
				/* ARMA ENC */ new ItemBuilder(Material.IRON_SWORD).amount(1).enchant(Enchantment.DAMAGE_ALL, 1).build(),
				
				/* ALIMENTOS */ new ItemBuilder(Material.BREAD).amount(13).build(),
				/* ALIMENTOS */ new ItemBuilder(Material.COOKIE).amount(28).build(),
				
				/* BLOQUES */ new ItemBuilder(Material.COBBLESTONE).amount(23).build(),
				/* BLOQUES */ new ItemBuilder(Material.SANDSTONE).amount(20).build(),
				

				/* ITEMS */ new ItemBuilder(Material.WATER_BUCKET).amount(1).build(),
				/* ITEMS */ new ItemBuilder(Material.LAVA_BUCKET).amount(1).build(),
				/* ITEMS */ new ItemBuilder(Material.ARROW).amount(8).build(),
				/* ITEMS */ new ItemBuilder(Material.BOOK).amount(1).enchant(Enchantment.DAMAGE_ALL, 2).build(),
				/* ITEMS */ new ItemBuilder(Material.BOOK).amount(1).enchant(Enchantment.FIRE_ASPECT, 1).build(),
				/* ITEMS */ new ItemBuilder(Material.ARROW).amount(16).build(),
				/* ITEMS */ new ItemBuilder(Material.SNOW_BALL).amount(10).build(),
				/* ITEMS */ new ItemBuilder(Material.EXP_BOTTLE).amount(20).build(),
				/* ITEMS */ new ItemBuilder(Material.GOLD_INGOT).amount(4).build(),
				/* ITEMS */ new ItemBuilder(Material.GOLD_INGOT).amount(8).build(),
				/* ITEMS */ new ItemBuilder(Material.LAPIS_ORE).amount(3).build(),
				/* ITEMS */ new ItemBuilder(Material.GOLD_INGOT).amount(12).build()
		};
		return itemN;	
	}
	
	public static ItemStack[] trappedChest(){
		ItemStack[] itemT = new ItemStack[]{
				
				/* ARMADURA */ new ItemBuilder(Material.LEATHER_CHESTPLATE).amount(1).build(),
				/* ARMADURA */ new ItemBuilder(Material.LEATHER_BOOTS).amount(1).build(),
				/* ARMADURA */ new ItemBuilder(Material.IRON_HELMET).amount(1).build(),
				/* ARMADURA */ new ItemBuilder(Material.IRON_CHESTPLATE).amount(1).build(),
				/* ARMADURA */ new ItemBuilder(Material.IRON_LEGGINGS).amount(1).build(),
				/* ARMADURA */ new ItemBuilder(Material.IRON_BOOTS).amount(1).build(),
				/* ARMADURA */ new ItemBuilder(Material.DIAMOND_HELMET).amount(1).build(),
				/* ARMADURA */ new ItemBuilder(Material.DIAMOND_BOOTS).amount(1).build(),
		
				/* ARMADURA ENC */ new ItemBuilder(Material.IRON_CHESTPLATE).amount(1).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build(),
				/* ARMADURA ENC */ new ItemBuilder(Material.LEATHER_HELMET).amount(1).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build(),
				/* ARMADURA ENC */ new ItemBuilder(Material.IRON_BOOTS).amount(1).enchant(Enchantment.PROTECTION_FALL, 1).build(),
				/* ARMADURA ENC */ new ItemBuilder(Material.LEATHER_LEGGINGS).amount(1).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build(),
				/* ARMADURA ENC */ new ItemBuilder(Material.IRON_BOOTS).amount(1).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build(),
				/* ARMADURA ENC */ new ItemBuilder(Material.IRON_CHESTPLATE).amount(1).enchant(Enchantment.PROTECTION_EXPLOSIONS, 2).build(),
		
				/* ARMA */	new ItemBuilder(Material.IRON_SWORD).amount(1).build(),
				/* ARMA */	new ItemBuilder(Material.WOOD_SWORD).amount(1).build(),
				/* ARMA */ 	new ItemBuilder(Material.STONE_SWORD).amount(1).build(),
				/* ARMA */ 	new ItemBuilder(Material.BOW).amount(1).build(),
				/* ARMA */	new ItemBuilder(Material.IRON_PICKAXE).amount(1).build(),
		
				/* ARMA ENC */ new ItemBuilder(Material.WOOD_SWORD).amount(1).enchant(Enchantment.KNOCKBACK, 2).build(),
				/* ARMA ENC */ new ItemBuilder(Material.IRON_SWORD).amount(1).enchant(Enchantment.DURABILITY, 2).build(),
				/* ARMA ENC */ new ItemBuilder(Material.BOW).amount(1).enchant(Enchantment.ARROW_KNOCKBACK, 1).build(),
				/* ARMA ENC */ new ItemBuilder(Material.BOW).amount(1).enchant(Enchantment.ARROW_DAMAGE, 1).build(),
				/* ARMA ENC */ new ItemBuilder(Material.WOOD_SWORD).amount(1).enchant(Enchantment.DAMAGE_ALL, 1).build(),
				/* ARMA ENC */ new ItemBuilder(Material.IRON_SWORD).amount(1).enchant(Enchantment.DAMAGE_ALL, 1).build(),
		
				/* ALIMENTOS */ new ItemBuilder(Material.APPLE).amount(4).build(),
				/* ALIMENTOS */ new ItemBuilder(Material.APPLE).amount(8).build(),
				/* ALIMENTOS */ new ItemBuilder(Material.COOKED_BEEF).amount(10).build(),
		
				/* BLOQUES */ new ItemBuilder(Material.COBBLESTONE).amount(23).build(),
				/* BLOQUES */ new ItemBuilder(Material.COBBLESTONE).amount(46).build(),
				/* BLOQUES */ new ItemBuilder(Material.SANDSTONE).amount(20).build(),
				/* BLOQUES */ new ItemBuilder(Material.STONE).amount(10).build(),
				/* BLOQUES */ new ItemBuilder(Material.STONE).amount(20).build(),
				/* BLOQUES */ new ItemBuilder(Material.ENCHANTMENT_TABLE).amount(1).build(),
		

				/* ITEMS */ new ItemBuilder(Material.WATER_BUCKET).amount(1).build(),
				/* ITEMS */ new ItemBuilder(Material.LAVA_BUCKET).amount(1).build(),
				/* ITEMS */ new ItemBuilder(Material.ARROW).amount(8).build(),
				/* ITEMS */ new ItemBuilder(Material.BOOK).amount(1).enchant(Enchantment.DAMAGE_ALL, 2).build(),
				/* ITEMS */ new ItemBuilder(Material.BOOK).amount(1).enchant(Enchantment.FIRE_ASPECT, 1).build(),
				/* ITEMS */ new ItemBuilder(Material.ARROW).amount(16).build(),
				/* ITEMS */ new ItemBuilder(Material.SNOW_BALL).amount(10).build(),
				/* ITEMS */ new ItemBuilder(Material.EXP_BOTTLE).amount(20).build(),
				/* ITEMS */ new ItemBuilder(Material.GOLD_INGOT).amount(4).build(),
				/* ITEMS */ new ItemBuilder(Material.GOLD_INGOT).amount(8).build(),
				/* ITEMS */ new ItemBuilder(Material.LAPIS_ORE).amount(3).build(),
				/* ITEMS */ new ItemBuilder(Material.GOLD_INGOT).amount(12).build()
		
		};
		return itemT;
	
	}
}
