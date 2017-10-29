package net.omniblock.skywars.patch.managers.chest.presets;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import net.omniblock.skywars.patch.managers.chest.defaults.type.ImprovedItemType;
import net.omniblock.skywars.patch.managers.chest.defaults.type.LegendaryItemType;
import net.omniblock.skywars.patch.managers.chest.object.ChestPack;
import net.omniblock.skywars.util.ItemBuilder;

public class ArmorPresets {

	protected static final ChestPack[] NORMAL_CHESTPACKS = new ChestPack[] {

			new ChestPack().addContent(new ItemBuilder(Material.DIAMOND_SWORD).amount(1).build())
					.addContent(new ItemBuilder(Material.EXP_BOTTLE).amount(4).build())
					.addContent(new ItemBuilder(Material.GOLD_AXE).amount(1).build())
					.addContent(new ItemBuilder(Material.LEATHER_LEGGINGS).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.IRON_SWORD).amount(1).build())
					.addContent(new ItemBuilder(Material.EXP_BOTTLE).amount(6).build())
					.addContent(new ItemBuilder(Material.STONE_SWORD).amount(1).build())
					.addContent(new ItemBuilder(Material.CHAINMAIL_LEGGINGS).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.IRON_SWORD).amount(1).build())
					.addContent(new ItemBuilder(Material.LAVA_BUCKET).amount(1).build())
					.addContent(new ItemBuilder(Material.STONE_SWORD).amount(1).build())
					.addContent(new ItemBuilder(Material.LEATHER_LEGGINGS)
							.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.BOW).amount(1).build())
					.addContent(new ItemBuilder(Material.ARROW).amount(12).build())
					.addContent(new ItemBuilder(Material.LAVA_BUCKET).amount(1).build())
					.addContent(
							new ItemBuilder(Material.WOOD_SWORD).enchant(Enchantment.DAMAGE_ALL, 1).amount(1).build())
					.addContent(new ItemBuilder(Material.LEATHER_LEGGINGS).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.BOW).amount(1).build())
					.addContent(new ItemBuilder(Material.ARROW).amount(8).build())
					.addContent(ImprovedItemType.TNT.getItem())
					.addContent(new ItemBuilder(Material.CHAINMAIL_LEGGINGS).amount(1).build())
					.addContent(new ItemBuilder(Material.LEATHER_BOOTS).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.STONE_SWORD).amount(1).build())
					.addContent(
							new ItemBuilder(Material.WOOD_SWORD).enchant(Enchantment.FIRE_ASPECT, 1).amount(1).build())
					.addContent(new ItemBuilder(Material.CHAINMAIL_LEGGINGS).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.IRON_SWORD).amount(1).build())
					.addContent(new ItemBuilder(Material.WOOD_SWORD).amount(1).build())
					.addContent(new ItemBuilder(Material.GOLD_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
							.amount(1).build())
					.addContent(new ItemBuilder(Material.GOLD_BOOTS).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.IRON_SWORD).amount(1).build())
					.addContent(new ItemBuilder(Material.WOOD_SWORD).amount(1).build())
					.addContent(new ItemBuilder(Material.GOLD_LEGGINGS).enchant(Enchantment.PROTECTION_PROJECTILE, 2)
							.amount(1).build())
					.addContent(new ItemBuilder(Material.IRON_BOOTS).amount(1).build()),

			new ChestPack()
					.addContent(
							new ItemBuilder(Material.STONE_SWORD).enchant(Enchantment.DAMAGE_ALL, 1).amount(16).build())
					.addContent(new ItemBuilder(Material.GOLD_AXE).enchant(Enchantment.DAMAGE_ALL, 1).amount(1).build())
					.addContent(new ItemBuilder(Material.IRON_LEGGINGS).amount(1).build()),

	};

	protected static final ChestPack[] INSANE_CHESTPACKS = new ChestPack[] {

			new ChestPack()
					.addContent(
							new ItemBuilder(Material.IRON_SWORD).enchant(Enchantment.DAMAGE_ALL, 2).amount(1).build())
					.addContent(new ItemBuilder(Material.EXP_BOTTLE).amount(8).build())
					.addContent(new ItemBuilder(Material.DIAMOND_BLOCK).amount(1).build())
					.addContent(new ItemBuilder(Material.WORKBENCH).amount(1).build()),

			new ChestPack()
					.addContent(
							new ItemBuilder(Material.IRON_SWORD).enchant(Enchantment.DAMAGE_ALL, 1).amount(1).build())
					.addContent(new ItemBuilder(Material.STONE_SWORD).enchant(Enchantment.DURABILITY, 1)
							.enchant(Enchantment.FIRE_ASPECT, 2).amount(1).build())
					.addContent(new ItemBuilder(Material.FLINT_AND_STEEL).amount(1).build())
					.addContent(new ItemBuilder(Material.CHAINMAIL_LEGGINGS)
							.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).amount(1).build()),

			new ChestPack()
					.addContent(
							new ItemBuilder(Material.IRON_SWORD).enchant(Enchantment.DAMAGE_ALL, 2).amount(1).build())
					.addContent(
							new ItemBuilder(Material.WOOD_SWORD).enchant(Enchantment.DAMAGE_ALL, 4).amount(1).build())
					.addContent(new ItemBuilder(Material.SNOW_BALL).amount(6).build())
					.addContent(new ItemBuilder(Material.CHAINMAIL_LEGGINGS)
							.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).amount(1).build()),

			new ChestPack()
					.addContent(new ItemBuilder(Material.BOW).enchant(Enchantment.ARROW_DAMAGE, 2).amount(1).build())
					.addContent(new ItemBuilder(Material.ARROW).amount(16).build())
					.addContent(new ItemBuilder(Material.GOLD_SWORD).amount(1).build())
					.addContent(new ItemBuilder(Material.GOLD_LEGGINGS).amount(1).build())
					.addContent(new ItemBuilder(Material.IRON_LEGGINGS).amount(1).build()),

			new ChestPack()
					.addContent(new ItemBuilder(Material.BOW).enchant(Enchantment.ARROW_DAMAGE, 2).amount(1).build())
					.addContent(new ItemBuilder(Material.ARROW).amount(12).build())
					.addContent(
							new ItemBuilder(Material.IRON_SWORD).enchant(Enchantment.DURABILITY, 1).amount(1).build())
					.addContent(new ItemBuilder(Material.IRON_LEGGINGS).amount(1).build())
					.addContent(new ItemBuilder(Material.CHAINMAIL_BOOTS).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.BOW).amount(1).build())
					.addContent(new ItemBuilder(Material.ARROW).amount(18).build())
					.addContent(
							new ItemBuilder(Material.IRON_SWORD).enchant(Enchantment.DURABILITY, 2).amount(1).build())
					.addContent(new ItemBuilder(Material.CHAINMAIL_LEGGINGS).amount(1).build())
					.addContent(new ItemBuilder(Material.CHAINMAIL_BOOTS).amount(1).build()),

			new ChestPack()
					.addContent(new ItemBuilder(Material.IRON_AXE).enchant(Enchantment.DAMAGE_ALL, 2).amount(1).build())
					.addContent(ImprovedItemType.TNT.getItem()).addContent(ImprovedItemType.TNT.getItem())
					.addContent(new ItemBuilder(Material.DIAMOND_LEGGINGS).amount(1).build())
					.addContent(new ItemBuilder(Material.CHAINMAIL_BOOTS).amount(1).build()),

			new ChestPack()
					.addContent(
							new ItemBuilder(Material.IRON_SWORD).enchant(Enchantment.DAMAGE_ALL, 2).amount(1).build())
					.addContent(new ItemBuilder(Material.LAVA_BUCKET).amount(1).build())
					.addContent(new ItemBuilder(Material.DIAMOND_LEGGINGS).amount(1).build())
					.addContent(new ItemBuilder(Material.IRON_BOOTS).amount(1).build()),

			new ChestPack()
					.addContent(
							new ItemBuilder(Material.IRON_SWORD).enchant(Enchantment.DURABILITY, 2).amount(1).build())
					.addContent(new ItemBuilder(Material.IRON_LEGGINGS).amount(1).build())
					.addContent(new ItemBuilder(Material.CHAINMAIL_BOOTS).amount(1).build())
					.addContent(new ItemBuilder(Material.IRON_BOOTS).amount(1).build()),

			new ChestPack()
					.addContent(new ItemBuilder(Material.DIAMOND_SWORD).enchant(Enchantment.FIRE_ASPECT, 1)
							.enchant(Enchantment.DURABILITY, 2).amount(1).build())
					.addContent(new ItemBuilder(Material.DIAMOND_LEGGINGS).amount(1).build())
					.addContent(new ItemBuilder(Material.CHAINMAIL_BOOTS)
							.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).amount(1).build())
					.addContent(new ItemBuilder(Material.DIAMOND_BOOTS).amount(1).build()),

			new ChestPack().addContent(ImprovedItemType.ARCO_PODEROSO.getItem())
					.addContent(new ItemBuilder(Material.ARROW).amount(4).build())
					.addContent(new ItemBuilder(Material.ARROW).amount(4).build())
					.addContent(new ItemBuilder(Material.GOLD_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
							.amount(1).build())
					.addContent(new ItemBuilder(Material.CHAINMAIL_BOOTS)
							.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).amount(1).build()),

			new ChestPack().addContent(ImprovedItemType.ESPADA_HECHIZADA_II.getItem())
					.addContent(new ItemBuilder(Material.GOLD_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4)
							.amount(1).build())
					.addContent(new ItemBuilder(Material.CHAINMAIL_BOOTS)
							.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).amount(1).build()),

			new ChestPack().addContent(ImprovedItemType.ESPADA_HECHIZADA.getItem())
					.addContent(new ItemBuilder(Material.GOLD_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
							.amount(1).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(4).build())
					.addContent(new ItemBuilder(Material.CHAINMAIL_BOOTS)
							.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).amount(1).build()),

	};

	protected static final ChestPack[] LEGENDARY_CHESTPACKS = new ChestPack[] {

			new ChestPack().addContent(ImprovedItemType.ESPADA_EXTRAÑA.getItem())
					.addContent(LegendaryItemType.RAYO.getItem())
					.addContent(new ItemBuilder(Material.EXP_BOTTLE).amount(18).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(4).build())
					.addContent(new ItemBuilder(Material.GOLD_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
							.amount(1).build())
					.addContent(new ItemBuilder(Material.STONE).amount(4).build()),

			new ChestPack().addContent(ImprovedItemType.ESPADA_X.getItem())
					.addContent(LegendaryItemType.RAYO_CONGELADO.getItem())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(2).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(2).build())
					.addContent(new ItemBuilder(Material.IRON_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
							.amount(1).build())
					.addContent(new ItemBuilder(Material.CHAINMAIL_BOOTS).enchant(Enchantment.PROTECTION_FALL, 2)
							.amount(1).build())
					.addContent(new ItemBuilder(Material.STONE).amount(4).build())
					.addContent(new ItemBuilder(Material.STONE).amount(6).build()),

			new ChestPack()
					.addContent(new ItemBuilder(Material.DIAMOND_SWORD).enchant(Enchantment.DAMAGE_ALL, 2)
							.enchant(Enchantment.DURABILITY, 2).amount(1).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(2).build())
					.addContent(new ItemBuilder(Material.IRON_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
							.amount(1).build())
					.addContent(new ItemBuilder(Material.CHAINMAIL_BOOTS).enchant(Enchantment.PROTECTION_FALL, 2)
							.amount(1).build())
					.addContent(new ItemBuilder(Material.STONE).amount(4).build())
					.addContent(new ItemBuilder(Material.STONE).amount(6).build()),

			new ChestPack()
					.addContent(new ItemBuilder(Material.BOW).enchant(Enchantment.ARROW_DAMAGE, 4).amount(1).build())
					.addContent(new ItemBuilder(Material.ARROW).amount(24).build())
					.addContent(new ItemBuilder(Material.GOLD_SWORD).enchant(Enchantment.FIRE_ASPECT, 2)
							.enchant(Enchantment.DAMAGE_ALL, 1).amount(1).build())
					.addContent(new ItemBuilder(Material.GOLD_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5)
							.amount(1).build())
					.addContent(new ItemBuilder(Material.IRON_LEGGINGS).enchant(Enchantment.PROTECTION_EXPLOSIONS, 5)
							.amount(1).build()),

			new ChestPack()
					.addContent(new ItemBuilder(Material.BOW).enchant(Enchantment.ARROW_DAMAGE, 4).amount(1).build())
					.addContent(new ItemBuilder(Material.ARROW).amount(24).build())
					.addContent(new ItemBuilder(Material.GOLD_SWORD).enchant(Enchantment.FIRE_ASPECT, 2)
							.enchant(Enchantment.DAMAGE_ALL, 1).amount(1).build())
					.addContent(new ItemBuilder(Material.DIAMOND_LEGGINGS)
							.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).amount(1).build())
					.addContent(new ItemBuilder(Material.IRON_LEGGINGS).enchant(Enchantment.PROTECTION_EXPLOSIONS, 4)
							.amount(1).build()),

			new ChestPack()
					.addContent(new ItemBuilder(Material.IRON_SWORD).enchant(Enchantment.DAMAGE_ALL, 2)
							.enchant(Enchantment.DURABILITY, 2).amount(1).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(4).build())
					.addContent(new ItemBuilder(Material.IRON_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5)
							.amount(1).build())
					.addContent(new ItemBuilder(Material.DIAMOND_BOOTS).enchant(Enchantment.PROTECTION_FALL, 4)
							.amount(1).build())
					.addContent(new ItemBuilder(Material.STONE).amount(12).build()),

			new ChestPack().addContent(LegendaryItemType.PANTALON_TITAN.getItem())
					.addContent(new ItemBuilder(Material.DIAMOND_LEGGINGS)
							.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).amount(1).build())
					.addContent(new ItemBuilder(Material.EGG).amount(12).build())
					.addContent(new ItemBuilder(Material.STONE).amount(6).build()),

			new ChestPack().addContent(LegendaryItemType.BOTAS_TITAN.getItem())
					.addContent(new ItemBuilder(Material.DIAMOND_BOOTS).enchant(Enchantment.DURABILITY, 2)
							.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).amount(1).build())
					.addContent(new ItemBuilder(Material.IRON_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
							.amount(1).build())
					.addContent(new ItemBuilder(Material.STONE).amount(6).build()),

			new ChestPack().addContent(ImprovedItemType.HACHA_DEL_DRAGON.getItem())
					.addContent(new ItemBuilder(Material.DIAMOND_BOOTS).enchant(Enchantment.DURABILITY, 2)
							.enchant(Enchantment.PROTECTION_FALL, 8).amount(1).build())
					.addContent(new ItemBuilder(Material.STONE).amount(4).build())
					.addContent(new ItemBuilder(Material.STONE).amount(6).build()),

			new ChestPack()
					.addContent(new ItemBuilder(Material.DIAMOND_SWORD).enchant(Enchantment.FIRE_ASPECT, 1)
							.enchant(Enchantment.DURABILITY, 2).amount(1).build())
					.addContent(new ItemBuilder(Material.DIAMOND_LEGGINGS).amount(1).build())
					.addContent(new ItemBuilder(Material.CHAINMAIL_BOOTS)
							.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).amount(1).build())
					.addContent(new ItemBuilder(Material.DIAMOND_BOOTS).amount(1).build()),

			new ChestPack().addContent(ImprovedItemType.ARCO_PODEROSO.getItem())
					.addContent(new ItemBuilder(Material.ARROW).amount(4).build())
					.addContent(new ItemBuilder(Material.ARROW).amount(6).build())
					.addContent(new ItemBuilder(Material.DIAMOND_LEGGINGS)
							.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).amount(1).build())
					.addContent(new ItemBuilder(Material.DIAMOND_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2)
							.amount(1).build()),

			new ChestPack()
					.addContent(new ItemBuilder(Material.DIAMOND_SWORD).enchant(Enchantment.FIRE_ASPECT, 1)
							.enchant(Enchantment.DURABILITY, 2).amount(1).build())
					.addContent(new ItemBuilder(Material.CHAINMAIL_LEGGINGS).amount(1).build())
					.addContent(new ItemBuilder(Material.CHAINMAIL_BOOTS)
							.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).amount(1).build())
					.addContent(ImprovedItemType.BOTAS_MECANICOS.getItem()),

			new ChestPack().addContent(ImprovedItemType.OZ_DEL_DRAGON.getItem())
					.addContent(new ItemBuilder(Material.STONE).amount(12).build())
					.addContent(new ItemBuilder(Material.CHAINMAIL_BOOTS)
							.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).amount(1).build())
					.addContent(ImprovedItemType.BOTAS_MECANICOS.getItem()),

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