package net.omniblock.skywars.patch.managers.chest.item;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import net.omniblock.skywars.patch.managers.chest.item.type.EItem;
import net.omniblock.skywars.util.ItemBuilder;

public class SkywarsItem {

	
	public static List<ItemStack> itemGameNormalChest(){
		
		List<ItemStack> itemGameNormal = new ArrayList<ItemStack>(){{

			
			/* ARMADURA */ add(new ItemBuilder(Material.LEATHER_LEGGINGS).amount(1).build());
			/* ARMADURA */ add(new ItemBuilder(Material.IRON_HELMET).amount(1).build());
			/* ARMADURA */ add(new ItemBuilder(Material.IRON_BOOTS).amount(1).build());
			
			/* ARMADURA ENC */ add(new ItemBuilder(Material.IRON_HELMET).amount(1).enchant(Enchantment.PROTECTION_PROJECTILE, 1).build());
			/* ARMADURA ENC */ add(new ItemBuilder(Material.IRON_CHESTPLATE).amount(1).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build());
			
			/* ARMA ENC */ add(new ItemBuilder(Material.BOW).amount(1).enchant(Enchantment.ARROW_KNOCKBACK, 1).build());
			/* ARMA ENC */ add(new ItemBuilder(Material.IRON_SWORD).amount(1).enchant(Enchantment.DAMAGE_ALL, 1).build());
			
			/* ITEMS */ add(new ItemBuilder(Material.BOOK).amount(1).enchant(Enchantment.DAMAGE_ALL, 2).build());
			/* ITEMS */ add(new ItemBuilder(Material.BOOK).amount(1).enchant(Enchantment.FIRE_ASPECT, 1).build());
			/* ITEMS */ add(new ItemBuilder(Material.ARROW).amount(16).build());
			/* ITEMS */ add(new ItemBuilder(Material.SNOW_BALL).amount(10).build());
			/* ITEMS */ add(new ItemBuilder(Material.EXP_BOTTLE).amount(20).build());
			/* ITEMS */ add(new ItemBuilder(Material.GOLD_INGOT).amount(4).build());
			/* ITEMS */ add(new ItemBuilder(Material.GOLD_INGOT).amount(8).build());
			/* ITEMS */ add(new ItemBuilder(Material.LAPIS_ORE).amount(3).build());
			/* ITEMS */ add(new ItemBuilder(Material.GOLD_INGOT).amount(12).build());
		}};
		
		return itemGameNormal;
	}
			
	public static List<ItemStack> itemGameNormalTrappedChest(){
		List<ItemStack> itemGameNormalTrappedChest = new ArrayList<ItemStack>(){{
				
			/* ARMADURA */ add(new ItemBuilder(Material.LEATHER_CHESTPLATE).amount(1).build());
			/* ARMADURA */ add(new ItemBuilder(Material.LEATHER_BOOTS).amount(1).build());
			/* ARMADURA */ add(new ItemBuilder(Material.IRON_HELMET).amount(1).build());
			/* ARMADURA */ add(new ItemBuilder(Material.IRON_CHESTPLATE).amount(1).build());
			/* ARMADURA */ add(new ItemBuilder(Material.IRON_LEGGINGS).amount(1).build());
			/* ARMADURA */ add(new ItemBuilder(Material.IRON_BOOTS).amount(1).build());
			/* ARMADURA */ add(new ItemBuilder(Material.DIAMOND_HELMET).amount(1).build());
			/* ARMADURA */ add(new ItemBuilder(Material.DIAMOND_BOOTS).amount(1).build());
		
			/* ARMADURA ENC */ add(new ItemBuilder(Material.IRON_CHESTPLATE).amount(1).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build());
			/* ARMADURA ENC */ add(new ItemBuilder(Material.LEATHER_HELMET).amount(1).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());
			/* ARMADURA ENC */ add(new ItemBuilder(Material.IRON_BOOTS).amount(1).enchant(Enchantment.PROTECTION_FALL, 1).build());
			/* ARMADURA ENC */ add(new ItemBuilder(Material.LEATHER_LEGGINGS).amount(1).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());
			/* ARMADURA ENC */ add(new ItemBuilder(Material.IRON_BOOTS).amount(1).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build());
			/* ARMADURA ENC */ add(new ItemBuilder(Material.IRON_CHESTPLATE).amount(1).enchant(Enchantment.PROTECTION_EXPLOSIONS, 2).build());
	
			/* ARMA */	add(new ItemBuilder(Material.IRON_PICKAXE).amount(1).build());
		
			/* ARMA ENC */ add(new ItemBuilder(Material.WOOD_SWORD).amount(1).enchant(Enchantment.KNOCKBACK, 2).build());
			/* ARMA ENC */ add(new ItemBuilder(Material.IRON_SWORD).amount(1).enchant(Enchantment.DURABILITY, 2).build());
			/* ARMA ENC */ add(new ItemBuilder(Material.BOW).amount(1).enchant(Enchantment.ARROW_KNOCKBACK, 1).build());
			/* ARMA ENC */ add(new ItemBuilder(Material.BOW).amount(1).enchant(Enchantment.ARROW_DAMAGE, 1).build());
			/* ARMA ENC */ add(new ItemBuilder(Material.WOOD_SWORD).amount(1).enchant(Enchantment.DAMAGE_ALL, 1).build());
			/* ARMA ENC */ add(new ItemBuilder(Material.IRON_SWORD).amount(1).enchant(Enchantment.DAMAGE_ALL, 1).build());
		
			/* ALIMENTOS */ add(new ItemBuilder(Material.GOLDEN_APPLE).amount(4).build());
		
			/* BLOQUES */ add(new ItemBuilder(Material.COBBLESTONE).amount(23).build());
			/* BLOQUES */ add(new ItemBuilder(Material.ENCHANTMENT_TABLE).amount(1).build());
		

			/* ITEMS */ add(new ItemBuilder(Material.WATER_BUCKET).amount(1).build());
			/* ITEMS */ add(new ItemBuilder(Material.LAVA_BUCKET).amount(1).build());
			/* ITEMS */ add(new ItemBuilder(Material.ARROW).amount(8).build());
			/* ITEMS */ add(new ItemBuilder(Material.BOOK).amount(1).enchant(Enchantment.DAMAGE_ALL, 2).build());
			/* ITEMS */ add(new ItemBuilder(Material.BOOK).amount(1).enchant(Enchantment.FIRE_ASPECT, 1).build());
			/* ITEMS */ add(new ItemBuilder(Material.ARROW).amount(16).build());
			/* ITEMS */ add(new ItemBuilder(Material.SNOW_BALL).amount(10).build());
			/* ITEMS */ add(new ItemBuilder(Material.EXP_BOTTLE).amount(20).build());
			/* ITEMS */ add(new ItemBuilder(Material.GOLD_INGOT).amount(4).build());
			/* ITEMS */ add(new ItemBuilder(Material.GOLD_INGOT).amount(8).build());
			/* ITEMS */ add(new ItemBuilder(Material.LAPIS_ORE).amount(3).build());
			/* ITEMS */ add(new ItemBuilder(Material.GOLD_INGOT).amount(12).build());
		
		}};
		return itemGameNormalTrappedChest;
	
	}
	
	public static List<ItemStack> itemGameInsaneChest(){
		
		List<ItemStack> item = new ArrayList<ItemStack>(){{
			
			/* ARMADURA */ add(new ItemBuilder(Material.LEATHER_BOOTS).amount(1).build());
			/* ARMADURA */ add(new ItemBuilder(Material.IRON_CHESTPLATE).amount(1).build());
			/* ARMADURA */ add(new ItemBuilder(Material.IRON_LEGGINGS).amount(1).build());
			/* ARMADURA */ add(new ItemBuilder(Material.IRON_BOOTS).amount(1).build());
			/* ARMADURA */ add(new ItemBuilder(Material.DIAMOND_HELMET).amount(1).build());
			/* ARMADURA */ add(new ItemBuilder(Material.DIAMOND_LEGGINGS).amount(1).build());
			
			/* ARMADURA ENC */ add(new ItemBuilder(Material.IRON_CHESTPLATE).amount(1).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build());
			/* ARMADURA ENC */ add(new ItemBuilder(Material.IRON_CHESTPLATE).amount(1).enchant(Enchantment.PROTECTION_PROJECTILE, 2).build());
			/* ARMADURA ENC */ add(new ItemBuilder(Material.DIAMOND_BOOTS).amount(1).enchant(Enchantment.DURABILITY, 1).build());
			/* ARMADURA ENC */ add(new ItemBuilder(Material.IRON_LEGGINGS).amount(1).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());
			
			
			/* ARMA ENC */ add(new ItemBuilder(Material.IRON_SWORD).amount(1).enchant(Enchantment.DAMAGE_ALL, 2).build());
			/* ARMA ENC */ add(new ItemBuilder(Material.STONE_SWORD).amount(1).enchant(Enchantment.KNOCKBACK, 2).build());
			/* ARMA ENC */ add(new ItemBuilder(Material.WOOD_AXE).amount(1).enchant(Enchantment.DAMAGE_ALL, 3).build());
			/* ARMA ENC */ add(new ItemBuilder(Material.BOW).amount(1).enchant(Enchantment.ARROW_DAMAGE, 1).build());
			/* ARMA ENC */	add(new ItemBuilder(Material.BOW).amount(1).enchant(Enchantment.ARROW_KNOCKBACK, 1).build());

			/* ITEMS */ add(new ItemBuilder(Material.WEB).amount(4).build());
			/* ITEMS */ add(new ItemBuilder(Material.WATER_BUCKET).amount(1).build());
			/* ITEMS */ add(new ItemBuilder(Material.LAPIS_ORE).amount(3).build());
				
		}};
		
		return item;
	}
	
	public static List<ItemStack> itemGameInsaneTrappedChest(){
		
		List<ItemStack> item = new ArrayList<ItemStack>(){{
				
			/* ARMADURA */ add(new ItemBuilder(Material.LEATHER_BOOTS).amount(1).build());
			/* ARMADURA */ add(new ItemBuilder(Material.IRON_LEGGINGS).amount(1).build());
			/* ARMADURA */ add(new ItemBuilder(Material.IRON_BOOTS).amount(1).build());
			/* ARMADURA */ add(new ItemBuilder(Material.DIAMOND_HELMET).amount(1).build());
			/* ARMADURA */ add(new ItemBuilder(Material.DIAMOND_BOOTS).amount(1).build());
			
			/* ARMADURA ENC */ add(new ItemBuilder(Material.IRON_CHESTPLATE).amount(1).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build());
			/* ARMADURA ENC */ add(new ItemBuilder(Material.IRON_CHESTPLATE).amount(1).enchant(Enchantment.THORNS, 2).build());
			/* ARMADURA ENC */ add(new ItemBuilder(Material.LEATHER_HELMET).amount(1).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());
			/* ARMADURA ENC */ add(new ItemBuilder(Material.IRON_BOOTS).amount(1).enchant(Enchantment.PROTECTION_FALL, 1).build());
			/* ARMADURA ENC */ add(new ItemBuilder(Material.LEATHER_LEGGINGS).amount(1).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());
			/* ARMADURA ENC */ add(new ItemBuilder(Material.IRON_BOOTS).amount(1).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build());
			/* ARMADURA ENC */ add(new ItemBuilder(Material.IRON_CHESTPLATE).amount(1).enchant(Enchantment.PROTECTION_EXPLOSIONS, 2).build());
			/* ARMADURA ENC */ add(new ItemBuilder(Material.LEATHER_CHESTPLATE).amount(1).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build());
			/* ARMADURA ENC */ add(new ItemBuilder(Material.IRON_HELMET).amount(1).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());
			/* ARMADURA ENC */ add(new ItemBuilder(Material.IRON_HELMET).amount(1).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build());
			/* ARMADURA ENC */ add(new ItemBuilder(Material.DIAMOND_HELMET).amount(1).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());
			/* ARMADURA ENC */ add(new ItemBuilder(Material.DIAMOND_BOOTS).amount(1).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build());
			/* ARMADURA ENC */ add(new ItemBuilder(Material.DIAMOND_CHESTPLATE).amount(1).enchant(Enchantment.PROTECTION_EXPLOSIONS, 2).build());
			/* ARMADURA ENC */ add(new ItemBuilder(Material.DIAMOND_CHESTPLATE).amount(1).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).enchant(Enchantment.THORNS, 1).build());
			
			/* ARMA */	add(new ItemBuilder(Material.IRON_PICKAXE).amount(1).build());
			
			/* ARMA ENC */ add(new ItemBuilder(Material.IRON_PICKAXE).amount(1).enchant(Enchantment.DURABILITY, 1).build());
			/* ARMA ENC */ add(new ItemBuilder(Material.DIAMOND_SWORD).amount(1).enchant(Enchantment.DAMAGE_ALL, 1).build());
			/* ARMA ENC */ add(new ItemBuilder(Material.DIAMOND_HOE).amount(1).enchant(Enchantment.DAMAGE_ALL, 4).enchant(Enchantment.KNOCKBACK, 1).build());
			/* ARMA ENC */ add(new ItemBuilder(Material.WOOD_AXE).amount(1).enchant(Enchantment.DAMAGE_ALL, 2).enchant(Enchantment.FIRE_ASPECT, 1).build());
			/* ARMA ENC */ add(new ItemBuilder(Material.SHEARS).amount(1).enchant(Enchantment.KNOCKBACK, 3).enchant(Enchantment.FIRE_ASPECT, 1).build());
			/* ARMA ENC */ add(new ItemBuilder(Material.IRON_SWORD).amount(1).enchant(Enchantment.DAMAGE_ALL, 1).build());
			/* ARMA ENC */ add(new ItemBuilder(Material.STONE_SWORD).amount(1).enchant(Enchantment.KNOCKBACK, 2).build());
			/* ARMA ENC */ add(new ItemBuilder(Material.BOW).amount(1).enchant(Enchantment.ARROW_DAMAGE, 1).build());
			/* ARMA ENC */ add(new ItemBuilder(Material.BOW).amount(1).enchant(Enchantment.FIRE_ASPECT, 1).build());
			/* ARMA ENC */ add(new ItemBuilder(Material.BOW).amount(1).enchant(Enchantment.DAMAGE_ALL, 1).enchant(Enchantment.ARROW_KNOCKBACK, 1).build());
			/* ARMA ENC */ add(new ItemBuilder(Material.BOW).amount(1).enchant(Enchantment.ARROW_DAMAGE, 2).enchant(Enchantment.ARROW_FIRE, 1).build());
			/* ARMA ENC */ add(new ItemBuilder(Material.STONE_SWORD).amount(1).enchant(Enchantment.DAMAGE_ARTHROPODS, 3).enchant(Enchantment.FIRE_ASPECT, 1).build());
			/* ARMA ENC */ add(new ItemBuilder(Material.DIAMOND_SWORD).amount(1).enchant(Enchantment.KNOCKBACK, 1).enchant(Enchantment.FIRE_ASPECT, 1).build());
			/* ARMA ENC */ add(new ItemBuilder(Material.BLAZE_POWDER).amount(1).enchant(Enchantment.FIRE_ASPECT, 3).build());
			/* ARMA ENC */ add(new ItemBuilder(Material.FEATHER).amount(1).enchant(Enchantment.KNOCKBACK, 3).build());
			
			/* ALIMENTOS */ add(new ItemBuilder(Material.GOLDEN_APPLE).amount(6).build());
			
			/* BLOQUES */ add(new ItemBuilder(Material.COBBLESTONE).amount(23).build());
			/* BLOQUES */ add(new ItemBuilder(Material.STONE).amount(20).build());
			

			/* ITEMS */ add(new ItemBuilder(Material.WATER_BUCKET).amount(1).build());
			/* ITEMS */ add(new ItemBuilder(Material.ARROW).amount(8).build());
			/* ITEMS */ add(new ItemBuilder(Material.ARROW).amount(16).build());
			/* ITEMS */ add(new ItemBuilder(Material.FISHING_ROD).amount(1).build());
			/* ITEMS */ add(new ItemBuilder(Material.WEB).amount(4).build());
			/* ITEMS */ add(new ItemBuilder(Material.FLINT).amount(1).build());
			/* ITEMS */ add(new ItemBuilder(Material.LAVA_BUCKET).amount(1).build());
			/* ITEMS */ add(new ItemBuilder(Material.SNOW_BALL).amount(20).build());
			/* ITEMS */ add(new ItemBuilder(Material.GOLD_INGOT).amount(8).build());
			/* ITEMS */ add(new ItemBuilder(Material.GOLD_INGOT).amount(16).build());
			/* ITEMS */ add(new ItemBuilder(Material.GOLD_INGOT).amount(12).build());
			/* ITEMS */ add(new ItemBuilder(Material.LAPIS_ORE).amount(3).build());
		}};
		
		return item;
	}
	
	public static List<ItemStack> itemGameZChest(){
		
		List<ItemStack> item = new ArrayList<ItemStack>(){{
				
			/* ARMADURA */ add(new ItemBuilder(Material.LEATHER_BOOTS).amount(1).build());
			/* ARMADURA */ add(new ItemBuilder(Material.IRON_CHESTPLATE).amount(1).build());
			/* ARMADURA */ add(new ItemBuilder(Material.IRON_LEGGINGS).amount(1).build());
			/* ARMADURA */ add(new ItemBuilder(Material.IRON_BOOTS).amount(1).build());
			/* ARMADURA */ add(new ItemBuilder(Material.DIAMOND_HELMET).amount(1).build());
			/* ARMADURA */ add(new ItemBuilder(Material.DIAMOND_CHESTPLATE).amount(1).build());
			/* ARMADURA */ add(new ItemBuilder(Material.DIAMOND_LEGGINGS).amount(1).build());
				
			/* ARMADURA ENC */ add(new ItemBuilder(Material.IRON_CHESTPLATE).amount(1).enchant(Enchantment.THORNS, 2).build());
			/* ARMADURA ENC */ add(new ItemBuilder(Material.IRON_BOOTS).amount(1).enchant(Enchantment.PROTECTION_FALL, 1).build());
			/* ARMADURA ENC */ add(new ItemBuilder(Material.LEATHER_LEGGINGS).amount(1).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());
			/* ARMADURA ENC */ add(new ItemBuilder(Material.IRON_BOOTS).amount(1).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build());
			/* ARMADURA ENC */ add(new ItemBuilder(Material.IRON_CHESTPLATE).amount(1).enchant(Enchantment.PROTECTION_EXPLOSIONS, 2).build());
			/* ARMADURA ENC */ add(EItem.CASCO_DE_TITAN.getItem());
			/* ARMADURA ENC */ add(EItem.PETO_TITAN.getItem());
			/* ARMADURA ENC */ add(EItem.PANTALON_TITAN.getItem());
			/* ARMADURA ENC */ add(EItem.BOTAS_TITAN.getItem());
				
			/* ARMA ENC */ add(new ItemBuilder(Material.IRON_PICKAXE).amount(1).enchant(Enchantment.DURABILITY, 1).build());
			/* ARMA ENC */ add(new ItemBuilder(Material.DIAMOND_SWORD).amount(1).enchant(Enchantment.DAMAGE_ALL, 1).build());
			/* ARMA ENC */ add(new ItemBuilder(Material.WOOD_AXE).amount(1).enchant(Enchantment.DAMAGE_ALL, 2).enchant(Enchantment.FIRE_ASPECT, 1).build());

			/* BLOQUE */ add(EItem.TORRETA_LASER.getItem());
			/* BLOQUE */ add(EItem.TORRETA_PORCINA.getItem());
			/* BLOQUE */ add(EItem.COFRE_EXPLOSIVO.getItem());
			/* BLOQUE */ add(EItem.TORRETA_SANADORA.getItem());
			/* BLOQUE */ add(EItem.TORRETA_CONGELADORA.getItem());
			/* BLOQUE */ add(EItem.PUNTE_FUTURISTICO.getItem());
				
			/* ITEMS */ add(EItem.BOLA_CONGELACEREBROS.getItem());
			/* ITEMS */ add(EItem.RAYO.getItem());
			/* ITEMS */ add(EItem.VARITA_MAGICA.getItem());
			/* ITEMS */ add(EItem.KRAKEN.getItem());
			/* ITEMS */ add(EItem.BOMBARDERO.getItem());
			/* ITEMS */ add(EItem.POCION_PURIFICADORA.getItem());
			/* ITEMS */ add(EItem.RAYO_CONGELADO.getItem());
			/* ITEMS */ add(EItem.PUÑO_DE_JHONCENA.getItem());
			/* ITEMS */ add(EItem.METEORITO.getItem());
		}};
		return item;
	}
	
	@SuppressWarnings("deprecation")
	public static List<ItemStack> itemGameZTrappedChest(){
		List<ItemStack> item = new ArrayList<ItemStack>(){{
				
			/* ARMADURA ENC */ add(EItem.CASCO_BRILLANTE.getItem());
			/* ARMADURA ENC */ add(EItem.PETO_BRILLANTE.getItem());
			/* ARMADURA ENC */ add(EItem.PANTALONES_BRILLANTES.getItem());
			/* ARMADURA ENC */ add(EItem.BOTAS_BRILLANTES.getItem());
			/* ARMADURA ENC */ add(EItem.CASCO_BRILLANTE_II.getItem());
			/* ARMADURA ENC */ add(EItem.PETO_BRILLANTE_II.getItem());
			/* ARMADURA ENC */ add(EItem.PANTALONES_BRILLANTES_II.getItem());
			/* ARMADURA ENC */ add(EItem.BOTAS_BRILLANTES_II.getItem());
			/* ARMADURA ENC */ add(EItem.CASCO_BRILLANTE.getItem());
			/* ARMADURA ENC */ add(EItem.PETO_BRILLANTE.getItem());
			/* ARMADURA ENC */ add(EItem.PANTALONES_BRILLANTES.getItem());
			/* ARMADURA ENC */ add(EItem.BOTAS_BRILLANTES.getItem());
			/* ARMADURA ENC */ add(EItem.CASCO_FUGAZ.getItem());
			/* ARMADURA ENC */ add(EItem.PECHERA_FUGAZ.getItem());
			/* ARMADURA ENC */ add(EItem.PANTALON_FUGAZ.getItem());
			/* ARMADURA ENC */ add(EItem.BOTAS_FUGAZ.getItem());
			/* ARMADURA ENC */ add(EItem.CASCO_FENIX.getItem());
			/* ARMADURA ENC */ add(EItem.PECHERA_FENIX.getItem());
			/* ARMADURA ENC */ add(EItem.PANTALON_FENIX.getItem());
			/* ARMADURA ENC */ add(EItem.BOTAS_FENIX.getItem());
			/* ARMADURA ENC */ add(EItem.PECHERA_ESTELAR.getItem());
			/* ARMADURA ENC */ add(EItem.PANTALON_ESTELAR.getItem());
			/* ARMADURA ENC */ add(EItem.CASCO_DE_TITAN.getItem());
			/* ARMADURA ENC */ add(EItem.PETO_TITAN.getItem());
			/* ARMADURA ENC */ add(EItem.PANTALON_TITAN.getItem());
			/* ARMADURA ENC */ add(EItem.BOTAS_TITAN.getItem());
				
			/* ARMA ENC */ add(EItem.HACHA_VIKINGA.getItem());
			/* ARMA ENC */ add(EItem.OZ_INFERNAL.getItem());
	
			/* ARMA ENC */ add(EItem.ARCO_PODEROSO.getItem());
			/* ARMA ENC */ add(EItem.ESPADA_HECHIZADA.getItem());
			/* ARMA ENC */ add(EItem.ESPADA_HECHIZADA_II.getItem());
			/* ARMA ENC */ add(EItem.HACHA_DEL_DRAGON.getItem());
			/* ARMA ENC */ add(EItem.ESPADA_DEL_DRAGON.getItem());
			/* ARMA ENC */ add(EItem.OZ_DEL_DRAGON.getItem());
			/* ARMA ENC */ add(EItem.HACHA_DE_RAIZ.getItem());
			/* ARMA ENC */ add(EItem.PICO_DE_RAIZ.getItem());
			/* ARMA ENC */ add(EItem.ESPADA_DE_RAIZ.getItem());
			/* ARMA ENC */ add(EItem.ESPADA_X.getItem());
			/* ARMA ENC */ add(EItem.PALA_DEL_PODER.getItem());
			/* ARMA ENC */ add(EItem.ESPADA_EXTRAÑA.getItem());
				
			/* ALIMENTOS */ add(new ItemBuilder(Material.GOLDEN_APPLE).amount(4).build());
				
			/* BLOQUE */ add(EItem.TNT.getItem());
			/* BLOQUE */ add(EItem.TORRETA_LASER.getItem());
			/* BLOQUE */ add(EItem.TORRETA_PORCINA.getItem());
			/* BLOQUE */ add(EItem.COFRE_EXPLOSIVO.getItem());
			/* BLOQUE */ add(EItem.TORRETA_SANADORA.getItem());
			/* BLOQUE */ add(EItem.TORRETA_CONGELADORA.getItem());
			/* BLOQUE */ add(EItem.PUNTE_FUTURISTICO.getItem());
			/* BLOQUE */ add(new ItemBuilder(Material.COBBLESTONE).amount(23).build());
			/* BLOQUE */ add(new ItemBuilder(Material.COBBLESTONE).amount(46).build());
				
			/* ITEMS */ add(EItem.MATRIX.getItem());
			/* ITEMS */ add(EItem.BOLA_CONGELACEREBROS.getItem());
			/* ITEMS */ add(EItem.RAYO.getItem());
			/* ITEMS */ add(EItem.VARITA_MAGICA.getItem());
			/* ITEMS */ add(EItem.KRAKEN.getItem());
			/* ITEMS */ add(EItem.BOMBARDERO.getItem());
			/* ITEMS */ add(EItem.POCION_PURIFICADORA.getItem());
			/* ITEMS */ add(EItem.RAYO_CONGELADO.getItem());
			/* ITEMS */ add(EItem.PUÑO_DE_JHONCENA.getItem());
			/* ITEMS */ add(EItem.METEORITO.getItem());
		}};
		return item;
	}
	
	
	public static List<ItemStack> getOnlyItemLegendady(){
		
		List<ItemStack> item  = new ArrayList<ItemStack>(){{
				
				/* ARMA ENC */ add(new ItemBuilder(Material.SHEARS).amount(1).enchant(Enchantment.KNOCKBACK, 3).enchant(Enchantment.FIRE_ASPECT, 1).enchant(Enchantment.DAMAGE_ALL, 12).build());
								
				/* BLOQUE */ add(EItem.TNT.getItem());
				/* BLOQUE */ add(EItem.TORRETA_LASER.getItem());
				/* BLOQUE */ add(EItem.TORRETA_PORCINA.getItem());
				/* BLOQUE */ add(EItem.COFRE_EXPLOSIVO.getItem());
				/* BLOQUE */ add(EItem.TORRETA_SANADORA.getItem());
				/* BLOQUE */ add(EItem.TORRETA_CONGELADORA.getItem());
				/* BLOQUE */ add(EItem.PUNTE_FUTURISTICO.getItem());
				/* BLOQUE */ add(new ItemBuilder(Material.COBBLESTONE).amount(23).build());
				/* BLOQUE */ add(new ItemBuilder(Material.COBBLESTONE).amount(46).build());
					
				/* ITEMS */ add(EItem.MATRIX.getItem());
				/* ITEMS */ add(EItem.BOLA_CONGELACEREBROS.getItem());
				/* ITEMS */ add(EItem.RAYO.getItem());
				/* ITEMS */ add(EItem.VARITA_MAGICA.getItem());
				/* ITEMS */ add(EItem.KRAKEN.getItem());
				/* ITEMS */ add(EItem.BOMBARDERO.getItem());
				/* ITEMS */ add(EItem.POCION_PURIFICADORA.getItem());
				/* ITEMS */ add(EItem.RAYO_CONGELADO.getItem());
				/* ITEMS */ add(EItem.PUÑO_DE_JHONCENA.getItem());
				/* ITEMS */ add(EItem.METEORITO.getItem());			
				
		}};
		return item;
	}
}
