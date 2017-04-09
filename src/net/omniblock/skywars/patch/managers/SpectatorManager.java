package net.omniblock.skywars.patch.managers;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.google.common.collect.Lists;

import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.games.solo.managers.SoloPlayerManager;
import net.omniblock.skywars.util.DebugUtil;

public class SpectatorManager {

	private List<Player> playersSpectators = Lists.newArrayList();
	
	public boolean playerIsSpectator(Player p) {
		return playersSpectators.contains(p);
	}
	
	public List<Player> getSpectators() {
		return playersSpectators;
	}
	
	public void addPlayerToSpectator(Player p) {
		
		switch(Skywars.currentMatchType) {
		case NONE:
			break;
		case SW_INSANE_SOLO:
		case SW_NORMAL_SOLO:
		case SW_Z_SOLO:
			SoloPlayerManager.removePlayer(p);
			break;
		case SW_INSANE_TEAMS:
		case SW_NORMAL_TEAMS:
			//TODO: TeamPlayerManager
			break;
		
		}
		
		playersSpectators.add(p);
		
		PlayerInventory pi = p.getInventory();
		pi.clear();
		pi.setArmorContents(null);
		
		for(PotionEffect pe : p.getActivePotionEffects()) {
			p.removePotionEffect(pe.getType());
		}
		
		p.setExp(0);
		p.setHealth(20D);
		p.setFoodLevel(20);
		p.resetMaxHealth();
		p.setPlayerTime(10000L, true);
		p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 1));
		p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
		p.setCanPickupItems(false);
		p.setAllowFlight(true);
		p.setFlying(true);
		
		Scoreboard bukkitScoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
		
		Team tspec = bukkitScoreboard.getTeam("SPECTATORS_G");
		
		if(tspec == null) {
			tspec = bukkitScoreboard.registerNewTeam("SPECTATORS_G");
			tspec.setAllowFriendlyFire(false);
			tspec.setCanSeeFriendlyInvisibles(true);
		}
		
		tspec.addEntry(p.getName());
		
		for(Player pin : SoloPlayerManager.getPlayersInGameList()) {
			Team bukkiPlayerTeam = bukkitScoreboard.getTeam(pin.getName() + "_G");
			
			if(bukkiPlayerTeam == null) {
				bukkiPlayerTeam = bukkitScoreboard.registerNewTeam(pin.getName() + "_G");
				bukkiPlayerTeam.setAllowFriendlyFire(false);
				bukkiPlayerTeam.setCanSeeFriendlyInvisibles(false);
				bukkiPlayerTeam.addEntry(pin.getName());
			}
			
			bukkiPlayerTeam.addEntry(p.getName());
			
			Scoreboard pScoreboard = pin.getScoreboard();
			
			if(pScoreboard == null) {
				DebugUtil.warning(pin.getName() + " no tiene un scoreboard y no se ha podido añadir a " + p.getName() + " al equipo de espectadores (fake team). ¿Algo anda mal con el sistema del scoreboard?");
			}
			
			Team spectatorPlayerTeam = pScoreboard.getTeam("SPECTATORS_P");
			if(spectatorPlayerTeam == null) {
				spectatorPlayerTeam = pScoreboard.registerNewTeam("SPECTATORS_P");
				spectatorPlayerTeam.setAllowFriendlyFire(true);
				spectatorPlayerTeam.setCanSeeFriendlyInvisibles(false);
			}
		}
	}
	
}
