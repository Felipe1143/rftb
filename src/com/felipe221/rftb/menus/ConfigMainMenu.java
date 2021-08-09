package com.felipe221.rftb.menus;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.felipe221.rftb.RFTB;
import com.felipe221.rftb.game.Game;
import com.felipe221.rftb.game.GameManager;
import com.felipe221.rftb.listeners.PlaceListener;

public class ConfigMainMenu {
	private static HashMap<String, Integer> list2 = new HashMap<String, Integer>();
	private static ArrayList<Integer> list3 = new ArrayList<Integer>();

	public static ArrayList<Integer> getSlot(){
		return list3;
	}
	
	public static HashMap<String, Integer> getArenaWithSlot(){
		return list2;
	}
	
	public static void openMenu(Player player) {
		PlaceListener.getMap().remove(player);
		
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

		Inventory join = Bukkit.getServer().createInventory(player, rows * 9, "§nGames config");
				
		for(Game game : RFTB.getGameManager().getGames()) {	
			String games = game.getName();
			
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
			
			ItemStack arena = new ItemStack(Material.getMaterial(159));
			arena.setDurability((short) 5);
			ItemMeta arenameta = arena.getItemMeta();
			
			arenameta.setDisplayName(RFTB.getVars(player, games, "&a&o%map%"));
			 
			ArrayList<String> lore= new ArrayList<String>();
			
			lore.add(RFTB.getVars(player, games, "&a--> &7Click to config arena!"));
			
			arenameta.setLore(lore);
			
			arena.setItemMeta(arenameta);
					
			join.setItem(list2.get(games), arena);	
		}		
		
		player.openInventory(join);	
	}
}
