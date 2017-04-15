package net.omniblock.skywars.games.solo.chest.item;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import net.omniblock.skywars.games.solo.chest.item.z.type.EItem;
import net.omniblock.skywars.util.ItemBuilder;

public class ItemZ {
	@SuppressWarnings("deprecation")
	public static ItemStack[] normalChest(){
		
		ItemStack[] item = new ItemStack[]{
				
				/* ARMADURA */ new ItemBuilder(Material.LEATHER_BOOTS).amount(1).build(),
				/* ARMADURA */ new ItemBuilder(Material.IRON_CHESTPLATE).amount(1).build(),
				/* ARMADURA */ new ItemBuilder(Material.IRON_LEGGINGS).amount(1).build(),
				/* ARMADURA */ new ItemBuilder(Material.IRON_BOOTS).amount(1).build(),
				/* ARMADURA */ new ItemBuilder(Material.DIAMOND_HELMET).amount(1).build(),
				/* ARMADURA */ new ItemBuilder(Material.DIAMOND_CHESTPLATE).amount(1).build(),
				/* ARMADURA */ new ItemBuilder(Material.DIAMOND_LEGGINGS).amount(1).build(),
				
				/* ARMADURA ENC */ new ItemBuilder(Material.IRON_CHESTPLATE).amount(1).enchant(Enchantment.THORNS, 2).build(),
				/* ARMADURA ENC */ new ItemBuilder(Material.IRON_BOOTS).amount(1).enchant(Enchantment.PROTECTION_FALL, 1).build(),
				/* ARMADURA ENC */ new ItemBuilder(Material.LEATHER_LEGGINGS).amount(1).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build(),
				/* ARMADURA ENC */ new ItemBuilder(Material.IRON_BOOTS).amount(1).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build(),
				/* ARMADURA ENC */ new ItemBuilder(Material.IRON_CHESTPLATE).amount(1).enchant(Enchantment.PROTECTION_EXPLOSIONS, 2).build(),
				/* ARMADURA ENC */ EItem.ItemBuilder(Material.LEATHER_HELMET, EItem.CASCO_DE_OSCURIDAD, 2),
				/* ARMADURA ENC */ EItem.ItemBuilder(Material.LEATHER_CHESTPLATE, EItem.PETO_DE_LA_OSCURIDAD, 2),
				/* ARMADURA ENC */ EItem.ItemBuilder(Material.LEATHER_LEGGINGS, EItem.PANTALON_DE_LA_OSCURIDAD, 2),
				/* ARMADURA ENC */ EItem.ItemBuilder(Material.LEATHER_BOOTS, EItem.BOTAS_DE_LA_OSCURIDAD, 2),
				/* ARMADURA ENC */ EItem.ItemBuilder(Material.GOLD_HELMET, EItem.CASCO_DE_TITAN, 3),
				/* ARMADURA ENC */ EItem.ItemBuilder(Material.GOLD_CHESTPLATE, EItem.PETO_TITAN, 3),
				/* ARMADURA ENC */ EItem.ItemBuilder(Material.GOLD_LEGGINGS, EItem.PANTALON_TITAN, 3),
				/* ARMADURA ENC */ EItem.ItemBuilder(Material.GOLD_BOOTS, EItem.BOTAS_TITAN, 3),
				
				/* ARMA */ new ItemBuilder(Material.DIAMOND_SWORD).amount(1).build(),
				/* ARMA */ new ItemBuilder(Material.GOLD_SWORD).amount(1).build(),
				/* ARMA */ new ItemBuilder(Material.IRON_SWORD).amount(1).build(),
				/* ARMA */ new ItemBuilder(Material.STONE_SWORD).amount(1).build(),
				/* ARMA */ new ItemBuilder(Material.WOOD_SWORD).amount(1).build(),
				/* ARMA */  new ItemBuilder(Material.BOW).amount(1).build(),
				
				/* ARMA ENC */ new ItemBuilder(Material.IRON_PICKAXE).amount(1).enchant(Enchantment.DURABILITY, 1).build(),
				/* ARMA ENC */ new ItemBuilder(Material.DIAMOND_SWORD).amount(1).enchant(Enchantment.DAMAGE_ALL, 1).build(),
				/* ARMA ENC */ new ItemBuilder(Material.WOOD_AXE).amount(1).enchant(Enchantment.DAMAGE_ALL, 2).enchant(Enchantment.FIRE_ASPECT, 1).build(),

		
				/* BLOQUE */ new ItemBuilder(Material.COBBLESTONE).amount(23).build(),
				/* BLOQUE */ new ItemBuilder(Material.COBBLESTONE).amount(46).build(),
				/* BLOQUE */ new ItemBuilder(Material.SANDSTONE).amount(20).build(),
				/* BLOQUE */ new ItemBuilder(Material.STONE).amount(10).build(),
				/* BLOQUE */ new ItemBuilder(Material.STONE).amount(20).build(),
				/* BLOQUE */ EItem.ItemBuilder(Material.SPONGE, EItem.TORRETA_LASER, 1),
				/* BLOQUE */ EItem.ItemBuilder(Material.HARD_CLAY, EItem.TORRETA_PORCINA, 1),
				/* BLOQUE */ EItem.ItemBuilder(Material.TRAPPED_CHEST, EItem.COFRE_EXPLOSIVO, 1),
				/* BLOQUE */ EItem.ItemBuilder(Material.JUKEBOX, EItem.TORRETA_SANADORA, 1),
				/* BLOQUE */ EItem.ItemBuilder(Material.NOTE_BLOCK, EItem.TORRETA_CONGELADORA, 1),
				/* BLOQUE */ EItem.ItemBuilder(Material.MELON_BLOCK, EItem.PUENTE_FUTURISTICO, 1),
				
				/* ITEMS */ new ItemBuilder(Material.WATER_BUCKET).amount(1).build(),
				/* ITEMS */ new ItemBuilder(Material.LAVA_BUCKET).amount(1).build(),
				/* ITEMS */ EItem.ItemBuilder(Material.RECORD_9, EItem.BOLA_CONGELACEREBROS, 1),
				/* ITEMS */ EItem.ItemBuilder(Material.RECORD_8, EItem.RAYO, 1),
				/* ITEMS */ EItem.ItemBuilder(Material.RECORD_7, EItem.VARITA_MAGICA, 1),
				/* ITEMS */ EItem.ItemBuilder(Material.RECORD_6, EItem.KRAKEN, 1),
				/* ITEMS */ EItem.ItemBuilder(Material.BLAZE_POWDER, EItem.BOMBARDERO, 1),
				/* ITEMS */ EItem.ItemBuilder(Material.POTION, EItem.POCION_PURIFICADORA, 1),
				/* ITEMS */ EItem.ItemBuilder(Material.getMaterial(2259), EItem.RAYO_CONGELADO, 1),
				/* ITEMS */ EItem.ItemBuilder(Material.getMaterial(2257), EItem.PUÑO_DE_JHONCENA, 1),
				/* ITEMS */ EItem.ItemBuilder(Material.getMaterial(2256), EItem.METEORITO, 1)
		};
		return item;
	
		
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack[] trappedChest(){
		ItemStack[] item = new ItemStack[]{
				
				/* ARMADURA ENC */ EItem.ItemBuilder(Material.IRON_HELMET, EItem.CASCO_BRILLANTE, 1),
				/* ARMADURA ENC */ EItem.ItemBuilder(Material.IRON_CHESTPLATE, EItem.PETO_BRILLANTE, 1),
				/* ARMADURA ENC */ EItem.ItemBuilder(Material.IRON_LEGGINGS, EItem.PANTALONES_BRILLANTES, 1),
				/* ARMADURA ENC */ EItem.ItemBuilder(Material.IRON_BOOTS, EItem.BOTAS_BRILLANTES, 1),
				/* ARMADURA ENC */ EItem.ItemBuilder(Material.CHAINMAIL_HELMET, EItem.CASCO_BRILLANTE_II, 2),
				/* ARMADURA ENC */ EItem.ItemBuilder(Material.CHAINMAIL_CHESTPLATE, EItem.PETO_BRILLANTE_II, 2),
				/* ARMADURA ENC */ EItem.ItemBuilder(Material.CHAINMAIL_LEGGINGS, EItem.PANTALONES_BRILLANTES_II, 2),
				/* ARMADURA ENC */ EItem.ItemBuilder(Material.CHAINMAIL_BOOTS, EItem.BOTAS_BRILLANTES_II, 2),
				/* ARMADURA ENC */ EItem.ItemBuilder(Material.GOLD_HELMET, EItem.CASCO_BRILLANTE, 2),
				/* ARMADURA ENC */ EItem.ItemBuilder(Material.GOLD_CHESTPLATE, EItem.PETO_BRILLANTE, 2),
				/* ARMADURA ENC */ EItem.ItemBuilder(Material.GOLD_LEGGINGS, EItem.PANTALONES_BRILLANTES, 2),
				/* ARMADURA ENC */ EItem.ItemBuilder(Material.GOLD_BOOTS, EItem.BOTAS_BRILLANTES, 2),
				/* ARMADURA ENC */ EItem.ItemBuilder(Material.CHAINMAIL_HELMET, EItem.CASCO_FUGAZ, 1),
				/* ARMADURA ENC */ EItem.ItemBuilder(Material.CHAINMAIL_CHESTPLATE, EItem.PECHERA_FUGAZ, 1),
				/* ARMADURA ENC */ EItem.ItemBuilder(Material.CHAINMAIL_LEGGINGS, EItem.PANTALON_FUGAZ, 1),
				/* ARMADURA ENC */ EItem.ItemBuilder(Material.CHAINMAIL_BOOTS, EItem.BOTAS_FUGAZ, 1),
				/* ARMADURA ENC */ EItem.ItemBuilder(Material.DIAMOND_HELMET, EItem.CASCO_FENIX, 2),
				/* ARMADURA ENC */ EItem.ItemBuilder(Material.DIAMOND_CHESTPLATE, EItem.PECHERA_FENIX, 2),
				/* ARMADURA ENC */ EItem.ItemBuilder(Material.DIAMOND_LEGGINGS, EItem.PANTALON_FENIX, 2),
				/* ARMADURA ENC */ EItem.ItemBuilder(Material.DIAMOND_BOOTS, EItem.BOTAS_FENIX, 2),
				/* ARMADURA ENC */ EItem.ItemBuilder(Material.LEATHER_LEGGINGS, EItem.PECHERA_ESTELAR, 4),
				/* ARMADURA ENC */ EItem.ItemBuilder(Material.LEATHER_LEGGINGS, EItem.PANTALON_ESTELAR, 4),
				/* ARMADURA ENC */ EItem.ItemBuilder(Material.LEATHER_HELMET, EItem.CASCO_DE_OSCURIDAD, 2),
				/* ARMADURA ENC */ EItem.ItemBuilder(Material.LEATHER_CHESTPLATE, EItem.PETO_DE_LA_OSCURIDAD, 2),
				/* ARMADURA ENC */ EItem.ItemBuilder(Material.LEATHER_LEGGINGS, EItem.PANTALON_DE_LA_OSCURIDAD, 2),
				/* ARMADURA ENC */ EItem.ItemBuilder(Material.LEATHER_BOOTS, EItem.BOTAS_DE_LA_OSCURIDAD, 2),
				/* ARMADURA ENC */ EItem.ItemBuilder(Material.GOLD_HELMET, EItem.CASCO_DE_TITAN, 3),
				/* ARMADURA ENC */ EItem.ItemBuilder(Material.GOLD_CHESTPLATE, EItem.PETO_TITAN, 3),
				/* ARMADURA ENC */ EItem.ItemBuilder(Material.GOLD_LEGGINGS, EItem.PANTALON_TITAN, 3),
				/* ARMADURA ENC */ EItem.ItemBuilder(Material.GOLD_BOOTS, EItem.BOTAS_TITAN, 3),
				
				/* ARMA ENC */ EItem.ItemBuilder(Material.IRON_AXE, EItem.HACHA_VIKINGA, 2),
				/* ARMA ENC */ EItem.ItemBuilder(Material.DIAMOND_HOE, EItem.OZ_INFERNAL, 2),
				/* ARMA ENC */ EItem.ItemBuilder(Material.IRON_SWORD, EItem.TRIDENTE, 2),
				/* ARMA ENC */ EItem.ItemBuilder(Material.BOW, EItem.ARCO_PODEROSO, 1),
				/* ARMA ENC */ EItem.ItemBuilder(Material.BOW, EItem.ARCO_DE_LAS_TINIEBLAS, 2),
				/* ARMA ENC */ EItem.ItemBuilder(Material.WOOD_SWORD, EItem.ESPADA_HECHIZADA, 4),
				/* ARMA ENC */ EItem.ItemBuilder(Material.GOLD_SWORD, EItem.ESPADA_HECHIZADA_II, 5),
				/* ARMA ENC */ EItem.ItemBuilder(Material.DIAMOND_AXE, EItem.HACHA_DEL_DRAGON, 1),
				/* ARMA ENC */ EItem.ItemBuilder(Material.DIAMOND_SWORD, EItem.ESPADA_DEL_DRAGON, 1),
				/* ARMA ENC */ EItem.ItemBuilder(Material.DIAMOND_HOE, EItem.OZ_DEL_DRAGON, 1),
				/* ARMA ENC */ EItem.ItemBuilder(Material.WOOD_AXE, EItem.HACHA_DE_RAIZ, 2),
				/* ARMA ENC */ EItem.ItemBuilder(Material.WOOD_PICKAXE, EItem.PICO_DE_RAIZ, 2),
				/* ARMA ENC */ EItem.ItemBuilder(Material.WOOD_SWORD, EItem.ESPADA_DE_RAIZ, 1),
				/* ARMA ENC */ EItem.ItemBuilder(Material.GOLD_SWORD, EItem.ESPADA_X, 4),
				/* ARMA ENC */ EItem.ItemBuilder(Material.DIAMOND_SPADE, EItem.PALA_DEL_PODER, 2),
				/* ARMA ENC */ EItem.ItemBuilder(Material.DIAMOND_SWORD, EItem.ESPADA_EXTRAÑA, 3),
				
				/* ALIMENTOS */ new ItemBuilder(Material.APPLE).amount(4).build(),
				/* ALIMENTOS */ new ItemBuilder(Material.COOKED_BEEF).amount(14).build(),
				
				/* BLOQUE */  EItem.ItemBuilder(Material.TNT, EItem.TNT, 1),
				/* BLOQUE */ EItem.ItemBuilder(Material.SPONGE, EItem.TORRETA_LASER, 1),
				/* BLOQUE */ EItem.ItemBuilder(Material.HARD_CLAY, EItem.TORRETA_PORCINA, 1),
				/* BLOQUE */ EItem.ItemBuilder(Material.TRAPPED_CHEST, EItem.COFRE_EXPLOSIVO, 1),
				/* BLOQUE */ EItem.ItemBuilder(Material.JUKEBOX, EItem.TORRETA_SANADORA, 1),
				/* BLOQUE */ EItem.ItemBuilder(Material.NOTE_BLOCK, EItem.TORRETA_CONGELADORA, 1),
				/* BLOQUE */ EItem.ItemBuilder(Material.MELON_BLOCK, EItem.PUENTE_FUTURISTICO, 1), 
				/* BLOQUE */ new ItemBuilder(Material.COBBLESTONE).amount(23).build(),
				/* BLOQUE */ new ItemBuilder(Material.COBBLESTONE).amount(46).build(),
				/* BLOQUE */ new ItemBuilder(Material.SANDSTONE).amount(20).build(),
				/* BLOQUE */ new ItemBuilder(Material.STONE).amount(10).build(),
				/* BLOQUE */ new ItemBuilder(Material.STONE).amount(20).build(),
				

				/* ITEMS */ new ItemBuilder(Material.WATER_BUCKET).amount(1).build(),
				/* ITEMS */ new ItemBuilder(Material.LAVA_BUCKET).amount(1).build(),
				/* ITEMS */ EItem.ItemBuilder(Material.ENDER_PEARL, EItem.MATRIX, 1),
				/* ITEMS */ EItem.ItemBuilder(Material.RECORD_9, EItem.BOLA_CONGELACEREBROS, 1),
				/* ITEMS */ EItem.ItemBuilder(Material.RECORD_8, EItem.RAYO, 1),
				/* ITEMS */ EItem.ItemBuilder(Material.RECORD_7, EItem.VARITA_MAGICA, 1),
				/* ITEMS */ EItem.ItemBuilder(Material.RECORD_6, EItem.KRAKEN, 1),
				/* ITEMS */ EItem.ItemBuilder(Material.BLAZE_POWDER, EItem.BOMBARDERO, 1),
				/* ITEMS */ EItem.ItemBuilder(Material.POTION, EItem.POCION_PURIFICADORA, 1),
				/* ITEMS */ EItem.ItemBuilder(Material.getMaterial(2259), EItem.RAYO_CONGELADO, 1),
				/* ITEMS */ EItem.ItemBuilder(Material.getMaterial(2257), EItem.PUÑO_DE_JHONCENA, 1),
				/* ITEMS */ EItem.ItemBuilder(Material.getMaterial(2256), EItem.METEORITO, 1)				
		};
		return item;
	}
}
