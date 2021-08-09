package com.felipe221.rftb.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import com.felipe221.rftb.game.Game;
import com.felipe221.rftb.game.GamePlayer;

public class PortalListener implements Listener{

	@EventHandler
	public void JoinPortal(PlayerPortalEvent e) {
		Player player = e.getPlayer();
		
		if (e.getCause() == TeleportCause.END_PORTAL) {
			GamePlayer meta = GamePlayer.getMeta(player);
			
			if (meta.getArena() != null) {
				e.setCancelled(true);
				
				if (Game.getArena(meta.getArena()).getBeastPlayer().getName() != player.getName()) {
					if (!meta.inChestZone()) {
						String map = meta.getArena();
						Game game = Game.getArena(map);
						
						meta.setInChestZone(true);
						
						player.teleport(game.getChestZone());
					}else {
						String map = meta.getArena();
						Game game = Game.getArena(map);
						
						meta.setInChestZone(false);
						
						player.teleport(game.getEndPoint());
					}
				}
			}
		}
	}
}
