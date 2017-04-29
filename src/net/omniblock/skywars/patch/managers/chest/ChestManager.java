package net.omniblock.skywars.patch.managers.chest;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;

import net.omniblock.skywars.patch.managers.MapManager;
import net.omniblock.skywars.patch.managers.chest.item.ItemInsane;
import net.omniblock.skywars.patch.managers.chest.item.ItemNormal;
import net.omniblock.skywars.patch.managers.chest.item.ItemZ;
import net.omniblock.skywars.patch.managers.chest.item.object.FillChest;
import net.omniblock.skywars.patch.types.MatchType;
import net.omniblock.skywars.util.Scan;

public class ChestManager {
	
	private FillChest fillchest = null;

	public static MatchType gMatchType = MatchType.NONE;
	
	public static List<Location> normalchest = new ArrayList<Location>();
	public static List<Location> trappedchest = new ArrayList<Location>();
	
	
	public ChestManager() {
		
		normalchest = Scan.oneMaterial(MapManager.CURRENT_MAP, Material.CHEST);
		trappedchest = Scan.oneMaterial(MapManager.CURRENT_MAP, Material.TRAPPED_CHEST);
		startChest();
			
	}

	public void startChest() {

		MatchType matchtype = getCurrentMatchType();
		fillChestType(matchtype);

	}

	public void fillChestType(MatchType mt) {

		switch (mt) {
		case NORMAL:
			fillchest = new FillChest(ItemNormal.normalChest(), ItemNormal.trappedChest(), 6, 8);
			fillchest.fillChest();
			break;
		case INSANE:
			fillchest = new FillChest(ItemInsane.normalChest(), ItemInsane.trappedChest(), 6, 8);
			fillchest.fillChest();
			break;
		case Z:
			fillchest = new FillChest(ItemZ.normalChest(), ItemZ.trappedChest(), 6, 8);
			fillchest.fillChest();
			break;
		default:
			break;
		}
		
	}
	
	public static MatchType getCurrentMatchType() {
		return gMatchType;
	}

	public FillChest getFillChest() {
		return fillchest;
	}
}
