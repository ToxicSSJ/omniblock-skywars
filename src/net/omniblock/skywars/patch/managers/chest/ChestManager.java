package net.omniblock.skywars.patch.managers.chest;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;

import net.omniblock.skywars.games.solo.SoloSkywars;
import net.omniblock.skywars.games.solo.types.MatchType;
import net.omniblock.skywars.patch.managers.MapManager;
import net.omniblock.skywars.patch.managers.chest.item.ItemInsane;
import net.omniblock.skywars.patch.managers.chest.item.ItemNormal;
import net.omniblock.skywars.patch.managers.chest.item.ItemZ;
import net.omniblock.skywars.patch.managers.chest.item.object.FillChest;
import net.omniblock.skywars.util.Scan;

public class ChestManager {
	

	public static List<Location> normalchest = new ArrayList<Location>();
	public static List<Location> trappedchest = new ArrayList<Location>();
	
	public ChestManager() {
		
		normalchest = Scan.oneMaterial(MapManager.CURRENT_MAP, Material.CHEST);
		trappedchest = Scan.oneMaterial(MapManager.CURRENT_MAP, Material.TRAPPED_CHEST);
		
		startChest();
		
	}

	public void startChest() {
		MatchType matchtype = SoloSkywars.getCurrentMatchType();
		fillChestType(matchtype);

	}

	public void fillChestType(MatchType mt) {

		switch (mt) {
		case NORMAL:
			FillChest itemN = new FillChest(ItemNormal.normalChest(), ItemNormal.trappedChest(), 6, 8);
			itemN.fillChest();
			break;
		case INSANE:
			FillChest itemI = new FillChest(ItemInsane.normalChest(), ItemInsane.trappedChest(), 6, 8);
			itemI.fillChest();
			break;
		case Z:
			FillChest itemZ = new FillChest(ItemZ.normalChest(), ItemZ.trappedChest(), 6, 8);
			itemZ.fillChest();
			break;
		default:
			break;
		}
		
	}

	public List<Location> getNormalchest() {
		return normalchest;
	}

	public void setNormalchest(List<Location> normalchest) {
		ChestManager.normalchest = normalchest;
	}

	public List<Location> getTrappedchest() {
		return trappedchest;
	}

	public void setTrappedchest(List<Location> trappedchest) {
		ChestManager.trappedchest = trappedchest;
	}
}
