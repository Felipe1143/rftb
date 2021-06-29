package com.mancosyt.rftb.commands;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.mancosyt.rftb.RFTB;
import com.mancosyt.rftb.Utils;
import com.mancosyt.rftb.game.Game;
import com.mancosyt.rftb.game.GameManager;
import com.mancosyt.rftb.game.GamePlayer;
import com.mancosyt.rftb.listeners.PlaceListener;
import com.mancosyt.rftb.manager.PortalManager;
import com.mancosyt.rftb.menus.ConfigMainMenu;
import com.mancosyt.rftb.menus.GameMenu;
import com.mancosyt.rftb.object.IStatus;
import com.mancosyt.rftb.object.IStats;
import com.mancosyt.rftb.object.IStats.StatsType;

import net.md_5.bungee.api.ChatColor;

public class RFTBCommand implements CommandExecutor{
    private final JavaPlugin javaPlugin;

    public RFTBCommand(JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
    }
	
	public boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
			
			return true;
		}catch (NumberFormatException ex) {
			return false;
		}
	}
	
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "This command is only for users!");
            
            return true;
        }
        
        Player player = (Player) commandSender;
        
        if (args.length == 0) {
        	this.help(commandSender);
        	
            return true;
        }	
        
        if (args[0].equalsIgnoreCase("join")) {
            this.join(commandSender, args);
            
            return true;        	
        }
        
        if (args[0].equalsIgnoreCase("leave")) {
            this.leave(commandSender);
            
            return true;        	
        }
        
        if (args[0].equalsIgnoreCase("games")) {
            this.games(commandSender);
            
            return true;        	
        }
        
        if (args[0].equalsIgnoreCase("create")) {
        	if (player.hasPermission("rftb.admin")) {       	
	            this.createGame(commandSender, args);
	            
	            return true;
        	}else {
        		player.sendMessage(RFTB.getConfigManager().get("messages.yml", RFTB.getConfigManager().getMessages().getString("Error messages.Permission error")));	
				
        		 return true;
        	}
        }       
        
        if (args[0].equalsIgnoreCase("admin")) {
        	if (player.hasPermission("rftb.admin")) {       	
	            this.admin(commandSender);
	            
	            return true;
        	}else {
        		player.sendMessage(RFTB.getConfigManager().get("messages.yml", RFTB.getConfigManager().getMessages().getString("Error messages.Permission error")));	
				
        		 return true;
        	}
        }      
        
        if (args[0].equalsIgnoreCase("config")) {
        	if (player.hasPermission("rftb.admin")) {       	
	            this.config(commandSender);
	            
	            return true;
        	}else {
        		player.sendMessage(RFTB.getConfigManager().get("messages.yml", RFTB.getConfigManager().getMessages().getString("Error messages.Permission error")));	
				
        		 return true;
        	}
        }      
        
        if (args[0].equalsIgnoreCase("mapbuilder")) {
        	if (player.hasPermission("rftb.admin")) {       	
	            this.builder(commandSender);
	            
	            return true;
        	}else {
        		player.sendMessage(RFTB.getConfigManager().get("messages.yml", RFTB.getConfigManager().getMessages().getString("Error messages.Permission error")));	
				
        		return true;
        	}
        }      
        
        if (args[0].equalsIgnoreCase("save")) {
        	if (player.hasPermission("rftb.admin")) {       	
	            this.save(commandSender);
	            
	            return true;
        	}else {
        		player.sendMessage(RFTB.getConfigManager().get("messages.yml", RFTB.getConfigManager().getMessages().getString("Error messages.Permission error")));	
				
        		return true;
        	}
        }      
        
        if (args[0].equalsIgnoreCase("setspawn")) {
        	if (player.hasPermission("rftb.admin")) {       	
	            this.spawn(commandSender, args);
	            
	            return true;
        	}else {
        		player.sendMessage(RFTB.getConfigManager().get("messages.yml", RFTB.getConfigManager().getMessages().getString("Error messages.Permission error")));	
				
        		 return true;
        	}
        }      
        
        if (args[0].equalsIgnoreCase("database")) {
        	if (player.hasPermission("rftb.admin")) {       	
	            this.database(commandSender, args);
	            
	            return true;
        	}else {
        		player.sendMessage(RFTB.getConfigManager().get("messages.yml", RFTB.getConfigManager().getMessages().getString("Error messages.Permission error")));	
				
        		 return true;
        	}
        }      
        
        if (args[0].equalsIgnoreCase("setchestzone")) {
        	if (player.hasPermission("rftb.admin")) {       	
	            this.chest(commandSender, args);
	            
	            return true;
        	}else {
        		player.sendMessage(RFTB.getConfigManager().get("messages.yml", RFTB.getConfigManager().getMessages().getString("Error messages.Permission error")));	
				
        		 return true;
        	}
        }     
        
        if (args[0].equalsIgnoreCase("set")) {
        	if (player.hasPermission("rftb.admin")) {       	
	            this.set(commandSender, args);
	            
	            return true;
        	}else {
        		player.sendMessage(RFTB.getConfigManager().get("messages.yml", RFTB.getConfigManager().getMessages().getString("Error messages.Permission error")));	
				
				return true;
        	}
        }    
        
        if (args[0].equalsIgnoreCase("stats")) {
        	if (player.hasPermission("rftb.admin")) {       	
	            this.stats(commandSender, args);
	            
	            return true;
        	}else {
        		player.sendMessage(RFTB.getConfigManager().get("messages.yml", RFTB.getConfigManager().getMessages().getString("Error messages.Permission error")));	
				
				return true;
        	}
        }    
        
        if (args[0].equalsIgnoreCase("delete")) {
        	if (player.hasPermission("rftb.admin")) {       	
	            this.delete(commandSender, args);
	            
	            return true;
        	}else {
        		player.sendMessage(RFTB.getConfigManager().get("messages.yml", RFTB.getConfigManager().getMessages().getString("Error messages.Permission error")));	
				
				return true;
        	}
        }    
        
        if (args[0].equalsIgnoreCase("start")) {
        	if (player.hasPermission("rftb.admin")) {       	
	            this.start(commandSender, args);
	            
	            return true;
        	}else {
        		player.sendMessage(RFTB.getConfigManager().get("messages.yml", RFTB.getConfigManager().getMessages().getString("Error messages.Permission error")));	
				
				return true;
        	}
        }    
        
        if (args[0].equalsIgnoreCase("portal")) {
        	if (player.hasPermission("rftb.admin")) {       	
	            this.portal(commandSender, args);
	            
	            return true;
        	}else {
        		player.sendMessage(RFTB.getConfigManager().get("messages.yml", RFTB.getConfigManager().getMessages().getString("Error messages.Permission error")));	
				
				return true;
        	}
        }    
        
        if (args[0].equalsIgnoreCase("setlobby")) {
        	if (player.hasPermission("rftb.admin")) {       	
	            this.lobby(commandSender);
	            
	            return true;
        	}else {
        		player.sendMessage(RFTB.getConfigManager().get("messages.yml", RFTB.getConfigManager().getMessages().getString("Error messages.Permission error")));	
				
				return true;
        	}
        }    
    
        commandSender.sendMessage(ChatColor.RED + "Please use /rftb help");       
        
        return true;
    }
		
	private void help(CommandSender commandSender) {
		Bukkit.getScheduler().runTaskAsynchronously(this.javaPlugin, () -> {
            Player player = (Player) commandSender;
		
            player.sendMessage("§a§m---------------------------------");		
			player.sendMessage("§aPlayer commands: ");
			player.sendMessage("§a  - §f/rftb join §a--> §fJoin an arena");
			player.sendMessage("§a  - §f/rftb leave §a--> §fLeave an arena");
			player.sendMessage("§a  - §f/rftb stats §a--> §fPlayer stats");
			player.sendMessage("§a  - §f/rftb games §a--> §fGUI of maps");
			player.sendMessage("§6  - §f/rftb admin §6--> §fAdmin commands");
			player.sendMessage("§a§m---------------------------------");

			player.playSound(player.getLocation(), Sound.ENDERDRAGON_WINGS, 1, 1);
        });
	}
	
	private void save(CommandSender commandSender) {
		Bukkit.getScheduler().runTaskAsynchronously(this.javaPlugin, () -> {
			GameManager.LoadGames();
        });
	}
	
	private void admin(CommandSender commandSender) {
		Bukkit.getScheduler().runTaskAsynchronously(this.javaPlugin, () -> {
            Player player = (Player) commandSender;
		
            player.sendMessage("§6§m---------------------------------");		
        	player.sendMessage("§6Admin commands: ");
        	player.sendMessage("§6  - §f/rftb create §6--> §fCreate arena");
        	player.sendMessage("§6  - §f/rftb remove §6--> §fRemove an arena");
        	player.sendMessage("§6  - §f/rftb start §6--> §fStart an arena");
        	player.sendMessage("§6  - §f/rftb setlobby §6--> §fSet main spawn");
        	player.sendMessage("§6  - §f/rftb mapbuilder §6--> §fMap builder in GUI");
        	player.sendMessage("§6  - §f/rftb config §6--> §fMap builder in commands");
        	player.sendMessage("§6  - §f/rftb reload §6--> §fReload configs");
        	player.sendMessage("§6§m---------------------------------");
        		
        	player.playSound(player.getLocation(), Sound.ENDERDRAGON_WINGS, 1, 1);
        });
	}
	
	
	private void config(CommandSender commandSender) {
		Bukkit.getScheduler().runTaskAsynchronously(this.javaPlugin, () -> {
	        Player player = (Player) commandSender;
		
	        player.sendMessage("§6§m---------------------------------");		
			player.sendMessage("§6Arena commands: ");
			player.sendMessage("§6  - §f/rftb create §6--> §fCreate arena");
			player.sendMessage("§6  - §f/rftb remove §6--> §fRemove an arena");
			player.sendMessage("§6  - §f/rftb setlobby §6--> §fSet main spawn");
			player.sendMessage("§6  - §f/rftb setspawn §6--> §fSet spawn of map");
			player.sendMessage("§6  - §f/rftb set startpoint §6--> §fSet a point to teleport players on game start");
			player.sendMessage("§6  - §f/rftb set beastpoint §6--> §fSet a point to teleport beast");
			player.sendMessage("§6  - §f/rftb set spectatorpoint §6--> §fSet a point to teleport spectators");
			player.sendMessage("§6  - §f/rftb set min §6--> §fSet min players for map");
			player.sendMessage("§6  - §f/rftb set max §6--> §fSet max players for map");
			player.sendMessage("§6  - §f/rftb portal create §6--> §fCreate portal for map");
			player.sendMessage("§6  - §f/rftb portal remove §6--> §fRemove portal for map");
			player.sendMessage("§6  - §f/rftb setchestzone §6--> §fSet zone of chest");
			player.sendMessage("§6§m---------------------------------");
				
			player.playSound(player.getLocation(), Sound.ENDERDRAGON_WINGS, 1, 1);
	    });
	}
	
	private void builder(CommandSender commandSender) {
		Bukkit.getScheduler().runTaskAsynchronously(this.javaPlugin, () -> {
            Player player = (Player) commandSender;
            ConfigMainMenu.openMenu(player);
				
            player.playSound(player.getLocation(), Sound.ENDERDRAGON_WINGS, 1, 1);
        });
	}
	
	private void join(CommandSender commandSender, String[] args) {
		Player player = (Player) commandSender;
		
        if (args.length == 1) {
			player.sendMessage(ChatColor.RED + "Please, use /rftb join <map>");
			
			player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
        }else {					
			String name = args[1];
			
			RFTB.getGameManager().onPlayerJoin(player, name);
        }
	}
	
	private void database(CommandSender commandSender, String[] args) {
		Player player = (Player) commandSender;
		
        if (args.length == 1) {
			player.sendMessage(ChatColor.RED + "Please, use /rftb database <jugador>");
			
			player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
        }else {					
			String name = args[1];
			
			RFTB.getDatabaseManager().query("DELETE FROM Stats WHERE name='" + name + "';");
			
			player.sendMessage(ChatColor.RED + "¡El jugador " + name + " fue removido de la base de datos!");
        }
	}
	
	private void leave(CommandSender commandSender) {
        Player player = (Player) commandSender;

        RFTB.getGameManager().onPlayerLeave(player, GamePlayer.getMeta(player).getArena());
	}
	
	private void games(CommandSender commandSender) {
		Bukkit.getScheduler().runTaskAsynchronously(this.javaPlugin, () -> {
            Player player = (Player) commandSender;

            GameMenu.openMenu(player);
		});
	}
	
	private void spawn(CommandSender commandSender, String[] args) {
		Bukkit.getScheduler().runTaskAsynchronously(this.javaPlugin, () -> {
            Player player = (Player) commandSender;
		
            if (args.length == 1) {
				player.sendMessage(ChatColor.RED + "Please, use /rftb setspawn <map>");
				
				player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
			}else {					
				String name = args[1];
				
				if (RFTB.getConfigManager().getMaps().contains("Games." + name)) {
					RFTB.getConfigManager().getMaps().set("Games." + name + ".Spawn.World", player.getWorld().getName());
					RFTB.getConfigManager().getMaps().set("Games." + name + ".Spawn.X", player.getLocation().getX());
					RFTB.getConfigManager().getMaps().set("Games." + name + ".Spawn.Y", player.getLocation().getY());
					RFTB.getConfigManager().getMaps().set("Games." + name + ".Spawn.Z", player.getLocation().getZ());
					RFTB.getConfigManager().getMaps().set("Games." + name + ".Spawn.Yaw", player.getLocation().getYaw());
					RFTB.getConfigManager().getMaps().set("Games." + name + ".Spawn.Pitch", player.getLocation().getPitch());
				
					RFTB.getConfigManager().saveMaps();
					
					Game game = Game.getArena(name);
					
					game.setSpawn(new Location(player.getWorld(), 
							player.getLocation().getX(),
							player.getLocation().getY(),
							player.getLocation().getZ(),
							player.getLocation().getYaw(),
							player.getLocation().getPitch()));
					
					player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
					
					player.sendMessage(ChatColor.GREEN + "Spawn " + args[1] + " set in X: " + player.getLocation().getBlockX() + ", Y: " + player.getLocation().getBlockY() + ", Z: " + player.getLocation().getBlockZ() + ", YAW: " + player.getLocation().getYaw() + ", PITCH: " + player.getLocation().getPitch());					
				}else {							
					player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
					
					player.sendMessage(ChatColor.RED + "The game '" + name + "' not exist");
				}
			}
        });
	}
	
	private void chest(CommandSender commandSender, String[] args) {
		Bukkit.getScheduler().runTaskAsynchronously(this.javaPlugin, () -> {
            Player player = (Player) commandSender;
		
            if (args.length == 1) {
				player.sendMessage(ChatColor.RED + "Please, use /rftb setchestzone <map>");
				
				player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
			}else {					
				String name = args[1];
				
				if (RFTB.getConfigManager().getMaps().contains("Games." + name)) {
					RFTB.getConfigManager().getMaps().set("Games." + name + ".Chest zone.World", player.getWorld().getName());
					RFTB.getConfigManager().getMaps().set("Games." + name + ".Chest zone.X", player.getLocation().getX());
					RFTB.getConfigManager().getMaps().set("Games." + name + ".Chest zone.Y", player.getLocation().getY());
					RFTB.getConfigManager().getMaps().set("Games." + name + ".Chest zone.Z", player.getLocation().getZ());
					RFTB.getConfigManager().getMaps().set("Games." + name + ".Chest zone.Yaw", player.getLocation().getYaw());
					RFTB.getConfigManager().getMaps().set("Games." + name + ".Chest zone.Pitch", player.getLocation().getPitch());
				
					RFTB.getConfigManager().saveMaps();
					
					Game game = Game.getArena(name);
					
					game.setChestZone(new Location(player.getWorld(), 
							player.getLocation().getX(),
							player.getLocation().getY(),
							player.getLocation().getZ(),
							player.getLocation().getYaw(),
							player.getLocation().getPitch()));
					
					player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
					
					player.sendMessage(ChatColor.GREEN + "Chest zone " + args[1] + " set in X: " + player.getLocation().getBlockX() + ", Y: " + player.getLocation().getBlockY() + ", Z: " + player.getLocation().getBlockZ() + ", YAW: " + player.getLocation().getYaw() + ", PITCH: " + player.getLocation().getPitch());					
				}else {							
					player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
					
					player.sendMessage(ChatColor.RED + "The game '" + name + "' not exist");
				}
			}
        });
	}
	
	private void lobby(CommandSender commandSender) {
		Bukkit.getScheduler().runTaskAsynchronously(this.javaPlugin, () -> {
            Player player = (Player) commandSender;
						
            RFTB.getConfigManager().getMaps().set("Lobby.World", player.getWorld().getName());
            RFTB.getConfigManager().getMaps().set("Lobby.X", player.getLocation().getX());
            RFTB.getConfigManager().getMaps().set("Lobby.Y", player.getLocation().getY());
            RFTB.getConfigManager().getMaps().set("Lobby.Z", player.getLocation().getZ());
            RFTB.getConfigManager().getMaps().set("Lobby.Yaw", player.getLocation().getYaw());
            RFTB.getConfigManager().getMaps().set("Lobby.Pitch", player.getLocation().getPitch());
			
            RFTB.getConfigManager().saveMaps();
				
            player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
				
            player.sendMessage(ChatColor.GREEN + "Main lobby set in X: " + player.getLocation().getBlockX() + ", Y: " + player.getLocation().getBlockY() + ", Z: " + player.getLocation().getBlockZ() + ", Yaw: " + player.getLocation().getYaw() + ", Pitch: " + player.getLocation().getPitch());			
        });
	}
	
	private void delete(CommandSender commandSender, String[] args) {
		Bukkit.getScheduler().runTaskAsynchronously(this.javaPlugin, () -> {
            Player player = (Player) commandSender;
            if (args.length == 1) {
				player.sendMessage(ChatColor.RED + "Please, use /rftb delete <map>");
				
				player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);		
			}else {					
				String name = args[1];
					
				if (RFTB.getConfigManager().getMaps().contains("Games." + name)) {	
						RFTB.getConfigManager().getMaps().set("Games." + name, null);
						
						player.sendMessage(ChatColor.GREEN + "Arena " + name + " removed successfully");
						
						RFTB.getConfigManager().saveMaps();
						GameManager.LoadGames();		
				}else {
					player.sendMessage(ChatColor.RED + "The game '" + name + "' not exist");
					
					player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);				
				}
			}
        });
	}
	
	private void start(CommandSender commandSender, String[] args) {
		Bukkit.getScheduler().runTaskAsynchronously(this.javaPlugin, () -> {
            Player player = (Player) commandSender;
            if (args.length == 1) {
				player.sendMessage(ChatColor.RED + "Please, use /rftb start <map>");
				
				player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);		
			}else {					
				String map = args[1];
					
				Game game = Game.getArena(map);
				
				if (game.getStatus() == IStatus.WAITING) {
					if (game.getPlayers().size() != 0) {
						RFTB.getGameManager().startGame(map, true);
						
						player.sendMessage(ChatColor.GREEN + "Starting...");
						
						player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
					}else {
						player.sendMessage(ChatColor.RED + "The map '" + map + "' has no players inside");
						
						player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
					}
				}else {
					player.sendMessage(ChatColor.RED + "The game '" + map + "' has already begun");
					
					player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);	
				}
			}
        });
	}
	
	private void stats(CommandSender commandSender, String[] args) {
		Bukkit.getScheduler().runTaskAsynchronously(this.javaPlugin, () -> {
            Player player = (Player) commandSender;
                				
            if (args.length == 1 || args.length == 2 || args.length == 3) {				
				player.sendMessage(ChatColor.RED + "Please, use /rftb stats <jugador> <stat> <cantidad>");
    				
    			player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
    		}else {	        			
        		StatsType stat = StatsType.valueOf(args[2]);       		
        		String playerStat = args[1];   		
        		int valor = Integer.parseInt(args[3]);
        		
        		IStats.get(playerStat).setStat(stat, valor);
        		
				player.sendMessage("§a¡Estadistica cambiada correctamente!");
            }
		});
	}
	
	private void portal(CommandSender commandSender, String[] args) {
		Bukkit.getScheduler().runTaskAsynchronously(this.javaPlugin, () -> {
            Player player = (Player) commandSender;
            
            if (args[1].equalsIgnoreCase("remove")) {        				
    			if (args.length == 1 || args.length == 2) {						
					player.sendMessage(ChatColor.RED + "Please, use /rftb portal remove <map>");
    				
    				player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
    			}else {	        			
        			String map = args[2];
        			
        			if (RFTB.getConfigManager().getMaps().contains("Games." + map + ".Portal point")) {
						String world = RFTB.getConfigManager().getMaps().getString("Games." + map + ".Portal point.World");
						
						int x = RFTB.getConfigManager().getMaps().getInt("Games." + map + ".Portal point.X");
						int y = RFTB.getConfigManager().getMaps().getInt("Games." + map + ".Portal point.Y");
						int z = RFTB.getConfigManager().getMaps().getInt("Games." + map + ".Portal point.Z");	

						World w = Bukkit.getWorld(world);
						
						Location locportal = new Location(w, x,y,z);
						
						PortalManager.removePortal(locportal);
						
						RFTB.getConfigManager().getMaps().set("Games." + map + ".Portal point", null);							
						RFTB.getConfigManager().saveMaps();
						
						player.playSound(player.getLocation(), Sound.ANVIL_USE, 1, 1);
						player.sendMessage("§aThe portal of " + map + " has removed successfully!");
						
						Game.getArena(map).setPortalToChest(false);
					}else {
						player.sendMessage("§cPlease, first create a portal!");
						
						player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
					}
    			}
            }else if (args[1].equalsIgnoreCase("create")) {        				
    			if (args.length == 1 || args.length == 2) {						
					player.sendMessage(ChatColor.RED + "Please, use /rftb portal create <map>");
    				
    				player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
    			}else {	        			
        			String map = args[2];
        			
        			if (!RFTB.getConfigManager().getMaps().contains("Games." + map + ".Portal point")) {
						player.closeInventory();
						player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
						player.sendMessage(ChatColor.GREEN + "Place the ender portal frame to create map portal!");
						
						ItemStack portal = new ItemStack(Material.ENDER_PORTAL_FRAME);
				   	 	ItemMeta portal1 = portal.getItemMeta();	
						portal1.setDisplayName("§aSet portal §7(" + map + ")");
												
						portal.setItemMeta(portal1);
						
						player.getInventory().addItem(portal);
						PlaceListener.getMap().put(player, map);
						
						Game.getArena(map).setPortalToChest(true);
					}else {		
						player.sendMessage("§cPlease, first remove portal!");
						
						player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
					}
    			}
            }
        });
	}
	
	private void set(CommandSender commandSender, String[] args) {
		Bukkit.getScheduler().runTaskAsynchronously(this.javaPlugin, () -> {
            Player player = (Player) commandSender;
		
        	if (args.length == 1) {						
        		player.sendMessage(ChatColor.RED + "Please, use /rftb admin for help");
        		
        		player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);       		        		
        	}else {
        		if (args[1].equalsIgnoreCase("startpoint")) {
        			if (args.length == 1 || args.length == 2) {						
        				player.sendMessage(ChatColor.RED + "Please, use /rftb set startpoint <map>");
        				
        				player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);     				
        			}else {      			
	        			String name = args[2];
	        			
	        			if (RFTB.getConfigManager().getMaps().contains("Games." + name)) {
	        				RFTB.getConfigManager().getMaps().set("Games." + name + ".Start point.World", player.getWorld().getName());
	        				RFTB.getConfigManager().getMaps().set("Games." + name + ".Start point.X", player.getLocation().getX());
	        				RFTB.getConfigManager().getMaps().set("Games." + name + ".Start point.Y", player.getLocation().getY());
	        				RFTB.getConfigManager().getMaps().set("Games." + name + ".Start point.Z", player.getLocation().getZ());
	        				RFTB.getConfigManager().getMaps().set("Games." + name + ".Start point.Yaw", player.getLocation().getYaw());
	        				RFTB.getConfigManager().getMaps().set("Games." + name + ".Start point.Pitch", player.getLocation().getPitch());
	        			
	        				RFTB.getConfigManager().saveMaps();
	        				
	        				Game game = Game.getArena(name);
	        				
	    					game.setStartPoint(new Location(player.getWorld(), 
	    							player.getLocation().getX(),
	    							player.getLocation().getY(),
	    							player.getLocation().getZ(),
	    							player.getLocation().getYaw(),
	    							player.getLocation().getPitch()));
	    					      				
	        				player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
	        				
	        				player.sendMessage(ChatColor.GREEN + "Start point " + args[2] + " set in X: " + player.getLocation().getBlockX() + ", Y: " + player.getLocation().getBlockY() + ", Z: " + player.getLocation().getBlockZ() + ", YAW: " + player.getLocation().getYaw() + ", PITCH: " + player.getLocation().getPitch());													
	        			}else {							
	        				player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
	        				
	        				player.sendMessage(ChatColor.RED + "The game '" + name + "' not exist");
	        			}
        			}
        		}else if (args[1].equalsIgnoreCase("spectatorpoint")) {        				
        			if (args.length == 1 || args.length == 2) {						
    					player.sendMessage(ChatColor.RED + "Please, use /rftb set spectatorpoint <map>");
        				
        				player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
        			}else {	        			
	        			String name = args[2];
	        			
	        			if (RFTB.getConfigManager().getMaps().contains("Games." + name)) {
	        				RFTB.getConfigManager().getMaps().set("Games." + name + ".Spectation point.World", player.getWorld().getName());
	        				RFTB.getConfigManager().getMaps().set("Games." + name + ".Spectation point.X", player.getLocation().getX());
	        				RFTB.getConfigManager().getMaps().set("Games." + name + ".Spectation point.Y", player.getLocation().getY());
	        				RFTB.getConfigManager().getMaps().set("Games." + name + ".Spectation point.Z", player.getLocation().getZ());
	        				RFTB.getConfigManager().getMaps().set("Games." + name + ".Spectation point.Yaw", player.getLocation().getYaw());
	        				RFTB.getConfigManager().getMaps().set("Games." + name + ".Spectation point.Pitch", player.getLocation().getPitch());
	        			
	        				RFTB.getConfigManager().saveMaps();
	        				
	        				Game game = Game.getArena(name);
	        				
	    					game.setSpectator(new Location(player.getWorld(), 
	    							player.getLocation().getX(),
	    							player.getLocation().getY(),
	    							player.getLocation().getZ(),
	    							player.getLocation().getYaw(),
	    							player.getLocation().getPitch()));
	        				
	        				player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
	        				
	        				player.sendMessage(ChatColor.GREEN + "Spectation point " + args[2] + " set in X: " + player.getLocation().getBlockX() + ", Y: " + player.getLocation().getBlockY() + ", Z: " + player.getLocation().getBlockZ() + ", YAW: " + player.getLocation().getYaw() + ", PITCH: " + player.getLocation().getPitch());											
	        			}else {							
	        				player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
	        				
	        				player.sendMessage(ChatColor.RED + "The game '" + name + "' not exist");
	        			}
        			}
        		}else if (args[1].equalsIgnoreCase("beastpoint")) {
        			if (args.length == 1 || args.length == 2) {						
        				player.sendMessage(ChatColor.RED + "Please, use /rftb set beastpoint <map>");
	        				
        				player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);			
        			}else {
        				String name = args[2];
			        			
	        			if (RFTB.getConfigManager().getMaps().contains("Games." + name)) {
	        				RFTB.getConfigManager().getMaps().set("Games." + name + ".Beast point.World", player.getWorld().getName());
	        				RFTB.getConfigManager().getMaps().set("Games." + name + ".Beast point.X", player.getLocation().getX());
	        				RFTB.getConfigManager().getMaps().set("Games." + name + ".Beast point.Y", player.getLocation().getY());
	        				RFTB.getConfigManager().getMaps().set("Games." + name + ".Beast point.Z", player.getLocation().getZ());
	        				RFTB.getConfigManager().getMaps().set("Games." + name + ".Beast point.Yaw", player.getLocation().getYaw());
	        				RFTB.getConfigManager().getMaps().set("Games." + name + ".Beast point.Pitch", player.getLocation().getPitch());
	        			
	        				RFTB.getConfigManager().saveMaps();
	        				
	        				Game game = Game.getArena(name);
	        				
	      					game.setBeastPoint(new Location(player.getWorld(), 
	    							player.getLocation().getX(),
	    							player.getLocation().getY(),
	    							player.getLocation().getZ(),
	    							player.getLocation().getYaw(),
	    							player.getLocation().getPitch()));
	        				
	        				player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
	        				
	        				player.sendMessage(ChatColor.GREEN + "Beast point " + args[2] + " set in X: " + player.getLocation().getBlockX() + ", Y: " + player.getLocation().getBlockY() + ", Z: " + player.getLocation().getBlockZ() + ", YAW: " + player.getLocation().getYaw() + ", PITCH: " + player.getLocation().getPitch());
	        				
	        			}else {							
	        				player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
	        				
	        				player.sendMessage(ChatColor.RED + "The game '" + name + "' not exist");	
	        			}
        			}
        		}else if (args[1].equalsIgnoreCase("min")) {
        			if (args.length == 1 || args.length == 2 || args.length == 3) {						
        				player.sendMessage(ChatColor.RED + "Please, use /rftb set min <map> <min-players>");
	        				
        				player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
        			}else {	        			
	        			String name = args[2];
	        				
	        			if (isInteger(args[3])) {
	        				if (Integer.parseInt(args[3]) >= 2) {
	        					if (RFTB.getConfigManager().getMaps().contains("Games." + name)) {
	        						if (RFTB.getConfigManager().getMaps().getInt("Games." + name + ".Max players") > Integer.parseInt(args[3])){													
	        							RFTB.getConfigManager().getMaps().set("Games." + name + ".Min players", Integer.parseInt(args[3]));
	        								
	        							RFTB.getConfigManager().saveMaps();
	        							
	        							Game game = Game.getArena(name);
	        							
	        							game.setMinPlayers(Integer.parseInt(args[3]));
	        								
	        							player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
	        							
	        							player.sendMessage(ChatColor.GREEN + "Minium players for map " + name + " has setted successfully! (" + args[3] + ")"); 																		
	        						}else {
	        							player.sendMessage("§cThe minium number of players must be less than the maximum number of players.");
	        							
	        							player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);	
	        						}
	        					}else {							
	        						player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
	        							
	        						player.sendMessage(ChatColor.RED + "The game '" + name + "' not exist");	
	        					}
	        				}else {
	        					player.sendMessage("§cMinium players of all games is 2! Please, change the min players.");
	        					
	        					player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);	
	        				}
	        			}else {
	        				player.sendMessage(ChatColor.RED + "Please, replace ARGUMENT 3 -> '" + args[3] + "' for numbers.");
	        				
	        				player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);	
	        			}
        			}
        		}else if (args[1].equalsIgnoreCase("max")) {        		
        			if (args.length == 1 || args.length == 2 || args.length == 3) {						
        				player.sendMessage(ChatColor.RED + "Please, use /rftb set max <map> <min-players>");
        				
        				player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);	
        			}else {       			
        				String name = args[2];
	        				
	        			if (isInteger(args[3])) {
	        				if (RFTB.getConfigManager().getMaps().contains("Games." + name)) {
	        					if (RFTB.getConfigManager().getMaps().getInt("Games." + name + ".Min players") < Integer.parseInt(args[3])){
	        						RFTB.getConfigManager().getMaps().set("Games." + name + ".Max players", Integer.parseInt(args[3]));
	        									
	        						RFTB.getConfigManager().saveMaps();
	        						
        							Game game = Game.getArena(name);
        							
        							game.setMaxPlayers(Integer.parseInt(args[3]));
	        									
	        						player.playSound(player.getLocation(), Sound.SUCCESSFUL_HIT, 1, 1);
	        								
	        						player.sendMessage(ChatColor.GREEN + "Max players for map " + name + " has setted successfully! (" + args[3] + ")");
	        					}else {
	        						player.sendMessage("§cThe maximum number of players must be less than the minium number of players.");
	        								
	        						player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
	        					}
	        				}else {							
	        					player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
	        							
	        					player.sendMessage(ChatColor.RED + "The game '" + name + "' not exist");
	        				}
	        			}else {
	        				player.sendMessage(ChatColor.RED + "Please, replace ARGUMENT 3 -> '" + args[3] + "' for numbers.");
	        				
	        				player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
	        			}
        			}
        		}
        	}
        });
	}
	
	private void createGame(CommandSender commandSender, String[] args) {
        Bukkit.getScheduler().runTaskAsynchronously(this.javaPlugin, () -> {
            Player player = (Player) commandSender;
            
            if (args.length == 1 || args.length == 2 || args.length == 3) {						
				player.sendMessage(ChatColor.RED + "Please, use /rftb create <map> <min> <max>");
				
				player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
			}else {
	            if (isInteger(args[2])) {
	        		if (isInteger(args[3])) {
						if (!RFTB.getGameManager().isExist(args[1])) {
							if (Integer.parseInt(args[2]) >= 2) {
								if (Integer.parseInt(args[2]) < Integer.parseInt(args[3])){
									if (Integer.parseInt(args[3]) > Integer.parseInt(args[2])){
										new Game(args[1], Integer.parseInt(args[3]), Integer.parseInt(args[2]));
										
										player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
										
										player.sendMessage("§aMap " + args[1] + " created successfully! (MIN: " + args[2] + "/MAX: " + args[3] + ")");
									}else {
										player.sendMessage("§cThe maximum number of players must be less than the minium number of players.");
												
										player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
									}
								}else {
									player.sendMessage("§cThe minium number of players must be less than the maximum number of players.");
									
									player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
								}
							}else {
								player.sendMessage("§cMinium players of all games is 2! Please, change the min players.");
								
								player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
							}
						}else {							
							player.sendMessage("§cThe map " + args[1] + " already exist!");
							
							player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
						}
					}else {
						player.sendMessage(ChatColor.RED + "Please, replace ARGUMENT 3 -> '" + args[3] + "' for numbers.");
						
						player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
					}
				}else {
					player.sendMessage(ChatColor.RED + "Please, replace ARGUMENT 2 -> '" + args[2] + "' for numbers.");
					
					player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1, 1);
				}	
			}
        });
	}
}
