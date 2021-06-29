package com.mancosyt.rftb.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.mancosyt.rftb.RFTB;
import com.mancosyt.rftb.game.Game;
import com.mancosyt.rftb.game.GamePlayer;
import com.mancosyt.rftb.object.ICheckpoint;
import com.mancosyt.rftb.object.IStatus;

public class CheckpointListener implements Listener{
	@EventHandler
	public void onPlaceCkeckpoint(BlockPlaceEvent e) {
		Player player = e.getPlayer();
		
		if (e.getBlock().getType() == Material.BEACON) {
			GamePlayer meta = GamePlayer.getMeta(player);
			Game game = Game.getArena(meta.getArena()); 
			
			if (meta.getArena() != null) {
				if (game.getStatus() == IStatus.IN_GAME) {
					if (ICheckpoint.hasCheckpoint(player)) {
						e.setCancelled(true);
						
						player.sendMessage("§e§l>> §f¡Ya tienes un §aCheckpoint §fpuesto!");
					}else {
						e.setCancelled(true);
						
						ICheckpoint.getCheckpoints().put(player, e.getBlock().getLocation());
						
						player.sendMessage("§e§l>> §f¡Checkpoint establecido! Haz §aclick derecho §fen el faro para volver a esta ubicación");
					}
				}			
			}
		}
	}
	
	@EventHandler
	public void onCheckpointClick(PlayerInteractEvent event){
		Player player = event.getPlayer();
						 
		Action a = event.getAction();
					
		if (a == Action.RIGHT_CLICK_AIR) {
			ItemStack handItem = player.getItemInHand();
			
			GamePlayer meta = GamePlayer.getMeta(player);
			Game game = Game.getArena(meta.getArena()); 
			
			if (meta.getArena() != null) {
				if (game.getStatus() == IStatus.IN_GAME) {					
					if (handItem != null) {
						if (handItem.getType() == Material.BEACON) {
							if (ICheckpoint.hasCheckpoint(player)) {
								Location teleport = ICheckpoint.getCheckpoints().get(player);
								
								player.teleport(teleport);
								
								ICheckpoint.getCheckpoints().remove(player);
								
								ItemStack beacon = new ItemStack(Material.BEACON, 1);
								player.getInventory().remove(beacon);
								
								player.updateInventory();
								
								player.sendMessage("§aTeletransportando al Checkpoint...");
							}else {
								player.sendMessage("§e§l>> §f¡No hay ningún checkpoint guardado! Para poner uno solo §acolocalo §fdonde quieras volver posteriormente");
							}
						}
					}
				}
			}
		}
	}
}
