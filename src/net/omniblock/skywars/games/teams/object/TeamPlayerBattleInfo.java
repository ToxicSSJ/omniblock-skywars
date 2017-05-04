package net.omniblock.skywars.games.teams.object;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import net.omniblock.skywars.games.teams.managers.TeamPlayerManager;
import net.omniblock.skywars.network.NetworkData;
import net.omniblock.skywars.util.TextUtil;
import omniblock.on.addons.games.general.BankBase;

public class TeamPlayerBattleInfo {
	
	public Player player;
	public Player team;
	
	public boolean unknow = false;
	
	public static final int money_kill = 20;
	public static final int exp_kill = 15;
	
	public static final int money_assistence = 6;
	public static final int exp_assistence = 2;
	
	public static final int money_firstblood = 30;
	public static final int exp_firstblood = 16;
	
	public static final int money_survival = 15;
	public static final int exp_survival = 5;
	
	public static final double factor_kills = 1.0;
	public static final double factor_assistences = 0.2;
	
	public boolean alive = true;
	
	public boolean firstBlood = false;
	public boolean survival = false;
	
	public int kills = 0;
	public int assistences = 0;
	
	public TeamPlayerBattleInfo(Player player) {
		
		this.player = player;
		this.team = TeamPlayerManager.getPlayerTeam(player);
		
	}
	
	public TeamPlayerBattleInfo(boolean unknow) {
		
		this.unknow = unknow;
		this.alive = false;
		
	}

	public double getAverage() {
		
		double kills_points = (kills) * factor_kills;
		double assistences_points = (assistences) * factor_assistences;
		
		return kills_points + assistences_points;
		
	}
	
	public int getKills() {
		return kills;
	}
	
	public int getAssistences() {
		return assistences;
	}
	
	public int getMoney() {
		return (kills * exp_kill) + (assistences * exp_assistence);
	}
	
	public int getExp() {
		return (kills * money_kill) + (assistences * money_assistence);
	}
	
	public boolean isUnknow() {
		return unknow;
	}
	
	public boolean isFirstBlood() {
		return firstBlood;
	}
	
	public int getTotalExp() {
		
		int exp = getExp();
		if(firstBlood) {
			exp += exp_firstblood;
		}
		
		if(survival) {
			exp += exp_survival;
		}
		
		return exp;
		
	}
	
	public int getTotalMoney() {
		
		int money = getMoney();
		if(firstBlood) {
			money += money_firstblood;
		}
		
		if(survival) {
			money += money_survival;
		}
		
		return money;
		
	}
	
	public int getTopNumber(Map<TeamPlayerBattleInfo, Integer> topmap) {
		
		int top = 0;
		
		if(!unknow) {
			
			for(Map.Entry<TeamPlayerBattleInfo, Integer> k : topmap.entrySet()) {
				if(k.getKey().player == player) {
					top = k.getValue();
					break;
				}
			}
			
		}
		
		return top;
		
	}

	public void redeemPrizes() {
		
		boolean X2_BUFF = NetworkData.generalbooster;
		
		int total_money = getTotalMoney();
		int total_exp = getTotalExp();
		
		if(X2_BUFF) {
			total_money = total_money * 2;
			total_exp = total_exp *2;
		}
		
		BankBase.setMoney(player, BankBase.getMoney(player) + total_money);
		BankBase.setExp(player, BankBase.getExp(player) + total_exp);
		
	}
	
	public String getAlliedStatus() {
		
		if(team == null) {
			
			return TextUtil.format("&7Sin equipo.");
			
		} else {
			
			if(!team.isOnline() || team.isDead() || !TeamPlayerManager.getPlayersInGameList().contains(team)) {
				
				return TextUtil.format("&7" + team.getName() + " ✖");
				
			} else {
				
				return TextUtil.format("&a" + team.getName() + " ❤");
				
			}
			
		}
		
	}
	
	public String getTopTierMessage(int top) {
		
		if(top >= 1 && top <= 3) {
			if(top == 1) {
				if(isUnknow()) {
					return TextUtil.getCenteredMessage("&r           &c&l1er Lugar&r &8&l&m-&r &7Desconocido &8&l(&c0 K &8&l&m-&r &30 A&8&l)");
				} else {
					return TextUtil.getCenteredMessage("&r           &c&l1er Lugar&r &8&l&m-&r &7" + player.getName() + " &8&l(&c" + kills + " K &8&l&m-&r &3" + assistences + " A&8&l)");
				}
			} else if(top == 2) {
				if(isUnknow()) {
					return TextUtil.getCenteredMessage("&r           &6&l2do Lugar&r  &8&l&m-&r &7Desconocido &8&l(&c0 K &8&l&m-&r &30 A&8&l)");
				} else {
					return TextUtil.getCenteredMessage("&r           &6&l2do Lugar&r  &8&l&m-&r &7" + player.getName() + " &8&l(&c" + kills + " K &8&l&m-&r &3" + assistences + " A&8&l)");
				}
			} else if(top == 3) {
				if(isUnknow()) {
					return TextUtil.getCenteredMessage("&r           &e&l3er Lugar&r &8&l&m-&r &7Desconocido &8&l(&c0 K &8&l&m-&r &30 A&8&l)");
				} else {
					return TextUtil.getCenteredMessage("&r           &e&l3er Lugar&r &8&l&m-&r &7" + player.getName() + " &8&l(&c" + kills + " K &8&l&m-&r &3" + assistences + " A&8&l)");
				}
			}
		}
		
		return TextUtil.getCenteredMessage("&r           &c&l? Lugar&r &8&l&m-&r &7zlToxicNetherlz &8&l(&c8 K &8&l&m-&r &34 A&8&l)");
	}
	
	public static class PlayerBattleInfoUtils {
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public static Map<TeamPlayerBattleInfo, Integer> getTop(Map<Player, TeamPlayerBattleInfo> battle_info){
			
			int posx = 1;
			List<Player> pasted = new ArrayList<Player>();
			
			Map<TeamPlayerBattleInfo, Integer> top = new HashMap<TeamPlayerBattleInfo, Integer>();
			Map<TeamPlayerBattleInfo, Double> average = new HashMap<TeamPlayerBattleInfo, Double>();
			
			int amount = battle_info.size();
			if(amount >= 1) {
				
				for(Map.Entry<Player, TeamPlayerBattleInfo> k : battle_info.entrySet()) {
					
					if(!pasted.contains(k.getKey())) {
						
						pasted.add(k.getKey());
						
						TeamPlayerBattleInfo cache_pbi = k.getValue();
						average.put(cache_pbi, cache_pbi.getAverage());
						
					}
					
				}
				
				Object[] a = average.entrySet().toArray();

				Arrays.sort(a, new Comparator() {
					public int compare(Object o1, Object o2) {
				        return ((Map.Entry<TeamPlayerBattleInfo, Double>) o2).getValue()
				                   .compareTo(((Map.Entry<TeamPlayerBattleInfo, Double>) o1).getValue());
				    }
				});
				
				for (Object e : a) {
					top.put(((Map.Entry<TeamPlayerBattleInfo, Double>) e).getKey(), posx);
					posx++;
				}
				
			}
			
			if(top.size() < 3) {
				
				for(int i = top.size(); i <= 3; i++) {
					top.put(new TeamPlayerBattleInfo(true), i);
				}
				
			}
			
			return top;
			
		}
		
		public static <K,V> HashMap<V,K> reverse(Map<K,V> map) {
		    HashMap<V,K> rev = new HashMap<V, K>();
		    for(Map.Entry<K,V> entry : map.entrySet())
		        rev.put(entry.getValue(), entry.getKey());
		    return rev;
		}
		
	}
	
}