package net.omniblock.skywars.patch.managers.chest.presets;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import net.omniblock.skywars.patch.managers.chest.defaults.type.ImprovedItemType;
import net.omniblock.skywars.patch.managers.chest.defaults.type.LegendaryItemType;
import net.omniblock.skywars.patch.managers.chest.object.ChestPack;
import net.omniblock.skywars.util.ItemBuilder;

public class FoodPresets {

	protected static final ChestPack[] NORMAL_CHESTPACKS = new ChestPack[] {

			new ChestPack().addContent(new ItemBuilder(Material.COOKED_BEEF).amount(8).build())
					.addContent(new ItemBuilder(Material.SNOW_BALL).amount(16).build())
					.addContent(new ItemBuilder(Material.EXP_BOTTLE).amount(12).build())
					.addContent(new ItemBuilder(Material.WATER_BUCKET).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.COOKED_MUTTON).amount(8).build())
					.addContent(new ItemBuilder(Material.EGG).amount(18).build())
					.addContent(new ItemBuilder(Material.EXP_BOTTLE).amount(16).build()),

			new ChestPack().addContent(new ItemBuilder(Material.COOKED_BEEF).amount(6).build())
					.addContent(new ItemBuilder(Material.EGG).amount(18).build())
					.addContent(new ItemBuilder(Material.STONE).amount(16).build()),

			new ChestPack().addContent(new ItemBuilder(Material.COOKED_MUTTON).amount(6).build())
					.addContent(new ItemBuilder(Material.SNOW_BALL).amount(18).build())
					.addContent(new ItemBuilder(Material.SHIELD).amount(1).build())
					.addContent(new ItemBuilder(Material.LEATHER_HELMET).amount(1).build())
					.addContent(new ItemBuilder(Material.EXP_BOTTLE).amount(10).build()),

			new ChestPack().addContent(new ItemBuilder(Material.COOKED_MUTTON).amount(8).build())
					.addContent(new ItemBuilder(Material.EGG).amount(16).build())
					.addContent(new ItemBuilder(Material.WOOD).amount(10).build()),

			new ChestPack().addContent(new ItemBuilder(Material.COOKED_BEEF).amount(12).build())
					.addContent(new ItemBuilder(Material.SNOW_BALL).amount(12).build())
					.addContent(new ItemBuilder(Material.LAVA_BUCKET).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.COOKED_MUTTON).amount(12).build())
					.addContent(new ItemBuilder(Material.EGG).amount(12).build())
					.addContent(new ItemBuilder(Material.LAVA_BUCKET).amount(1).build()),

	};

	protected static final ChestPack[] INSANE_CHESTPACKS = new ChestPack[] {

			new ChestPack().addContent(new ItemBuilder(Material.COOKED_BEEF).amount(12).build())
					.addContent(new ItemBuilder(Material.SNOW_BALL).amount(16).build())
					.addContent(new ItemBuilder(Material.EXP_BOTTLE).amount(24).build())
					.addContent(new ItemBuilder(Material.LEATHER_HELMET)
							.enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).amount(1).build())
					.addContent(new ItemBuilder(Material.WATER_BUCKET).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.COOKED_BEEF).amount(8).build())
					.addContent(new ItemBuilder(Material.EGG).amount(16).build())
					.addContent(new ItemBuilder(Material.FLINT_AND_STEEL).amount(1).build())
					.addContent(new ItemBuilder(Material.CHAINMAIL_HELMET).amount(1).build())
					.addContent(new ItemBuilder(Material.LAVA_BUCKET).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(3).build())
					.addContent(new ItemBuilder(Material.EGG).amount(12).build())
					.addContent(new ItemBuilder(Material.CHAINMAIL_HELMET).enchant(Enchantment.PROTECTION_PROJECTILE, 1)
							.amount(1).build())
					.addContent(new ItemBuilder(Material.STONE).amount(16).build())
					.addContent(new ItemBuilder(Material.LAVA_BUCKET).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(2).build())
					.addContent(ImprovedItemType.TNT.getItem())
					.addContent(new ItemBuilder(Material.EXP_BOTTLE).amount(12).build())
					.addContent(new ItemBuilder(Material.FLINT_AND_STEEL).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.COOKED_BEEF).amount(6).build())
					.addContent(new ItemBuilder(Material.ARROW).amount(6).build())
					.addContent(new ItemBuilder(Material.ANVIL).amount(1).build())
					.addContent(new ItemBuilder(Material.SHIELD).amount(1).build())
					.addContent(new ItemBuilder(Material.ENDER_CHEST).amount(2).build())
					.addContent(new ItemBuilder(Material.EXP_BOTTLE).amount(16).build()),

			new ChestPack().addContent(new ItemBuilder(Material.COOKED_MUTTON).amount(5).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(2).build())
					.addContent(new ItemBuilder(Material.LAVA_BUCKET).amount(1).build())
					.addContent(new ItemBuilder(Material.EGG).amount(16).build()),

			new ChestPack().addContent(new ItemBuilder(Material.COOKED_MUTTON).amount(3).build())
					.addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(1).build())
					.addContent(ImprovedItemType.HACHA_VIKINGA.getItem())
					.addContent(ImprovedItemType.CASCO_BRILLANTE.getItem()),

			new ChestPack().addContent(new ItemBuilder(Material.COOKED_BEEF).amount(8).build())
					.addContent(ImprovedItemType.OZ_INFERNAL.getItem())
					.addContent(new ItemBuilder(Material.ARROW).amount(6).build())
					.addContent(new ItemBuilder(Material.EXP_BOTTLE).amount(16).build()),

			new ChestPack().addContent(new ItemBuilder(Material.COOKED_BEEF).amount(8).build())
					.addContent(ImprovedItemType.TNT.getItem())
					.addContent(new ItemBuilder(Material.IRON_SWORD).amount(1).build())
					.addContent(new ItemBuilder(Material.STONE).amount(16).build()),

			new ChestPack().addContent(new ItemBuilder(Material.COOKED_MUTTON).amount(8).build())
					.addContent(ImprovedItemType.TNT.getItem())
					.addContent(new ItemBuilder(Material.IRON_SWORD).amount(1).build())
					.addContent(new ItemBuilder(Material.WOOD).amount(16).build()),

			new ChestPack().addContent(new ItemBuilder(Material.APPLE).amount(3).build())
					.addContent(new ItemBuilder(Material.GOLD_INGOT).amount(24).build())
					.addContent(new ItemBuilder(Material.WORKBENCH).amount(1).build())
					.addContent(ImprovedItemType.OZ_DEL_DRAGON.getItem())
					.addContent(new ItemBuilder(Material.WOOD).amount(12).build()),

			new ChestPack().addContent(new ItemBuilder(Material.APPLE).amount(3).build())
					.addContent(new ItemBuilder(Material.GOLD_INGOT).amount(24).build())
					.addContent(new ItemBuilder(Material.WORKBENCH).amount(1).build())
					.addContent(ImprovedItemType.HACHA_DEL_DRAGON.getItem())
					.addContent(new ItemBuilder(Material.WOOD).amount(12).build()),

			new ChestPack().addContent(new ItemBuilder(Material.BAKED_POTATO).amount(8).build())
					.addContent(new ItemBuilder(Material.WATER_BUCKET).amount(1).build())
					.addContent(new ItemBuilder(Material.SNOW_BALL).amount(12).build())
					.addContent(ImprovedItemType.TNT.getItem()),

			new ChestPack().addContent(new ItemBuilder(Material.BAKED_POTATO).amount(6).build())
					.addContent(new ItemBuilder(Material.LAVA_BUCKET).amount(1).build())
					.addContent(new ItemBuilder(Material.SHIELD).amount(1).build())
					.addContent(new ItemBuilder(Material.EGG).amount(12).build())
					.addContent(new ItemBuilder(Material.IRON_HELMET).enchant(Enchantment.PROTECTION_PROJECTILE, 2)
							.amount(1).build())
					.addContent(new ItemBuilder(Material.EXP_BOTTLE).amount(12).build()),

	};

	protected static final ChestPack[] LEGENDARY_CHESTPACKS = new ChestPack[] {

			new ChestPack().addContent(new ItemBuilder(Material.COOKED_BEEF).amount(16).build())
					.addContent(new ItemBuilder(Material.SNOW_BALL).amount(8).build())
					.addContent(new ItemBuilder(Material.EXP_BOTTLE).amount(22).build())
					.addContent(ImprovedItemType.CASCO_FENIX.getItem())
					.addContent(new ItemBuilder(Material.WATER_BUCKET).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(8).build())
					.addContent(new ItemBuilder(Material.EGG).amount(16).build())
					.addContent(ImprovedItemType.CASCO_FUGAZ.getItem()).addContent(ImprovedItemType.TNT.getItem())
					.addContent(ImprovedItemType.TNT.getItem())
					.addContent(new ItemBuilder(Material.FLINT_AND_STEEL).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(6).build())
					.addContent(ImprovedItemType.CASCO_BRILLANTE.getItem())
					.addContent(LegendaryItemType.METEORITO.getItem())
					.addContent(new ItemBuilder(Material.FLINT_AND_STEEL).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(6).build())
					.addContent(ImprovedItemType.CASCO_BRILLANTE.getItem())
					.addContent(LegendaryItemType.METEORITO.getItem())
					.addContent(new ItemBuilder(Material.WOOD).amount(16).build())
					.addContent(new ItemBuilder(Material.LAVA_BUCKET).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(3).build())
					.addContent(ImprovedItemType.CASCO_BRILLANTE_II.getItem())
					.addContent(LegendaryItemType.KRAKEN.getItem())
					.addContent(new ItemBuilder(Material.STONE).amount(12).build())
					.addContent(new ItemBuilder(Material.LAVA_BUCKET).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(6).build())
					.addContent(ImprovedItemType.HACHA_DEL_DRAGON.getItem())
					.addContent(LegendaryItemType.PUENTE_FUTURISTICO.getItem())
					.addContent(new ItemBuilder(Material.SHIELD).amount(1).build())
					.addContent(new ItemBuilder(Material.WATER_BUCKET).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.APPLE).amount(1).build())
					.addContent(new ItemBuilder(Material.GOLD_BLOCK).amount(7).build())
					.addContent(new ItemBuilder(Material.GOLD_INGOT).amount(9).build())
					.addContent(new ItemBuilder(Material.WORKBENCH).amount(1).build())
					.addContent(ImprovedItemType.TNT.getItem()).addContent(LegendaryItemType.PUÑO_DE_JHONCENA.getItem())
					.addContent(new ItemBuilder(Material.STONE).amount(16).build()),

			new ChestPack().addContent(new ItemBuilder(Material.RAW_BEEF).amount(8).build())
					.addContent(new ItemBuilder(Material.COAL).amount(4).build())
					.addContent(new ItemBuilder(Material.FURNACE).amount(1).build())
					.addContent(ImprovedItemType.HACHA_DEL_DRAGON.getItem())
					.addContent(new ItemBuilder(Material.SHIELD).amount(1).build())
					.addContent(LegendaryItemType.PUÑO_DE_JHONCENA.getItem())
					.addContent(new ItemBuilder(Material.WOOD).amount(16).build()),

			new ChestPack().addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(6).build())
					.addContent(new ItemBuilder(Material.ARROW).amount(12).build())
					.addContent(ImprovedItemType.CASCO_MECANICO.getItem())
					.addContent(LegendaryItemType.PUENTE_FUTURISTICO.getItem())
					.addContent(new ItemBuilder(Material.WOOD).amount(8).build()),

			new ChestPack().addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(6).build())
					.addContent(new ItemBuilder(Material.EXP_BOTTLE).amount(16).build())
					.addContent(LegendaryItemType.PUENTE_FUTURISTICO.getItem())
					.addContent(LegendaryItemType.PUENTE_FUTURISTICO.getItem()),

			new ChestPack().addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(3).build())
					.addContent(new ItemBuilder(Material.EXP_BOTTLE).amount(12).build())
					.addContent(ImprovedItemType.PALA_DEL_PODER.getItem())
					.addContent(LegendaryItemType.PUENTE_FUTURISTICO.getItem()),

			new ChestPack().addContent(new ItemBuilder(Material.COOKED_MUTTON).amount(8).build())
					.addContent(new ItemBuilder(Material.EXP_BOTTLE).amount(24).build())
					.addContent(ImprovedItemType.CASCO_FENIX.getItem())
					.addContent(LegendaryItemType.BOMBARDERO.getItem()),

			new ChestPack().addContent(new ItemBuilder(Material.COOKED_MUTTON).amount(6).build())
					.addContent(ImprovedItemType.TNT.getItem()).addContent(LegendaryItemType.BOMBARDERO.getItem())
					.addContent(new ItemBuilder(Material.STONE).amount(16).build())
					.addContent(new ItemBuilder(Material.WATER_BUCKET).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(12).build())
					.addContent(ImprovedItemType.PICO_DE_RAIZ.getItem())
					.addContent(new ItemBuilder(Material.SHIELD).amount(1).build())
					.addContent(new ItemBuilder(Material.WOOD).amount(16).build())
					.addContent(new ItemBuilder(Material.LAVA_BUCKET).amount(1).build()),

			new ChestPack().addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(6).build())
					.addContent(ImprovedItemType.ARCO_PODEROSO.getItem())
					.addContent(new ItemBuilder(Material.ARROW).amount(4).build())
					.addContent(LegendaryItemType.BOMBARDERO.getItem())
					.addContent(LegendaryItemType.PUENTE_FUTURISTICO.getItem()),

			new ChestPack().addContent(new ItemBuilder(Material.APPLE).amount(12).build())
					.addContent(new ItemBuilder(Material.GOLD_INGOT).amount(64).build())
					.addContent(new ItemBuilder(Material.GOLD_INGOT).amount(32).build())
					.addContent(new ItemBuilder(Material.WORKBENCH).amount(1).build())
					.addContent(LegendaryItemType.BOMBARDERO.getItem())
					.addContent(LegendaryItemType.PUENTE_FUTURISTICO.getItem())
					.addContent(new ItemBuilder(Material.WOOD).amount(16).build()),

			new ChestPack().addContent(new ItemBuilder(Material.GOLDEN_APPLE).amount(8).build())
					.addContent(ImprovedItemType.TNT.getItem()).addContent(ImprovedItemType.CASCO_FUGAZ.getItem())
					.addContent(LegendaryItemType.PUENTE_FUTURISTICO.getItem())
					.addContent(new ItemBuilder(Material.EGG).amount(12).build())
					.addContent(new ItemBuilder(Material.STONE).amount(16).build()),

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