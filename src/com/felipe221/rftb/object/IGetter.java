package com.felipe221.rftb.object;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.felipe221.rftb.RFTB;
import com.felipe221.rftb.Utils;
import com.felipe221.rftb.object.IStats.StatsType;

public class IGetter {
	public static HashMap<String, HashMap<StatsType, Integer>> players = new HashMap<String, HashMap<StatsType, Integer>>();
	public static HashMap<String, HashMap<String, String>> games = new HashMap<String, HashMap<String, String>>();
	
	public static ArrayList<String> getGames(String player) {
		HashMap<String, String> stats = games.get(player);
		ArrayList<String> played = new ArrayList<String>();
		
		for (String values : stats.keySet()) {
			played.add(values);
		}
		
		return played;
	}
	
	public static int getGamePlayedCache(String player, String arena) {
		HashMap<String, String> stats = games.get(player);

		if (stats.isEmpty() || stats == null) {
			return 0;
		}
		
		if (stats.containsKey(arena)) {
			String[] split = stats.get(arena).split("//");
			
			return Integer.parseInt(split[0]);
		}else {
			return 0;
		}
	}
	
	public static int getGameWinsCache(String player, String arena) {
		HashMap<String, String> stats = games.get(player);

		if (stats.containsKey(arena)) {
			String[] split = stats.get(arena).split("//");
			
			return Integer.parseInt(split[1]);
		}else {
			return 0;
		}
	}
	
	public static void setGamePlayedCache(String player, String arena, int value) {
		HashMap<String, String> stats = games.get(player);

		int wins = getGameWinsCache(player, arena);
		
		String toString = value + "//" + wins;
		stats.put(arena, toString);
		
		games.put(player, stats);
	}
	
	public static void setGameWinsCache(String player, String arena, int value) {
		HashMap<String, String> stats = games.get(player);

		int played = getGamePlayedCache(player, arena);
		
		String toString = played + "//" + value;
		stats.put(arena, toString);
		
		games.put(player, stats);
	}
	
	public static void upGamePlayedCache(String player, String arena, int value) {
		HashMap<String, String> stats = games.get(player);

		int played = getGamePlayedCache(player, arena) + value;
		int wins = getGameWinsCache(player, arena);
		
		String toString = played + "//" + wins;
		stats.put(arena, toString);
		
		games.put(player, stats);
	}
	
	public static void upGameWinsCache(String player, String arena, int value) {
		HashMap<String, String> stats = games.get(player);

		int played = getGamePlayedCache(player, arena);
		int wins = getGameWinsCache(player, arena) + value;
		
		String toString = played + "//" + wins;
		stats.put(arena, toString);
		
		games.put(player, stats);
	}
		
	public static void loadGamesCache(String player) {
		HashMap<String, String> stats = new HashMap<String, String>();

		ArrayList<String> gamesPlayer = fromString(getGamesFrom(player));
		
		Utils.sendLog(gamesPlayer.toString());
		
		if (!getGamesFrom(player).isEmpty()) {
			for (String played : gamesPlayer) {
				String[] split = played.split(":");
				
				String game = split[0];
	
				stats.put(game, split[1]);
			}
		}
		
		stats.put("test", "22//22");
		
		games.put(player, stats);
	}
		
	public static void sendGamesCacheToMySQL(String player) {
		ArrayList<String> played = getGames(player);
		ArrayList<String> toString = new ArrayList<String>();

		for (String plays : played) {
			toString.add(plays + ":" + getGamePlayedCache(player, plays) + "//" + getGameWinsCache(player, plays));
		}
		
		setGames(player, toString);
		
		games.remove(player);
	}
	
	public static int getStatsCache(String player, StatsType stat) {
		HashMap<StatsType, Integer> stats = players.get(player);

		return stats.get(stat);
	}
	
	public static void setStatsCache(String player, StatsType stat, int value) {
		HashMap<StatsType, Integer> stats = players.get(player);

		stats.put(stat, value);
		
		players.put(player, stats);
	}
	
	public static void upStatsCache(String player, StatsType stat, int value) {
		HashMap<StatsType, Integer> stats = players.get(player);

		stats.put(stat, stats.get(stat) + value);
		
		players.put(player, stats);
	}
	
	public static void loadStatsCache(String player) {
		HashMap<StatsType, Integer> stats = new HashMap<StatsType, Integer>();

		for (StatsType stat : StatsType.values()) {
			stats.put(stat, getStatsFrom(player, stat));
		}
		
		players.put(player, stats);
	}
	
	public static void sendStatsCacheToMySQL(String player) {
		for (StatsType stat : StatsType.values()) {
			setStatsFromSync(player, stat, getStatsCache(player, stat));
		}
		
		players.remove(player);
	}
	
	public static int getStatsFrom(String player, StatsType type) {
		int stat = 0;
		
		try {
			ResultSet rs = RFTB.getDatabaseManager().query("SELECT * FROM `Stats` WHERE `name`='"
					+ player + "'").getResultSet();
					
			while (rs.next()) {
				stat = rs.getInt(type.name().toLowerCase());
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			stat = 0;
		}

		return stat;
	}
	
	public static String getGamesFrom(String player) {
		String stat = "";
		
		try {
			ResultSet rs = RFTB.getDatabaseManager().query("SELECT * FROM `Stats` WHERE `name`='"
					+ player + "'").getResultSet();
					
			while (rs.next()) {
				stat = rs.getString("gamesArray");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			stat = "";
		}

		return stat;
	}


	public static void upStatsFrom(String player, StatsType type, int value) {
		int data = getStatsFrom(player, type) + value;
		
		RFTB.getDatabaseManager().query("UPDATE `Stats` SET `" + type.name().toLowerCase() + "`='" +  + data + "' WHERE `name`='" + player + "';");
	}

	public static void setStatsFrom(String player, StatsType type, int value) {
		RFTB.getDatabaseManager().query("UPDATE `Stats` SET `" + type.name().toLowerCase() + "`='" + value + "' WHERE `name`='" + player + "';");
	}
	
	public static void setStatsFromSync(String player, StatsType type, int value) {
		RFTB.getDatabaseManager().querySync("UPDATE `Stats` SET `" + type.name().toLowerCase() + "`='" + value + "' WHERE `name`='" + player + "';", false);
	}
	
    public static void setGames(String player, ArrayList<String> games) {
    	RFTB.getDatabaseManager().query("UPDATE `Stats` SET `gamesArray`='" + games.toString() + "' WHERE `name`='" + player + "';");
    }
	
	public static boolean isExist(String player) {
		try {
			ResultSet rs = RFTB.getDatabaseManager().query("SELECT * FROM `Stats`").getResultSet();
					
			while (rs.next()) {
				String name = rs.getString("name");
				
				if (name.equals(player)) {
					return true;
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			
			return false;
		}
		
		return false;			
	}
	
	public static void load(Player player) {	
		ResultSet rs = RFTB.getDatabaseManager().query("SELECT * FROM `Stats` WHERE `name`='"+ player.getName() + "'").getResultSet();
        try {
            if (!rs.next()){	
            	ArrayList<String> games = new ArrayList<String>();
            	
            	RFTB.getDatabaseManager().query("INSERT IGNORE INTO `Stats` (`name`, `kills`, `deaths`, `games`, `wins`, `beast_kills`, `xp`, `gamesArray`, `skin`) VALUES " + "('" + player.getName() + "', '0', '0', '0', '0', '0', '0', '" + games.toString() +"', '0');");   
            }
         } catch (SQLException e1) {
             e1.printStackTrace();
         }
	}
	
	public static ArrayList<String> fromString(String string){
    	ArrayList<String> arrayFinal = new ArrayList<String>();
    	
    	String[] params = string.split(",");  	
    	
    	for (String s: params) {
    		arrayFinal.add(s.replace("[", "").replace(" ", "").replace("]", ""));
    	}
    	
    	return arrayFinal;
	}
}
