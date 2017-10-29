package net.omniblock.skywars.patch.managers.chest.presets;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import net.omniblock.skywars.patch.managers.chest.defaults.type.ImprovedItemType;
import net.omniblock.skywars.patch.managers.chest.defaults.type.LegendaryItemType;
import net.omniblock.skywars.patch.managers.chest.object.ChestPack;
import net.omniblock.skywars.util.ItemBuilder;

public class BlockPresets {

	protected static final ChestPack[] NORMAL_CHESTPACKS = new ChestPack[] {

			new ChestPack().addContent(new ItemBuilder(Material.STONE).amount(8).build())
					.addContent(new ItemBuilder(Material.STONE).amount(8).build())
					.addContent(new ItemBuilder(Material.IRON_CHESTPLATE).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.STONE).amount(12).build())
					.addContent(new ItemBuilder(Material.STONE).amount(8).build())
					.addContent(new ItemBuilder(Material.ARROW).amount(6).build())
					.addContent(new ItemBuilder(Material.BOW).amount(1).build())
					.addContent(new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.WOOD).amount(16).build())
					.addContent(new ItemBuilder(Material.BRICK).amount(4).build())
					.addContent(new ItemBuilder(Material.LEATHER_CHESTPLATE)
							.enchant(Enchantment.PROTECTION_PROJECTILE, 2).amount(1).build())
					.addContent(new ItemBuilder(Material.FLINT_AND_STEEL).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.STONE).amount(16).build())
					.addContent(new ItemBuilder(Material.LOG).amount(3).build())
					.addContent(new ItemBuilder(Material.LOG).amount(3).build())
					.addContent(new ItemBuilder(Material.LEATHER_CHESTPLATE)
							.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.WOOD).amount(12).build())
					.addContent(new ItemBuilder(Material.BRICK).amount(6).build())
					.addContent(new ItemBuilder(Material.CHAINMAIL_CHESTPLATE)
							.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).amount(1).build())
					.addContent(new ItemBuilder(Material.DIAMOND).amount(2).build())
					.addContent(new ItemBuilder(Material.STICK).amount(1).build())
					.addContent(new ItemBuilder(Material.LOG).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.STONE).amount(12).build())
					.addContent(new ItemBuilder(Material.BRICK).amount(6).build())
					.addContent(new ItemBuilder(Material.DIAMOND_CHESTPLATE).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.WOOD).amount(16).build())
					.addContent(new ItemBuilder(Material.IRON_AXE).amount(1).build())
					.addContent(new ItemBuilder(Material.GOLD_CHESTPLATE).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.STONE).amount(12).build())
					.addContent(new ItemBuilder(Material.BRICK).amount(6).build())
					.addContent(new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).amount(1).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.STONE).amount(16).build())
					.addContent(
							new ItemBuilder(Material.IRON_PICKAXE).enchant(Enchantment.DIG_SPEED, 2).amount(1).build())
					.addContent(new ItemBuilder(Material.LEATHER_CHESTPLATE).amount(1).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(1).build()),

	};

	protected static final ChestPack[] INSANE_CHESTPACKS = new ChestPack[] {

			new ChestPack().addContent(new ItemBuilder(Material.STONE).amount(16).build())
					.addContent(new ItemBuilder(Material.STONE).amount(4).build())
					.addContent(
							new ItemBuilder(Material.IRON_PICKAXE).enchant(Enchantment.DIG_SPEED, 2).amount(1).build())
					.addContent(ImprovedItemType.PETO_BRILLANTE.getItem()),

			new ChestPack().addContent(new ItemBuilder(Material.WOOD).amount(12).build())
					.addContent(new ItemBuilder(Material.WOOD).amount(6).build())
					.addContent(new ItemBuilder(Material.IRON_AXE).enchant(Enchantment.DIG_SPEED, 1).amount(1).build())
					.addContent(new ItemBuilder(Material.GOLD_CHESTPLATE).enchant(Enchantment.DURABILITY, 2).amount(1)
							.build())
					.addContent(new ItemBuilder(Material.EGG).amount(8).build()),

			new ChestPack().addContent(new ItemBuilder(Material.WOOD).amount(12).build())
					.addContent(new ItemBuilder(Material.BRICK).amount(8).build())
					.addContent(new ItemBuilder(Material.IRON_AXE).enchant(Enchantment.DIG_SPEED, 1).amount(1).build())
					.addContent(new ItemBuilder(Material.GOLD_CHESTPLATE).enchant(Enchantment.DURABILITY, 2).amount(1)
							.build()),

			new ChestPack().addContent(new ItemBuilder(Material.STONE).amount(12).build())
					.addContent(new ItemBuilder(Material.STONE).amount(8).build())
					.addContent(
							new ItemBuilder(Material.GOLD_PICKAXE).enchant(Enchantment.DIG_SPEED, 2).amount(1).build())
					.addContent(new ItemBuilder(Material.IRON_CHESTPLATE).enchant(Enchantment.DURABILITY, 1)
							.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.STONE).amount(12).build())
					.addContent(new ItemBuilder(Material.STONE).amount(8).build())
					.addContent(
							new ItemBuilder(Material.GOLD_PICKAXE).enchant(Enchantment.DIG_SPEED, 2).amount(1).build())
					.addContent(new ItemBuilder(Material.IRON_CHESTPLATE).enchant(Enchantment.DURABILITY, 1)
							.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.STONE).amount(8).build())
					.addContent(new ItemBuilder(Material.STONE).amount(6).build())
					.addContent(new ItemBuilder(Material.DIAMOND_PICKAXE).enchant(Enchantment.DIG_SPEED, 2).amount(1)
							.build())
					.addContent(new ItemBuilder(Material.DIAMOND_CHESTPLATE).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.BRICK).amount(12).build())
					.addContent(new ItemBuilder(Material.STONE).amount(6).build())
					.addContent(
							new ItemBuilder(Material.STONE_PICKAXE).enchant(Enchantment.DIG_SPEED, 3).amount(1).build())
					.addContent(new ItemBuilder(Material.DIAMOND_CHESTPLATE)
							.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.STONE).amount(12).build())
					.addContent(new ItemBuilder(Material.STONE).amount(8).build())
					.addContent(new ItemBuilder(Material.IRON_CHESTPLATE).enchant(Enchantment.PROTECTION_FIRE, 2)
							.enchant(Enchantment.PROTECTION_PROJECTILE, 2).amount(1).build())
					.addContent(new ItemBuilder(Material.FISHING_ROD).enchant(Enchantment.FIRE_ASPECT, 1)
							.enchant(Enchantment.KNOCKBACK, 2).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.STONE).amount(12).build())
					.addContent(new ItemBuilder(Material.STONE).amount(8).build())
					.addContent(new ItemBuilder(Material.CHAINMAIL_CHESTPLATE)
							.enchant(Enchantment.PROTECTION_PROJECTILE, 2).amount(1).build())
					.addContent(new ItemBuilder(Material.FISHING_ROD).enchant(Enchantment.FIRE_ASPECT, 1)
							.enchant(Enchantment.KNOCKBACK, 2).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.WOOD).amount(8).build())
					.addContent(new ItemBuilder(Material.WOOD).amount(6).build())
					.addContent(ImprovedItemType.TNT.getItem())
					.addContent(new ItemBuilder(Material.POTION).durability((short) 16386).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.STONE).amount(12).build())
					.addContent(new ItemBuilder(Material.STONE).amount(6).build())
					.addContent(ImprovedItemType.TNT.getItem())
					.addContent(new ItemBuilder(Material.POTION).durability((short) 16418).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.STONE).amount(12).build())
					.addContent(new ItemBuilder(Material.STONE).amount(6).build())
					.addContent(ImprovedItemType.HACHA_VIKINGA.getItem())
					.addContent(new ItemBuilder(Material.POTION).durability((short) 16388).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.WOOD).amount(12).build())
					.addContent(new ItemBuilder(Material.WOOD).amount(6).build())
					.addContent(ImprovedItemType.HACHA_VIKINGA.getItem())
					.addContent(new ItemBuilder(Material.POTION).durability((short) 16424).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.WOOD).amount(12).build())
					.addContent(new ItemBuilder(Material.WOOD).amount(6).build())
					.addContent(new ItemBuilder(Material.IRON_CHESTPLATE).enchant(Enchantment.DURABILITY, 1)
							.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).amount(1).build())
					.addContent(new ItemBuilder(Material.POTION).durability((short) 16428).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.WOOD).amount(12).build())
					.addContent(new ItemBuilder(Material.BRICK).amount(6).build())
					.addContent(new ItemBuilder(Material.IRON_CHESTPLATE).enchant(Enchantment.PROTECTION_PROJECTILE, 1)
							.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).amount(1).build())
					.addContent(new ItemBuilder(Material.POTION).durability((short) 16417).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.WOOD).amount(12).build())
					.addContent(new ItemBuilder(Material.BRICK).amount(6).build())
					.addContent(
							new ItemBuilder(Material.DIAMOND_CHESTPLATE).enchant(Enchantment.PROTECTION_PROJECTILE, 1)
									.enchant(Enchantment.PROTECTION_FIRE, 2).amount(1).build())
					.addContent(new ItemBuilder(Material.POTION).durability((short) 16417).amount(1).build()),

	};

	protected static final ChestPack[] LEGENDARY_CHESTPACKS = new ChestPack[] {

			new ChestPack().addContent(new ItemBuilder(Material.STONE).amount(16).build())
					.addContent(new ItemBuilder(Material.STONE).amount(4).build())
					.addContent(LegendaryItemType.POCION_PURIFICADORA.getItem())
					.addContent(ImprovedItemType.PETO_BRILLANTE.getItem()),

			new ChestPack().addContent(new ItemBuilder(Material.WOOD).amount(16).build())
					.addContent(new ItemBuilder(Material.WOOD).amount(4).build())
					.addContent(new ItemBuilder(Material.DIAMOND_CHESTPLATE)
							.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5).enchant(Enchantment.PROTECTION_FIRE, 2)
							.amount(1).build())
					.addContent(new ItemBuilder(Material.WOOD).amount(4).build()),

			new ChestPack().addContent(new ItemBuilder(Material.WOOD).amount(12).build())
					.addContent(new ItemBuilder(Material.WOOD).amount(4).build())
					.addContent(new ItemBuilder(Material.STONE_AXE).enchant(Enchantment.DIG_SPEED, 5)
							.enchant(Enchantment.DURABILITY, 5).amount(4).build())
					.addContent(ImprovedItemType.PECHERA_ESTELAR.getItem())
					.addContent(new ItemBuilder(Material.WOOD).amount(4).build()),

			new ChestPack().addContent(new ItemBuilder(Material.STONE).amount(12).build())
					.addContent(new ItemBuilder(Material.BRICK).amount(4).build())
					.addContent(new ItemBuilder(Material.DIAMOND_PICKAXE).enchant(Enchantment.DIG_SPEED, 5)
							.enchant(Enchantment.DURABILITY, 5).amount(4).build())
					.addContent(ImprovedItemType.PECHERA_MECANICA.getItem())
					.addContent(new ItemBuilder(Material.BRICK).amount(4).build()),

			new ChestPack().addContent(new ItemBuilder(Material.BRICK).amount(12).build())
					.addContent(new ItemBuilder(Material.IRON_PICKAXE).enchant(Enchantment.DIG_SPEED, 4)
							.enchant(Enchantment.DURABILITY, 4).amount(4).build())
					.addContent(ImprovedItemType.PECHERA_MECANICA.getItem())
					.addContent(LegendaryItemType.PUENTE_FUTURISTICO.getItem()),

			new ChestPack().addContent(new ItemBuilder(Material.STONE).amount(12).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(4).build())
					.addContent(new ItemBuilder(Material.IRON_PICKAXE).enchant(Enchantment.DIG_SPEED, 4)
							.enchant(Enchantment.DURABILITY, 4).amount(4).build())
					.addContent(ImprovedItemType.PECHERA_FENIX.getItem())
					.addContent(LegendaryItemType.PUENTE_FUTURISTICO.getItem()),

			new ChestPack().addContent(new ItemBuilder(Material.BRICK).amount(12).build())
					.addContent(new ItemBuilder(Material.STONE).amount(6).build())
					.addContent(LegendaryItemType.BOMBARDERO.getItem())
					.addContent(
							new ItemBuilder(Material.STONE_PICKAXE).enchant(Enchantment.DIG_SPEED, 3).amount(1).build())
					.addContent(new ItemBuilder(Material.DIAMOND_CHESTPLATE)
							.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.STONE).amount(12).build())
					.addContent(new ItemBuilder(Material.STONE).amount(8).build())
					.addContent(LegendaryItemType.BOMBARDERO.getItem())
					.addContent(new ItemBuilder(Material.IRON_CHESTPLATE)
							.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5).enchant(Enchantment.PROTECTION_FIRE, 2)
							.enchant(Enchantment.PROTECTION_PROJECTILE, 2).amount(1).build())
					.addContent(new ItemBuilder(Material.FISHING_ROD).enchant(Enchantment.FIRE_ASPECT, 1)
							.enchant(Enchantment.KNOCKBACK, 2).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.STONE).amount(12).build())
					.addContent(new ItemBuilder(Material.STONE).amount(8).build())
					.addContent(LegendaryItemType.PUÑO_DE_JHONCENA.getItem())
					.addContent(new ItemBuilder(Material.CHAINMAIL_CHESTPLATE)
							.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 7)
							.enchant(Enchantment.PROTECTION_PROJECTILE, 2).amount(1).build())
					.addContent(new ItemBuilder(Material.FISHING_ROD).enchant(Enchantment.FIRE_ASPECT, 1)
							.enchant(Enchantment.KNOCKBACK, 2).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.WOOD).amount(8).build())
					.addContent(new ItemBuilder(Material.WOOD).amount(6).build())
					.addContent(ImprovedItemType.TNT.getItem()).addContent(LegendaryItemType.PUÑO_DE_JHONCENA.getItem())
					.addContent(new ItemBuilder(Material.CHAINMAIL_CHESTPLATE)
							.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 7)
							.enchant(Enchantment.PROTECTION_PROJECTILE, 2).amount(1).build())
					.addContent(new ItemBuilder(Material.POTION).durability((short) 16386).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.STONE).amount(12).build())
					.addContent(new ItemBuilder(Material.STONE).amount(6).build())
					.addContent(ImprovedItemType.TNT.getItem()).addContent(LegendaryItemType.VARITA_MAGICA.getItem())
					.addContent(new ItemBuilder(Material.LEATHER_CHESTPLATE)
							.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 7)
							.enchant(Enchantment.PROTECTION_PROJECTILE, 3).amount(1).build())
					.addContent(new ItemBuilder(Material.POTION).durability((short) 16418).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.WOOD).amount(14).build())
					.addContent(new ItemBuilder(Material.WOOD).amount(6).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(4).build())
					.addContent(LegendaryItemType.BOLA_CONGELACEREBROS.getItem())
					.addContent(new ItemBuilder(Material.IRON_AXE).enchant(Enchantment.DIG_SPEED, 4)
							.enchant(Enchantment.DURABILITY, 4).amount(1).build())
					.addContent(ImprovedItemType.PECHERA_ESTELAR.getItem()),

			new ChestPack().addContent(new ItemBuilder(Material.WOOD).amount(12).build())
					.addContent(new ItemBuilder(Material.WOOD).amount(6).build())
					.addContent(new ItemBuilder(Material.IRON_AXE).enchant(Enchantment.DIG_SPEED, 4)
							.enchant(Enchantment.DURABILITY, 4).amount(1).build())
					.addContent(LegendaryItemType.BOLA_CONGELACEREBROS.getItem())
					.addContent(ImprovedItemType.PECHERA_MECANICA.getItem())
					.addContent(new ItemBuilder(Material.POTION).durability((short) 16424).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.STONE).amount(14).build())
					.addContent(new ItemBuilder(Material.STONE).amount(6).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(2).build())
					.addContent(new ItemBuilder(Material.DIAMOND_PICKAXE).enchant(Enchantment.DIG_SPEED, 4)
							.enchant(Enchantment.DURABILITY, 4).amount(1).build())
					.addContent(LegendaryItemType.COFRE_EXPLOSIVO.getItem())
					.addContent(LegendaryItemType.COFRE_EXPLOSIVO.getItem())
					.addContent(ImprovedItemType.PECHERA_FUGAZ.getItem()),

			new ChestPack().addContent(new ItemBuilder(Material.STONE).amount(14).build())
					.addContent(new ItemBuilder(Material.STONE).amount(6).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(2).build())
					.addContent(new ItemBuilder(Material.DIAMOND_PICKAXE).enchant(Enchantment.DIG_SPEED, 4)
							.enchant(Enchantment.DURABILITY, 4).amount(1).build())
					.addContent(ImprovedItemType.PECHERA_FUGAZ.getItem())
					.addContent(LegendaryItemType.METEORITO.getItem())
					.addContent(new ItemBuilder(Material.POTION).durability((short) 16417).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.WOOD).amount(14).build())
					.addContent(new ItemBuilder(Material.WOOD).amount(6).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(2).build())
					.addContent(ImprovedItemType.HACHA_DEL_DRAGON.getItem())
					.addContent(ImprovedItemType.PECHERA_FUGAZ.getItem()).addContent(LegendaryItemType.KRAKEN.getItem())
					.addContent(new ItemBuilder(Material.POTION).durability((short) 16417).amount(1).build()),

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