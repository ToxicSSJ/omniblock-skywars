package net.omniblock.skywars.games.solo.object;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import net.omniblock.lobbies.api.LobbyUtility;
import net.omniblock.lobbies.skywars.handler.base.SkywarsBase;
import net.omniblock.network.handlers.base.bases.type.BankBase;
import net.omniblock.network.library.utils.TextUtil;
import net.omniblock.skywars.util.ArrayUtils;
import net.omniblock.skywars.util.NumberUtil;

public class SoloPlayerBattleInfo {

	public Player player;
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

	public SoloPlayerBattleInfo(Player player) {

		this.player = player;

	}

	public SoloPlayerBattleInfo(boolean unknow) {

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
		if (firstBlood) {
			exp += exp_firstblood;
		}

		if (survival) {
			exp += exp_survival;
		}

		return exp;

	}

	public int getTotalMoney() {

		int money = getMoney();
		if (firstBlood) {
			money += money_firstblood;
		}

		if (survival) {
			money += money_survival;
		}

		return money;

	}

	public int getTopNumber(Map<SoloPlayerBattleInfo, Integer> topmap) {

		int top = 0;

		if (!unknow) {

			for (Map.Entry<SoloPlayerBattleInfo, Integer> k : topmap.entrySet()) {
				if (k.getKey().player == player) {
					top = k.getValue();
					break;
				}
			}

		}

		return top;

	}

	public void redeemPrizes() {

		boolean X2_BUFF = LobbyUtility.getFixedBoosterStatusBoolean("skywarsnetworkbooster");

		int total_money = X2_BUFF ? getTotalMoney() * 2 : getTotalMoney();
		int total_exp = X2_BUFF ? getTotalExp() * 2 : getTotalExp();

		BankBase.setMoney(player, BankBase.getMoney(player) + total_money);
		BankBase.setExp(player, BankBase.getExp(player) + total_exp);
		
		String stats = SkywarsBase.getStats(player);
		
		int kills = SkywarsBase.getKills(stats);
		int assistences = SkywarsBase.getAssistences(stats);
		int games = SkywarsBase.getPlayedGames(stats);
		int wins = SkywarsBase.getWinnedGames(stats);
		String average = SkywarsBase.getAverage(stats);
		
		double[] averages = SkywarsBase.getAverages(player);
		
		if(alive) wins = wins + 1;
		kills = kills + this.kills;
		assistences = assistences + this.assistences;
		games = games + 1;
		
		if(averages.length >= 50)
			averages[NumberUtil.getRandomInt(0, 49)] = getAverage();
		else
			averages[averages.length - 1] = getAverage();
		
		average = averages.length >= 50 ? String.valueOf(ArrayUtils.getAverage(averages)) : "NEW";
		stats = kills + ";" + assistences + ";" + games + ";" + wins + ";" + average;
		
		StringBuffer buffer = new StringBuffer();
		
		for(int i = 0; i < averages.length; i++)
			buffer.append(averages[i] + (i == averages.length - 1 ? "" : ";"));
		
		SkywarsBase.setStats(player, stats);
		SkywarsBase.setAverage(player, buffer.toString());
		SkywarsBase.addWeekPrizePoints(player, this.kills);
		
	}

	public String getTopTierMessage(int top) {

		if (top >= 1 && top <= 3) {
			if (top == 1) {
				if (isUnknow()) {
					return TextUtil.getCenteredMessage(
							"&r           &c&l1er Lugar&r &8&l&m-&r &7Desconocido &8&l(&c0 K &8&l&m-&r &30 A&8&l)");
				} else {
					return TextUtil.getCenteredMessage("&r           &c&l1er Lugar&r &8&l&m-&r &7" + player.getName()
							+ " &8&l(&c" + kills + " K &8&l&m-&r &3" + assistences + " A&8&l)");
				}
			} else if (top == 2) {
				if (isUnknow()) {
					return TextUtil.getCenteredMessage(
							"&r           &6&l2do Lugar&r  &8&l&m-&r &7Desconocido &8&l(&c0 K &8&l&m-&r &30 A&8&l)");
				} else {
					return TextUtil.getCenteredMessage("&r           &6&l2do Lugar&r  &8&l&m-&r &7" + player.getName()
							+ " &8&l(&c" + kills + " K &8&l&m-&r &3" + assistences + " A&8&l)");
				}
			} else if (top == 3) {
				if (isUnknow()) {
					return TextUtil.getCenteredMessage(
							"&r           &e&l3er Lugar&r &8&l&m-&r &7Desconocido &8&l(&c0 K &8&l&m-&r &30 A&8&l)");
				} else {
					return TextUtil.getCenteredMessage("&r           &e&l3er Lugar&r &8&l&m-&r &7" + player.getName()
							+ " &8&l(&c" + kills + " K &8&l&m-&r &3" + assistences + " A&8&l)");
				}
			}
		}
		return TextUtil.getCenteredMessage(
				"&r           &c&l? Lugar&r &8&l&m-&r &7Desconocido &8&l(&c0 K &8&l&m-&r &30 A&8&l)");
	}

	public static class PlayerBattleInfoUtils {

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public static Map<SoloPlayerBattleInfo, Integer> getTop(Map<Player, SoloPlayerBattleInfo> pbi) {

			int posx = 1;
			List<Player> pasted = new ArrayList<Player>();

			Map<SoloPlayerBattleInfo, Integer> top = new HashMap<SoloPlayerBattleInfo, Integer>();
			Map<SoloPlayerBattleInfo, Double> average = new HashMap<SoloPlayerBattleInfo, Double>();

			int amount = pbi.size();
			
			if (amount >= 1) {

				for (Map.Entry<Player, SoloPlayerBattleInfo> k : pbi.entrySet()) {

					if (!pasted.contains(k.getKey())) {

						pasted.add(k.getKey());

						SoloPlayerBattleInfo cache_pbi = k.getValue();
						average.put(cache_pbi, cache_pbi.getAverage());
						
					}

				}

				Object[] a = average.entrySet().toArray();

				Arrays.sort(a, new Comparator() {
					public int compare(Object o1, Object o2) {
						return ((Map.Entry<SoloPlayerBattleInfo, Double>) o2).getValue()
								.compareTo(((Map.Entry<SoloPlayerBattleInfo, Double>) o1).getValue());
					}
				});

				for (Object e : a) {
					top.put(((Map.Entry<SoloPlayerBattleInfo, Double>) e).getKey(), posx);
					posx++;
				}

			}

			if (top.size() < 3) {

				for (int i = top.size(); i <= 3; i++) {
					top.put(new SoloPlayerBattleInfo(true), i);
				}

			}

			return top;

		}

		public static <K, V> HashMap<V, K> reverse(Map<K, V> map) {
			HashMap<V, K> rev = new HashMap<V, K>();
			for (Map.Entry<K, V> entry : map.entrySet())
				rev.put(entry.getValue(), entry.getKey());
			return rev;
		}

	}

}