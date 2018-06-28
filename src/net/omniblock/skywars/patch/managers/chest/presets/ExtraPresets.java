package net.omniblock.skywars.patch.managers.chest.presets;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import net.omniblock.skywars.patch.managers.chest.defaults.type.ImprovedItemType;
import net.omniblock.skywars.patch.managers.chest.defaults.type.LegendaryItemType;
import net.omniblock.skywars.patch.managers.chest.object.ChestPack;
import net.omniblock.skywars.util.ItemBuilder;

public class ExtraPresets {

	protected static final ChestPack[] MORE_INSANE_CHESTPACKS = new ChestPack[] {

			new ChestPack()
					.addContent(ImprovedItemType.ESPADA_HECHIZADA_II.getItem())
					.addContent(new ItemBuilder(Material.EXP_BOTTLE).amount(18).build())
					.addContent(ImprovedItemType.ESPADA_EXTRAÑA.getItem())
					.addContent(new ItemBuilder(Material.STONE).amount(20).build())
					.addContent(ImprovedItemType.PALA_DEL_PODER.getItem())
					.addContent(ImprovedItemType.BOTAS_FENIX.getItem()),

			new ChestPack()
					.addContent(new ItemBuilder(Material.DIAMOND_SWORD).enchant(Enchantment.DAMAGE_ALL, 2).amount(1).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(2).build())
					.addContent(ImprovedItemType.CASCO_FENIX.getItem())
					.addContent(ImprovedItemType.HACHA_DEL_DRAGON.getItem())
					.addContent(new ItemBuilder(Material.SHIELD).amount(1).build())
					.addContent(new ItemBuilder(Material.STONE).amount(32).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(2).build())
					.addContent(new ItemBuilder(Material.IRON_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).amount(1).build())
					.addContent(ImprovedItemType.TNT.getItem()),

			new ChestPack()
					.addContent(new ItemBuilder(Material.IRON_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).amount(1).build())
					.addContent(new ItemBuilder(Material.APPLE).amount(1).build())
					.addContent(ImprovedItemType.PECHERA_FENIX.getItem())
					.addContent(new ItemBuilder(Material.STONE).amount(18).build())
					.addContent(new ItemBuilder(Material.GOLD_BLOCK).amount(7).build())
					.addContent(new ItemBuilder(Material.GOLD_INGOT).amount(9).build())
					.addContent(new ItemBuilder(Material.WORKBENCH).amount(1).build())
					.addContent(new ItemBuilder(Material.DIAMOND_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).amount(1).build())
					.addContent(ImprovedItemType.BOTAS_FUGAZ.getItem()),

			new ChestPack()
					.addContent(new ItemBuilder(Material.DIAMOND_CHESTPLATE).enchant(Enchantment.PROTECTION_FIRE, 4).amount(1).build())
					.addContent(ImprovedItemType.PANTALON_FENIX.getItem())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(4).build())
					.addContent(new ItemBuilder(Material.STONE).amount(26).build())
					.addContent(new ItemBuilder(Material.COOKIE).amount(4).build())
					.addContent(new ItemBuilder(Material.DIAMOND_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).amount(1).build())
					.addContent(ImprovedItemType.BOTAS_FENIX.getItem()),

			new ChestPack()
					.addContent(new ItemBuilder(Material.BOW).enchant(Enchantment.ARROW_DAMAGE, 4).amount(1).build())
					.addContent(new ItemBuilder(Material.ARROW).amount(12).build())
					.addContent(ImprovedItemType.BOTAS_FENIX.getItem())
					.addContent(new ItemBuilder(Material.DIAMOND_SWORD).enchant(Enchantment.DURABILITY, 4).amount(1).build())
					.addContent(new ItemBuilder(Material.STONE).amount(32).build())
					.addContent(ImprovedItemType.PICO_DE_RAIZ.getItem())
					.addContent(new ItemBuilder(Material.CHAINMAIL_BOOTS).amount(1).build()),

			new ChestPack()
					.addContent(new ItemBuilder(Material.BOW).enchant(Enchantment.ARROW_DAMAGE, 2).enchant(Enchantment.DURABILITY, 2).amount(1).build())
					.addContent(new ItemBuilder(Material.ARROW).amount(6).build())
					.addContent(ImprovedItemType.PICO_DE_RAIZ.getItem())
					.addContent(ImprovedItemType.TNT.getItem())
					.addContent(new ItemBuilder(Material.ARROW).amount(4).build())
					.addContent(new ItemBuilder(Material.STONE).amount(26).build())
					.addContent(new ItemBuilder(Material.SHIELD).amount(1).build())
					.addContent(new ItemBuilder(Material.IRON_AXE).enchant(Enchantment.DAMAGE_ALL, 4).enchant(Enchantment.DURABILITY, 4).amount(1).build())
					.addContent(ImprovedItemType.ESPADA_EXTRAÑA.getItem()),

			new ChestPack()
					.addContent(new ItemBuilder(Material.ARROW).amount(12).build())
					.addContent(ImprovedItemType.CASCO_BRILLANTE.getItem())
					.addContent(ImprovedItemType.TNT.getItem())
					.addContent(ImprovedItemType.TNT.getItem())
					.addContent(ImprovedItemType.HACHA_DE_RAIZ.getItem())
					.addContent(new ItemBuilder(Material.ARROW).amount(8).build())
					.addContent(new ItemBuilder(Material.DIAMOND_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).enchant(Enchantment.DURABILITY, 4).amount(1).build())
					.addContent(new ItemBuilder(Material.STONE).amount(20).build())
					.addContent(ImprovedItemType.ESPADA_X.getItem()),

			new ChestPack()
					.addContent(new ItemBuilder(Material.EXP_BOTTLE).amount(12).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(2).build())
					.addContent(ImprovedItemType.PANTALON_ESTELAR.getItem())
					.addContent(ImprovedItemType.OZ_INFERNAL.getItem())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(2).build())
					.addContent(new ItemBuilder(Material.STONE).amount(42).build())
					.addContent(new ItemBuilder(Material.DIAMOND_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).enchant(Enchantment.DURABILITY, 2).amount(1).build())
					.addContent(new ItemBuilder(Material.DIAMOND_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).enchant(Enchantment.DURABILITY, 2).amount(1).build())
					.addContent(ImprovedItemType.ESPADA_DE_RAIZ.getItem()),

			new ChestPack()
					.addContent(new ItemBuilder(Material.EXP_BOTTLE).amount(12).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(2).build())
					.addContent(ImprovedItemType.PECHERA_ESTELAR.getItem())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(2).build())
					.addContent(new ItemBuilder(Material.STONE).amount(30).build())
					.addContent(new ItemBuilder(Material.DIAMOND_HELMET).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).enchant(Enchantment.DURABILITY, 2).amount(1).build())
					.addContent(new ItemBuilder(Material.DIAMOND_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).enchant(Enchantment.DURABILITY, 2).amount(1).build())
					.addContent(ImprovedItemType.ESPADA_X.getItem()),

			new ChestPack()
					.addContent(new ItemBuilder(Material.LAVA_BUCKET).amount(1).build())
					.addContent(ImprovedItemType.PECHERA_MECANICA.getItem())
					.addContent(ImprovedItemType.MATRIX.getItem())
					.addContent(ImprovedItemType.PANTALON_ESTELAR.getItem())
					.addContent(new ItemBuilder(Material.WATER_BUCKET).amount(1).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(2).build())
					.addContent(new ItemBuilder(Material.SHIELD).amount(1).build())
					.addContent(new ItemBuilder(Material.STONE).amount(28).build())
					.addContent(new ItemBuilder(Material.GOLD_AXE).enchant(Enchantment.FIRE_ASPECT, 2).enchant(Enchantment.DURABILITY, 2).amount(1).build()),

			new ChestPack()
					.addContent(ImprovedItemType.ARCO_PODEROSO.getItem())
					.addContent(ImprovedItemType.OZ_DEL_DRAGON.getItem())
					.addContent(ImprovedItemType.PANTALONES_MECANICOS.getItem())
					.addContent(new ItemBuilder(Material.ARROW).amount(4).build())
					.addContent(new ItemBuilder(Material.ARROW).amount(12).build())
					.addContent(new ItemBuilder(Material.STONE).amount(36).build())
					.addContent(new ItemBuilder(Material.STONE).amount(12).build()),

			new ChestPack()
					.addContent(ImprovedItemType.MATRIX.getItem())
					.addContent(ImprovedItemType.TNT.getItem())
					.addContent(ImprovedItemType.CASCO_MECANICO.getItem())
					.addContent(new ItemBuilder(Material.EGG).amount(12).build())
					.addContent(new ItemBuilder(Material.STONE).amount(4).build())
					.addContent(new ItemBuilder(Material.STONE).amount(24).build())
					.addContent(new ItemBuilder(Material.SHIELD).amount(1).build())
					.addContent(new ItemBuilder(Material.STONE).amount(4).build()),

			new ChestPack()
					.addContent(ImprovedItemType.MATRIX.getItem())
					.addContent(ImprovedItemType.BOTAS_MECANICOS.getItem())
					.addContent(ImprovedItemType.HACHA_VIKINGA.getItem())
					.addContent(new ItemBuilder(Material.EGG).amount(12).build())
					.addContent(new ItemBuilder(Material.STONE).amount(8).build())
					.addContent(new ItemBuilder(Material.STONE).amount(20).build())
					.addContent(new ItemBuilder(Material.STONE).amount(4).build()),

	};

	protected static final ChestPack[] MORE_LEGENDARY_CHESTPACKS = new ChestPack[] {

			new ChestPack()
					.addContent(ImprovedItemType.ESPADA_EXTRAÑA.getItem())
					.addContent(ImprovedItemType.PICO_DE_RAIZ.getItem())
					.addContent(LegendaryItemType.COFRE_EXPLOSIVO.getItem())
					.addContent(LegendaryItemType.POCION_DE_LAG.getItem())
					.addContent(new ItemBuilder(Material.STONE).amount(46).build())
					.addContent(new ItemBuilder(Material.STONE).amount(4).build())
					.addContent(new ItemBuilder(Material.SHIELD).amount(1).build())
					.addContent(new ItemBuilder(Material.WATER_BUCKET).amount(1).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(2).build()),

			new ChestPack()
					.addContent(ImprovedItemType.ESPADA_HECHIZADA_II.getItem())
					.addContent(ImprovedItemType.PICO_DE_RAIZ.getItem())
					.addContent(new ItemBuilder(Material.STONE).amount(42).build())
					.addContent(LegendaryItemType.BOMBARDERO.getItem())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(2).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(2).build())
					.addContent(new ItemBuilder(Material.IRON_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).amount(1).build())
					.addContent(new ItemBuilder(Material.CHAINMAIL_BOOTS).enchant(Enchantment.PROTECTION_FIRE, 4).enchant(Enchantment.PROTECTION_FALL, 8).amount(1).build())
					.addContent(new ItemBuilder(Material.STONE).amount(4).build())
					.addContent(new ItemBuilder(Material.STONE).amount(6).build()),

			new ChestPack()
					.addContent(ImprovedItemType.ESPADA_DE_RAIZ.getItem())
					.addContent(ImprovedItemType.PICO_DE_RAIZ.getItem())
					.addContent(LegendaryItemType.TORRETA_CONGELADORA.getItem())
					.addContent(LegendaryItemType.TORRETA_LASER.getItem())
					.addContent(LegendaryItemType.POCION_DE_LAG.getItem())
					.addContent(new ItemBuilder(Material.SHIELD).amount(1).build())
					.addContent(new ItemBuilder(Material.STONE).amount(24).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(2).build())
					.addContent(new ItemBuilder(Material.DIAMOND_BOOTS).enchant(Enchantment.PROTECTION_FIRE, 8).enchant(Enchantment.PROTECTION_FALL, 8).amount(1).build()),

			new ChestPack()
					.addContent(ImprovedItemType.MATRIX.getItem())
					.addContent(LegendaryItemType.BOMBARDERO.getItem())
					.addContent(LegendaryItemType.PUENTE_FUTURISTICO.getItem())
					.addContent(LegendaryItemType.TORRETA_LASER.getItem())
					.addContent(new ItemBuilder(Material.STONE).amount(32).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(4).build())
					.addContent(LegendaryItemType.CASCO_DE_TITAN.getItem()),

			new ChestPack()
					.addContent(ImprovedItemType.MATRIX.getItem())
					.addContent(LegendaryItemType.TORRETA_PORCINA.getItem())
					.addContent(LegendaryItemType.COFRE_EXPLOSIVO.getItem())
					.addContent(LegendaryItemType.PUÑO_DE_JHONCENA.getItem())
					.addContent(new ItemBuilder(Material.STONE).amount(26).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(4).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(2).build())
					.addContent(LegendaryItemType.PETO_TITAN.getItem()),

			new ChestPack()
					.addContent(ImprovedItemType.TNT.getItem())
					.addContent(LegendaryItemType.TORRETA_PORCINA.getItem())
					.addContent(LegendaryItemType.VARITA_MAGICA.getItem())
					.addContent(LegendaryItemType.TORRETA_SANADORA.getItem())
					.addContent(new ItemBuilder(Material.SHIELD).amount(1).build())
					.addContent(new ItemBuilder(Material.STONE).amount(42).build())
					.addContent(new ItemBuilder(Material.DIAMOND_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).amount(1).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(2).build())
					.addContent(LegendaryItemType.PANTALON_TITAN.getItem()),

			new ChestPack()
					.addContent(LegendaryItemType.PUÑO_DE_JHONCENA.getItem())
					.addContent(LegendaryItemType.PUENTE_FUTURISTICO.getItem())
					.addContent(LegendaryItemType.VARITA_MAGICA.getItem())
					.addContent(new ItemBuilder(Material.STONE).amount(32).build())
					.addContent(new ItemBuilder(Material.SHIELD).amount(1).build())
					.addContent(new ItemBuilder(Material.LAVA_BUCKET).amount(1).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(2).build())
					.addContent(LegendaryItemType.BOTAS_TITAN.getItem()),

			new ChestPack()
					.addContent(LegendaryItemType.POCION_DE_LAG.getItem())
					.addContent(LegendaryItemType.POCION_DE_LAG.getItem())
					.addContent(new ItemBuilder(Material.STONE).amount(12).build())
					.addContent(new ItemBuilder(Material.STONE).amount(12).build())
					.addContent(new ItemBuilder(Material.WATER_BUCKET).amount(1).build())
					.addContent(LegendaryItemType.RAYO.getItem())
					.addContent(ImprovedItemType.PALA_DEL_PODER.getItem())
					.addContent(LegendaryItemType.COFRE_EXPLOSIVO.getItem()),

			new ChestPack()
					.addContent(new ItemBuilder(Material.EXP_BOTTLE).amount(10).build())
					.addContent(new ItemBuilder(Material.WATER_BUCKET).amount(1).build())
					.addContent(new ItemBuilder(Material.STONE).amount(12).build())
					.addContent(new ItemBuilder(Material.STONE).amount(12).build())
					.addContent(LegendaryItemType.KRAKEN.getItem())
					.addContent(LegendaryItemType.RAYO.getItem())
					.addContent(ImprovedItemType.TNT.getItem())
					.addContent(LegendaryItemType.TORRETA_LASER.getItem()),

			new ChestPack()
					.addContent(new ItemBuilder(Material.COOKED_BEEF).amount(10).build())
					.addContent(new ItemBuilder(Material.WEB).amount(12).build())
					.addContent(new ItemBuilder(Material.DIAMOND_CHESTPLATE).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).amount(1).build())
					.addContent(new ItemBuilder(Material.STONE).amount(12).build())
					.addContent(LegendaryItemType.COFRE_EXPLOSIVO.getItem())
					.addContent(ImprovedItemType.MATRIX.getItem())
					.addContent(LegendaryItemType.PUÑO_DE_JHONCENA.getItem())
					.addContent(LegendaryItemType.RAYO_CONGELADO.getItem()),

			new ChestPack()
					.addContent(new ItemBuilder(Material.COOKED_MUTTON).amount(10).build())
					.addContent(new ItemBuilder(Material.WEB).amount(12).build())
					.addContent(new ItemBuilder(Material.STONE).amount(32).build())
					.addContent(new ItemBuilder(Material.SHIELD).amount(1).build())
					.addContent(new ItemBuilder(Material.DIAMOND_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).amount(1).build())
					.addContent(ImprovedItemType.PANTALON_ESTELAR.getItem())
					.addContent(LegendaryItemType.COFRE_EXPLOSIVO.getItem())
					.addContent(LegendaryItemType.RAYO_CONGELADO.getItem()),

			new ChestPack()
					.addContent(new ItemBuilder(Material.COOKED_MUTTON).amount(10).build())
					.addContent(new ItemBuilder(Material.DIAMOND_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).amount(1).build())
					.addContent(ImprovedItemType.PECHERA_ESTELAR.getItem())
					.addContent(LegendaryItemType.BOLA_CONGELACEREBROS.getItem()),

			new ChestPack()
					.addContent(new ItemBuilder(Material.ARROW).amount(10).build())
					.addContent(new ItemBuilder(Material.STONE).amount(42).build())
					.addContent(new ItemBuilder(Material.ARROW).amount(10).build())
					.addContent(new ItemBuilder(Material.DIAMOND_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).amount(1).build())
					.addContent(ImprovedItemType.ARCO_PODEROSO.getItem())
					.addContent(LegendaryItemType.RAYO_CONGELADO.getItem())
					.addContent(LegendaryItemType.BOLA_CONGELACEREBROS.getItem()),

			new ChestPack()
					.addContent(new ItemBuilder(Material.ARROW).amount(10).build())
					.addContent(new ItemBuilder(Material.STONE).amount(22).build())
					.addContent(new ItemBuilder(Material.ARROW).amount(8).build())
					.addContent(new ItemBuilder(Material.WATER_BUCKET).amount(1).build())
					.addContent(ImprovedItemType.ARCO_PODEROSO.getItem())
					.addContent(LegendaryItemType.KRAKEN.getItem())
					.addContent(LegendaryItemType.VARITA_MAGICA.getItem()),

			new ChestPack()
					.addContent(new ItemBuilder(Material.WOOD).amount(12).build())
					.addContent(new ItemBuilder(Material.WOOD).amount(8).build())
					.addContent(new ItemBuilder(Material.SHIELD).amount(1).build())
					.addContent(ImprovedItemType.PECHERA_FENIX.getItem())
					.addContent(ImprovedItemType.PANTALON_FENIX.getItem())
					.addContent(LegendaryItemType.PUENTE_FUTURISTICO.getItem())
					.addContent(LegendaryItemType.METEORITO.getItem()),

	};
	
	public static ChestPack[] getMoreZPackages() {
		return MORE_LEGENDARY_CHESTPACKS;
	}

	public static ChestPack[] getMoreInsanePackages() {
		return MORE_INSANE_CHESTPACKS;
	}
	
}
