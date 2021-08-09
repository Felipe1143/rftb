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

import com.comugamers.core.Main;
import com.felipe221.rftb.RFTB;
import com.felipe221.rftb.Utils;
import com.felipe221.rftb.game.Game;
import com.felipe221.rftb.game.GameManager;
import com.felipe221.rftb.object.IStats;
import com.felipe221.rftb.object.IStatus;

public class GameMenu {
	private static HashMap<String, Integer> list2 = new HashMap<String, Integer>();
	private static ArrayList<Integer> list3 = new ArrayList<Integer>();
	private static HashMap<Player, String> open = new HashMap<Player, String>();
	
	
	public static ArrayList<Integer> getSlot(){
		return list3;
	}
	
	public static HashMap<String, Integer> getArenaWithSlot(){
		return list2;
	}
	
	public static HashMap<Player, String> getOpen() {
		return open;
	}
	
	public static void openMenu(Player player) {	
		open.put(player, player.getName());
	
		int a = 9;
		int z = 9;
		int rows = 0;
	
		for(int games = 0; games < RFTB.getGameManager().getGames().size(); games++) {	
			if (z <= 16) {
				z = z + 1;			
			}
			
			if (z == 17) {
				z = 18;
			}
			
			if (z == 25) {
				z = 27;
			}
			
			if (z >= 18 && z <= 25){
				z = z + 1;
			}
			
			if (z >= 27 && z <= 34){
				z = z + 1;				
			}
		}

		if (z <= 16) {
			rows = 3;
		}
			
		if (z >= 18 && z <= 25){
			rows = 4;
		}
		
		if (z >= 27 && z <= 34){
			rows = 5;
		}
		
		Inventory join = Bukkit.getServer().createInventory(player, rows * 9, "» Arenas");
				
		for(Game gameList : RFTB.getGameManager().getGames()) {	
			String games = gameList.getName();
			
			if (a <= 16) {
				a = a + 1;
			}
			
			if (a == 17) {
				a = 18;
			}
			
			if (a == 25) {
				a = 27;
			}
			
			if (a >= 18 && a <= 25){
				a = a + 1;
			}
			
			if (a >= 27 && a <= 34){
				a = a + 1;
			}
		    
			list3.add(a);
			list2.put(games, a);
			
			Game game = Game.getArena(games);
			
			new BukkitRunnable() {
				@Override
				public void run() {
					if (open.containsKey(player)) {			
						ItemStack waiting = new ItemStack(Material.getMaterial(RFTB.getConfigManager().getConfig().getInt("Menus.Games.Waiting format.ID")));
						ItemMeta waitingmeta = waiting.getItemMeta();
						
						waitingmeta.setDisplayName(RFTB.getVars(player, games, RFTB.getConfigManager().getConfig().getString("Menus.Games.Waiting format.Name")));
						 
						ArrayList<String> lore= new ArrayList<String>();
						
						lore.add("");
						lore.add("§fJugadores: §a" + game.getPlayers().size() + "/" + game.getMaxPlayers());
						lore.add("§fEstado: §aEsperando");
						lore.add("");
						
						if (!IStats.get(player).hasGamePlayed(game.getName())) {
							lore.add("§c¡Nunca has jugado este mapa!");
						}else {
							lore.add("§b> §fJugadas: §b" + IStats.get(player).getGamesPlayed(game.getName()));
							lore.add("§a> §fGanadas: §a" + (!IStats.get(player).hasGameWins(game.getName()) ? "¡Ninguna!" : IStats.get(player).getGamesWin(game.getName()) ));
						}
						
						lore.add("");
						lore.add("§b§o¡Haz click para ingresar!");
						
						waitingmeta.setLore(lore);
						
						waiting.setDurability((short) RFTB.getConfigManager().getConfig().getInt("Menus.Games.Waiting format.Data"));	
						waiting.setItemMeta(waitingmeta);
						
						ItemStack starting = new ItemStack(Material.getMaterial(RFTB.getConfigManager().getConfig().getInt("Menus.Games.Starting format.ID")));
						ItemMeta startingmeta = starting.getItemMeta();
						
						startingmeta.setDisplayName(RFTB.getVars(player, games, RFTB.getConfigManager().getConfig().getString("Menus.Games.Starting format.Name")));
						 
						ArrayList<String> loreS= new ArrayList<String>();
						
						loreS.add("");
						loreS.add("§fJugadores: §a" + game.getPlayers().size() + "/" + game.getMaxPlayers());
						loreS.add("§fEstado: §6Comenzando...");
						loreS.add("");
						
						if (!IStats.get(player).hasGamePlayed(game.getName())) {
							loreS.add("§c¡Nunca has jugado este mapa!");
						}else {
							loreS.add("§b> §fJugadas: §b" + IStats.get(player).getGamesPlayed(game.getName()));
							loreS.add("§a> §fGanadas: §a" + (!IStats.get(player).hasGameWins(game.getName()) ? "¡Ninguna!" : IStats.get(player).getGamesWin(game.getName()) ));
						}
						
						loreS.add("");
						loreS.add("§b§o¡Haz click para ingresar!");
						
						startingmeta.setLore(loreS);
						
						starting.setDurability((short) RFTB.getConfigManager().getConfig().getInt("Menus.Games.Starting format.Data"));	
						starting.setItemMeta(startingmeta);
						
						ItemStack ingame = new ItemStack(Material.getMaterial(RFTB.getConfigManager().getConfig().getInt("Menus.Games.InGame format.ID")));
						ItemMeta ingamemeta = ingame.getItemMeta();
						
						ingamemeta.setDisplayName(RFTB.getVars(player, games, RFTB.getConfigManager().getConfig().getString("Menus.Games.InGame format.Name")));
						 
						ArrayList<String> loreG= new ArrayList<String>();
						
						loreG.add("");
						loreG.add("§fJugadores: §a" + game.getPlayers().size() + "/" + game.getMaxPlayers());
						loreG.add("§fEstado: §cEn juego");
						loreG.add("§fTermina en: §c" + Utils.timeString(game.getToEndTime()));
						loreG.add("");
						
						if (!IStats.get(player).hasGamePlayed(game.getName())) {
							loreG.add("§c¡Nunca has jugado este mapa!");
						}else {
							loreG.add("§b> §fJugadas: §b" + IStats.get(player).getGamesPlayed(game.getName()));
							loreG.add("§a> §fGanadas: §a" + (!IStats.get(player).hasGameWins(game.getName()) ? "¡Ninguna!" : IStats.get(player).getGamesWin(game.getName()) ));
						}
						
						loreG.add("");
						loreG.add("§b§o¡Ya no puedes ingresar!");
						
						ingamemeta.setLore(loreG);
						
						ingame.setDurability((short) RFTB.getConfigManager().getConfig().getInt("Menus.Games.InGame format.Data"));	
						ingame.setItemMeta(ingamemeta);		
						
						if (game.getStatus() == IStatus.WAITING) {
							join.setItem(list2.get(games), waiting);	
						}else if (game.getStatus() == IStatus.STARTING) {
							join.setItem(list2.get(games), starting);
						}else if (game.getStatus() == IStatus.IN_GAME) {
							join.setItem(list2.get(games), ingame);
						}
					}else {
						cancel();
					}
				}
			}.runTaskTimerAsynchronously(RFTB.getInstance(), 0, 0);
		}

		
		player.openInventory(join);	
	}
}
