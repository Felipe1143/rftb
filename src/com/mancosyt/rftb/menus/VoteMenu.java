package com.mancosyt.rftb.menus;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.mancosyt.rftb.RFTB;
import com.mancosyt.rftb.Utils;
import com.mancosyt.rftb.game.Game;
import com.mancosyt.rftb.game.GamePlayer;
import com.mancosyt.rftb.game.GameVote;
import com.mancosyt.rftb.game.GameVote.HEARTS;
import com.mancosyt.rftb.game.GameVote.MODE;
import com.mancosyt.rftb.game.GameVote.TIME;

public class VoteMenu {
	private static HashMap<Player, String> open = new HashMap<Player, String>();
	
	public static HashMap<Player, String> getOpen() {
		return open;
	}
	
	public static void openMenu(Player player) {
		Inventory join = Bukkit.getServer().createInventory(player, 27, "» Votaciones");
		
		GamePlayer meta = GamePlayer.getMeta(player);
		Game game = Game.getArena(meta.getArena());
		GameVote vote = GameVote.getArena(meta.getArena());
	
		open.put(player, game.getName());
		
		new BukkitRunnable() {			
			@Override
			public void run() {				
				if (open.containsKey(player)) {				
					ItemStack hearts = new ItemStack(Material.GOLDEN_CARROT);
					ItemMeta heartsmeta = hearts.getItemMeta();
					
					ItemStack time = new ItemStack(Material.getMaterial(347));
					ItemMeta timemeta = time.getItemMeta();
					
					ItemStack mode = new ItemStack(Material.getMaterial(373));
					mode.setDurability((short)8226);
					ItemMeta modemeta = mode.getItemMeta();
					
					modemeta.setDisplayName("§b§oModos");
					 
					ArrayList<String> loreMode= new ArrayList<String>();
					
					loreMode.add("");
					loreMode.add("§b> §fModo rápido: §b" + (vote.getVoteMode(MODE.FAST) == 0 ? "¡Sin votos!" : vote.getVoteMode(MODE.FAST)));
					loreMode.add("§b> §fModo normal: §b" + (vote.getVoteMode(MODE.NORMAL) == 0 ? "¡Sin votos!" : vote.getVoteMode(MODE.NORMAL)));
					loreMode.add("");
					loreMode.add("§bCLICK IZQUIERDO §fpara votar §bRápido");
					loreMode.add("§bCLICK DERECHO §fpara votar §bNormal");
					
					modemeta.setLore(loreMode);
					
					mode.setItemMeta(modemeta);
					
					timemeta.setDisplayName("§a§oTiempo");
					 
					ArrayList<String> loreTime = new ArrayList<String>();
					
					loreTime.add("");
					loreTime.add("§a> §fTiempo lluvioso: §a" + (vote.getVoteTime(TIME.STORM) == 0 ? "¡Sin votos!" : vote.getVoteTime(TIME.STORM)));
					loreTime.add("§a> §fTiempo de día: §a" + (vote.getVoteTime(TIME.DIA) == 0 ? "¡Sin votos!" : vote.getVoteTime(TIME.DIA)));
					loreTime.add("§a> §fTiempo de noche: §a" + (vote.getVoteTime(TIME.NOCHE) == 0 ? "¡Sin votos!" : vote.getVoteTime(TIME.NOCHE)));
					loreTime.add("");
					loreTime.add("§aSHIFT + CLICK §fpara votar §aLluvioso");
					loreTime.add("§aCLICK IZQUIERDO §fpara votar §aDía");
					loreTime.add("§aCLICK DERECHO §fpara votar §aNoche");
					
					timemeta.setLore(loreTime);
					
					time.setItemMeta(timemeta);
									
					heartsmeta.setDisplayName("§c§oCorazones");
					 
					ArrayList<String> loreHearts = new ArrayList<String>();
					
					loreHearts.add("");
					loreHearts.add("§c> §fCorazones dobles: §c" + (vote.getVoteHearths(HEARTS.DOUBLE) == 0 ? "¡Sin votos!" : vote.getVoteHearths(HEARTS.DOUBLE)));
					loreHearts.add("§c> §fCorazones normales: §c" + (vote.getVoteHearths(HEARTS.NORMAL) == 0 ? "¡Sin votos!" : vote.getVoteHearths(HEARTS.NORMAL)));
					loreHearts.add("");
					loreHearts.add("§cCLICK IZQUIERDO §fpara votar §cDobles");
					loreHearts.add("§cCLICK DERECHO §fpara votar §cNormales");
					
					heartsmeta.setLore(loreHearts);
					
					hearts.setItemMeta(heartsmeta);
					
					join.setItem(13, mode);
					join.setItem(11, time);
					join.setItem(15, hearts);						
				}else {
					cancel();
				}
			}
		}.runTaskTimerAsynchronously(RFTB.getInstance(), 0, 0);
				
		player.openInventory(join);
	}
}
