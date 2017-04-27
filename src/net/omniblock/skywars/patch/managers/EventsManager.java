package net.omniblock.skywars.patch.managers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import com.google.common.collect.Lists;

import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.games.solo.events.SoloPlayerBattleListener;
import net.omniblock.skywars.games.solo.events.SoloPlayerCustomProtocols;
import net.omniblock.skywars.games.solo.events.SoloPlayerToggleListener;
import net.omniblock.skywars.games.teams.events.TeamPlayerBattleListener;
import net.omniblock.skywars.games.teams.events.TeamPlayerCustomProtocols;
import net.omniblock.skywars.games.teams.events.TeamPlayerToggleListener;
import net.omniblock.skywars.patch.managers.chest.item.z.AngryChest;
import net.omniblock.skywars.patch.managers.chest.item.z.Bombardier;
import net.omniblock.skywars.patch.managers.chest.item.z.Bridged;
import net.omniblock.skywars.patch.managers.chest.item.z.Clone;
import net.omniblock.skywars.patch.managers.chest.item.z.HealthTurret;
import net.omniblock.skywars.patch.managers.chest.item.z.IBall;
import net.omniblock.skywars.patch.managers.chest.item.z.IceTurret;
import net.omniblock.skywars.patch.managers.chest.item.z.Kraken;
import net.omniblock.skywars.patch.managers.chest.item.z.LaserTurret;
import net.omniblock.skywars.patch.managers.chest.item.z.Meteoro;
import net.omniblock.skywars.patch.managers.chest.item.z.PorkTurret;
import net.omniblock.skywars.patch.managers.chest.item.z.Potion;
import net.omniblock.skywars.patch.managers.chest.item.z.Punch;
import net.omniblock.skywars.patch.managers.chest.item.z.ThorA;
import net.omniblock.skywars.patch.managers.chest.item.z.ThorI;
import net.omniblock.skywars.patch.types.SkywarsType;

public class EventsManager {

	public static List<Listener> ACTIVED_EVENTS = Lists.newArrayList();
	
	public static void setupEvents(SkywarsType type) {
		
		switch(type) {
		case NONE:
			
			break;
		case SW_INSANE_SOLO:
			
			@SuppressWarnings("serial") List<Listener> SW_INSANE_SOLO = new ArrayList<Listener>() {{
				
				add(new SoloPlayerBattleListener());
				add(new SoloPlayerToggleListener());
				add(new SoloPlayerCustomProtocols());
				
			}};
			
			for(Listener listener : SW_INSANE_SOLO) {
				addEvents(true, listener);
			}
			
			break;
		case SW_INSANE_TEAMS:
			
			@SuppressWarnings("serial") List<Listener> SW_INSANE_TEAMS = new ArrayList<Listener>() {{
				
				add(new TeamPlayerBattleListener());
				add(new TeamPlayerToggleListener());
				add(new TeamPlayerCustomProtocols());
				
			}};
			
			for(Listener listener : SW_INSANE_TEAMS) {
				addEvents(true, listener);
			}
			
			break;
		case SW_NORMAL_SOLO:
			
			@SuppressWarnings("serial") List<Listener> SW_NORMAL_SOLO = new ArrayList<Listener>() {{
				
				add(new SoloPlayerBattleListener());
				add(new SoloPlayerToggleListener());
				add(new SoloPlayerCustomProtocols());
				
			}};
			
			for(Listener listener : SW_NORMAL_SOLO) {
				addEvents(true, listener);
			}
			
			break;
		case SW_NORMAL_TEAMS:
			
			@SuppressWarnings("serial") List<Listener> SW_NORMAL_TEAMS = new ArrayList<Listener>() {{
				
				add(new TeamPlayerBattleListener());
				add(new TeamPlayerToggleListener());
				add(new TeamPlayerCustomProtocols());
				
			}};
			
			for(Listener listener : SW_NORMAL_TEAMS) {
				addEvents(true, listener);
			}
			
			break;
		case SW_Z_SOLO:
			
			@SuppressWarnings("serial") List<Listener> SW_Z_SOLO = new ArrayList<Listener>() {{
				
				add(new SoloPlayerBattleListener());
				add(new SoloPlayerToggleListener());
				add(new SoloPlayerCustomProtocols());
				
				add(new AngryChest());
				add(new Meteoro());
				add(new Punch());
				add(new Bombardier());
				add(new Bridged());
				add(new Kraken());
				add(new IBall());
				add(new Clone());
				add(new ThorA());
				add(new ThorI());
				add(new PorkTurret());
				add(new LaserTurret());
				add(new IceTurret());
				add(new HealthTurret());
				add(new Potion());
				
			}};
			
			for(Listener listener : SW_Z_SOLO) {
				addEvents(true, listener);
			}
			
			break;
		case SW_Z_TEAMS:
			
			@SuppressWarnings("serial") List<Listener> SW_Z_TEAMS = new ArrayList<Listener>() {{
				
				add(new TeamPlayerBattleListener());
				add(new TeamPlayerToggleListener());
				add(new TeamPlayerCustomProtocols());
				
				add(new AngryChest());
				add(new Meteoro());
				add(new Punch());
				add(new Bombardier());
				add(new Bridged());
				add(new Kraken());
				add(new IBall());
				add(new Clone());
				add(new ThorA());
				add(new ThorI());
				add(new PorkTurret());
				add(new LaserTurret());
				add(new IceTurret());
				add(new HealthTurret());
				add(new Potion());
				
			}};
			
			for(Listener listener : SW_Z_TEAMS) {
				addEvents(true, listener);
			}
			
			break;
		default:
			break;
		
		}
		
	}
	
	public static void addEvents(boolean register, Listener...listeners) {
		
		for(Listener listener : listeners) {
			
			if(register) {
				Skywars.getInstance().getServer().getPluginManager().registerEvents(listener, Skywars.getInstance());
			}
			
			ACTIVED_EVENTS.add(listener);
			
		}
		
	}
	
	public static void reset() {
		
		for(Listener listener : ACTIVED_EVENTS) {
			HandlerList.unregisterAll(listener);
		}
		
		ACTIVED_EVENTS.clear();
		
	}
	
}
