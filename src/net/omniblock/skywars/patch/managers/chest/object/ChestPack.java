package net.omniblock.skywars.patch.managers.chest.object;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import net.omniblock.skywars.util.NumberUtil;

public class ChestPack {

	private List<ItemStack> items;

	public ChestPack() {

		items = new ArrayList<ItemStack>();
		return;

	}

	public ChestPack addContent(ItemStack item) {

		items.add(item);
		return this;

	}

	public ChestPack addContent(ItemStack item, int chance) {

		if (chance >= NumberUtil.getRandomInt(1, 100)) {

			items.add(item);
			return this;

		}

		return this;

	}

	public List<ItemStack> getContents() {
		return items;
	}

}
