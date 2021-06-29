package com.mancosyt.rftb.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class WorldListener implements Listener{

	public void onSpawnCreatures(CreatureSpawnEvent e) {
		if (e.getEntity() instanceof Player) {
			return;
		}
		
		if (e.getSpawnReason() != SpawnReason.CUSTOM) {
			e.setCancelled(true);
		}
	}
}
