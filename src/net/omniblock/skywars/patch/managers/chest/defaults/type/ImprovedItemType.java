package net.omniblock.skywars.patch.managers.chest.defaults.type;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import net.omniblock.skywars.util.ItemBuilder;

public enum ImprovedItemType {

		HACHA_VIKINGA(
			new ItemBuilder(Material.IRON_AXE)
			.amount(1)
			.name("&8&k||&r &c&lHacha Vikinga &8&k||")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.lore("&7Usada por los")
			.lore("&7vikingos mas temidos")
			.lore("&7para destrozar a sus enemigos")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.enchant(Enchantment.DAMAGE_ALL, 3)
			.enchant(Enchantment.DURABILITY, 3).build()),
		OZ_INFERNAL(
			new ItemBuilder(Material.DIAMOND_HOE)
			.amount(1)
			.name("&8&k||&r &c&lOz Infernal &lore8&k||")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.lore("&7Proviene de la")
			.lore("&7más temida Muerte, a")
			.lore("&7cobrar Venganza!")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.enchant(Enchantment.FIRE_ASPECT, 3)
			.enchant(Enchantment.DAMAGE_ALL, 3).build()),
		MATRIX(
			new ItemBuilder(Material.ENDER_PEARL)
			.amount(1)
			.name("&8&k||&r &c&lMatrix &8&k||")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.lore("&7Una reliquia")
			.lore("&7de los endermans,")
			.lore("&7tiene un misterioso poder")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.enchant(Enchantment.KNOCKBACK,1).build()),
		CASCO_BRILLANTE(
			new ItemBuilder(Material.IRON_HELMET)
			.amount(1)
			.name("&8&k||&r &c&lCasco Brillante &8&k||")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.lore("&7Forjado por los guerreros")
			.lore("&7del cielo, para resistir")
			.lore("las arduas batallas")
			.lore("&7con sus enemigos")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
			.enchant(Enchantment.DURABILITY, 1).build()),
		PETO_BRILLANTE(
			new ItemBuilder(Material.IRON_HELMET)
			.amount(1)
			.name("&8&k||&r &c&lPeto Brillante &8&k||")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.lore("&7Forjado por los guerreros")
			.lore("&7del cielo, para resistir")
			.lore("las arduas batallas")
			.lore("&7con sus enemigos")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
			.enchant(Enchantment.DURABILITY, 1).build()),
		PANTALONES_BRILLANTES(
			new ItemBuilder(Material.IRON_LEGGINGS)
			.amount(1)
			.name("&8&k||&r &c&lPantalones Brillantes &8&k||")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.lore("&7Forjado por los guerreros")
			.lore("&7del cielo, para resistir")
			.lore("las arduas batallas")
			.lore("&7con sus enemigos")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
			.enchant(Enchantment.DURABILITY, 1).build()),
		BOTAS_BRILLANTES(
			new ItemBuilder(Material.IRON_BOOTS)
			.amount(1)
			.name("&8&k||&r &c&lBotas Brillantes &8&k||")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.lore("&7Forjado por los guerreros")
			.lore("&7del cielo, para resistir")
			.lore("las arduas batallas")
			.lore("&7con sus enemigos")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
			.enchant(Enchantment.DURABILITY, 1).build()),
		ARCO_PODEROSO(
			new ItemBuilder(Material.BOW)
			.amount(1)
			.name("&8&k||&r &c&lArco Poderoso &8&k||")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.lore("&7Con sus flechas")
			.lore("&7hace arder a sus enemigos")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.enchant(Enchantment.ARROW_FIRE, 3)
			.enchant(Enchantment.ARROW_DAMAGE, 1).build()),
		TNT(
			new ItemBuilder(Material.TNT)
			.amount(1)
			.name("&8&k||&r &c&lTNT &8&k||")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.lore("&7Dificulta el paso de tus")
			.lore("&7enemigos con una gran ")
			.lore("&7explosion")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.enchant(Enchantment.DURABILITY, 1).build()),
		CASCO_BRILLANTE_II(
			new ItemBuilder(Material.IRON_HELMET)
			.amount(1)
			.name("&8&k||&r &c&lCasco Brillante II &8&k||")
			.lore("&7━━━━━━━━━━━━━━━━━━━━━━━━━")
			.lore("&7Usado por los guerreros")
			.lore("&7mas fuertes del cielo.")
			.lore("&7considerados la elite")
			.lore("&7━━━━━━━━━━━━━━━━━━━━━━━━━")
			.enchant(Enchantment.PROTECTION_FALL,2)
			.enchant(Enchantment.DURABILITY,2).build()),
		PETO_BRILLANTE_II(
			new ItemBuilder(Material.IRON_CHESTPLATE)
			.amount(1)
			.name("&8&k||&r &c&lPeto Brillante II &8&k||")
			.lore("&7━━━━━━━━━━━━━━━━━━━━━━━━━")
			.lore("&7Usado por los guerreros")
			.lore("&7mas fuertes del cielo.")
			.lore("&7considerados la elite")
			.lore("&7━━━━━━━━━━━━━━━━━━━━━━━━━")
			.enchant(Enchantment.PROTECTION_FALL,2)
			.enchant(Enchantment.DURABILITY,2).build()),
		PANTALONES_BRILLANTES_II(
			new ItemBuilder(Material.IRON_LEGGINGS)
			.amount(1)
			.name("&8&k||&r &c&lPantalones Brillantes II &8&k||")
			.lore("&7━━━━━━━━━━━━━━━━━━━━━━━━━")
			.lore("&7Usado por los guerreros")
			.lore("&7mas fuertes del cielo.")
			.lore("&7considerados la elite")
			.lore("&7━━━━━━━━━━━━━━━━━━━━━━━━━")
			.enchant(Enchantment.PROTECTION_FALL,2)
			.enchant(Enchantment.DURABILITY,2).build()),
		BOTAS_BRILLANTES_II(
			new ItemBuilder(Material.IRON_BOOTS)
			.amount(1)
			.name("&8&k||&r &c&lBotas Brillantes II &8&k||")
			.lore("&7━━━━━━━━━━━━━━━━━━━━━━━━━")
			.lore("&7Usado por los guerreros")
			.lore("&7mas fuertes del cielo,")
			.lore("&7considerados la elite")
			.lore("&7━━━━━━━━━━━━━━━━━━━━━━━━━")
			.enchant(Enchantment.PROTECTION_FALL,2)
			.enchant(Enchantment.DURABILITY,2).build()),
		ESPADA_HECHIZADA(
			new ItemBuilder(Material.DIAMOND_SWORD)
			.amount(1)
			.name("&8&k||&r &c&lEspada Hechizada &8&k||")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.lore("&7Esta espada")
			.lore("&7no deja rastros del")
			.lore("&7enemigo")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.enchant(Enchantment.DAMAGE_ALL, 2).build()),
		ESPADA_HECHIZADA_II(
			new ItemBuilder(Material.DIAMOND_SWORD)
			.amount(1)
			.name("&8&k||&r &c&lEspada Hechizada II &8&k||")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.lore("&7Su poder de hechizo")
			.lore("&7genera temor, algunos")
			.lore("&7temen tocarla.")
			.lore("&7Malignos hechizos")
			.lore("&7emanan de esta espada")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.enchant(Enchantment.DAMAGE_ALL, 2)
			.enchant(Enchantment.FIRE_ASPECT, 2).build()),
		HACHA_DEL_DRAGON(
			new ItemBuilder(Material.IRON_AXE)
			.amount(1)
			.name("&8&k||&r &c&lHacha del Dragon &8&k||")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.lore("&7Temida y odiada por")
			.lore("&7los Dioses, por su masivo")
			.lore("&7poder")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.enchant(Enchantment.DAMAGE_ALL, 4)
			.enchant(Enchantment.FIRE_ASPECT, 1)
			.enchant(Enchantment.KNOCKBACK, 1).build()),
		ESPADA_DEL_DRAGON(
			new ItemBuilder(Material.IRON_SWORD)
			.amount(1)
			.name("&8&k||&r &c&lEspada del Dragon &8&k||")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.lore("&7La primera espada echa")
			.lore("&7por los dragones.")
			.lore("&7No es tan poderosa")
			.lore("&7pero algunos dioses")
			.lore("&7han sido decapitados")
			.lore("&7por su afilada hoja")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.enchant(Enchantment.FIRE_ASPECT, 1)
			.enchant(Enchantment.DAMAGE_ALL, 2).build()),
		OZ_DEL_DRAGON(
			new ItemBuilder(Material.DIAMOND_HOE)
			.amount(1)
			.name("&8&k||&r &c&lOz del Dragon &8&k||")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.lore("&7La burla de algunos")
			.lore("&7provocó su inmediata")
			.lore("&7muerte.")
			.lore("&7La arma mas simple, pero")
			.lore("&7la mas audaz para")
			.lore("&7decapitar")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.enchant(Enchantment.DAMAGE_ALL, 6)
			.enchant(Enchantment.KNOCKBACK, 2).build()),
		HACHA_DE_RAIZ(
			new ItemBuilder(Material.WOOD_AXE)
			.amount(1)
			.name("&8&k||&r &c&lHacha de Raiz &8&k||")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.lore("&7por defender sus sagradas")
			.lore("&7tierras, profanaron")
			.lore("&7sus arboles.")
			.lore("&7Esta simple, pero")
			.lore("&7sagrada arma,")
			.lore("&7aleja a sus mas")
			.lore("&7mortiferos enemigos")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.enchant(Enchantment.KNOCKBACK, 4).build()),
		PICO_DE_RAIZ(
			new ItemBuilder(Material.WOOD_PICKAXE)
			.amount(1)
			.name("&8&k||&r &c&lPico de Raiz &8&k||")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.lore("&7por defender sus sagradas")
			.lore("&7tierras, profanaron")
			.lore("&7sus arboles.")
			.lore("&7Esta simple, pero")
			.lore("&7sagrada arma,")
			.lore("&7aleja a sus mas")
			.lore("&7mortiferos enemigos")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.enchant(Enchantment.KNOCKBACK, 4)
			.enchant(Enchantment.DURABILITY, 10).build()),
		ESPADA_DE_RAIZ(
			new ItemBuilder(Material.WOOD_SWORD)
			.amount(1)
			.name("&8&k||&r &c&lEspada de Raiz &8&k||")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.lore("&7por defender sus sagradas")
			.lore("&7tierras, profanaron")
			.lore("&7sus arboles.")
			.lore("&7Esta simple, pero")
			.lore("&7sagrada arma,")
			.lore("&7aleja a sus mas")
			.lore("&7mortiferos enemigos")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.enchant(Enchantment.KNOCKBACK, 4)
			.enchant(Enchantment.DAMAGE_ALL, 4).build()),
		CASCO_MECANICO(
			new ItemBuilder(Material.IRON_HELMET)
			.amount(1)
			.name("&8&k||&r &c&lCasco Mecanico &8&k||")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.lore("&7Estas civilizaciones")
			.lore("&7tenian un objetivo")
			.lore("&7claro, aniquilar")
			.lore("&7a sus estupidos")
			.lore("&7enemigos.")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.enchant(Enchantment.PROTECTION_FIRE, 3)
			.enchant(Enchantment.DURABILITY, 5)
			.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).build()),
		PECHERA_MECANICA(
			new ItemBuilder(Material.IRON_CHESTPLATE)
			.amount(1)
			.name("&8&k||&r &c&lPechera Mecanica &8&k||")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.lore("&7Estas civilizaciones")
			.lore("&7tenian un objetivo")
			.lore("&7claro, aniquilar")
			.lore("&7a sus estupidos")
			.lore("&7enemigos.")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.enchant(Enchantment.PROTECTION_FIRE, 3)
			.enchant(Enchantment.DURABILITY, 5)
			.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).build()),
		PANTALONES_MECANICOS(
			new ItemBuilder(Material.IRON_LEGGINGS)
			.amount(1)
			.name("&8&k||&r &c&lPantalones Mecanicos &8&k||")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.lore("&7Estas civilizaciones")
			.lore("&7tenian un objetivo")
			.lore("&7claro, aniquilar")
			.lore("&7a sus estupidos")
			.lore("&7enemigos.")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.enchant(Enchantment.PROTECTION_FIRE, 3)
			.enchant(Enchantment.DURABILITY, 5)
			.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).build()),
		BOTAS_MECANICOS(
			new ItemBuilder(Material.IRON_BOOTS)
			.amount(1)
			.name("&8&k||&r &c&lBotas Mecanicas &8&k||")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.lore("&7Estas civilizaciones")
			.lore("&7tenian un objetivo")
			.lore("&7claro, aniquilar")
			.lore("&7a sus estupidos")
			.lore("&7enemigos.")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.enchant(Enchantment.PROTECTION_FIRE, 3)
			.enchant(Enchantment.DURABILITY, 5)
			.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).build()),
		CASCO_FUGAZ(
			new ItemBuilder(Material.DIAMOND_HELMET)
			.amount(1)
			.name("&8&k||&r &c&lCasco Fugaz &8&k||")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.lore("&7Nadie sabe quien fue")
			.lore("&7el herrero de tan ")
			.lore("&7perfectas armaduras,")
			.lore("&7sin duda, un misterio.")
			.lore("&7Algunos piensan que fue")
			.lore("&7el cosmos, para poner")
			.lore("&7fin a las inagotables")
			.lore("&7batallas")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5)
			.enchant(Enchantment.PROTECTION_EXPLOSIONS, 2)
			.enchant(Enchantment.PROTECTION_FIRE, 2)
			.enchant(Enchantment.PROTECTION_PROJECTILE, 2).build()),
		PECHERA_FUGAZ(
			new ItemBuilder(Material.DIAMOND_CHESTPLATE)
			.amount(1)
			.name("&8&k||&r &c&lPechera Fugaz &8&k||")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.lore("&7Nadie sabe quien fue")
			.lore("&7el herrero de tan ")
			.lore("&7perfectas armaduras,")
			.lore("&7sin duda, un misterio.")
			.lore("&7Algunos piensan que fue")
			.lore("&7el cosmos, para poner")
			.lore("&7fin a las inagotables")
			.lore("&7batallas")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5)
			.enchant(Enchantment.PROTECTION_EXPLOSIONS, 2)
			.enchant(Enchantment.PROTECTION_FIRE, 2)
			.enchant(Enchantment.PROTECTION_PROJECTILE, 2).build()),
		PANTALON_FUGAZ(
			new ItemBuilder(Material.DIAMOND_LEGGINGS)
			.amount(1)
			.name("&8&k||&r &c&lPantalon Fugaz &8&k||")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.lore("&7Nadie sabe quien fue")
			.lore("&7el herrero de tan ")
			.lore("&7perfectas armaduras,")
			.lore("&7sin duda, un misterio.")
			.lore("&7Algunos piensan que fue")
			.lore("&7el cosmos, para poner")
			.lore("&7fin a las inagotables")
			.lore("&7batallas")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5)
			.enchant(Enchantment.PROTECTION_EXPLOSIONS, 2)
			.enchant(Enchantment.PROTECTION_FIRE, 2)
			.enchant(Enchantment.PROTECTION_PROJECTILE, 2).build()),
		BOTAS_FUGAZ(
			new ItemBuilder(Material.DIAMOND_BOOTS)
			.amount(1)
			.name("&8&k||&r &c&lBotas Fugaz &8&k||")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.lore("&7Nadie sabe quien fue")
			.lore("&7el herrero de tan ")
			.lore("&7perfectas armaduras,")
			.lore("&7sin duda, un misterio.")
			.lore("&7Algunos piensan que fue")
			.lore("&7el cosmos, para poner")
			.lore("&7fin a las inagotables")
			.lore("&7batallas")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5)
			.enchant(Enchantment.PROTECTION_EXPLOSIONS, 2)
			.enchant(Enchantment.PROTECTION_FIRE, 2)
			.enchant(Enchantment.PROTECTION_PROJECTILE, 2).build()),
		CASCO_FENIX(
			new ItemBuilder(Material.GOLD_HELMET)
			.amount(1)
			.name("&8&k||&r &c&lCasco Fenix &8&k||")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.lore("&7considerada armadura de")
			.lore("&7elite, por resistir las")
			.lore("&7abrasadoras llamas")
			.lore("&7de Dios")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.enchant(Enchantment.PROTECTION_FIRE, 10)
			.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build()),
		PECHERA_FENIX(
			new ItemBuilder(Material.GOLD_CHESTPLATE)
			.amount(1)
			.name("&8&k||&r &c&lPecheraFenix &8&k||")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.lore("&7considerada armadura de")
			.lore("&7elite, por resistir las")
			.lore("&7abrasadoras llamas")
			.lore("&7de Dios")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.enchant(Enchantment.PROTECTION_FIRE, 10)
			.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build()),
		PANTALON_FENIX(
			new ItemBuilder(Material.GOLD_LEGGINGS)
			.amount(1)
			.name("&8&k||&r &c&lPantalon Fenix &8&k||")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.lore("&7considerada armadura de")
			.lore("&7elite, por resistir las")
			.lore("&7abrasadoras llamas")
			.lore("&7de Dios")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.enchant(Enchantment.PROTECTION_FIRE, 10)
			.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build()),
		BOTAS_FENIX(
			new ItemBuilder(Material.GOLD_BOOTS)
			.amount(1)
			.name("&8&k||&r &c&lBotas Fenix &8&k||")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.lore("&7considerada armadura de")
			.lore("&7elite, por resistir las")
			.lore("&7abrasadoras llamas")
			.lore("&7de Dios")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.enchant(Enchantment.PROTECTION_FIRE, 10)
			.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build()),
		ESPADA_X(
			new ItemBuilder(Material.IRON_SWORD)
			.amount(1)
			.name("&8&k||&r &c&lEspada X &8&k||")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.lore("&7La nave que la")
			.lore("&7trajo dejo entendido")
			.lore("&7que provenia del futuro")
			.lore("&7con un mensaje extraño:")
			.lore("&701101001 01000011")
			.lore("&701110010 01100001")
			.lore("&701100110 01110100")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.enchant(Enchantment.DAMAGE_ALL, 3)
			.enchant(Enchantment.KNOCKBACK, 1)
			.enchant(Enchantment.FIRE_ASPECT, 2).build()),
		PALA_DEL_PODER(
			new ItemBuilder(Material.DIAMOND_SPADE)
			.amount(1)
			.name("&8&k||&r &c&lPala del Poder &8&k||")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.lore("&7fue utilizada para")
			.lore("&7sepultar a los guerreros y")
			.lore("&7Dioses, se dice que")
			.lore("&7adquirio los poderes")
			.lore("&7de algunos dioses y")
			.lore("&7el alma de un feroz")
			.lore("&7guerrero")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.enchant(Enchantment.KNOCKBACK, 3)
			.enchant(Enchantment.FIRE_ASPECT, 3).build()),
		ESPADA_EXTRAÑA(
			new ItemBuilder(Material.IRON_SWORD)
			.amount(1)
			.name("&8&k||&r &c&lEspada Extraña &8&k||")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.lore("&7No hay datos de")
			.lore("&7donde o como se creo.")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.enchant(Enchantment.DAMAGE_ALL, 2).build()),
		PECHERA_ESTELAR(
			new ItemBuilder(Material.DIAMOND_CHESTPLATE)
			.amount(1)
			.name("&8&k||&r &c&lPechera Estelar &8&k||")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.lore("&7Los Astros la")
			.lore("&7denominaron superior.")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 6).build()),
		PANTALON_ESTELAR(
			new ItemBuilder(Material.DIAMOND_LEGGINGS)
			.amount(1)
			.name("&8&k||&r &c&lPantalon Estelar &8&k||")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.lore("&7Los Astros la")
			.lore("&7denominaron superior.")
			.lore("&8━━━━━━━━━━━━━━━━━━━━━━━━━")
			.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 6).build()),
		
		;
	
	private ItemStack item;

	ImprovedItemType(ItemStack item){
		this.item = item;
	}

	public ItemStack getItem() {
		return item;
	}

	public void setItem(ItemStack item) {
		this.item = item;
	}
	
}
