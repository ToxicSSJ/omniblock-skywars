package net.omniblock.skywars.patch.managers.chest.defaults.events.stuff;

import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import net.omniblock.network.library.helpers.effectlib.effect.LineEffect;
import net.omniblock.network.library.helpers.effectlib.util.ParticleEffect;
import net.omniblock.network.library.utils.TextUtil;
import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.games.solo.events.SoloPlayerBattleListener;
import net.omniblock.skywars.games.solo.events.SoloPlayerBattleListener.DamageCauseZ;
import net.omniblock.skywars.games.teams.events.TeamPlayerBattleListener;
import net.omniblock.skywars.patch.managers.chest.defaults.type.LegendaryItemType;
import net.omniblock.skywars.patch.types.SkywarsType;

public class AngryChestData {
	
	private Chest chest;
	private Location location;
	private Block block;
	private Player player;
	

	public AngryChestData(Block b, Player player){
		
		this.block = b;
		this.player = player;
		
		chest = (Chest) this.block.getState();
		this.location = this.block.getLocation();
		
	}
	
	public void removeBlock() {

		chest.getInventory().clear();
		chest.update();
		block.setType(Material.AIR);
	
	}
	
	public void getAngryChestBlock() {
		
		removeBlock();
		
		player.sendMessage(TextUtil.format("&6¡Has desarmado tu cofre trampa!"));
		player.getWorld().playEffect(block.getLocation(), Effect.SMOKE, 10);
		block.getWorld().dropItem(location, LegendaryItemType.COFRE_EXPLOSIVO.getItem());
		
	}
	
	public void replaceChestWithAngryChest() {
		
		if(chest != null && chest.getType() == Material.CHEST) {
			
			removeBlock();
			
			player.getInventory().setItemInHand(null);
			
			player.getPlayer().getWorld().playEffect(block.getLocation(), Effect.SMOKE, 10);
			block.getState();
			block.setType(Material.CHEST);
			
			
			
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public void makeExplode(Player onClick) {
		
		Location locUp = location.clone().add(0, 3, 0); 
		Location locDown = location;
		
		FallingBlock fallingblock = block.getWorld()
				.spawnFallingBlock(location, Material.TRAPPED_CHEST, (byte) 0);
		
		fallingblock.setVelocity(new Vector(0, 4, 0));
		
		new BukkitRunnable() {

			Block b = location.clone().add(0, 3, 0).getBlock();
			int start = 0;
			int face = 0;
			
			@Override
			public void run() {
				
				start+=20;
				face++;
				
				if (fallingblock.isDead() == false) { 
					fallingblock.remove();
					
					b.setType(Material.TRAPPED_CHEST);
				}
				
				b.getState().getData().setData((byte) face);
				b.getState().update(true);
				
				laser(locUp, locDown);
				
				b.getLocation().getWorld().playSound(b.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, 2);
				
				if(start >= 60) {
					
					cancel();
					
					LineEffect ef = new LineEffect(Skywars.effectmanager);
					ef.visibleRange = 300;
					ef.particle = ParticleEffect.FIREWORKS_SPARK;
					ef.iterations = 3;
					ef.speed = 2;
					ef.setLocation(b.getLocation());
					ef.setTargetLocation(b.getLocation().clone().add(0, 2, 0));
					ef.start();
					
					b.setType(Material.AIR);
					
					location.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 2, 2);
					onClick.getWorld().playEffect(onClick.getLocation(), Effect.MOBSPAWNER_FLAMES, 50);
					b.getWorld().playEffect(b.getLocation(), Effect.MOBSPAWNER_FLAMES, 50);
					onClick.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 3, 2));

					
					onClick.setVelocity(onClick.getLocation().getDirection().add(new Vector(0, 1, 0))
							.multiply(1));
					
					if (Skywars.currentMatchType == SkywarsType.SW_NORMAL_TEAMS
							|| Skywars.currentMatchType == SkywarsType.SW_INSANE_TEAMS
							|| Skywars.currentMatchType == SkywarsType.SW_Z_TEAMS) {

						TeamPlayerBattleListener.makeZDamage(onClick, player, 5.5, net.omniblock.skywars.games.teams.events.TeamPlayerBattleListener.DamageCauseZ.EXP_CHEST);
						return;

					}
					
					SoloPlayerBattleListener.makeZDamage(onClick, player, 5.5, DamageCauseZ.EXP_CHEST);
				}
			}
			
		}.runTaskTimer(Skywars.getInstance(), 0L, 12L);
	}
	
	private void laser(Location pos1, Location pos2) {

		pos1.getWorld().playSound(pos1, Sound.ENTITY_ENDERDRAGON_FLAP, 5, -2);

		LineEffect ef = new LineEffect(Skywars.effectmanager);
		ef.particle = ParticleEffect.REDSTONE;
		ef.color = Color.YELLOW;
		ef.autoOrient = true;
		ef.visibleRange = 500;
		ef.setLocation(pos2.clone().add(0.5, 0.5, 0.5));
		ef.setTargetLocation(pos1.clone().add(0.5, 0.5, 0.5));
		ef.start();
	}
	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
