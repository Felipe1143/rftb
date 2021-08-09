package com.felipe221.rftb.menus;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.felipe221.rftb.RFTB;
import com.felipe221.rftb.game.Game;

public class ConfigMenu {
	private static HashMap<Player, String> open = new HashMap<Player, String>();
	
	public static HashMap<Player, String> getOpen() {
		return open;
	}
	
	public static void openMenu(Player player, String map) {
		Inventory join = Bukkit.getServer().createInventory(player, 45, map + " Config");

		open.put(player, map);
		
		Game game = Game.getArena(map);
		
		new BukkitRunnable() {
			@Override
			public void run() {
				if (open.containsKey(player)) {
					ItemStack min = new ItemStack(Material.PAPER);
					ItemMeta min1 = min.getItemMeta();	
					min1.setDisplayName("§aSet min players");
							
					ArrayList<String> loremin= new ArrayList<String>();
					 
					loremin.add("");
					loremin.add("§7Minium players: §a" + game.getMinPlayers());
					loremin.add("");
					loremin.add("§aLEFT CLICK §7to increment minium players");
					loremin.add("§aRIGHT CLICK §7to decrement minium players");
					    	 
					min1.setLore(loremin);
											
					min.setItemMeta(min1);
					
					ItemStack max = new ItemStack(Material.PAPER);
			   	 	ItemMeta max1 = max.getItemMeta();	
					max1.setDisplayName("§aSet max players");
					 
					ArrayList<String> loremax= new ArrayList<String>();
								
					loremax.add("");
					loremax.add("§7Maximum players: §a" + game.getMaxPlayers());
					loremax.add("");
					loremax.add("§aLEFT CLICK §7to increment maximum players");
					loremax.add("§aRIGHT CLICK §7to decrement maximum players");
					    	 
					max1.setLore(loremax);
											
					max.setItemMeta(max1);
					
					ItemStack spawn = new ItemStack(Material.GRASS);
			   	 	ItemMeta spawn1 = spawn.getItemMeta();	
					spawn1.setDisplayName("§aSet spawn of game");
					 
					ArrayList<String> lorespawn= new ArrayList<String>();
								
					lorespawn.add("");
					if (game.getSpawn() != null) {
						lorespawn.add("§7Spawn: §aSETTED");								
					}else {
						lorespawn.add("§7Spawn: §cNOT SETTED");
					}
					lorespawn.add("");
					lorespawn.add("§aLEFT CLICK §7to set a spawn with a block");
					    	 
					spawn1.setLore(lorespawn);
											
					spawn.setItemMeta(spawn1);
							
					ItemStack beast = new ItemStack(Material.ANVIL);
			   	 	ItemMeta beast1 = beast.getItemMeta();	
					beast1.setDisplayName("§aSet beast point of game");
					 
					ArrayList<String> lorebeast= new ArrayList<String>();
								
					lorebeast.add("");
					if (game.getBeastPoint() != null) {
						lorebeast.add("§7Beast point: §aSETTED");
					}else {
						lorebeast.add("§7Beast point: §cNOT SETTED");
					}
					lorebeast.add("");
					lorebeast.add("§aLEFT CLICK §7to set a beast point in your location");
					    	 
					beast1.setLore(lorebeast);
											
					beast.setItemMeta(beast1);
					
					ItemStack spectator = new ItemStack(Material.NETHER_STAR);
			   	 	ItemMeta spectator1 = spectator.getItemMeta();	
					spectator1.setDisplayName("§aSet spectator point of game");
					 
					ArrayList<String> lorespectator= new ArrayList<String>();
								
					lorespectator.add("");
					if (game.getSpectator() != null) {
						lorespectator.add("§7Spectation point: §aSETTED");
					}else {
						lorespectator.add("§7Spectation point: §cNOT SETTED");
					}
					lorespectator.add("");
					lorespectator.add("§aLEFT CLICK §7to set a spectation point with a block");
					    	 
					spectator1.setLore(lorespectator);
											
					spectator.setItemMeta(spectator1);
					
					ItemStack beastwait = new ItemStack(Material.ENCHANTMENT_TABLE);
			   	 	ItemMeta beastwait1 = beastwait.getItemMeta();	
					beastwait1.setDisplayName("§aSet beast wait point of game");
					 
					ArrayList<String> lorebeastwait= new ArrayList<String>();
								
					lorebeastwait.add("");
					if (game.getBeastWaitPoint() != null) {
						lorebeastwait.add("§7Beast wait point: §aSETTED");
					}else {
						lorebeastwait.add("§7Beast wait point: §cNOT SETTED");
					}
					lorebeastwait.add("");
					lorebeastwait.add("§aLEFT CLICK §7to set a beast wait point with a block");
					    	 
					beastwait1.setLore(lorebeastwait);
											
					beastwait.setItemMeta(beastwait1);
					
					ItemStack start = new ItemStack(Material.BEACON);
			   	 	ItemMeta start1 = start.getItemMeta();	
					start1.setDisplayName("§aSet start point of game");
					 
					ArrayList<String> lorestart= new ArrayList<String>();
								
					lorestart.add("");
					if (game.getStartPoint() != null) {
						lorestart.add("§7Start point: §aSETTED");
					}else {
						lorestart.add("§7Start point: §cNOT SETTED");
					}
					lorestart.add("");
					lorestart.add("§aLEFT CLICK §7to set a start point with a block");
					    	 
					start1.setLore(lorestart);
											
					start.setItemMeta(start1);
					
					ItemStack portal = new ItemStack(Material.ENDER_PORTAL_FRAME);
			   	 	ItemMeta portal1 = portal.getItemMeta();	
					portal1.setDisplayName("§aSet portal point of game");
					 
					ArrayList<String> loreportal= new ArrayList<String>();
								
					loreportal.add("");
					if (game.hasPortalToChest()) {
						loreportal.add("§7Portal point to chest: §aSETTED");
					}else {
						loreportal.add("§7Portal point to chest: §cNOT SETTED");
					}
					
					if (game.hasPortalToBack()) {
						loreportal.add("§7Portal point to back: §aSETTED");
					}else {
						loreportal.add("§7Portal point to back: §cNOT SETTED");
					}
					loreportal.add("");
					loreportal.add("§aLEFT CLICK §7to set a portal to chest point with a block");
					loreportal.add("§aSHIFT §7to set a portal to back point with a block");
					loreportal.add("§aRIGHT CLICK §7to remove portal");
					    	 
					portal1.setLore(loreportal);
											
					portal.setItemMeta(portal1);
					
					ItemStack chest = new ItemStack(Material.CHEST);
			   	 	ItemMeta chest1 = chest.getItemMeta();	
					chest1.setDisplayName("§aSet chest zone of game");
					 
					ArrayList<String> lorechest= new ArrayList<String>();
								
					lorechest.add("");
					if (game.getChestZone() != null) {
						lorechest.add("§7Chest zone: §aSETTED");
					}else {
						lorechest.add("§7Chest zone: §cNOT SETTED");
					}
					lorechest.add("");
					lorechest.add("§aLEFT CLICK §7to set a chest zone with a block");
					    	 
					chest1.setLore(lorechest);
											
					chest.setItemMeta(chest1);
					
					ItemStack end = new ItemStack(Material.EYE_OF_ENDER);
			   	 	ItemMeta end1 = end.getItemMeta();	
					end1.setDisplayName("§aSet end point of game");
					 
					ArrayList<String> loreend= new ArrayList<String>();
								
					loreend.add("");
					if (game.getEndPoint() != null) {
						loreend.add("§7End point: §aSETTED");
					}else {
						loreend.add("§7End point: §cNOT SETTED");
					}
					loreend.add("");
					loreend.add("§aLEFT CLICK §7to set a end point with a block");
					    	 
					end1.setLore(loreend);
											
					end.setItemMeta(end1);
					
					ItemStack type = new ItemStack(Material.CHEST);
			   	 	ItemMeta type1 = type.getItemMeta();	
					type1.setDisplayName("§aGet special chest");
					 
					ArrayList<String> loretype= new ArrayList<String>();
								
					loretype.add("");
					loretype.add("§aLEFT CLICK §7to get a special chest");
					loretype.add("§7(Use this chest to locate the special chests");
					loretype.add("§7with the items to fight against the beast)");
					    	 
					type1.setLore(loretype);
											
					type.setItemMeta(type1);
					
					ItemStack remove = new ItemStack(Material.BARRIER);
			   	 	ItemMeta remove1 = remove.getItemMeta();	
			   	 		
					remove1.setDisplayName("§c§lX" + " §cRemove arena");
					 
					ArrayList<String> loreremove= new ArrayList<String>();
								
					loreremove.add("");
					loreremove.add("§cLEFT CLICK §7to remove arena");
					    	 
					remove1.setLore(loreremove);
											
					remove.setItemMeta(remove1);
					
					ItemStack back = new ItemStack(Material.MAGMA_CREAM);
			   	 	ItemMeta back1 = back.getItemMeta();	
			   	 		
					back1.setDisplayName("§aBack to the main menu");
					 											
					back.setItemMeta(back1);
								
					join.setItem(10, min);
					join.setItem(11, max);
					join.setItem(12, beast);
					join.setItem(13, start);
					join.setItem(14, spawn);
					join.setItem(15, spectator);
					join.setItem(16, portal);
					join.setItem(19, chest);
					join.setItem(20, end);
					join.setItem(21, beastwait);
					
					join.setItem(30, back);
					join.setItem(31, remove);
					join.setItem(32, type);					 			 
				 }else {
					 cancel();
				 }
			 }
		}.runTaskTimer(RFTB.getInstance(), 0, 0);
	
		player.openInventory(join);	
	}
}
