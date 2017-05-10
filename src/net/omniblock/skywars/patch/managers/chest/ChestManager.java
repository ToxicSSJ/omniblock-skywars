package net.omniblock.skywars.patch.managers.chest;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;

import net.omniblock.skywars.patch.managers.MapManager;
import net.omniblock.skywars.patch.managers.chest.item.SkywarsItem;
import net.omniblock.skywars.patch.managers.chest.item.object.FillChest;
import net.omniblock.skywars.patch.types.MatchType;
import net.omniblock.skywars.util.Scan;

public class ChestManager {
	
	private FillChest fillchest = null;

	public static MatchType gMatchType = MatchType.NONE;
	
	public static List<Location> normalchest = new ArrayList<Location>();
	public static List<Location> trappedchest = new ArrayList<Location>();
	
	
	public ChestManager(){
		normalchest = Scan.oneMaterial(MapManager.CURRENT_MAP, Material.CHEST);
		trappedchest = Scan.oneMaterial(MapManager.CURRENT_MAP, Material.TRAPPED_CHEST);
		startChest();
	}

	public void startChest() {
		
		MatchType matchtype = getCurrentMatchType();
		getChestType(matchtype);

	}

	public ChestManager getChestType(MatchType mt) {

		switch (mt) {
		case NORMAL:
			fillchest = new FillChest(SkywarsItem.itemGameNormalChest(), SkywarsItem.itemGameNormalTrappedChest(), 6, 8);
			fillchest.startFilled();
			break;
		case INSANE:
			fillchest = new FillChest(SkywarsItem.itemGameInsaneChest(), SkywarsItem.itemGameInsaneTrappedChest(),6,8);
			fillchest.startFilled();
			break;
		case Z:
			fillchest = new FillChest(SkywarsItem.itemGameZChest(), SkywarsItem.itemGameZTrappedChest(), 6,8);
			fillchest.startFilled();
			break;
		default:
			break;
		}
		
		return this;
	}
	
	public static MatchType getCurrentMatchType() {
		return gMatchType;
	}
}
