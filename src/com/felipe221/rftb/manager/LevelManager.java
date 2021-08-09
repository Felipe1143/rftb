package com.felipe221.rftb.manager;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.felipe221.rftb.RFTB;
import com.felipe221.rftb.object.IStats;
import com.felipe221.rftb.object.IStats.StatsType;

import net.md_5.bungee.api.ChatColor;

public class LevelManager {
	private static HashMap<Integer, Integer> levels = new HashMap<Integer, Integer>();
	
	public static void register() {
		for (int a = 0; a < 150; a++) {			
			int xp = 20 * a * 2 * a / 4;
			
			levels.put(a, xp);
		}
	}
	
	public Integer getXP(int level) {
		return levels.get(level);
	}
	
	public int getLevelPlayer(Player player) {
		int xp = IStats.get(player).getStat(StatsType.XP);
		
		for (Entry<Integer, Integer> entry : levels.entrySet()) {
			int levelEntry = entry.getKey();
			int xpEntry = entry.getValue();

			if (xp >= xpEntry && xp < levels.get(levelEntry + 1)) {
				return levelEntry;
			}
		}
		
		return 150;
	}
	
	public int getXPNext(int level) {
		return levels.get(level + 1);
	}
	
	
	public ChatColor getColor(int level) {
		if (level < 5) {
			return ChatColor.GREEN;
		}else if (level >= 15 && level < 30) {
			return ChatColor.AQUA;
		}else if (level >= 30 && level < 40) {
			return ChatColor.GOLD;
		}else if (level >= 40) {
			return ChatColor.RED;
		}
		
		return ChatColor.AQUA;
	}
}
