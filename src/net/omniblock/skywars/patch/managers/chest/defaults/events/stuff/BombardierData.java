package net.omniblock.skywars.patch.managers.chest.defaults.events.stuff;

import java.util.Arrays;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import net.omniblock.lobbies.utils.PlayerUtils;
import net.omniblock.network.library.utils.TextUtil;
import net.omniblock.packets.util.Lists;
import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.patch.managers.CustomProtocolManager;
import net.omniblock.skywars.patch.managers.MapManager;
import net.omniblock.skywars.patch.managers.SpectatorManager;
import net.omniblock.skywars.patch.managers.chest.defaults.events.Bombardier;
import net.omniblock.skywars.util.ActionBarApi;
import net.omniblock.skywars.util.CameraUtil;
import net.omniblock.skywars.util.ItemBuilder;
import net.omniblock.skywars.util.SoundPlayer;

public class BombardierData {

	public static BukkitTask launchingtask;
	public static boolean launching;
	
	private PlayerSavedData saveddata;
	private BombardierStatus status;
	
	private Player player;
	private Block target;
	private Location location;
	private ClonData clon;
	
	private List<FallingBlock> tnt;
	private Entity plane;
	
	private BukkitTask elevationtask;
	
	public BombardierData(Player player) {
		
		launching = true;
		
		this.player = player;
		this.location = player.getLocation();
		this.saveddata = new PlayerSavedData(player);
		
		this.status = BombardierStatus.ELEVATION;
		this.clon = new ClonData(player, location);
		this.tnt = Lists.newArrayList();
		
	}

	public boolean useBombardier() {
		
		if(!player.isOnline())
			return false;
		
		if(SpectatorManager.playersSpectators.contains(player))
			return false;
		
		this.clon.makeClon();
		this.clon.setProtect(false);
		
		this.status = BombardierStatus.ELEVATION;
		
		CustomProtocolManager.PROTECTED_PLAYER_LIST.add(player);
		
		Location tp_loc = MapManager.lobbyschematic.getLocation().clone().add(0.5, 0, 0.5);
		tp_loc.setYaw(90L);
		tp_loc.setPitch(90L);
		
		PlayerUtils.forceFly(player);
		PlayerUtils.emptyPlayer(player);
		PlayerUtils.healPlayer(player);
		
		player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false), false);
		SoundPlayer.sendSound(player, "skywars.bombardier_elevation", 5);
		
		CameraUtil.travel(player, Arrays.asList(location, tp_loc), 20 * 2, false);
		
		this.elevationtask = new BukkitRunnable() {

			int round = 0;
			
			@SuppressWarnings("deprecation")
			@Override
			public void run() {

				round++;
				
				if (!player.isOnline()) {
					
					cancel();
					return;
					
				}

				if (player.getLocation().distance(tp_loc) <= 5) {
					
					cancel();

					player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, -5);

					ItemStack launcher = new ItemBuilder(Material.BLAZE_POWDER).amount(1)
							.name(TextUtil.format("&8&lLANZAR BOMBA DE &c&lTNT")).build();
					player.getInventory().setItemInHand(launcher);
					launchBombardier();
					return;
					
				}
				
				if(round >= 20 * 8) {
					
					cancel();
					back(BombardierLauncherStatus.TIME);
					return;
					
				}
				
			}
			
		}.runTaskTimer(Skywars.getInstance(), 1L, 1L);
		return true;
		
	}
	
	public void launchBombardier() {
		
		status = BombardierStatus.LAUNCHING;
		
		launchingtask = new BukkitRunnable() {

			int seconds = 12;
			int click = 0;

			@Override
			public void run() {

				if (player.isOnline()) {

					click++;

					if (click == 10) {

						click = 0;
						seconds--;

					}

					if (seconds <= 0) {

						cancel();

						ActionBarApi.sendActionBar(player,
								TextUtil.format("&c&l - &7Se te ha acabado el tiempo!"));

						back(BombardierLauncherStatus.TIME);
						Bombardier.BOMBARDIER_DATA.remove(player);
						
						BombardierData.launching = false;
						return;

					} else {

						ActionBarApi.sendActionBar(player,
								TextUtil.format("&c&l¡Apunta y Dispara! &8&l: &7Te quedan &a" + seconds
										+ " &7segundos."));
						return;

					}

				} else {

					cancel();
					return;

				}

			}
		}.runTaskTimer(Skywars.getInstance(), 2L, 2L);
		
	}
	
	public void back(BombardierLauncherStatus status) {
		
		if(CustomProtocolManager.PROTECTED_PLAYER_LIST.contains(player))
			CustomProtocolManager.PROTECTED_PLAYER_LIST.remove(player);
		
		this.player.sendMessage(status.getLauncherMSG());
		
		if (this.clon.getClon().isSpawned()) {

			PlayerUtils.emptyPlayer(player);
			
			Location tracker = this.clon.getClon().getEntity().getLocation();
			player.teleport(tracker);

			player.removePotionEffect(PotionEffectType.INVISIBILITY);
			saveddata.apply();
			
			player.setFireTicks(0);

			player.setCanPickupItems(true);
			player.updateInventory();

			player.setGameMode(GameMode.SURVIVAL);
			player.setVelocity(this.clon.getClon().getEntity().getVelocity());
			
			PlayerUtils.forceRemoveFly(player);

			SoundPlayer.stopSound(player);
			player.playSound(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 2, -2);

			this.clon.destroyClon(0);
			return;
			
		}
		
		player.teleport(clon.getSaved());

		PlayerUtils.emptyPlayer(player);
		
		Location tracker = clon.getClon().getEntity().getLocation();
		player.teleport(tracker);

		player.removePotionEffect(PotionEffectType.INVISIBILITY);
		saveddata.apply();
		
		player.setFireTicks(0);
		player.setCanPickupItems(true);
		player.updateInventory();

		player.setGameMode(GameMode.SURVIVAL);

		PlayerUtils.forceRemoveFly(player);

		SoundPlayer.stopSound(player);
		player.playSound(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 2, -2);
		
	}
	
	public Player getPlayer() {
		return player;
	}

	public Block getTarget() {
		return target;
	}

	public List<FallingBlock> getTnt() {
		return tnt;
	}

	public Entity getPlane() {
		return plane;
	}

	public PlayerSavedData getSavedData() {
		return saveddata;
	}

	public Location getLocation() {
		return location;
	}

	public ClonData getClon() {
		return clon;
	}
	
	public BukkitTask getElevationTask() {
		return elevationtask;
	}

	public BukkitTask getLaunchingtask() {
		return launchingtask;
	}

	public BombardierStatus getStatus() {
		return status;
	}

	public static enum BombardierStatus {
		
		ELEVATION,
		LAUNCHING,
		LAUNCHED,
		
		;
		
	}
	
	public static enum BombardierLauncherStatus {
		
		DAMAGE(TextUtil.format("&c¡Has recibido daño y se te ha cancelado el bombardero!")),
		TIME(TextUtil.format("&c¡Se te ha acabado el tiempo y se te ha cancelado el bombardero!")),
		LAUNCHED(TextUtil.format("&a¡Has lanzado el bombardero correctamente!")),
		
		;
		
		private String launchermsg;
		
		BombardierLauncherStatus(String launchermsg){
			
			this.launchermsg = launchermsg;
			return;
			
		}

		public String getLauncherMSG() {
			return launchermsg;
		}
		
	}
	
}
