package com.mancosyt.rftb.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.mancosyt.rftb.menus.GameMenu;
import com.mancosyt.rftb.menus.VoteMenu;

public class InteractListener implements Listener{
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		Action a = e.getAction();	
		ItemStack handItem = player.getItemInHand();
		
		if (handItem == null) {
			return;
		}
		
		if(handItem.getType() == Material.NETHER_STAR) {
			if (handItem.getItemMeta().getDisplayName().contains("otaciones")) {
				VoteMenu.openMenu(player);
				
				return;
			}
		}
		
		if(handItem.getType() == Material.EYE_OF_ENDER) {
			if (handItem.getItemMeta().getDisplayName().contains("renas")) {
				GameMenu.openMenu(player);
				
				return;
			}
		}
		
		if(handItem.getType() == Material.MAGMA_CREAM) {
			if (handItem.getItemMeta().getDisplayName().contains("alir")) {
				player.chat("/rftb leave");
				
				return;
			}
		}
		
		if(handItem.getType() == Material.COMPASS) {
			if (handItem.getItemMeta().getDisplayName().contains("alir")) {
				player.performCommand("hub");
				
				return;
			}
		}
	}
}
