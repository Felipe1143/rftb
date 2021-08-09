package com.felipe221.rftb.listeners;

import java.util.ArrayList;

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
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.felipe221.rftb.RFTB;
import com.felipe221.rftb.game.Game;
import com.felipe221.rftb.game.GameManager;
import com.felipe221.rftb.game.GamePlayer;
import com.felipe221.rftb.game.GameVote;
import com.felipe221.rftb.game.GameVote.HEARTS;
import com.felipe221.rftb.game.GameVote.MODE;
import com.felipe221.rftb.game.GameVote.TIME;
import com.felipe221.rftb.manager.PortalManager;
import com.felipe221.rftb.menus.ChestMenu;
import com.felipe221.rftb.menus.ConfigMainMenu;
import com.felipe221.rftb.menus.ConfigMenu;
import com.felipe221.rftb.menus.GameMenu;
import com.felipe221.rftb.menus.VoteMenu;
import com.felipe221.rftb.object.IStatus;

import net.md_5.bungee.api.ChatColor;

public class MenuListener implements Listener{
	
	@EventHandler
	public void InteractMenu(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		
		if (e.getCurrentItem() == null) {
			return;
		}
		
		if (e.getWhoClicked() == null) {
			return;
		}
		
		if (e.getInventory().getTitle().equals("» Arenas")){				
			for(Game gameList : RFTB.getGameManager().getGames()) {
				String gameName = gameList.getName();
				int slot = GameMenu.getArenaWithSlot().get(gameName);
				
				Game game = Game.getArena(gameName);
				
				if (e.getSlot() == slot) {
					if (game.getStatus() == IStatus.WAITING || game.getStatus() == IStatus.STARTING){
						e.setCancelled(true);
												
						RFTB.getGameManager().onPlayerJoin(player, gameName);
						
						return;
					}else {
						e.setCancelled(true);
						
						player.sendMessage("§e§l>> §f¡La arena §a" + game.getName() + "§fya comenzó!");
					
						return;
					}
				}
			}
		}
		
		if (e.getInventory().getTitle().equals("» Votaciones")){			
			Game game = Game.getArena(GamePlayer.getMeta(player).getArena());
			GameVote vote = GameVote.getArena(game.getName());
			
			e.setCancelled(true);
			
			if (e.getSlot() == 11) {				
				if (e.getClick().isLeftClick()) {
					vote.voteMode(player, MODE.FAST);	
					
					return;
				}
				
				if (e.getClick().isRightClick()) {
					vote.voteMode(player, MODE.NORMAL);
					
					return;
				}
			}
			
			if (e.getSlot() == 13) {
				if (e.getClick().isShiftClick()) {
					vote.voteTime(player, TIME.STORM);	
					
					return;
				}
				
				if (e.getClick().isLeftClick()) {
					vote.voteTime(player, TIME.DIA);
					
					return;
				}
				
				if (e.getClick().isRightClick()) {
					vote.voteTime(player, TIME.NOCHE);
					
					return;
				}
			}
			
			if (e.getSlot() == 15) {
				if (e.getClick().isLeftClick()) {
					vote.voteHeart(player, HEARTS.DOUBLE);	
					
					return;
				}
				
				if (e.getClick().isRightClick()) {
					vote.voteHeart(player, HEARTS.NORMAL);
					
					return;
				}
			}
		}
		
		if (e.getInventory().getTitle().equals("§nGames config")){				
			for(Game gameList : RFTB.getGameManager().getGames()) {
				String gameName = gameList.getName();
				int slot = ConfigMainMenu.getArenaWithSlot().get(gameName);
				
				if (e.getSlot() == slot) {
					e.setCancelled(true);
					
					player.closeInventory();
					
					ConfigMenu.openMenu(player, gameName);
					
					return;
				}
			}
		}
		
		 if (e.getInventory().getTitle().equals("§nSelect chest type")){				
			 for (String types : RFTB.getConfigManager().getChests().getConfigurationSection("Chests.Type").getKeys(false)) {
				 int slot = ChestMenu.getSlot().get(types);
					
				 if (e.getSlot() == slot) {
					 e.setCancelled(true);
					 
					 for(String chests : RFTB.getConfigManager().getChests().getConfigurationSection("Chests placed").getKeys(false)) {
						 String world = RFTB.getConfigManager().getChests().getString("Chests placed." + chests + ".World");
							
						 int x = RFTB.getConfigManager().getChests().getInt("Chests placed." + chests + ".X");
						 int y = RFTB.getConfigManager().getChests().getInt("Chests placed." + chests + ".Y");
						 int z = RFTB.getConfigManager().getChests().getInt("Chests placed." + chests + ".Z");	
							
						 World w = Bukkit.getWorld(world);
							
						 Location locchest = new Location(w, x,y,z);
							
						 if (PlaceListener.getPlayerLocation().get(player).equals(locchest)) {
							 RFTB.getConfigManager().getChests().set("Chests placed." + chests + ".Type", types);
								
							 RFTB.getConfigManager().saveChests();
								
							 PlaceListener.getPlayerLocation().remove(player);
								
							 player.playSound(player.getLocation(), Sound.ANVIL_USE, 1, 1);						
							 player.sendMessage(ChatColor.GREEN + "Chest type " + types + " set in X: " + x + ", Y: " + y + ", Z: " + z);	
								
							 player.closeInventory();
						 
						 }	
					 }
				 }
			 }
		 }
		
		if (ConfigMenu.getOpen().containsKey(player)) {
			String map = ConfigMenu.getOpen().get(player);
			
			if (e.getInventory().getTitle().equals(map + " Config")){	
				if (e.getSlot() == 30) {
					e.setCancelled(true);
					player.closeInventory();
					
					ConfigMainMenu.openMenu(player);
					
					return;
				}
				
				if (e.isRightClick()) {
					if (e.getSlot() == 10) {
						if (RFTB.getConfigManager().getMaps().getInt("Games." + map + ".Min players") > 2) {	
							e.setCancelled(true);
								
							RFTB.getConfigManager().getMaps().set("Games." + map + ".Min players", RFTB.getConfigManager().getMaps().getInt("Games." + map + ".Min players") - 1); 
							RFTB.getConfigManager().saveMaps();
								
							player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
								
							player.sendMessage(ChatColor.GREEN + "Minium players for map " + map + " has setted successfully! (" + RFTB.getConfigManager().getMaps().getInt("Games." + map + ".Min players") + ")");
							
							return;
						}else {
							e.setCancelled(true);
							
							player.sendMessage("§cMinium players of all games is 2! Please, change the min players.");
							
							player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
							
							return;
						}
					}
					
					if (e.getSlot() == 11) {
						if (RFTB.getConfigManager().getMaps().getInt("Games." + map + ".Min players") < RFTB.getConfigManager().getMaps().getInt("Games." + map + ".Max players")) {	
							e.setCancelled(true);
								
							RFTB.getConfigManager().getMaps().set("Games." + map + ".Max players", RFTB.getConfigManager().getMaps().getInt("Games." + map + ".Max players") - 1); 
							RFTB.getConfigManager().saveMaps();
																
							player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
								
							player.sendMessage(ChatColor.GREEN + "Maximum players for map " + map + " has setted successfully! (" + RFTB.getConfigManager().getMaps().getInt("Games." + map + ".Max players") + ")");
						
							return;
						}else {
							e.setCancelled(true);
							
							player.sendMessage("§cThe maximum number of players must be less than the minium number of players.");
							
							player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
						
							return;
						}
					}
					
					if (e.getSlot() == 16) {
						if (RFTB.getConfigManager().getMaps().contains("Games." + map + ".Portal point to chest") && RFTB.getConfigManager().getMaps().contains("Games." + map + ".Portal point to back")) {
							e.setCancelled(true);
							
							String world = RFTB.getConfigManager().getMaps().getString("Games." + map + ".Portal point to chest.World");
							
							int x = RFTB.getConfigManager().getMaps().getInt("Games." + map + ".Portal point to chest.X");
							int y = RFTB.getConfigManager().getMaps().getInt("Games." + map + ".Portal point to chest.Y");
							int z = RFTB.getConfigManager().getMaps().getInt("Games." + map + ".Portal point to chest.Z");	

							World w = Bukkit.getWorld(world);
							
							Location locportal = new Location(w, x,y,z);
							
							PortalManager.removePortal(locportal);
							
							RFTB.getConfigManager().getMaps().set("Games." + map + ".Portal point to chest", null);							
							RFTB.getConfigManager().saveMaps();
							
							Game.getArena(map).setPortalToChest(false);
							
							String worlda = RFTB.getConfigManager().getMaps().getString("Games." + map + ".Portal point to back.World");
							
							int xa = RFTB.getConfigManager().getMaps().getInt("Games." + map + ".Portal point to back.X");
							int ya = RFTB.getConfigManager().getMaps().getInt("Games." + map + ".Portal point to back.Y");
							int za = RFTB.getConfigManager().getMaps().getInt("Games." + map + ".Portal point to back.Z");	

							World wa = Bukkit.getWorld(worlda);
							
							Location locportala = new Location(wa, xa,ya,za);
							
							PortalManager.removePortal(locportala);
							
							RFTB.getConfigManager().getMaps().set("Games." + map + ".Portal point to back", null);							
							RFTB.getConfigManager().saveMaps();
							
							player.playSound(player.getLocation(), Sound.ANVIL_USE, 1, 1);
							player.sendMessage("§aThe portals of " + map + " has removed successfully!");
							
							Game.getArena(map).setPortalToBack(false);
							
							return;
						}else { 
							e.setCancelled(true);
							
							player.sendMessage("§cPlease, first create a two portals!");
							
							player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
							
							return;
						}
					}					
					e.setCancelled(true);
					
					return;
				}

				if (e.isShiftClick()) {
					if (e.getSlot() == 16) {
						if (!RFTB.getConfigManager().getMaps().contains("Games." + map + ".Portal point to back")) {
							e.setCancelled(true);
								
							player.closeInventory();
							player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
							player.sendMessage(ChatColor.GREEN + "Place the ender portal frame to create map portal in back!");
							
							ItemStack portal = new ItemStack(Material.ENDER_PORTAL_FRAME);
					   	 	ItemMeta portal1 = portal.getItemMeta();	
							portal1.setDisplayName("§aSet portal to back §7(" + map + ")");
													
							portal.setItemMeta(portal1);
							
							player.getInventory().addItem(portal);
							PlaceListener.getMap().put(player, map);
							
							return;
						}else {
							e.setCancelled(true);
							
							player.sendMessage("§cPlease, first remove a portals!");
							
							player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
							
							return;
						}
					}					
					e.setCancelled(true);
					
					return;
				}
				
				if (e.isLeftClick()) {
					if (e.getSlot() == 10) {
						if (RFTB.getConfigManager().getMaps().getInt("Games." + map + ".Min players") < RFTB.getConfigManager().getMaps().getInt("Games." + map + ".Max players")) {	
							e.setCancelled(true);
								
							RFTB.getConfigManager().getMaps().set("Games." + map + ".Min players", RFTB.getConfigManager().getMaps().getInt("Games." + map + ".Min players") + 1); 
							RFTB.getConfigManager().saveMaps();
								
							player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
								
							player.sendMessage(ChatColor.GREEN + "Minium players for map " + map + " has setted successfully! (" + RFTB.getConfigManager().getMaps().getInt("Games." + map + ".Min players") + ")");
						
							return;
						}else {
							e.setCancelled(true);
							
							player.sendMessage("§cThe minium number of players must be less than the maximum number of players.");
								
							player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
							
							return;
						}
					}
					
					if (e.getSlot() == 11) {
						e.setCancelled(true);
						
						RFTB.getConfigManager().getMaps().set("Games." + map + ".Max players", RFTB.getConfigManager().getMaps().getInt("Games." + map + ".Max players") + 1); 
						RFTB.getConfigManager().saveMaps();
															
						player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
							
						player.sendMessage(ChatColor.GREEN + "Maximum players for map " + map + " has setted successfully! (" + RFTB.getConfigManager().getMaps().getInt("Games." + map + ".Max players") + ")");
					
						return;
					}
					
					if (e.getSlot() == 12) {
						e.setCancelled(true);
							
						player.closeInventory();
						player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
						player.sendMessage(ChatColor.GREEN + "Place the anvil on the point of beast start!");
						
						ItemStack beast = new ItemStack(Material.ANVIL);
				   	 	ItemMeta beast1 = beast.getItemMeta();	
						beast1.setDisplayName("§aSet beast point §7(" + map + ")");
												
						beast.setItemMeta(beast1);
						
						player.getInventory().addItem(beast);
						PlaceListener.getMap().put(player, map);
						
						return;
					}
					
					if (e.getSlot() == 13) {
						e.setCancelled(true);
							
						player.closeInventory();
						player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
						player.sendMessage(ChatColor.GREEN + "Place the beacon on the point of players start!");
						
						ItemStack beacon = new ItemStack(Material.BEACON);
				   	 	ItemMeta beacon1 = beacon.getItemMeta();	
						beacon1.setDisplayName("§aSet start point §7(" + map + ")");
												
						beacon.setItemMeta(beacon1);
						
						player.getInventory().addItem(beacon);
						PlaceListener.getMap().put(player, map);
						
						return;
					}
					
					if (e.getSlot() == 14) {
						e.setCancelled(true);
							
						player.closeInventory();
						player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
						player.sendMessage(ChatColor.GREEN + "Place the grass on the spawn arena!");
						
						ItemStack grass = new ItemStack(Material.GRASS);
				   	 	ItemMeta grass1 = grass.getItemMeta();	
						grass1.setDisplayName("§aSet spawn §7(" + map + ")");
												
						grass.setItemMeta(grass1);
						
						player.getInventory().addItem(grass);
						PlaceListener.getMap().put(player, map);
						
						return;
					}
					
					if (e.getSlot() == 15) {
						e.setCancelled(true);
							
						player.closeInventory();
						player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
						player.sendMessage(ChatColor.GREEN + "Place the wool on the spectation point!");
						
						ItemStack wool = new ItemStack(Material.WOOL);
				   	 	ItemMeta wool1 = wool.getItemMeta();	
						wool1.setDisplayName("§aSet spectation point §7(" + map + ")");
												
						wool.setItemMeta(wool1);
						
						player.getInventory().addItem(wool);
						PlaceListener.getMap().put(player, map);
						
						return;
					}
					
					if (e.getSlot() == 16) {
						if (!RFTB.getConfigManager().getMaps().contains("Games." + map + ".Portal point to chest")) {
							e.setCancelled(true);
								
							player.closeInventory();
							player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
							player.sendMessage(ChatColor.GREEN + "Place the ender portal frame to create map portal!");
							
							ItemStack portal = new ItemStack(Material.ENDER_PORTAL_FRAME);
					   	 	ItemMeta portal1 = portal.getItemMeta();	
							portal1.setDisplayName("§aSet portal to chest §7(" + map + ")");
													
							portal.setItemMeta(portal1);
							
							player.getInventory().addItem(portal);
							PlaceListener.getMap().put(player, map);
							
							return;
						}else {
							e.setCancelled(true);
							
							player.sendMessage("§cPlease, first remove a portals!");
							
							player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
							
							return;
						}
					}
					
					if (e.getSlot() == 19) {
						e.setCancelled(true);
						
						player.closeInventory();
						player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
						player.sendMessage(ChatColor.GREEN + "Place the chest on the zone of chest!");
						
						ItemStack chest = new ItemStack(Material.CHEST);
				   	 	ItemMeta chest1 = chest.getItemMeta();	
						chest1.setDisplayName("§aSet chest zone §7(" + map + ")");
												
						chest.setItemMeta(chest1);
						
						player.getInventory().addItem(chest);
						PlaceListener.getMap().put(player, map);	
						
						return;
					}	
					
					if (e.getSlot() == 20) {
						e.setCancelled(true);
							
						player.closeInventory();
						player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
						player.sendMessage(ChatColor.GREEN + "Place the end stone on the point of players end!");
						
						ItemStack endstone = new ItemStack(Material.ENDER_STONE);
				   	 	ItemMeta endstone1 = endstone.getItemMeta();	
						endstone1.setDisplayName("§aSet end point §7(" + map + ")");
												
						endstone.setItemMeta(endstone1);
						
						player.getInventory().addItem(endstone);
						PlaceListener.getMap().put(player, map);
						
						return;
					}
					
					if (e.getSlot() == 21) {
						e.setCancelled(true);
							
						player.closeInventory();
						player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
						player.sendMessage(ChatColor.GREEN + "Place the enchantment table on the point of beast wait");
						
						ItemStack endstone = new ItemStack(Material.ENCHANTMENT_TABLE);
				   	 	ItemMeta endstone1 = endstone.getItemMeta();	
						endstone1.setDisplayName("§aSet beast wait point §7(" + map + ")");
												
						endstone.setItemMeta(endstone1);
						
						player.getInventory().addItem(endstone);
						PlaceListener.getMap().put(player, map);
						
						return;
					}
					
					if (e.getSlot() == 32) {
						e.setCancelled(true);
						
						player.closeInventory();
						player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
						player.sendMessage(ChatColor.GREEN + "Place the chest and select type!");
						
						ItemStack chest = new ItemStack(Material.CHEST);
				   	 	ItemMeta chest1 = chest.getItemMeta();	
						chest1.setDisplayName("§aSet special chest");
												
						chest.setItemMeta(chest1);
						
						player.getInventory().addItem(chest);
						PlaceListener.getMap().put(player, map);	
						
						return;
					}	
					
					if (e.getSlot() == 31) {
						e.setCancelled(true);
						
						RFTB.getConfigManager().getMaps().set("Games." + map, null);
						
						player.closeInventory();
						player.sendMessage(ChatColor.GREEN + "Arena " + map + " removed successfully");
						
						RFTB.getConfigManager().saveMaps();
						GameManager.LoadGames();
						
						return;
					}
				}
			}		
		}
	}
	
	@EventHandler
	public void CloseMenu(InventoryCloseEvent e) {
		if (ConfigMenu.getOpen().containsKey(e.getPlayer())) {
			ConfigMenu.getOpen().remove(e.getPlayer());
			
			return;
		}
		
		if (VoteMenu.getOpen().containsKey(e.getPlayer())) {
			VoteMenu.getOpen().remove(e.getPlayer());
			
			return;
		}
		
		if (GameMenu.getOpen().containsKey(e.getPlayer())) {
			GameMenu.getOpen().remove(e.getPlayer());
			
			return;
		}
		
		if (PlaceListener.getPlayerLocation().containsKey(e.getPlayer())) {
			Location loc = PlaceListener.getPlayerLocation().get(e.getPlayer());
			Block block = loc.getBlock();
			BlockState state = block.getState();
			
			state.setType(Material.AIR);
			state.update(true);
			
			PlaceListener.getPlayerLocation().remove(e.getPlayer());
			
			return;
		}
	}
}
