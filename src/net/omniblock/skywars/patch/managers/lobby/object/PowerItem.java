package net.omniblock.skywars.patch.managers.lobby.object;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.games.solo.SoloSkywars;
import net.omniblock.skywars.games.teams.TeamSkywars;
import net.omniblock.skywars.patch.managers.lobby.items.DaysBlazer;
import net.omniblock.skywars.patch.managers.lobby.items.TimeAlterator;
import net.omniblock.skywars.patch.managers.lobby.items.TimeAlterator.TimeType;
import net.omniblock.skywars.patch.types.SkywarsType;
import net.omniblock.skywars.util.TextUtil;

public interface PowerItem {

	public static final int MIN_VOTES = 1;
	
	public static Set<BukkitTask> actived_tasks = new HashSet<BukkitTask>();
	public static Set<PowerItemType> actived_powers = new HashSet<PowerItemType>();
	
	public static Map<Player, List<PowerItemType>> player_votes = new HashMap<Player, List<PowerItemType>>();
	public static Map<PowerItemType, Integer> votes = new HashMap<PowerItemType, Integer>();
	
	public static Map<PowerItemType, Object> tags = new HashMap<PowerItemType, Object>();
	
	public void setup();
	
	public void stop();
	
	public static class PowerItemManager {
		
		public static void applyVotes() {
			
			PowerItemType TIME_ALTERATOR = PowerItemType.TIME_ALTERATOR_DEFAULT;
			Integer LAST_TIME_ALTERATOR = 0;
			
			for(Map.Entry<PowerItemType, Integer> k : votes.entrySet()) {
				
				if(k.getValue() >= MIN_VOTES) {
					
					if(		k.getKey() == PowerItemType.TIME_ALTERATOR_AFTERNOON ||
							k.getKey() == PowerItemType.TIME_ALTERATOR_DEFAULT ||
							k.getKey() == PowerItemType.TIME_ALTERATOR_MIDDAY ||
							k.getKey() == PowerItemType.TIME_ALTERATOR_MIDNIGHT ||
							k.getKey() == PowerItemType.TIME_ALTERATOR_MORNING ||
							k.getKey() == PowerItemType.TIME_ALTERATOR_NIGHT) {
						
						if(k.getValue() >= LAST_TIME_ALTERATOR) {
							
							TIME_ALTERATOR = k.getKey();
							LAST_TIME_ALTERATOR = k.getValue();
							
						}
						continue;
						
					}
					
					actived_powers.add(k.getKey());
					
				}
				
			}
			
			if(TIME_ALTERATOR != PowerItemType.TIME_ALTERATOR_DEFAULT) actived_powers.add(TIME_ALTERATOR);
			setupPowerItems();
			
		}
		
		private static void setupPowerItems() {
			
			if(actived_powers.size() <= 0) new TimeAlterator().setup();
			
			if(actived_powers.contains(PowerItemType.NONE)) {
				
				broadcastModule(PowerItemType.NONE);
				
				TimeAlterator alterator = new TimeAlterator();
				alterator.setup();
				
				return;
				
			}
			
			for(PowerItemType pit : actived_powers) {
				
				broadcastModule(pit);
				
				if(		pit == PowerItemType.TIME_ALTERATOR_AFTERNOON ||
						pit == PowerItemType.TIME_ALTERATOR_DEFAULT ||
						pit == PowerItemType.TIME_ALTERATOR_MIDDAY ||
					    pit == PowerItemType.TIME_ALTERATOR_MIDNIGHT ||
						pit == PowerItemType.TIME_ALTERATOR_MORNING ||
						pit == PowerItemType.TIME_ALTERATOR_NIGHT) {
					
					TimeAlterator.SELECTED_TIME = TimeType.getByPit(pit);
					
					TimeAlterator alterator = new TimeAlterator();
					alterator.setup();
					
					continue;
					
				}
				
				if(pit == PowerItemType.DAYS_BLAZER) {
					
					DaysBlazer blazer = new DaysBlazer();
					blazer.setup();
					
					continue;
					
				}
				
				if(pit == PowerItemType.MORE_INSANE_ITEMS ||
						pit == PowerItemType.MORE_LEGENDARY_ITEMS) {
					
					
					
					
					continue;
					
				}
				
				if(pit == PowerItemType.CONTAMINATION) {
					
					if(		Skywars.currentMatchType == SkywarsType.SW_INSANE_SOLO) {
						
						SoloSkywars.mainRunnableTask.addEvent("&d&lCONTAMINACIÓN:", 120);
						
					} else { TeamSkywars.mainRunnableTask.addEvent("&d&lCONTAMINACIÓN:", 120); }
					
					continue;
					
				}
				
			}
			
		}
		
		private static void broadcastModule(PowerItemType pit) {
			
			if(		pit == PowerItemType.TIME_ALTERATOR_AFTERNOON ||
					pit == PowerItemType.TIME_ALTERATOR_DEFAULT ||
					pit == PowerItemType.TIME_ALTERATOR_MIDDAY ||
				    pit == PowerItemType.TIME_ALTERATOR_MIDNIGHT ||
					pit == PowerItemType.TIME_ALTERATOR_MORNING ||
					pit == PowerItemType.TIME_ALTERATOR_NIGHT) {
				
				Bukkit.broadcastMessage(TextUtil.format("&9&lMODULO ACTIVADO: &7El tiempo ha sido definido a: &6" + pit.getName()));
				return;
				
			}
			
			if(pit == PowerItemType.DAYS_BLAZER) {
				
				Bukkit.broadcastMessage(TextUtil.format("&9&lMODULO ACTIVADO: &7¡Dios de los Dias!"));
				return;
				
			}
			
			if(pit == PowerItemType.MORE_INSANE_ITEMS) {
				
				Bukkit.broadcastMessage(TextUtil.format("&9&lMODULO ACTIVADO: &7¡Más items Mejorados!"));
				return;
				
			}
			
			if(pit == PowerItemType.MORE_LEGENDARY_ITEMS) {
				
				Bukkit.broadcastMessage(TextUtil.format("&9&lMODULO ACTIVADO: &7¡Más items legendarios!"));
				return;
				
			}
			
			if(pit == PowerItemType.NONE) {
				
				Bukkit.broadcastMessage(TextUtil.format("&9&lMODULO ACTIVADO: &7No activar nada."));
				return;
				
			}
			
		}
		
		public static void addVote(PowerItemType pit) {
			
			if(votes.containsKey(pit)) {
				votes.put(pit, votes.get(pit) + 1);
				return;
			}
			
			votes.put(pit, 1);
			return;
			
		}
		
		public static void removeVote(PowerItemType pit) {
			
			if(votes.containsKey(pit)) {
				if(votes.get(pit) > 0) {
					votes.put(pit, votes.get(pit) - 1);
					return;
				}
			}
			
		}
		
	}
	
	public static enum PowerItemType {
		
		NONE("Partida sin Poderes", "ejecuta la partida ignorando todos los poderes votados."),
		
		TIME_ALTERATOR_DEFAULT("Por defecto"),
		
		TIME_ALTERATOR_MORNING("Madrugada", "elegir el tiempo de madrugada. "),
		TIME_ALTERATOR_MIDDAY("Medio dia", "elegir el tiempo de dia. "),
		TIME_ALTERATOR_AFTERNOON("Atardecer", "elegir el tiempo de atardecer. "),
		TIME_ALTERATOR_NIGHT("Noche", "elegir el tiempo de noche. "),
		TIME_ALTERATOR_MIDNIGHT("Media noche", "elegir el tiempo de media noche. "),
		
		CONTAMINATION("Contaminación", "activar la contaminación. "),
		DAYS_BLAZER("Dios de los dias", "activar al Dios de los dias. "),
		
		MORE_INSANE_ITEMS("Más Items Mejorados", "activar más items insanos. "),
		MORE_LEGENDARY_ITEMS("Más Items Legendarios", "activar más items legendarios. "),
		
		;
		
		private String name = "Unknow";
		private String voted_name = "activar Unknow ";
		
		PowerItemType() {}
		
		PowerItemType(String name){
			
			this.name = name;
			
		}

		PowerItemType(String name, String voted_name){
			
			this.name = name;
			this.voted_name = voted_name;
			
		}
		
		public String getVotedName() {
			return voted_name;
		}
		
		public String getName() {
			return name;
		}
		
	}
	
}
