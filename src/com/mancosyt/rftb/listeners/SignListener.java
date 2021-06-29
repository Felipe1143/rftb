package com.mancosyt.rftb.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.PluginManager;

import com.mancosyt.rftb.RFTB;
import com.mancosyt.rftb.game.Game;
import com.mancosyt.rftb.game.GamePlayer;
import com.mancosyt.rftb.manager.SignManager;


public class SignListener implements Listener {	
	public void register() {		
		PluginManager pm = Bukkit.getPluginManager();
		
		pm.registerEvents(this, RFTB.getInstance());
	}
	
	@EventHandler
	public void onRemoveSign(BlockBreakEvent e) {
		Block block = e.getBlock();
		Player player = e.getPlayer();
		
		if(block.getTypeId() != Material.SIGN_POST.getId() || block.getTypeId() != Material.WALL_SIGN.getId()) {
			if (RFTB.getConfigManager().getSigns().contains("Signs placed")) {
				for(String signs :RFTB.getConfigManager().getSigns().getConfigurationSection("Signs placed").getKeys(false)) {
					String world = RFTB.getConfigManager().getSigns().getString("Signs placed." + signs + ".World");
					
					int x =  RFTB.getConfigManager().getSigns().getInt("Signs placed." + signs + ".X");
					int y =  RFTB.getConfigManager().getSigns().getInt("Signs placed." + signs + ".Y");
					int z =  RFTB.getConfigManager().getSigns().getInt("Signs placed." + signs + ".Z");	

					World w = Bukkit.getWorld(world);
					
					Location locsign = new Location(w, x,y,z);
					
					if (block.getLocation().equals(locsign)){
						if (e.getPlayer().hasPermission("rftb.signs")){		
							player.sendMessage(ChatColor.GREEN + "Sign destroyed!");
							
							RFTB.getConfigManager().getSigns().set("Signs placed." + signs, null);
							RFTB.getConfigManager().saveSigns();
						}else {
							e.setCancelled(true);
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void SignClickEvent(PlayerInteractEvent event){
		Player player = event.getPlayer();
		
		Action a = event.getAction();
	
		if (a != Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		
		if (event.getClickedBlock() == null) {
			return;
		}
		
		Material clickedType = event.getClickedBlock().getType();
			
		if (clickedType == Material.SIGN_POST || clickedType == Material.WALL_SIGN) {	
			Sign sign = (Sign) event.getClickedBlock().getState();
				
			if (GamePlayer.getMeta(player).getArena() != null) {
				return;
			}
				
			for (Game game : RFTB.getGameManager().getGames()) {
				if (sign.getLine(0).contains(game.getName())){
					RFTB.getGameManager().onPlayerJoin(player, game.getName());			
				}
			}
		}
	}


	@EventHandler
	public void setBlock(SignChangeEvent e) {
		Block block = e.getBlock();
	
		Sign sign = (Sign) block.getState();
		
		String line1 = e.getLine(0);
		String line2 = e.getLine(1);
		
		if (line1.equalsIgnoreCase("[rftb]")) {
			for (Game game : RFTB.getGameManager().getGames()) {
				if (game.getName().equalsIgnoreCase(line2)) {
					e.setLine(0, RFTB.getVars(null, line2, RFTB.getConfigManager().getSigns().getString("Signs.Waiting format.Line 1")));
					e.setLine(1, RFTB.getVars(null, line2, RFTB.getConfigManager().getSigns().getString("Signs.Waiting format.Line 2")));
					e.setLine(2, RFTB.getVars(null, line2, RFTB.getConfigManager().getSigns().getString("Signs.Waiting format.Line 3")));
					e.setLine(3, RFTB.getVars(null, line2, RFTB.getConfigManager().getSigns().getString("Signs.Waiting format.Line 4")));		
					
					sign.update(true);
					
					Location b = e.getBlock().getLocation();
					
					int numbers = RFTB.getConfigManager().getSigns().getInt("Signs.Total");
					
					numbers++;
					
					RFTB.getConfigManager().getSigns().set("Signs.Total", numbers);

					RFTB.getConfigManager().getSigns().set("Signs placed." + numbers + ".Map", line2);
					RFTB.getConfigManager().getSigns().set("Signs placed." + numbers + ".World", b.getWorld().getName());
					RFTB.getConfigManager().getSigns().set("Signs placed." + numbers + ".X", b.getBlockX());
					RFTB.getConfigManager().getSigns().set("Signs placed." + numbers + ".Y", b.getBlockY());
					RFTB.getConfigManager().getSigns().set("Signs placed." + numbers + ".Z", b.getBlockZ());

					RFTB.getConfigManager().saveSigns();	
					
					SignManager.LoadSigns();
					
					return;
				}
			}
			
			e.getPlayer().sendMessage(ChatColor.RED + "Please check the word of LINE 2 '" + line2 + "' (the map does not exist)");
		}
	}	
}
