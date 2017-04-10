package net.omniblock.skywars.patch.managers;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import net.omniblock.skywars.patch.managers.CageManager.CageType;
import omniblock.on.addons.resolver.Resolver;
import omniblock.on.addons.resolver.Resolver.ResolveType;
import omniblock.on.database.MakeSQLQuery;
import omniblock.on.database.SQLQueryResult;
import omniblock.on.database.type.TableType;
import omniblock.on.database.util.VariableUtils;

public class AccountManager {

	public static Map<Player, AccountInfo> SAVED_ACCOUNTS = new HashMap<Player, AccountInfo>();
	
	public static void saveAccount(Player player) {
		
		AccountInfo ai = new AccountInfo(getStats(player),
									     getSelectedItems(player),
										 getAverages(player));
		
		SAVED_ACCOUNTS.put(player, ai);
		
	}
	
	public static String getStats(Player player) {
		
		MakeSQLQuery msq = new MakeSQLQuery(TableType.SKYWARS_DATA);
		
		msq.addRowToSelect("p_stats");
		msq.addWhereClause("p_id", Resolver.getResolverInfo(player.getName(), ResolveType.RESOLVER_KEY, false));
		
		try {
			
			SQLQueryResult sqr = msq.execute();
			sqr.iterateNextResult();
			if(!sqr.isEmpty()) {
				return sqr.getString("p_stats");
			}
			
		} catch (IllegalArgumentException | SQLException e) {
			e.printStackTrace();
		}
		
		return VariableUtils.SKYWARS_INITIAL_STATS;
		
	}
	
	public static int getKills(String stats) {
		
		if(stats.contains(";")) {
			
			try {
				
				String[] data_array = stats.split(";");
				String KILLS_STR = data_array[0];
				
				int kills = Integer.valueOf(KILLS_STR);
				return kills;
				
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return 0;
	}
	
	public static void setKills(Player player, int quantity) {
		 
	}
	
	public static int getAssistences(String stats) {
		
		if(stats.contains(";")) {
			
			try {
				
				String[] data_array = stats.split(";");
				String ASISTENCES_STR = data_array[1];
				
				int assistences = Integer.valueOf(ASISTENCES_STR);
				return assistences;
				
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return 0;
	}
	
	public static void setAssistences(Player player, int quantity) {
		 
	}
	
	public static int getPlayedGames(String stats) {
		
		if(stats.contains(";")) {
			
			try {
				
				String[] data_array = stats.split(";");
				String PLAYED_GAMES_STR = data_array[2];
				
				int played_games = Integer.valueOf(PLAYED_GAMES_STR);
				return played_games;
				
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return 0;
	}
	
	public static void setPlayedGames(Player player, int quantity) {
		 
	}
	
	public static int getWinnedGames(String stats) {
		
		if(stats.contains(";")) {
			
			try {
				
				String[] data_array = stats.split(";");
				String WINNED_GAMES_STR = data_array[3];
				
				int winned_games = Integer.valueOf(WINNED_GAMES_STR);
				return winned_games;
				
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return 0;
	}
	
	public static void setWinnedGames(Player player, int quantity) {
		 
	}
	
	public static String getAverage(String stats) {
		
		if(stats.contains(";")) {
			
			try {
				
				String[] data_array = stats.split(";");
				String AVERAGE_STR = data_array[4];
				
				return AVERAGE_STR;
				
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return "NEW";
	}
	
	public static void setAverage(Player player, String average) {
		 
	}
	
	public static Object getSelectedItem(SelectedItemType sit, String data) {
		
		switch(sit) {
			case CAGE:
				
				if(data.contains(";")) {
					
					String[] data_array = data.split(";");
					String CAGE_CODE = data_array[0];
					
					for(CageType ct : CageType.values()) {
						if(ct.getCode().equalsIgnoreCase(CAGE_CODE)) {
							return ct;
						}
					}
					
				}
				
				break;
			case EXTRA_INFO:
				
				break;
			case BOW_EFFECT:
				
				break;
			case DEATH_EFFECT:
				
				break;
			default:
				
				break;
		}
		
		return null;
		
	}
	
	public static String getSelectedItems(Player player) {
		
		MakeSQLQuery msq = new MakeSQLQuery(TableType.SKYWARS_DATA);
		
		msq.addRowToSelect("p_selected");
		msq.addWhereClause("p_id", Resolver.getResolverInfo(player.getName(), ResolveType.RESOLVER_KEY, false));
		
		try {
			
			SQLQueryResult sqr = msq.execute();
			sqr.iterateNextResult();
			if(!sqr.isEmpty()) {
				return sqr.getString("p_selected");
			}
			
		} catch (IllegalArgumentException | SQLException e) {
			e.printStackTrace();
		}
		
		return VariableUtils.SKYWARS_INITIAL_SELECTED;
	}
	
	public static String[] getItems(Player player) {
		
		MakeSQLQuery msq = new MakeSQLQuery(TableType.SKYWARS_DATA);
		
		msq.addRowToSelect("p_items");
		msq.addWhereClause("p_id", Resolver.getResolverInfo(player.getName(), ResolveType.RESOLVER_KEY, false));
		
		String items = "{}";
		
		try {
			
			SQLQueryResult sqr = msq.execute();
			sqr.iterateNextResult();
			if(!sqr.isEmpty()) {
				items = sqr.getString("p_items");
			}
			
		} catch (IllegalArgumentException | SQLException e) {
			e.printStackTrace();
		}
		
		if(items != "{}") {
			if(items.contains(";")) {
				
				String[] av_array = items.split(";");
				return av_array;
				
			}
		}
		
		return new String[] { };
	}
	
	public static double[] getAverages(Player player) {
		
		MakeSQLQuery msq = new MakeSQLQuery(TableType.SKYWARS_DATA);
		
		msq.addRowToSelect("p_last_games_average");
		msq.addWhereClause("p_id", Resolver.getResolverInfo(player.getName(), ResolveType.RESOLVER_KEY, false));
		
		String averages = "{}";
		
		try {
			
			SQLQueryResult sqr = msq.execute();
			sqr.iterateNextResult();
			if(!sqr.isEmpty()) {
				averages = sqr.getString("p_last_games_average");
			}
			
		} catch (IllegalArgumentException | SQLException e) {
			e.printStackTrace();
		}
		
		if(averages != "{}") {
			if(averages.contains(";")) {
				
				String[] av_array = averages.split(";");
				double[] co_array = new double[] { };
				
				if(av_array.length >= 50) {
					
					int pos_x = 0;
					for(String k : av_array) {
						
						try {
							
							double av = Double.valueOf(k);
							co_array[pos_x] = av;
							pos_x++;
							
						}catch(Exception e) {
							return new double[] { 0.0 };
						}
						
					}
					
				}
				
				if(co_array.length >= 50) {
					return co_array;
				}
				
			}
		}
		
		return new double[] { 0.0 };
	}
	
	public static void setAverages(Player player, double[] averages) {
		 
	}
	
	public static int getExtraPoints(Player player) {
		
		MakeSQLQuery msq = new MakeSQLQuery(TableType.SKYWARS_DATA);
		
		msq.addRowToSelect("p_extra_points");
		msq.addWhereClause("p_id", Resolver.getResolverInfo(player.getName(), ResolveType.RESOLVER_KEY, false));
		
		try {
			
			SQLQueryResult sqr = msq.execute();
			sqr.iterateNextResult();
			if(!sqr.isEmpty()) {
				return Integer.valueOf(sqr.getString("p_extra_points"));
			}
			
		} catch (IllegalArgumentException | SQLException e) {
			e.printStackTrace();
		}
		
		return VariableUtils.SKYWARS_INITIAL_EXTRA_POINTS;
	}
	
	public static int getMoney(Player player) {
		
		MakeSQLQuery msq = new MakeSQLQuery(TableType.BANK_DATA);
		
		msq.addRowToSelect("p_money");
		msq.addWhereClause("p_id", Resolver.getResolverInfo(player.getName(), ResolveType.RESOLVER_KEY, false));
		
		try {
			
			SQLQueryResult sqr = msq.execute();
			sqr.iterateNextResult();
			if(!sqr.isEmpty()) {
				return Integer.valueOf(sqr.getString("p_money"));
			}
			
		} catch (IllegalArgumentException | SQLException e) {
			e.printStackTrace();
		}
		
		return VariableUtils.BANK_INITIAL_MONEY;
	}
	
	public static void setMoney(Player player, int quantity) {
		
	}
	
	public static int getExp(Player player) {
		
		MakeSQLQuery msq = new MakeSQLQuery(TableType.BANK_DATA);
		
		msq.addRowToSelect("p_exp");
		msq.addWhereClause("p_id", Resolver.getResolverInfo(player.getName(), ResolveType.RESOLVER_KEY, false));
		
		try {
			
			SQLQueryResult sqr = msq.execute();
			sqr.iterateNextResult();
			if(!sqr.isEmpty()) {
				return Integer.valueOf(sqr.getString("p_exp"));
			}
			
		} catch (IllegalArgumentException | SQLException e) {
			e.printStackTrace();
		}
		
		return VariableUtils.BANK_INITIAL_EXP;
	}
	
	public static void setExp(Player player, int quantity) {
		
	}
	
	public enum SelectedItemType {
		
		CAGE,
		EXTRA_INFO,
		BOW_EFFECT,
		DEATH_EFFECT,
		
		;
		
	}
	
	public static class AccountInfo {
		
		private String stats = VariableUtils.SKYWARS_INITIAL_STATS;
		private String selected = VariableUtils.SKYWARS_INITIAL_SELECTED;
		
		private double[] averages = new double[] { 0.0 };
		
		public AccountInfo(String stats, String selected, double[] averages) {
			
			this.stats = stats;
			this.selected = selected;
			
			this.averages = averages;
			
		}

		public String getStats() {
			return stats;
		}

		public void setStats(String stats) {
			this.stats = stats;
		}

		public double[] getAverages() {
			return averages;
		}

		public void setAverages(double[] averages) {
			this.averages = averages;
		}

		public String getSelected() {
			return selected;
		}

		public void setSelected(String selected) {
			this.selected = selected;
		}
		
	}
	
}
