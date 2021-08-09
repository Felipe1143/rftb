package com.felipe221.rftb.object;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.felipe221.rftb.game.GameSkins;
import com.felipe221.rftb.game.GameSkins.Skin;

public class IStats {	
	public enum StatsType{
		KILLS, 
		DEATHS, 
		WINS, 
		GAMES, 
		BEAST_KILLS,
		XP,
		SKIN;
	}
	
	private String player;
	
	public static IStats get(String player) {
		return new IStats(player);
	}
	
	public static IStats get(Player player) {
		return new IStats(player);
	}
	
	public IStats(String player) {
		this.player = player;					
	}
	
	public IStats(Player player) {
		this.player = player.getName();					
	}
	
	public int getStat(StatsType stat) {
		return IGetter.getStatsCache(player, stat);
	}
	
	public void upStat(StatsType stat, int value) {
		IGetter.upStatsCache(player, stat, value);
	}
	
	public void setStat(StatsType stat, int value) {
		IGetter.setStatsCache(player, stat, value);
	}
	
	public Skin getSkin() {
		return GameSkins.getSkinFromNumber(getStat(StatsType.SKIN));
	}
	
	public int getGamesPlayed(String arena) {
		return IGetter.getGamePlayedCache(player, arena);		
	}
	
	public int getGamesWin(String arena) {
		return IGetter.getGameWinsCache(player, arena);
	}
	
	public boolean hasGamePlayed(String arena) {
		if (getGamesPlayed(arena) == 0) { return false;}else {return true;}
	}
	
	public boolean hasGameWins(String arena) {
		if (getGamesWin(arena) == 0) { return false;}else {return true;}
	}
	
	public void upGamePlayed(String arena, int value) {
		IGetter.upGamePlayedCache(player, arena, value);
	}
	
	public void upGameWins(String arena, int value) {
		IGetter.upGameWinsCache(player, arena, value);
	}
	
	public void setGamePlayed(String arena, int value) {
		IGetter.setGamePlayedCache(player, arena, value);
	}	
	
	public void setGameWins(String arena, int value) {
		IGetter.setGameWinsCache(player, arena, value);
	}
		
	public Player getPlayer() {
		return Bukkit.getPlayer(player);
	}
}
