package com.mancosyt.rftb.object;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ICheckpoint {
	private static HashMap<Player, Location> checkpoint = new HashMap<Player, Location>();
	
	public static HashMap<Player, Location> getCheckpoints() {
		return checkpoint;
	}
	
	public static boolean hasCheckpoint(Player player) {
		if (checkpoint.containsKey(player)) {
			return true;
		}else {
			return false;
		}
	}
	
	public static void removeChekpoint(Player player) {
		if (hasCheckpoint(player)) {
			checkpoint.remove(player);
		}
	}
}
