package com.mancosyt.rftb.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.mancosyt.rftb.RFTB;
import com.mancosyt.rftb.game.GamePlayer;
import com.mancosyt.rftb.menus.ChestMenu;

public class ChestListener implements Listener{

	@EventHandler
	public void onOpenChest(PlayerInteractEvent e){
		Player player = e.getPlayer();
						 
		Action a = e.getAction();
					
		if (a != Action.RIGHT_CLICK_BLOCK) {
			return;		
		}
		
		if (e.getClickedBlock() == null) {
			return;
		}
				
		GamePlayer meta = GamePlayer.getMeta(player);
				
		if (meta.getArena() == null) {
			return;
		}
					
		
		if (e.getClickedBlock().getType() != Material.CHEST) {
			return;
		}
		
		for(String chests : RFTB.getConfigManager().getChests().getConfigurationSection("Chests placed").getKeys(false)) {
			String world = RFTB.getConfigManager().getChests().getString("Chests placed." + chests + ".World");
			String type = RFTB.getConfigManager().getChests().getString("Chests placed." + chests + ".Type");
		
			int x = RFTB.getConfigManager().getChests().getInt("Chests placed." + chests + ".X");
			int y = RFTB.getConfigManager().getChests().getInt("Chests placed." + chests + ".Y");
			int z = RFTB.getConfigManager().getChests().getInt("Chests placed." + chests + ".Z");	
			
			World w = Bukkit.getWorld(world);
			
			Location locchest = new Location(w, x,y,z);
			
			if (e.getClickedBlock().getLocation().equals(locchest)){
				e.setCancelled(true);
				
				ChestMenu.openMenu(player, type);
			}
		}
	}
}
