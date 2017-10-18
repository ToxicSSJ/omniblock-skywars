package net.omniblock.skywars.patch.managers.chest.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import net.omniblock.skywars.patch.managers.chest.Chests;
import net.omniblock.skywars.patch.managers.chest.handler.ChestGetterHandler.ChestType;
import net.omniblock.skywars.patch.managers.chest.object.ChestPack;
import net.omniblock.skywars.patch.types.MatchType;
import net.omniblock.skywars.util.NumberUtil;

public class ChestFillerHandler {
	
	public enum ChestFillType {
		
		CLEAR_CHEST,
		
		COMMON_FILL_CHEST,
		COMMON_REFILL_CHEST,
		
		MORE_ITEM_Z_CHEST,
		MORE_ITEM_IMPROVED_CHEST
		
	}
	
	protected static MatchType matchtype;
	protected static World world;
	
	protected static Map<Chest, ChestType> chests = new HashMap<Chest, ChestType>();
	
	public void setupFiller(MatchType type, World w){
		
		matchtype = type;
		world = w;
		
		chests = Chests.GETTER.getChests(w);
		
	}
	
	public void makeFill(ChestFillType filltype){
		
		chests.entrySet().stream().forEach(k -> promptChestFill(k.getKey(), filltype));
		return;
		
	}
	
	public void promptChestFill(Chest chest, ChestFillType filltype){
		
		switch(filltype){
		
		case CLEAR_CHEST:
			
			chest.getBlockInventory().clear();
			chest.update(true);
			return;
			
		case COMMON_FILL_CHEST:
			
			ChestBundler.commonBundle(chest, getChestType(chest));
			break;
			
		case COMMON_REFILL_CHEST:
			
			ChestBundler.commonRefillBundle(chest, getChestType(chest));
			break;
			
		case MORE_ITEM_Z_CHEST:
			
			ChestBundler.commonMoreItemZ(chest, getChestType(chest));
			break;
			
		case MORE_ITEM_IMPROVED_CHEST:
			
			ChestBundler.commonMoreItemImproved(chest, getChestType(chest));
			break;
			
		default:
			
			ChestBundler.commonBundle(chest, getChestType(chest));
			break;
		
		}
		
	}
	
	public List<Block> getChestBlocks(ChestType type){
		
		List<Block> blocks = new ArrayList<Block>();
		
		for(Map.Entry<Chest, ChestType> k : chests.entrySet()){
			
			if(k.getValue() == type){
				
				blocks.add(k.getKey().getBlock());
				continue;
				
			}
			
		}
		
		return blocks;
		
	}
	
	public ChestType getChestType(Chest chest){
		
		if(chests.containsKey(chest)) return chests.get(chest);
		return ChestType.BLOCKS_CHEST;
		
	}
	
	public static class ChestBundler {
		
		public static void commonMoreItemImproved(Chest chest, ChestType type){
			
			ChestPack pack = Chests.PACKAGER.getCenterPackage(MatchType.INSANE);
			List<ItemStack> items = new ArrayList<ItemStack>();
			
			items.addAll(pack.getContents());
			Collections.shuffle(items);
			
			for(ItemStack item : items){
				
				int slot = NumberUtil.getRandomInt(0, 26);
				
				setItem(chest, item, slot);
				continue;
				
			}
			
		}
		
		public static void commonMoreItemZ(Chest chest, ChestType type){

			ChestPack pack = Chests.PACKAGER.getCenterPackage(MatchType.Z);
			List<ItemStack> items = new ArrayList<ItemStack>();
			
			items.addAll(pack.getContents());
			Collections.shuffle(items);
			
			for(ItemStack item : items){
				
				int slot = NumberUtil.getRandomInt(0, 26);
				
				setItem(chest, item, slot);
				continue;
				
			}
			
		}
		
		public static void commonRefillBundle(Chest chest, ChestType type){
			
			ChestPack pack = Chests.PACKAGER.getPackage(matchtype, ChestType.MEGA_CHEST);
			List<ItemStack> items = new ArrayList<ItemStack>();
			
			items.addAll(pack.getContents());
			Collections.shuffle(items);
			
			for(ItemStack item : items){
				
				int slot = NumberUtil.getRandomInt(0, 26);
				
				setItem(chest, item, slot);
				continue;
				
			}
			
		}
		
		public static void commonBundle(Chest chest, ChestType type){
			
			ChestPack pack = Chests.PACKAGER.getPackage(matchtype, type);
			List<ItemStack> items = new ArrayList<ItemStack>();
			
			items.addAll(pack.getContents());
			Collections.shuffle(items);
			
			for(ItemStack item : items){
				
				int slot = NumberUtil.getRandomInt(0, 26);
				
				setItem(chest, item, slot);
				continue;
				
			}
			
		}
		
		private static void setItem(Chest chest, ItemStack item, int slot){
			
			if(chest.getBlockInventory().getItem(slot) != null){
				
				int newslot = !(slot >= 26) ? slot + 1 : 0;
				
				setItem(chest, item, newslot);
				return;
				
			}
			
			chest.getBlockInventory().setItem(slot, item);
			return;
			
		}
		
	}
	
}
