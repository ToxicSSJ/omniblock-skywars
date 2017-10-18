package net.omniblock.skywars.patch.managers.chest.handler;

import org.bukkit.Material;

import net.omniblock.network.library.utils.NumberUtil;
import net.omniblock.skywars.patch.managers.chest.handler.ChestGetterHandler.ChestType;
import net.omniblock.skywars.patch.managers.chest.object.ChestPack;
import net.omniblock.skywars.patch.managers.chest.presets.ArmorPresets;
import net.omniblock.skywars.patch.managers.chest.presets.BlockPresets;
import net.omniblock.skywars.patch.managers.chest.presets.CenterPresets;
import net.omniblock.skywars.patch.managers.chest.presets.FoodPresets;
import net.omniblock.skywars.patch.types.MatchType;
import net.omniblock.skywars.util.ItemBuilder;

public class ChestPackagerHandler {

	public ChestPack getPackage(MatchType match, ChestType type){
		
		switch(type){
		
		case BLOCKS_CHEST: return getBlocksPackage(match);
		case FOOD_CHEST: return getFoodPackage(match);
		case MEGA_CHEST: return getCenterPackage(match);
		case TOOLS_CHEST:  return getArmorPackage(match);
		default: return new ChestPack().addContent(
						new ItemBuilder(Material.BARRIER)
							.name("&c&lERROR")
							.lore("&7Reporta esto en nuestro soporte!")
							.lore("&7Lamentamos mucho este problema.")
							.build());
		
		}
		
		 
		
	}
	
	public ChestPack getFoodPackage(MatchType match){
		
		switch(match){
			
		case NORMAL: return FoodPresets.getNormalPackages()[NumberUtil.getRandomInt(0, FoodPresets.getNormalPackages().length - 1)];
		case INSANE: return FoodPresets.getInsanePackages()[NumberUtil.getRandomInt(0, FoodPresets.getInsanePackages().length - 1)];
		case Z: return FoodPresets.getZPackages()[NumberUtil.getRandomInt(0, FoodPresets.getZPackages().length - 1)];
		default: return new ChestPack().addContent(
						new ItemBuilder(Material.BARRIER)
							.name("&c&lERROR")
							.lore("&7Reporta esto en nuestro soporte!")
							.lore("&7Lamentamos mucho este problema.")
							.build());
			
		}
		
	}
	
	public ChestPack getBlocksPackage(MatchType match){
		
		switch(match){
			
		case NORMAL: return BlockPresets.getNormalPackages()[NumberUtil.getRandomInt(0, BlockPresets.getNormalPackages().length - 1)];
		case INSANE: return BlockPresets.getInsanePackages()[NumberUtil.getRandomInt(0, BlockPresets.getInsanePackages().length - 1)];
		case Z: return BlockPresets.getZPackages()[NumberUtil.getRandomInt(0, BlockPresets.getZPackages().length - 1)];
		default: return new ChestPack().addContent(
						new ItemBuilder(Material.BARRIER)
							.name("&c&lERROR")
							.lore("&7Reporta esto en nuestro soporte!")
							.lore("&7Lamentamos mucho este problema.")
							.build());
			
		}
		
	}
	
	public ChestPack getArmorPackage(MatchType match){
		
		switch(match){
			
		case NORMAL: return ArmorPresets.getNormalPackages()[NumberUtil.getRandomInt(0, ArmorPresets.getNormalPackages().length - 1)];
		case INSANE: return ArmorPresets.getInsanePackages()[NumberUtil.getRandomInt(0, ArmorPresets.getInsanePackages().length - 1)];
		case Z: return ArmorPresets.getZPackages()[NumberUtil.getRandomInt(0, ArmorPresets.getZPackages().length - 1)];
		default: return new ChestPack().addContent(
						new ItemBuilder(Material.BARRIER)
							.name("&c&lERROR")
							.lore("&7Reporta esto en nuestro soporte!")
							.lore("&7Lamentamos mucho este problema.")
							.build());
			
		}
		
	}
	
	public ChestPack getCenterPackage(MatchType match){
		
		switch(match){
			
		case NORMAL: return CenterPresets.getNormalPackages()[NumberUtil.getRandomInt(0, ArmorPresets.getNormalPackages().length - 1)];
		case INSANE: return ArmorPresets.getInsanePackages()[NumberUtil.getRandomInt(0, ArmorPresets.getInsanePackages().length - 1)];
		case Z: return ArmorPresets.getZPackages()[NumberUtil.getRandomInt(0, ArmorPresets.getZPackages().length - 1)];
		default: return new ChestPack().addContent(
						new ItemBuilder(Material.BARRIER)
							.name("&c&lERROR")
							.lore("&7Reporta esto en nuestro soporte!")
							.lore("&7Lamentamos mucho este problema.")
							.build());
			
		}
		
	}
	
}
