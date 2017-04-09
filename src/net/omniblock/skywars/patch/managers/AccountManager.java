package net.omniblock.skywars.patch.managers;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

public class AccountManager {

	public static Map<Player, AccountInfo> SAVED_ACCOUNTS = new HashMap<Player, AccountInfo>();
	
	public static void saveAccount(Player player) {
		
		AccountInfo ai = new AccountInfo(getKills(player),
										 getAssistences(player),
										 getAverages(player));
		
		SAVED_ACCOUNTS.put(player, ai);
		
	}
	
	public static int getKills(Player player) {
		return 0;
	}
	
	public static void setKills(Player player, int quantity) {
		 
	}
	
	public static int getAssistences(Player player) {
		return 0;
	}
	
	public static void setAssistences(Player player, int quantity) {
		 
	}
	
	public static int[] getAverages(Player player) {
		return new int[] { 0 };
	}
	
	public static void setAverages(Player player, int[] averages) {
		 
	}
	
	public static int getMoney(Player player) {
		return 0;
	}
	
	public static void setMoney(Player player, int quantity) {
		
	}
	
	public static int getExp(Player player) {
		return 0;
	}
	
	public static void setExp(Player player, int quantity) {
		
	}
	
	public static class AccountInfo {
		
		private int kills = 0;
		private int assistences = 0;
		
		private int[] averages = new int[] { 0 };
		
		public AccountInfo(int kills, int assistences, int[] averages) {
			
			this.kills = kills;
			this.assistences = assistences;
			
			this.averages = averages;
			
		}

		public int getKills() {
			return kills;
		}

		public void setKills(int kills) {
			this.kills = kills;
		}

		public int getAssistences() {
			return assistences;
		}

		public void setAssistences(int assistences) {
			this.assistences = assistences;
		}

		public int[] getAverages() {
			return averages;
		}

		public void setAverages(int[] averages) {
			this.averages = averages;
		}
		
	}
	
}
