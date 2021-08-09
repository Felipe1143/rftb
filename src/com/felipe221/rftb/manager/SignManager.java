package com.felipe221.rftb.manager;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.scheduler.BukkitRunnable;

import com.felipe221.rftb.RFTB;
import com.felipe221.rftb.game.Game;
import com.felipe221.rftb.game.GameManager;
import com.felipe221.rftb.object.IStatus;

import net.md_5.bungee.api.ChatColor;

public class SignManager implements Listener{	
	private static HashMap<String, Location> signs;
	
	public HashMap<String, Location> getSigns() {
		return signs;
	}
	
	public static void LoadSigns() {
		signs = new HashMap<String, Location>();
		
		if (RFTB.getConfigManager().getSigns().contains("Signs placed")) {
			for(String sign : RFTB.getConfigManager().getSigns().getConfigurationSection("Signs placed").getKeys(false)) {
				String world = RFTB.getConfigManager().getSigns().getString("Signs placed." + sign + ".World");
				String map  = RFTB.getConfigManager().getSigns().getString("Signs placed." + sign + ".Map");
				int x = RFTB.getConfigManager().getSigns().getInt("Signs placed." + sign + ".X");
				int y = RFTB.getConfigManager().getSigns().getInt("Signs placed." + sign + ".Y");
				int z = RFTB.getConfigManager().getSigns().getInt("Signs placed." + sign + ".Z");			
					
				World w = Bukkit.getWorld(world);
					
				Location locsign = new Location(w, x,y,z);
					
				if (RFTB.getConfigManager().getMaps().contains("Games." + map)) {
					update(map, locsign);	
				}
			}	
		}
	}
	
    public static void SignUpdate() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(RFTB.getInstance(), new BukkitRunnable() {
			@Override
			public void run() {
				if (RFTB.getConfigManager().getSigns().contains("Signs placed")) {
					RFTB.getSignManager().LoadSigns();
				}
			}
		},0, 20);		
    }
    
	public static void update(String map, Location loc) {
		Block a = loc.getBlock();				
		
		if(a.getTypeId() == Material.SIGN_POST.getId() || a.getTypeId() == Material.WALL_SIGN.getId()) {			
			Sign a1 = (Sign) a.getState();
			
			Game game = Game.getArena(map);
			
			if (game.getStatus() == IStatus.WAITING) {	
				org.bukkit.material.Sign sd = (org.bukkit.material.Sign) a1.getData();
				
				Block block = a.getRelative(sd.getAttachedFace());
				BlockState state = block.getState();
				
				state.setType(Material.getMaterial(95));
				MaterialData e2 = state.getData();
				e2.setData((byte) 5);
				state.update(true);
				
				a1.setLine(0, "�o�n" + game.getName());
				a1.setLine(1, "�aEsperando...");
				a1.setLine(2, game.getPlayers().size() + "/" + game.getMaxPlayers());
				a1.setLine(3, "�Haz click!");		
				
				a1.update();
			}else if (game.getStatus() == IStatus.STARTING) {				
				org.bukkit.material.Sign sd = (org.bukkit.material.Sign) a1.getData();
				
				Block block = a.getRelative(sd.getAttachedFace());
				BlockState state = block.getState();
				state.setType(Material.getMaterial(95));
				MaterialData e2 = state.getData();
				e2.setData((byte) 4);
				state.update(true);
				
				a1.setLine(0, "�o�n" + game.getName());
				a1.setLine(1, "�eIniciando...");
				a1.setLine(2, game.getPlayers().size() + "/" + game.getMaxPlayers());
				a1.setLine(3, "�Haz click!");		
				
				a1.update();
			}else if (game.getStatus() == IStatus.IN_GAME) {	
				org.bukkit.material.Sign sd = (org.bukkit.material.Sign) a1.getData();
				
				Block block = a.getRelative(sd.getAttachedFace());
				BlockState state = block.getState();
				state.setType(Material.getMaterial(95));
				MaterialData e2 = state.getData();
				e2.setData((byte) 14);
				state.update(true);
				
				a1.setLine(0, "�o�n" + game.getName());
				a1.setLine(1, "�cEn juego");
				a1.setLine(2, game.getPlayers().size() + "/" + game.getMaxPlayers());
				a1.setLine(3, "�Escoje otra!");		
				
				a1.update();
			}else if (game.getStatus() == IStatus.RESTART) {	
				org.bukkit.material.Sign sd = (org.bukkit.material.Sign) a1.getData();
				
				Block block = a.getRelative(sd.getAttachedFace());
				BlockState state = block.getState();
				state.setType(Material.getMaterial(95));
				MaterialData e2 = state.getData();
				e2.setData((byte) 10);
				state.update(true);
				
				a1.setLine(0, "�o�n" + game.getName());
				a1.setLine(1, "�5�Reiniciando!");
				a1.setLine(2, "");
				a1.setLine(3, "");		
				
				a1.update();
			}			
		}		
	}
	
	public static Block getBlockSignAttachedTo(Block block) {
        if (block.getType().equals(Material.WALL_SIGN))
            switch (block.getData()) {
                case 2:
                    return block.getRelative(BlockFace.WEST);
                case 3:
                    return block.getRelative(BlockFace.EAST);
                case 4:
                    return block.getRelative(BlockFace.SOUTH);
                case 5:
                    return block.getRelative(BlockFace.NORTH);
            }
        return null;
    }
	

}
