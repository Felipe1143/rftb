package com.felipe221.rftb.menus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.felipe221.rftb.RFTB;
import com.felipe221.rftb.object.IChest;

public class ChestMenu {
	private static HashMap<String, Integer> list = new HashMap<String, Integer>();
	
	public static HashMap<String, Integer> getSlot(){
		return list;
	}
	
	public static String getKeys(String type) {
		for (String items : RFTB.getConfigManager().getChests().getConfigurationSection("Chests.Type." + type + ".Items").getKeys(false)) {
			return items;
		}
		return type;
	}
	
	public static void openMenu(Player player, String type) {		
		HashMap<String, Integer> aa = new HashMap<String, Integer>(); 
		aa.clear();
		
		Inventory join = Bukkit.getServer().createInventory(player, 27, RFTB.getConfigManager().getChests().getString("Chests.Type." + type + ".Title").replaceAll("&", "§"));
							
		for (String items : RFTB.getConfigManager().getChests().getConfigurationSection("Chests.Type." + type + ".Items").getKeys(false)) {
			aa.put(items, RFTB.getConfigManager().getChests().getInt("Chests.Type." + type + ".Items." + items + ".Amount"));
		}
		
		for (ItemStack e : IChest.lis.get(type)) {	
			if (e.getMaxStackSize() != 64) {		
				for (int a=0; a < aa.get(getKeys(type)); a++) {			
					e.setAmount(1);
									
					join.addItem(e);	
				}
			}else{				
				join.addItem(e);					
			}
		}		
		
		player.openInventory(join);	
	}
	
	public static void OpenMenuConfig(Player player) {								
		int a = 9;
		int z = 9;
		int rows = 0;
		
		for (String types : RFTB.getConfigManager().getChests().getConfigurationSection("Chests.Type").getKeys(false)) {
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

		Inventory join = Bukkit.getServer().createInventory(player, rows * 9, "§nSelect chest type");
				
		for (String types : RFTB.getConfigManager().getChests().getConfigurationSection("Chests.Type").getKeys(false)) {	
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

			list.put(types, a);
			
			ItemStack chest = new ItemStack(Material.CHEST);
			ItemMeta chestmeta = chest.getItemMeta();
			
			chestmeta.setDisplayName("§a§o" + types);
			 
			ArrayList<String> lore= new ArrayList<String>();
			
			lore.add("§a--> §7Click to set chest type!");
			
			chestmeta.setLore(lore);
			
			chest.setItemMeta(chestmeta);
					
			join.setItem(list.get(types), chest);	
		}		
		
		player.openInventory(join);	
	}
}
