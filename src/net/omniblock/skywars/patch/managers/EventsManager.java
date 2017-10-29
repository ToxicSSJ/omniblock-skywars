package net.omniblock.skywars.patch.managers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.google.common.collect.Lists;

import net.omniblock.network.handlers.base.bases.type.BankBase;
import net.omniblock.network.library.utils.TextUtil;
import net.omniblock.skywars.Skywars;
import net.omniblock.skywars.SkywarsGameState;
import net.omniblock.skywars.games.solo.events.SoloPlayerBattleListener;
import net.omniblock.skywars.games.solo.events.SoloPlayerCustomProtocols;
import net.omniblock.skywars.games.solo.events.SoloPlayerToggleListener;
import net.omniblock.skywars.games.teams.events.TeamPlayerBattleListener;
import net.omniblock.skywars.games.teams.events.TeamPlayerCustomProtocols;
import net.omniblock.skywars.games.teams.events.TeamPlayerToggleListener;
import net.omniblock.skywars.patch.managers.chest.defaults.events.AngryChest;
import net.omniblock.skywars.patch.managers.chest.defaults.events.AutomaticTNT;
import net.omniblock.skywars.patch.managers.chest.defaults.events.Bombardier;
import net.omniblock.skywars.patch.managers.chest.defaults.events.Bridged;
import net.omniblock.skywars.patch.managers.chest.defaults.events.Clone;
import net.omniblock.skywars.patch.managers.chest.defaults.events.HealthTurret;
import net.omniblock.skywars.patch.managers.chest.defaults.events.IBall;
import net.omniblock.skywars.patch.managers.chest.defaults.events.IceTurret;
import net.omniblock.skywars.patch.managers.chest.defaults.events.Kraken;
import net.omniblock.skywars.patch.managers.chest.defaults.events.LaserTurret;
import net.omniblock.skywars.patch.managers.chest.defaults.events.Meteoro;
import net.omniblock.skywars.patch.managers.chest.defaults.events.PorkTurret;
import net.omniblock.skywars.patch.managers.chest.defaults.events.Potion;
import net.omniblock.skywars.patch.managers.chest.defaults.events.Punch;
import net.omniblock.skywars.patch.managers.chest.defaults.events.ThorA;
import net.omniblock.skywars.patch.managers.chest.defaults.events.ThorI;
import net.omniblock.skywars.patch.types.SkywarsType;
import net.omniblock.skywars.util.SoundPlayer;

public class EventsManager {

	public static List<Listener> ACTIVED_EVENTS = Lists.newArrayList();
	
	public static void setupEvents(SkywarsType type) {

		switch (type) {
		case NONE:

			break;
		case SW_INSANE_SOLO:

			@SuppressWarnings("serial")
			List<Listener> SW_INSANE_SOLO = new ArrayList<Listener>() {
				{

					add(new SoloPlayerBattleListener());
					add(new SoloPlayerToggleListener());
					add(new SoloPlayerCustomProtocols());
					
					add(getGGListener());
					add(new AutomaticTNT());

				}
			};

			for (Listener listener : SW_INSANE_SOLO) {
				addEvents(true, listener);
			}

			break;
		case SW_INSANE_TEAMS:

			@SuppressWarnings("serial")
			List<Listener> SW_INSANE_TEAMS = new ArrayList<Listener>() {
				{

					add(new TeamPlayerBattleListener());
					add(new TeamPlayerToggleListener());
					add(new TeamPlayerCustomProtocols());
					
					add(getGGListener());
					add(new AutomaticTNT());

				}
			};

			for (Listener listener : SW_INSANE_TEAMS) {
				addEvents(true, listener);
			}

			break;
		case SW_NORMAL_SOLO:

			@SuppressWarnings("serial")
			List<Listener> SW_NORMAL_SOLO = new ArrayList<Listener>() {
				{

					add(new SoloPlayerBattleListener());
					add(new SoloPlayerToggleListener());
					add(new SoloPlayerCustomProtocols());
					
					add(getGGListener());
					add(new AutomaticTNT());

				}
			};

			for (Listener listener : SW_NORMAL_SOLO) {
				addEvents(true, listener);
			}

			break;
		case SW_NORMAL_TEAMS:

			@SuppressWarnings("serial")
			List<Listener> SW_NORMAL_TEAMS = new ArrayList<Listener>() {
				{

					add(new TeamPlayerBattleListener());
					add(new TeamPlayerToggleListener());
					add(new TeamPlayerCustomProtocols());
					
					add(getGGListener());
					add(new AutomaticTNT());

				}
			};

			for (Listener listener : SW_NORMAL_TEAMS) {
				addEvents(true, listener);
			}

			break;
		case SW_Z_SOLO:

			@SuppressWarnings("serial")
			List<Listener> SW_Z_SOLO = new ArrayList<Listener>() {
				{

					add(new SoloPlayerBattleListener());
					add(new SoloPlayerToggleListener());
					add(new SoloPlayerCustomProtocols());

					add(getGGListener());
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
					add(new AutomaticTNT());

				}
			};

			for (Listener listener : SW_Z_SOLO) {
				addEvents(true, listener);
			}

			break;
		case SW_Z_TEAMS:

			@SuppressWarnings("serial")
			List<Listener> SW_Z_TEAMS = new ArrayList<Listener>() {
				{

					add(new TeamPlayerBattleListener());
					add(new TeamPlayerToggleListener());
					add(new TeamPlayerCustomProtocols());

					add(getGGListener());
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
					add(new AutomaticTNT());

				}
			};

			for (Listener listener : SW_Z_TEAMS) {
				addEvents(true, listener);
			}

			break;
		default:
			break;

		}

	}

	public static void addEvents(boolean register, Listener... listeners) {

		for (Listener listener : listeners) {

			if (register) {
				Skywars.getInstance().getServer().getPluginManager().registerEvents(listener, Skywars.getInstance());
			}

			ACTIVED_EVENTS.add(listener);

		}

	}

	public static void reset() {

		for (Listener listener : ACTIVED_EVENTS) {
			HandlerList.unregisterAll(listener);
		}

		ACTIVED_EVENTS.clear();

	}

	public static Listener getGGListener(){
		
		return new Listener(){
			
			List<Player> gg_players = new ArrayList<Player>();
			
			@EventHandler
			public void onChat(AsyncPlayerChatEvent e){
				
				if(Skywars.getGameState() != SkywarsGameState.FINISHING) return;
				
				if(e.getMessage().equalsIgnoreCase("gg")){
					
					if(!gg_players.contains(e.getPlayer())){
						
						e.setCancelled(true);
						gg_players.add(e.getPlayer());
						
						if(Skywars.currentMatchType == SkywarsType.SW_Z_SOLO || Skywars.currentMatchType == SkywarsType.SW_Z_TEAMS){
							
							SoundPlayer.sendSound(e.getPlayer().getLocation(), "general.gg", 1000);
							e.getPlayer().sendMessage(TextUtil.format("&3&l+&310 &7EXP!"));
							e.getPlayer().chat(TextUtil.format("&c&lGG!"));
							BankBase.addExp(e.getPlayer(), 10);
							return;
							
						} else {
							
							e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.LEVEL_UP, 10, 1);
							e.getPlayer().sendMessage(TextUtil.format("&3&l+&310 &7EXP!"));
							e.getPlayer().chat(TextUtil.format("&8GG!"));
							BankBase.addExp(e.getPlayer(), 10);
							return;
							
						}
						
					}
					
				}
				
			}
			
		};
		
	}
		
		
	
}
