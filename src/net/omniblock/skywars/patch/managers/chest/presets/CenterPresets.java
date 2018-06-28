package net.omniblock.skywars.patch.managers.chest.presets;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import net.omniblock.skywars.patch.managers.chest.defaults.type.ImprovedItemType;
import net.omniblock.skywars.patch.managers.chest.defaults.type.LegendaryItemType;
import net.omniblock.skywars.patch.managers.chest.object.ChestPack;
import net.omniblock.skywars.util.ItemBuilder;

public class CenterPresets {

	protected static final ChestPack[] NORMAL_CHESTPACKS = new ChestPack[] {

			new ChestPack()
					.addContent(
							new ItemBuilder(Material.IRON_SWORD).enchant(Enchantment.FIRE_ASPECT, 2).amount(1).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(1).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(1).build())
					.addContent(new ItemBuilder(Material.SHIELD).amount(1).build())
					.addContent(new ItemBuilder(Material.GOLD_AXE).enchant(Enchantment.DAMAGE_ALL, 1).amount(1).build())
					.addContent(new ItemBuilder(Material.CHAINMAIL_LEGGINGS).enchant(Enchantment.DURABILITY, 2)
							.enchant(Enchantment.PROTECTION_PROJECTILE, 1).amount(1).build()),

			new ChestPack()
					.addContent(new ItemBuilder(Material.DIAMOND_SWORD).enchant(Enchantment.DURABILITY, 2).amount(1)
							.build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(1).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(1).build())
					.addContent(new ItemBuilder(Material.ARROW, 10).amount(1).build())
					.addContent(new ItemBuilder(Material.ARROW, 8).amount(1).build()).addContent(
							new ItemBuilder(Material.FISHING_ROD).enchant(Enchantment.KNOCKBACK, 1).amount(1).build()),

			new ChestPack()
					.addContent(new ItemBuilder(Material.IRON_CHESTPLATE)
							.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).amount(1).build())
					.addContent(new ItemBuilder(Material.ARROW, 8).amount(1).build())
					.addContent(new ItemBuilder(Material.FLINT_AND_STEEL).amount(1).build())
					.addContent(new ItemBuilder(Material.COOKED_BEEF).amount(8).build()),

			new ChestPack()
					.addContent(new ItemBuilder(Material.IRON_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
							.enchant(Enchantment.PROTECTION_FALL, 2).amount(1).build())
					.addContent(new ItemBuilder(Material.EGG).amount(8).build())
					.addContent(new ItemBuilder(Material.EGG).amount(8).build())
					.addContent(new ItemBuilder(Material.FLINT_AND_STEEL).amount(1).build())
					.addContent(new ItemBuilder(Material.COOKED_BEEF).amount(8).build()),

			new ChestPack()
					.addContent(new ItemBuilder(Material.BOW).enchant(Enchantment.ARROW_KNOCKBACK, 1).amount(1).build())
					.addContent(new ItemBuilder(Material.ARROW).amount(8).build())
					.addContent(new ItemBuilder(Material.ARROW).amount(6).build())
					.addContent(ImprovedItemType.TNT.getItem()).addContent(ImprovedItemType.TNT.getItem()),

			new ChestPack()
					.addContent(
							new ItemBuilder(Material.STONE_SWORD).enchant(Enchantment.DAMAGE_ALL, 2).amount(1).build())
					.addContent(
							new ItemBuilder(Material.WOOD_SWORD).enchant(Enchantment.KNOCKBACK, 1).amount(1).build())
					.addContent(new ItemBuilder(Material.CHAINMAIL_LEGGINGS).amount(1).build())
					.addContent(new ItemBuilder(Material.SHIELD).amount(1).build())
					.addContent(new ItemBuilder(Material.DIAMOND_HELMET).amount(1).build()),

			new ChestPack()
					.addContent(
							new ItemBuilder(Material.STONE_SWORD).enchant(Enchantment.DAMAGE_ALL, 2).amount(1).build())
					.addContent(new ItemBuilder(Material.CARROT).amount(10).build())
					.addContent(new ItemBuilder(Material.DIAMOND_LEGGINGS).amount(1).build())
					.addContent(new ItemBuilder(Material.DIAMOND_HELMET).amount(1).build()),

			new ChestPack()
					.addContent(
							new ItemBuilder(Material.IRON_SWORD).enchant(Enchantment.DAMAGE_ALL, 2).amount(1).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(2).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(1).build())
					.addContent(new ItemBuilder(Material.DIAMOND_CHESTPLATE)
							.enchant(Enchantment.PROTECTION_PROJECTILE, 2).amount(1).build())
					.addContent(new ItemBuilder(Material.DIAMOND_HELMET).amount(1).build()),

			new ChestPack()
					.addContent(
							new ItemBuilder(Material.DIAMOND_AXE).enchant(Enchantment.DAMAGE_ALL, 1).amount(1).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(2).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(1).build())
					.addContent(new ItemBuilder(Material.DIAMOND_BOOTS).enchant(Enchantment.PROTECTION_FALL, 2)
							.amount(1).build()),

	};

	protected static final ChestPack[] INSANE_CHESTPACKS = new ChestPack[] {

			new ChestPack()
					.addContent(
							new ItemBuilder(Material.IRON_SWORD).enchant(Enchantment.DAMAGE_ALL, 4).amount(1).build())
					.addContent(new ItemBuilder(Material.EXP_BOTTLE).amount(18).build())
					.addContent(ImprovedItemType.PALA_DEL_PODER.getItem())
					.addContent(new ItemBuilder(Material.SHIELD).amount(1).build())
					.addContent(ImprovedItemType.BOTAS_FENIX.getItem()),

			new ChestPack()
					.addContent(new ItemBuilder(Material.DIAMOND_SWORD).enchant(Enchantment.DAMAGE_ALL, 2).amount(1)
							.build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(2).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(2).build())
					.addContent(new ItemBuilder(Material.IRON_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3)
							.amount(1).build())
					.addContent(ImprovedItemType.TNT.getItem()),

			new ChestPack()
					.addContent(new ItemBuilder(Material.IRON_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
							.amount(1).build())
					.addContent(new ItemBuilder(Material.APPLE).amount(1).build())
					.addContent(new ItemBuilder(Material.GOLD_BLOCK).amount(7).build())
					.addContent(new ItemBuilder(Material.GOLD_INGOT).amount(9).build())
					.addContent(new ItemBuilder(Material.WORKBENCH).amount(1).build())
					.addContent(new ItemBuilder(Material.DIAMOND_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
							.amount(1).build())
					.addContent(ImprovedItemType.BOTAS_FUGAZ.getItem()),

			new ChestPack()
					.addContent(new ItemBuilder(Material.DIAMOND_CHESTPLATE).enchant(Enchantment.PROTECTION_FIRE, 4)
							.amount(1).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(4).build())
					.addContent(new ItemBuilder(Material.COOKIE).amount(4).build())
					.addContent(new ItemBuilder(Material.SHIELD).amount(1).build())
					.addContent(new ItemBuilder(Material.DIAMOND_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
							.amount(1).build())
					.addContent(ImprovedItemType.BOTAS_FENIX.getItem()),

			new ChestPack()
					.addContent(new ItemBuilder(Material.BOW).enchant(Enchantment.ARROW_DAMAGE, 4).amount(1).build())
					.addContent(new ItemBuilder(Material.ARROW).amount(12).build())
					.addContent(new ItemBuilder(Material.DIAMOND_SWORD).enchant(Enchantment.DURABILITY, 4).amount(1)
							.build())
					.addContent(ImprovedItemType.PICO_DE_RAIZ.getItem())
					.addContent(new ItemBuilder(Material.CHAINMAIL_BOOTS).amount(1).build()),

			new ChestPack()
					.addContent(new ItemBuilder(Material.BOW).enchant(Enchantment.ARROW_DAMAGE, 2)
							.enchant(Enchantment.DURABILITY, 2).amount(1).build())
					.addContent(new ItemBuilder(Material.ARROW).amount(6).build())
					.addContent(new ItemBuilder(Material.ARROW).amount(4).build())
					.addContent(new ItemBuilder(Material.IRON_AXE).enchant(Enchantment.DAMAGE_ALL, 4)
							.enchant(Enchantment.DURABILITY, 4).amount(1).build())
					.addContent(ImprovedItemType.ESPADA_EXTRAÑA.getItem()),

			new ChestPack().addContent(new ItemBuilder(Material.ARROW).amount(12).build())
					.addContent(new ItemBuilder(Material.ARROW).amount(8).build())
					.addContent(new ItemBuilder(Material.DIAMOND_CHESTPLATE)
							.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).enchant(Enchantment.DURABILITY, 4)
							.amount(1).build())
					.addContent(ImprovedItemType.ESPADA_X.getItem()),

			new ChestPack().addContent(new ItemBuilder(Material.EXP_BOTTLE).amount(12).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(2).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(2).build())
					.addContent(new ItemBuilder(Material.DIAMOND_CHESTPLATE)
							.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).enchant(Enchantment.DURABILITY, 2)
							.amount(1).build())
					.addContent(
							new ItemBuilder(Material.DIAMOND_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
									.enchant(Enchantment.DURABILITY, 2).amount(1).build())
					.addContent(ImprovedItemType.ESPADA_DE_RAIZ.getItem()),

			new ChestPack().addContent(new ItemBuilder(Material.EXP_BOTTLE).amount(12).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(2).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(2).build())
					.addContent(
							new ItemBuilder(Material.DIAMOND_HELMET).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
									.enchant(Enchantment.DURABILITY, 2).amount(1).build())
					.addContent(new ItemBuilder(Material.DIAMOND_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
							.enchant(Enchantment.DURABILITY, 2).amount(1).build())
					.addContent(ImprovedItemType.ESPADA_X.getItem()),

			new ChestPack().addContent(new ItemBuilder(Material.LAVA_BUCKET).amount(1).build())
					.addContent(new ItemBuilder(Material.WATER_BUCKET).amount(1).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(2).build())
					.addContent(new ItemBuilder(Material.GOLD_AXE).enchant(Enchantment.FIRE_ASPECT, 2)
							.enchant(Enchantment.DURABILITY, 2).amount(1).build()),

			new ChestPack().addContent(ImprovedItemType.ARCO_PODEROSO.getItem())
					.addContent(new ItemBuilder(Material.ARROW).amount(4).build())
					.addContent(new ItemBuilder(Material.ARROW).amount(12).build())
					.addContent(new ItemBuilder(Material.STONE).amount(12).build()),

			new ChestPack().addContent(ImprovedItemType.MATRIX.getItem())
					.addContent(new ItemBuilder(Material.EGG).amount(12).build())
					.addContent(new ItemBuilder(Material.STONE).amount(4).build())
					.addContent(new ItemBuilder(Material.STONE).amount(4).build()),

			new ChestPack().addContent(ImprovedItemType.MATRIX.getItem())
					.addContent(new ItemBuilder(Material.EGG).amount(12).build())
					.addContent(new ItemBuilder(Material.STONE).amount(8).build())
					.addContent(new ItemBuilder(Material.STONE).amount(4).build()),

	};

	protected static final ChestPack[] LEGENDARY_CHESTPACKS = new ChestPack[] {

			new ChestPack().addContent(ImprovedItemType.ESPADA_EXTRAÑA.getItem())
					.addContent(LegendaryItemType.COFRE_EXPLOSIVO.getItem())
					.addContent(new ItemBuilder(Material.STONE).amount(4).build())
					.addContent(new ItemBuilder(Material.SHIELD).amount(1).build())
					.addContent(new ItemBuilder(Material.WATER_BUCKET).amount(1).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(2).build()),

			new ChestPack().addContent(ImprovedItemType.ESPADA_HECHIZADA_II.getItem())
					.addContent(LegendaryItemType.BOMBARDERO.getItem())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(2).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(2).build())
					.addContent(new ItemBuilder(Material.IRON_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3)
							.amount(1).build())
					.addContent(new ItemBuilder(Material.CHAINMAIL_BOOTS).enchant(Enchantment.PROTECTION_FIRE, 3)
							.enchant(Enchantment.PROTECTION_FALL, 8).amount(1).build())
					.addContent(new ItemBuilder(Material.STONE).amount(4).build())
					.addContent(new ItemBuilder(Material.STONE).amount(6).build()),

			new ChestPack().addContent(ImprovedItemType.ESPADA_DE_RAIZ.getItem())
					.addContent(LegendaryItemType.TORRETA_CONGELADORA.getItem())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(2).build())
					.addContent(new ItemBuilder(Material.DIAMOND_BOOTS).enchant(Enchantment.PROTECTION_FIRE, 1)
							.enchant(Enchantment.PROTECTION_FALL, 8).amount(1).build()),

			new ChestPack().addContent(ImprovedItemType.MATRIX.getItem())
					.addContent(LegendaryItemType.TORRETA_LASER.getItem())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(4).build())
					.addContent(LegendaryItemType.CASCO_DE_TITAN.getItem()),

			new ChestPack().addContent(ImprovedItemType.MATRIX.getItem())
					.addContent(LegendaryItemType.TORRETA_PORCINA.getItem())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(2).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(2).build())
					.addContent(LegendaryItemType.PETO_TITAN.getItem()),

			new ChestPack().addContent(ImprovedItemType.TNT.getItem())
					.addContent(LegendaryItemType.TORRETA_SANADORA.getItem())
					.addContent(new ItemBuilder(Material.DIAMOND_CHESTPLATE)
							.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).amount(1).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(2).build())
					.addContent(LegendaryItemType.PANTALON_TITAN.getItem()),

			new ChestPack().addContent(LegendaryItemType.PUÑO_DE_JHONCENA.getItem())
					.addContent(LegendaryItemType.PUENTE_FUTURISTICO.getItem())
					.addContent(new ItemBuilder(Material.LAVA_BUCKET).amount(1).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(2).build())
					.addContent(LegendaryItemType.BOTAS_TITAN.getItem()),

			new ChestPack().addContent(LegendaryItemType.POCION_DE_LAG.getItem())
					.addContent(LegendaryItemType.POCION_DE_LAG.getItem())
					.addContent(new ItemBuilder(Material.WATER_BUCKET).amount(1).build())
					.addContent(LegendaryItemType.RAYO.getItem()),

			new ChestPack().addContent(new ItemBuilder(Material.EXP_BOTTLE).amount(10).build())
					.addContent(new ItemBuilder(Material.WATER_BUCKET).amount(1).build())
					.addContent(new ItemBuilder(Material.STONE).amount(12).build())
					.addContent(new ItemBuilder(Material.STONE).amount(12).build())
					.addContent(LegendaryItemType.RAYO.getItem()),

			new ChestPack().addContent(new ItemBuilder(Material.COOKED_BEEF).amount(10).build())
					.addContent(new ItemBuilder(Material.WEB).amount(12).build())
					.addContent(new ItemBuilder(Material.STONE).amount(12).build())
					.addContent(LegendaryItemType.RAYO_CONGELADO.getItem()),

			new ChestPack().addContent(new ItemBuilder(Material.COOKED_MUTTON).amount(10).build())
					.addContent(new ItemBuilder(Material.WEB).amount(12).build())
					.addContent(new ItemBuilder(Material.STONE).amount(12).build())
					.addContent(ImprovedItemType.PANTALON_ESTELAR.getItem())
					.addContent(LegendaryItemType.RAYO_CONGELADO.getItem()),

			new ChestPack().addContent(new ItemBuilder(Material.COOKED_MUTTON).amount(10).build())
					.addContent(new ItemBuilder(Material.DIAMOND_LEGGINGS)
							.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).amount(12).build())
					.addContent(ImprovedItemType.PECHERA_ESTELAR.getItem())
					.addContent(LegendaryItemType.BOLA_CONGELACEREBROS.getItem()),

			new ChestPack().addContent(new ItemBuilder(Material.ARROW).amount(10).build())
					.addContent(new ItemBuilder(Material.ARROW).amount(10).build())
					.addContent(new ItemBuilder(Material.DIAMOND_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
							.amount(12).build())
					.addContent(ImprovedItemType.ARCO_PODEROSO.getItem())
					.addContent(LegendaryItemType.BOLA_CONGELACEREBROS.getItem()),

			new ChestPack().addContent(new ItemBuilder(Material.ARROW).amount(10).build())
					.addContent(new ItemBuilder(Material.ARROW).amount(8).build())
					.addContent(new ItemBuilder(Material.WATER_BUCKET).amount(1).build())
					.addContent(new ItemBuilder(Material.SHIELD).amount(1).build())
					.addContent(ImprovedItemType.ARCO_PODEROSO.getItem())
					.addContent(LegendaryItemType.VARITA_MAGICA.getItem()),

			new ChestPack().addContent(new ItemBuilder(Material.WOOD).amount(12).build())
					.addContent(new ItemBuilder(Material.WOOD).amount(8).build())
					.addContent(ImprovedItemType.PECHERA_FENIX.getItem())
					.addContent(ImprovedItemType.PANTALON_FENIX.getItem())
					.addContent(LegendaryItemType.METEORITO.getItem()),

	};

	public static ChestPack[] getNormalPackages() {
		return NORMAL_CHESTPACKS;
	}

	public static ChestPack[] getInsanePackages() {
		return INSANE_CHESTPACKS;
	}

	public static ChestPack[] getZPackages() {
		return LEGENDARY_CHESTPACKS;
	}

}