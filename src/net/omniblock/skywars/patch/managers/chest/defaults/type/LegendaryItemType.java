package net.omniblock.skywars.patch.managers.chest.defaults.type;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import net.omniblock.skywars.util.ItemBuilder;

public enum LegendaryItemType {

	CASCO_DE_TITAN(new ItemBuilder(Material.LEATHER_HELMET).amount(1).name("&4&k||&r &e&lCasco Titan &4&k||").lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━").lore("&7Hace miles de años").lore("&7los titanes forjaron esta").lore("&7armadura para").lore("&7proteger el reino Titan").lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━").enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 12).build()),
	PETO_TITAN(new ItemBuilder(Material.LEATHER_CHESTPLATE).amount(1).name("&4&k||&r &e&lPeto Titan &4&k||").lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━").lore("&7Hace miles de años").lore("&7los titanes forjaron esta").lore("&7armadura para").lore("&7proteger el reino Titan").lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━").enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 12).build()),
	PANTALON_TITAN(new ItemBuilder(Material.LEATHER_LEGGINGS).amount(1).name("&4&k||&r &e&lPantalon Titan &4&k||").lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━").lore("&7Hace miles de años").lore("&7los titanes forjaron esta").lore("&7armadura para").lore("&7proteger el reino Titan").lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━").enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 12).build()),
	BOTAS_TITAN(new ItemBuilder(Material.LEATHER_BOOTS).amount(1).name("&4&k||&r &e&lBotas Titan &4&k||").lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━").lore("&7Hace miles de años").lore("&7los titanes forjaron esta").lore("&7armadura para").lore("&7proteger el reino Titan").lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━").enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 12).build()),
	TORRETA_LASER(new ItemBuilder(Material.SPONGE).amount(1).name("&4&k||&r &e&lTorreta Laser&4&k||").lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━").lore("&7Destroza a tus enemigos").lore("&7con un brutal laser.").lore("").lore("&7Daño: &70.5❤ x Disparo").lore("&7&lTiempo: &7 3 seg").lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━").enchant(Enchantment.DURABILITY, 1).build()),
	TORRETA_PORCINA(new ItemBuilder(Material.HARD_CLAY).amount(1).name("&4&k||&r &e&lTorreta Porcina &4&k||").lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━").lore("&7Masacra a tus enemigos").lore("&7con cerdos explosivos").lore("").lore("&7&lDaño: &73❤").lore("&7Ceguera II ; 3 seg").lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━").enchant(Enchantment.DURABILITY, 1).build()),
	COFRE_EXPLOSIVO(new ItemBuilder(Material.CHEST).amount(1).name("&4&k||&r &e&lCofre Explosivo &4&k||").lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━").lore("&7Será como un regalo para").lore("&7tus enemigos.").lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━").build()),
	TORRETA_SANADORA(new ItemBuilder(Material.JUKEBOX).amount(1).name("&4&k||&r &e&lTorreta Sanadora &4&k||").lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━").lore("&7Te curara si estas").lore("&7cerca de ella.").lore("").lore("&7&lCura: &71❤ x Rayo").lore("&7&lTiempo: &7 4 seg").lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━").enchant(Enchantment.DURABILITY, 1).build()),
	TORRETA_CONGELADORA(new ItemBuilder(Material.NOTE_BLOCK).amount(1).name("&4&k||&r &e&lTorreta Congeladora &4&k||").lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━").lore("&7Disparara bolas de").lore("&7enemigos de una manera").lore("&7rapida!").lore("").lore("&7&lCongela: &7+1 x Disparo").lore("&7&lTiempo: &7 1 seg").lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━").enchant(Enchantment.DURABILITY, 1).build()),
	PUENTE_FUTURISTICO(
	new ItemBuilder(Material.MELON_BLOCK).amount(1).name("&4&k||&r &e&lPuente Futuristico &4&k||").lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━").lore("&7Colocalo mirando").lore("&7hacia una dirección X").lore("&7crearas un puente donde").lore("&7solo tu podrás pasar!").lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━").enchant(Enchantment.DURABILITY, 1).build()),
	@SuppressWarnings("deprecation")
	BOLA_CONGELACEREBROS(
	new ItemBuilder(Material.getMaterial(2264)).amount(1).name("&4&k||&r &e&lBola Congelacerebros &4&k||").lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━").lore("&7A congelar cerebros!").lore("").lore("&7&lDaño: &75❤ x Disparo").lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━").enchant(
	Enchantment.DURABILITY, 1).build()),
	RAYO(
	new ItemBuilder(
	Material.RECORD_8).amount(1).name("&4&k||&r &e&lRayo &4&k||").lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━").lore("&7Lanza el rayo de").lore("&7tus sueños!").lore("").lore("&7&lDaño: &77❤").lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━").enchant(
	Enchantment.DURABILITY, 1).build()),
	VARITA_MAGICA(
	new ItemBuilder(
	Material.RECORD_7).amount(1).name("&4&k||&r &e&lVarita Magica &4&k||").lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━").lore("&7Engaña a tu").lore("&7adversario!").lore("").lore("&7&lGenera: &71 Clon").lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━").enchant(
	Enchantment.DURABILITY, 1).build()),
	KRAKEN(
	new ItemBuilder(
	Material.RECORD_6).amount(1).name("&4&k||&r &e&lKraken &4&k||").lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━").lore("&7Lanza un kraken").lore("&7a tus enemigos").lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━").enchant(
	Enchantment.DURABILITY, 1).build()),
	BOMBARDERO(
	new ItemBuilder(
	Material.BLAZE_POWDER).amount(1).name("&4&k||&r &e&lBombardero &4&k||").lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━").lore("&7Llamando a base, Lanzando").lore("&7misiles en 3, 2, 1...").lore("").lore("&7&lDaño: &76❤").lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━").enchant(
	Enchantment.DURABILITY, 1).build()),
	POCION_PURIFICADORA(
	new ItemBuilder(
	Material.POTION).amount(1).name("&4&k||&r &e&lPoción Purificadora &4&k||").lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━").lore("&7Sal de ese mal momento,").lore("&7pero cuidado al comienzo").lore("&7te dejara devil.").lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━").build()),
	@SuppressWarnings("deprecation")
	RAYO_CONGELADO(new ItemBuilder(Material.getMaterial(2259)).amount(1).name("&4&k||&r &e&lRayo Congelado &4&k||").lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━").lore("&7Dale un toque de hielo a").lore("&7una zona.").lore("").lore("&7&lDaño: &72.5❤").lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━").enchant(Enchantment.DURABILITY, 1).build()),
	@SuppressWarnings("deprecation")
	PUÑO_DE_JHONCENA(new ItemBuilder(Material.getMaterial(2257)).amount(1).name("&4&k||&r &e&lPuño de JhonCena &4&k||").lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━").lore("&7Uno de los puños más epicos").lore("&7y mortales de la WWE.").lore("").lore("&7&lDaño: &74❤").lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━").enchant(Enchantment.FIRE_ASPECT, 1).build()),
	@SuppressWarnings("deprecation")
	METEORITO(new ItemBuilder(Material.getMaterial(2256)).amount(1).name("&4&k||&r &e&lMeteorito &4&k||").lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━").lore("&7Desde el cosmos").lore("&7podrás invocar un enorme").lore("&7meteorito a distancia!").lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━").enchant(Enchantment.DURABILITY, 1).build()),

	;

	private ItemStack item;

	LegendaryItemType(ItemStack item) {
		this.item = item;
	}

	public ItemStack getItem() {
		return item;
	}

	public void setItem(ItemStack item) {
		this.item = item;
	}

}