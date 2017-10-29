/*
 *  Omniblock Developers Team - Copyright (C) 2016
 *
 *  This program is not a free software; you cannot redistribute it and/or modify it.
 *
 *  Only this enabled the editing and writing by the members of the team. 
 *  No third party is allowed to modification of the code.
 *
 */

package net.omniblock.skywars.network.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.omniblock.skywars.Skywars;

public class SkywarsLobbyFilterListener implements Listener {

	@EventHandler
	public void onDrag(InventoryDragEvent e) {
		if (Skywars.inlobby || Skywars.inpregame) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		if (Skywars.inlobby || Skywars.inpregame) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPhysics(PlayerInteractEvent e) {
		if (Skywars.inlobby || Skywars.inpregame) {
			if (e.getAction() == Action.PHYSICAL)
				e.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlock(BlockPlaceEvent e) {
		if (Skywars.inlobby || Skywars.inpregame) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if (Skywars.inlobby || Skywars.inpregame) {
			e.setCancelled(true);
		}
	}

}
