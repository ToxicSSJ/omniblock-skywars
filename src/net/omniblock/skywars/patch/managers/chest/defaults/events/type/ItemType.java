package net.omniblock.skywars.patch.managers.chest.defaults.events.type;

import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public interface ItemType {

	default void AngryChests(PlayerInteractEvent event) {

	};

	default void Bombvader(PlayerInteractEvent event) {

	};

	default void BridgeHud(BlockPlaceEvent event) {

	};

	default void CloneSpell(PlayerInteractEvent event) {

	};

	default void HealPot(PlayerInteractEvent event) {

	};

	default void IceBall(PlayerInteractEvent event) {

	};

	default void JhonPunch(EntityDamageByEntityEvent event) {

	};

	default void kraken(PlayerInteractEvent event) {

	};

	default void LaserTower(BlockPlaceEvent event) {

	};

	default void LifeTower(BlockPlaceEvent event) {

	};

	default void Meteoro(PlayerInteractEvent event) {

	};

	default void PorkTower(BlockPlaceEvent event) {

	};

	default void SlowTower(BlockPlaceEvent event) {

	};

	default void ThorAxe(PlayerInteractEvent event) {

	};

	default void ThorIce(PlayerInteractEvent event) {

	};
}
