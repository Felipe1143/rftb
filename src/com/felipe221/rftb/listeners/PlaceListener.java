package com.felipe221.rftb.listeners;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.felipe221.rftb.RFTB;
import com.felipe221.rftb.game.Game;
import com.felipe221.rftb.manager.PortalManager;
import com.felipe221.rftb.menus.ChestMenu;
import com.felipe221.rftb.menus.ConfigMenu;

import net.md_5.bungee.api.ChatColor;

public class PlaceListener implements Listener{
	private static HashMap<Player, String> configmap = new HashMap<Player, String>();
	private static HashMap<Player, Location> coun = new HashMap<Player, Location>();
	
	public static HashMap<Player, String> getMap() {
		return configmap;
	}

	public static HashMap<Player, Location> getPlayerLocation() {
		return coun;
	}

	@EventHandler
	public void onPlaceInConfig(BlockPlaceEvent e) {
		Player player = e.getPlayer();
		
		if (!configmap.containsKey(player)) {
			return;
		}
		
		if (!e.getItemInHand().getItemMeta().hasDisplayName()) {
			return;
		}
		
		if (e.getBlock().getType() == Material.ANVIL) {
			Location beastpoint = e.getBlock().getLocation();
			String map = configmap.get(player);
			
			e.setCancelled(true);
			player.getInventory().remove(Material.ANVIL);
			
			RFTB.getConfigManager().getMaps().set("Games." + map + ".Beast point.World", player.getWorld().getName());
			RFTB.getConfigManager().getMaps().set("Games." + map + ".Beast point.X", beastpoint.getX());
			RFTB.getConfigManager().getMaps().set("Games." + map + ".Beast point.Y", beastpoint.getY());
			RFTB.getConfigManager().getMaps().set("Games." + map + ".Beast point.Z", beastpoint.getZ());
			RFTB.getConfigManager().getMaps().set("Games." + map + ".Beast point.Yaw", 0.0);
			RFTB.getConfigManager().getMaps().set("Games." + map + ".Beast point.Pitch", 0.0);
		
			RFTB.getConfigManager().saveMaps();
			
			Game game = Game.getArena(map);
			
			game.setBeastPoint(new Location(player.getWorld(), 
					beastpoint.getX(),
					beastpoint.getY(),
					beastpoint.getZ(),
					beastpoint.getYaw(),
					beastpoint.getPitch()));
			
			player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
			
			player.sendMessage(ChatColor.GREEN + "Beast point " + map + " set in X: " + beastpoint.getBlockX() + ", Y: " + beastpoint.getBlockY() + ", Z: " + beastpoint.getBlockZ());									
		
			PlaceListener.configmap.remove(player);
			ConfigMenu.openMenu(player, map);
			
			return;
		}
		
			
				
		if (e.getBlock().getType() == Material.BEACON) {
			Location beastpoint = e.getBlock().getLocation();
			String map = configmap.get(player);
			
			e.setCancelled(true);
			player.getInventory().remove(Material.BEACON);
			
			RFTB.getConfigManager().getMaps().set("Games." + map + ".Start point.World", player.getWorld().getName());
			RFTB.getConfigManager().getMaps().set("Games." + map + ".Start point.X", beastpoint.getX());
			RFTB.getConfigManager().getMaps().set("Games." + map + ".Start point.Y", beastpoint.getY());
			RFTB.getConfigManager().getMaps().set("Games." + map + ".Start point.Z", beastpoint.getZ());
			RFTB.getConfigManager().getMaps().set("Games." + map + ".Start point.Yaw", 0.0);
			RFTB.getConfigManager().getMaps().set("Games." + map + ".Start point.Pitch", 0.0);
		
			RFTB.getConfigManager().saveMaps();
			
			Game game = Game.getArena(map);
			
			game.setStartPoint(new Location(player.getWorld(), 
					beastpoint.getX(),
					beastpoint.getY(),
					beastpoint.getZ(),
					beastpoint.getYaw(),
					beastpoint.getPitch()));
			
			player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
			
			player.sendMessage(ChatColor.GREEN + "Start point " + map + " set in X: " + beastpoint.getBlockX() + ", Y: " + beastpoint.getBlockY() + ", Z: " + beastpoint.getBlockZ());									
		
			ConfigMenu.openMenu(player, map);
			
			return;
		}
				
		if (e.getBlock().getType() == Material.ENDER_STONE) {
			Location beastpoint = e.getBlock().getLocation();
			String map = configmap.get(player);
			
			e.setCancelled(true);
			player.getInventory().remove(Material.ENDER_STONE);
			
			RFTB.getConfigManager().getMaps().set("Games." + map + ".End point.World", player.getWorld().getName());
			RFTB.getConfigManager().getMaps().set("Games." + map + ".End point.X", beastpoint.getX());
			RFTB.getConfigManager().getMaps().set("Games." + map + ".End point.Y", beastpoint.getY());
			RFTB.getConfigManager().getMaps().set("Games." + map + ".End point.Z", beastpoint.getZ());
			RFTB.getConfigManager().getMaps().set("Games." + map + ".End point.Yaw", 0.0);
			RFTB.getConfigManager().getMaps().set("Games." + map + ".End point.Pitch", 0.0);
		
			RFTB.getConfigManager().saveMaps();
			
			Game game = Game.getArena(map);
			
			game.setEndPoint(new Location(player.getWorld(), 
					beastpoint.getX(),
					beastpoint.getY(),
					beastpoint.getZ(),
					beastpoint.getYaw(),
					beastpoint.getPitch()));
			
			player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
			
			player.sendMessage(ChatColor.GREEN + "End point " + map + " set in X: " + beastpoint.getBlockX() + ", Y: " + beastpoint.getBlockY() + ", Z: " + beastpoint.getBlockZ());									
		
			ConfigMenu.openMenu(player, map);
			
			return;
		}
				
		if (e.getBlock().getType() == Material.GRASS) {
			Location beastpoint = e.getBlock().getLocation();
			String map = configmap.get(player);
			
			e.setCancelled(true);
			player.getInventory().remove(Material.GRASS);
			
			RFTB.getConfigManager().getMaps().set("Games." + map + ".Spawn.World", player.getWorld().getName());
			RFTB.getConfigManager().getMaps().set("Games." + map + ".Spawn.X", beastpoint.getX());
			RFTB.getConfigManager().getMaps().set("Games." + map + ".Spawn.Y", beastpoint.getY());
			RFTB.getConfigManager().getMaps().set("Games." + map + ".Spawn.Z", beastpoint.getZ());
			RFTB.getConfigManager().getMaps().set("Games." + map + ".Spawn.Yaw", 0.0);
			RFTB.getConfigManager().getMaps().set("Games." + map + ".Spawn.Pitch", 0.0);
		
			RFTB.getConfigManager().saveMaps();
			
			Game game = Game.getArena(map);
			
			game.setSpawn(new Location(player.getWorld(), 
					beastpoint.getX(),
					beastpoint.getY(),
					beastpoint.getZ(),
					beastpoint.getYaw(),
					beastpoint.getPitch()));
			
			player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
			
			player.sendMessage(ChatColor.GREEN + "Spawn " + map + " set in X: " + beastpoint.getBlockX() + ", Y: " + beastpoint.getBlockY() + ", Z: " + beastpoint.getBlockZ());									
		
			PlaceListener.configmap.remove(player);
			ConfigMenu.openMenu(player, map);
			
			return;
		}
				
		if (e.getBlock().getType() == Material.WOOL) {
			Location beastpoint = e.getBlock().getLocation();
			String map = configmap.get(player);
			
			e.setCancelled(true);
			player.getInventory().remove(Material.WOOL);
			
			RFTB.getConfigManager().getMaps().set("Games." + map + ".Spectation point.World", player.getWorld().getName());
			RFTB.getConfigManager().getMaps().set("Games." + map + ".Spectation point.X", beastpoint.getX());
			RFTB.getConfigManager().getMaps().set("Games." + map + ".Spectation point.Y", beastpoint.getY());
			RFTB.getConfigManager().getMaps().set("Games." + map + ".Spectation point.Z", beastpoint.getZ());
			RFTB.getConfigManager().getMaps().set("Games." + map + ".Spectation point.Yaw", 0.0);
			RFTB.getConfigManager().getMaps().set("Games." + map + ".Spectation point.Pitch", 0.0);
		
			RFTB.getConfigManager().saveMaps();
			
			Game game = Game.getArena(map);
			
			game.setSpectator(new Location(player.getWorld(), 
					beastpoint.getX(),
					beastpoint.getY(),
					beastpoint.getZ(),
					beastpoint.getYaw(),
					beastpoint.getPitch()));
			
			player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
			
			player.sendMessage(ChatColor.GREEN + "Spectation point " + map + " set in X: " + beastpoint.getBlockX() + ", Y: " + beastpoint.getBlockY() + ", Z: " + beastpoint.getBlockZ());									
		
			PlaceListener.configmap.remove(player);
			ConfigMenu.openMenu(player, map);
			
			return;
		}
		
		if (e.getBlock().getType() == Material.ENCHANTMENT_TABLE) {
			Location beastpoint = e.getBlock().getLocation();
			String map = configmap.get(player);
			
			e.setCancelled(true);
			player.getInventory().remove(Material.ENCHANTMENT_TABLE);
			
			RFTB.getConfigManager().getMaps().set("Games." + map + ".Beast wait point.World", player.getWorld().getName());
			RFTB.getConfigManager().getMaps().set("Games." + map + ".Beast wait point.X", beastpoint.getX());
			RFTB.getConfigManager().getMaps().set("Games." + map + ".Beast wait point.Y", beastpoint.getY());
			RFTB.getConfigManager().getMaps().set("Games." + map + ".Beast wait point.Z", beastpoint.getZ());
			RFTB.getConfigManager().getMaps().set("Games." + map + ".Beast wait point.Yaw", 0.0);
			RFTB.getConfigManager().getMaps().set("Games." + map + ".Beast wait point.Pitch", 0.0);
		
			RFTB.getConfigManager().saveMaps();
			
			Game game = Game.getArena(map);
			
			game.setBeastWaitPoint(new Location(player.getWorld(), 
					beastpoint.getX(),
					beastpoint.getY(),
					beastpoint.getZ(),
					beastpoint.getYaw(),
					beastpoint.getPitch()));
			
			player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
			
			player.sendMessage(ChatColor.GREEN + "Beast wait point " + map + " set in X: " + beastpoint.getBlockX() + ", Y: " + beastpoint.getBlockY() + ", Z: " + beastpoint.getBlockZ());									
		
			PlaceListener.configmap.remove(player);
			ConfigMenu.openMenu(player, map);
			
			return;
		}
				
		if (e.getBlock().getType() == Material.ENDER_PORTAL_FRAME) {
			if (e.getItemInHand().getItemMeta().getDisplayName().contains("chest")) {
				Location beastpoint = e.getBlock().getLocation();
				Block a = Bukkit.getWorld(e.getBlock().getWorld().getName()).getBlockAt(e.getBlock().getLocation().getBlockX(), e.getBlock().getLocation().getBlockY(), e.getBlock().getLocation().getBlockZ());
				String map = configmap.get(player);
				
				player.getInventory().remove(Material.ENDER_PORTAL_FRAME);
				
				BlockState a1 = a.getState();
				a1.setType(Material.ENDER_PORTAL);
				a1.update(true);
				
				RFTB.getConfigManager().getMaps().set("Games." + map + ".Portal point to chest.World", player.getWorld().getName());
				RFTB.getConfigManager().getMaps().set("Games." + map + ".Portal point to chest.X", beastpoint.getX());
				RFTB.getConfigManager().getMaps().set("Games." + map + ".Portal point to chest.Y", beastpoint.getY());
				RFTB.getConfigManager().getMaps().set("Games." + map + ".Portal point to chest.Z", beastpoint.getZ());
				RFTB.getConfigManager().getMaps().set("Games." + map + ".Portal point to chest.Yaw", 0.0);
				RFTB.getConfigManager().getMaps().set("Games." + map + ".Portal point to chest.Pitch", 0.0);
			
				RFTB.getConfigManager().saveMaps();
				
				player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
				
				String world = RFTB.getConfigManager().getMaps().getString("Games." + map + ".Portal point to chest.World");
				
				int x = RFTB.getConfigManager().getMaps().getInt("Games." + map + ".Portal point to chest.X");
				int y = RFTB.getConfigManager().getMaps().getInt("Games." + map + ".Portal point to chest.Y");
				int z = RFTB.getConfigManager().getMaps().getInt("Games." + map + ".Portal point to chest.Z");	
	
				World w = Bukkit.getWorld(world);
				
				Location locportal = new Location(w, x,y,z);
				
				Game.getArena(map).setPortalToChest(true);
				
				PortalManager.createPortal(locportal);
				player.sendMessage(ChatColor.GREEN + "Portal point to chest " + map + " set in X: " + beastpoint.getBlockX() + ", Y: " + beastpoint.getBlockY() + ", Z: " + beastpoint.getBlockZ());									
			
				PlaceListener.configmap.remove(player);
				ConfigMenu.openMenu(player, map);
				
				return;
			}else {
				Location beastpoint = e.getBlock().getLocation();
				Block a = Bukkit.getWorld(e.getBlock().getWorld().getName()).getBlockAt(e.getBlock().getLocation().getBlockX(), e.getBlock().getLocation().getBlockY(), e.getBlock().getLocation().getBlockZ());
				String map = configmap.get(player);
				
				player.getInventory().remove(Material.ENDER_PORTAL_FRAME);
				
				BlockState a1 = a.getState();
				a1.setType(Material.ENDER_PORTAL);
				a1.update(true);
				
				RFTB.getConfigManager().getMaps().set("Games." + map + ".Portal point to back.World", player.getWorld().getName());
				RFTB.getConfigManager().getMaps().set("Games." + map + ".Portal point to back.X", beastpoint.getX());
				RFTB.getConfigManager().getMaps().set("Games." + map + ".Portal point to back.Y", beastpoint.getY());
				RFTB.getConfigManager().getMaps().set("Games." + map + ".Portal point to back.Z", beastpoint.getZ());
				RFTB.getConfigManager().getMaps().set("Games." + map + ".Portal point to back.Yaw", 0.0);
				RFTB.getConfigManager().getMaps().set("Games." + map + ".Portal point to back.Pitch", 0.0);
			
				RFTB.getConfigManager().saveMaps();
				
				player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
				
				String world = RFTB.getConfigManager().getMaps().getString("Games." + map + ".Portal point to back.World");
				
				int x = RFTB.getConfigManager().getMaps().getInt("Games." + map + ".Portal point to back.X");
				int y = RFTB.getConfigManager().getMaps().getInt("Games." + map + ".Portal point to back.Y");
				int z = RFTB.getConfigManager().getMaps().getInt("Games." + map + ".Portal point to back.Z");	
	
				World w = Bukkit.getWorld(world);
				
				Location locportal = new Location(w, x,y,z);
				
				Game.getArena(map).setPortalToBack(true);
				
				PortalManager.createPortal(locportal);
				player.sendMessage(ChatColor.GREEN + "Portal point to back " + map + " set in X: " + beastpoint.getBlockX() + ", Y: " + beastpoint.getBlockY() + ", Z: " + beastpoint.getBlockZ());									
			
				PlaceListener.configmap.remove(player);
				ConfigMenu.openMenu(player, map);
				
				return;
			}
		}
				
		if (e.getBlock().getType() == Material.CHEST) {
			if (e.getItemInHand().getItemMeta().getDisplayName().contains("special")) {
				Location beastpoint = e.getBlock().getLocation();
				
				int count = RFTB.getConfigManager().getChests().getInt("Chests.Count");
				
				count++;
			
				RFTB.getConfigManager().getChests().set("Chests placed." + count + ".World", player.getWorld().getName());
				RFTB.getConfigManager().getChests().set("Chests placed." + count + ".X", beastpoint.getBlockX());
				RFTB.getConfigManager().getChests().set("Chests placed." + count + ".Y", beastpoint.getBlockY());
				RFTB.getConfigManager().getChests().set("Chests placed." + count + ".Z", beastpoint.getBlockZ());
				
				RFTB.getConfigManager().getChests().set("Chests.Count", count);
			
				RFTB.getConfigManager().saveChests();
								
				player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
				ChestMenu.OpenMenuConfig(player);
				PlaceListener.configmap.remove(player);
				coun.put(player, e.getBlock().getLocation());
				
				return;
			}else {
				Location beastpoint = e.getBlock().getLocation();
				String map = configmap.get(player);
				
				e.setCancelled(true);
				player.getInventory().remove(Material.CHEST);
				
				RFTB.getConfigManager().getMaps().set("Games." + map + ".Chest zone.World", player.getWorld().getName());
				RFTB.getConfigManager().getMaps().set("Games." + map + ".Chest zone.X", beastpoint.getX());
				RFTB.getConfigManager().getMaps().set("Games." + map + ".Chest zone.Y", beastpoint.getY());
				RFTB.getConfigManager().getMaps().set("Games." + map + ".Chest zone.Z", beastpoint.getZ());
				RFTB.getConfigManager().getMaps().set("Games." + map + ".Chest zone.Yaw", 0.0);
				RFTB.getConfigManager().getMaps().set("Games." + map + ".Chest zone.Pitch", 0.0);
			
				RFTB.getConfigManager().saveMaps();
				
				Game game = Game.getArena(map);
				
				game.setChestZone(new Location(player.getWorld(), 
						beastpoint.getX(),
						beastpoint.getY(),
						beastpoint.getZ(),
						beastpoint.getYaw(),
						beastpoint.getPitch()));
				
				player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
				
				player.sendMessage(ChatColor.GREEN + "Chest zone " + map + " set in X: " + beastpoint.getBlockX() + ", Y: " + beastpoint.getBlockY() + ", Z: " + beastpoint.getBlockZ());									
			
				PlaceListener.configmap.remove(player);
				ConfigMenu.openMenu(player, map);
				
				return;
			}		
		}
	}
}
