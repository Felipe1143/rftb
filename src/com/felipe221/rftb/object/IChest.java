package com.felipe221.rftb.object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.felipe221.rftb.RFTB;

public class IChest{
	static ArrayList<String> types = new ArrayList<String>();
	
	public static HashMap<String, ItemStack> item = new HashMap<String, ItemStack>();
	
	public static HashMap<String, List<ItemStack>> lis = new HashMap<String, List<ItemStack>>();
		
	public static ItemStack GameItems(int id, int data, int amount, String name, ArrayList<String> enchants, ArrayList<String> effects) {
		ItemStack item = new ItemStack(Material.getMaterial(id));
		item.setAmount(amount);
		
		if (data != 0) {
			item.setDurability((short) data); 
		}
		
		ItemMeta meta = item.getItemMeta();
		
		if (name != null) {
			meta.setDisplayName(name.replaceAll("&", "§"));
		}
		
		ArrayList<String> lore = new ArrayList<String>();
		
		if (item.getType() == Material.POTION) {
			PotionMeta potion = (PotionMeta) meta;
			
			for (String effect : effects) {
				String[] split = effect.split(":");
				
				if (effect != null) {
					if (effect.contains(":")) {
						int va = Integer.valueOf(split[2]);
						int value = va - 1;
						
						potion.addCustomEffect(new PotionEffect(PotionEffectType.getByName(split[0].toUpperCase()), Integer.valueOf(split[1]) * 20, value), true);
						
						lore.add("§a" + WordUtils.capitalize(split[0].toLowerCase()) + " §7- §a" + Integer.valueOf(split[1]) + " segundos (Level " + Integer.valueOf(split[2]) + ")");
					}else {
						potion.addCustomEffect(new PotionEffect(PotionEffectType.getByName(split[0].toUpperCase()), 60, 2), true);
					}
				}
			}
		}else {
			for (String enchant : enchants) {
				String[] split = enchant.split(":");
				
				if (enchant != null) {
					if (enchant.contains(":")) {
						meta.addEnchant(Enchantment.getByName(split[0].toUpperCase()), Integer.valueOf(split[1]), true);
					}else {
						meta.addEnchant(Enchantment.getByName(enchant.toUpperCase()), 1, true);
					}
				}
			}
		}		
		
		meta.setLore(lore);
		item.setItemMeta(meta);
			
		return item;	
	}
	
	public static ArrayList<String> getTypes() {
		return types;
	}
		
	public static void LoadChests() {	
		HashMap<String, List<ItemStack>> a2 = new HashMap<String, List<ItemStack>>();
		
		for(String chests : RFTB.getConfigManager().getChests().getConfigurationSection("Chests.Type").getKeys(false)) {
			types.add(chests);
			
			List<ItemStack> a1 = new ArrayList<ItemStack>();					
			
			for (String items : RFTB.getConfigManager().getChests().getConfigurationSection("Chests.Type." + chests + ".Items").getKeys(false)) {
				int id = RFTB.getConfigManager().getChests().getInt("Chests.Type." + chests + ".Items." + items + ".ID");
				int data = RFTB.getConfigManager().getChests().getInt("Chests.Type." + chests + ".Items." + items + ".Data");
				int amount = RFTB.getConfigManager().getChests().getInt("Chests.Type." + chests + ".Items." + items + ".Amount");
				String name = RFTB.getConfigManager().getChests().getString("Chests.Type." + chests + ".Items." + items + ".Name");
								
				ArrayList<String> enchants = new ArrayList<String>();
				ArrayList<String> effects = new ArrayList<String>();
				
				for (String list1 : RFTB.getConfigManager().getChests().getStringList("Chests.Type." + chests + ".Items." + items + ".Enchantments")) {
					enchants.add(list1);
				}			
				
				for (String list1 : RFTB.getConfigManager().getChests().getStringList("Chests.Type." + chests + ".Items." + items + ".Effects")) {
					effects.add(list1);
				}	
				
				a1.add(GameItems(id, data, amount, name, enchants, effects));
				
				a2.put(chests, a1);
				lis.put(chests, a2.get(chests));
				
				item.put(chests, GameItems(id, data, amount, name, enchants, effects));	
			}
		}
	}
}
