package net.omniblock.skywars.patch.managers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import com.google.common.collect.Lists;

import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.games.solo.chest.item.z.listeners.AngryChest;
import net.omniblock.skywars.games.solo.chest.item.z.listeners.Bombardier;
import net.omniblock.skywars.games.solo.chest.item.z.listeners.Bridged;
import net.omniblock.skywars.games.solo.chest.item.z.listeners.Clone;
import net.omniblock.skywars.games.solo.chest.item.z.listeners.HealthTurret;
import net.omniblock.skywars.games.solo.chest.item.z.listeners.IBall;
import net.omniblock.skywars.games.solo.chest.item.z.listeners.IceTurret;
import net.omniblock.skywars.games.solo.chest.item.z.listeners.LaserTurret;
import net.omniblock.skywars.games.solo.chest.item.z.listeners.Meteoro;
import net.omniblock.skywars.games.solo.chest.item.z.listeners.PorkTurret;
import net.omniblock.skywars.games.solo.chest.item.z.listeners.Potion;
import net.omniblock.skywars.games.solo.chest.item.z.listeners.Punch;
import net.omniblock.skywars.games.solo.chest.item.z.listeners.ThorA;
import net.omniblock.skywars.games.solo.chest.item.z.listeners.ThorI;
import net.omniblock.skywars.games.solo.events.SoloPlayerBattleListener;
import net.omniblock.skywars.games.solo.events.SoloPlayerCustomProtocols;
import net.omniblock.skywars.games.solo.events.SoloPlayerJoinListener;
import net.omniblock.skywars.games.solo.events.SoloPlayerQuitListener;
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
				add(new SoloPlayerJoinListener());
				add(new SoloPlayerQuitListener());
				add(new SoloPlayerCustomProtocols());
				
			}};
			
			for(Listener listener : SW_INSANE_SOLO) {
				addEvents(true, listener);
			}
			
			break;
		case SW_INSANE_TEAMS:
			
			break;
		case SW_NORMAL_SOLO:
			
			@SuppressWarnings("serial") List<Listener> SW_NORMAL_SOLO = new ArrayList<Listener>() {{
				
				add(new SoloPlayerBattleListener());
				add(new SoloPlayerJoinListener());
				add(new SoloPlayerQuitListener());
				add(new SoloPlayerCustomProtocols());
				
			}};
			
			for(Listener listener : SW_NORMAL_SOLO) {
				addEvents(true, listener);
			}
			
			break;
		case SW_NORMAL_TEAMS:
			
			break;
		case SW_Z_SOLO:
			
			@SuppressWarnings("serial") List<Listener> SW_Z_SOLO = new ArrayList<Listener>() {{
				
				add(new SoloPlayerBattleListener());
				add(new SoloPlayerJoinListener());
				add(new SoloPlayerQuitListener());
				add(new SoloPlayerCustomProtocols());
				
				add(new AngryChest());
				add(new Meteoro());
				add(new Punch());
				add(new Bombardier());
				add(new Bridged());
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
