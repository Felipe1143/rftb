package com.mancosyt.rftb.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitRunnable;

import com.mancosyt.rftb.RFTB;
import com.comugamers.core.CGPlayerData;
import com.comugamers.core.Manager.TagManager;
import com.mancosyt.rftb.Utils;
import com.mancosyt.rftb.game.Game;
import com.mancosyt.rftb.game.GamePlayer;
import com.mancosyt.rftb.manager.ScoreboardManager;
import com.mancosyt.rftb.object.IGetter;
import com.mancosyt.rftb.object.IStats;
import com.mancosyt.rftb.object.IStatus;
import com.mancosyt.rftb.object.IWinner;
import com.mancosyt.rftb.object.IStats.StatsType;

import net.md_5.bungee.api.ChatColor;

public class PlayerListener implements Listener{	
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		
        ScoreboardManager helper = ScoreboardManager.createScore(player);
        helper.setTitle("§cC60");      
        
		player.getInventory().clear();
		player.getEquipment().clear();

		PlayerInventory inv = player.getInventory();
		inv.setHelmet(null);
		inv.setChestplate(null);
		inv.setLeggings(null);
		inv.setBoots(null);
		
		player.setMaxHealth(20);
		player.setHealth(20);
		player.setAllowFlight(false);
		player.setFlying(false);
		player.setExp(0);
		player.setGameMode(GameMode.SURVIVAL);
		player.closeInventory();
		player.getActivePotionEffects().clear();
		
		ItemStack games = new ItemStack(Material.EYE_OF_ENDER);
		ItemMeta gamesmeta = games.getItemMeta();		
		gamesmeta.setDisplayName("§2§lA§a§lrenas §f- Click Derecho");
		games.setItemMeta(gamesmeta);
		
		ItemStack perfil = new ItemStack(Material.SKULL_ITEM, 1, (short) 4);
		ItemMeta perfilmeta = perfil.getItemMeta();		
		perfilmeta.setDisplayName("§3§lP§b§lerfil §f- Click Derecho");
		perfil.setItemMeta(perfilmeta);
		
		ItemStack salir = new ItemStack(Material.COMPASS);
		ItemMeta salirmeta = salir.getItemMeta();		
		salirmeta.setDisplayName("§4§lS§c§lalir §f- Click Derecho");
		salir.setItemMeta(salirmeta);
		
		player.getInventory().setItem(0, games);
		player.getInventory().setItem(4, perfil);
		player.getInventory().setItem(8, salir);

		player.updateInventory();
		
		for (Player all : Bukkit.getOnlinePlayers()) {
			GamePlayer metaAll = GamePlayer.getMeta(all);
			
			if (metaAll.getArena() != null) {
				all.hidePlayer(player);
				player.hidePlayer(all);
			}
		}
		
        if (Utils.getLobbyPoint() != null) {
        	player.teleport((Location) Utils.getLobbyPoint());
        }
        
        IGetter.load(player);
        IGetter.loadStatsCache(player.getName());
        IGetter.loadGamesCache(player.getName());
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		
		ScoreboardManager.removeScore(player);
		
		GamePlayer meta = GamePlayer.getMeta(player);
		
		if (meta.getArena() != null) {
			Game game = Game.getArena(meta.getArena());
			
			if (game.getBeastPlayer() == player) {
				if (game.getStatus() == IStatus.STARTING) {
					game.setBeastPlayer(null);
					game.setStop(true);
					game.setTime(30);
					game.setStatus(IStatus.WAITING);
					
					if (game.getPlayers().size() >= game.getMinPlayers()) {
						RFTB.getGameManager().startGame(game.getName(), false);
					}		
				}else if (game.getStatus() == IStatus.IN_GAME) {
					for (Player inGame : game.getPlayers()) {
						inGame.sendMessage("§e§l>> §f¡La bestia salió de la partida!");
					}
					
					RFTB.getGameManager().endGame(game.getName(), IWinner.RUNNERS);
					
					
				}
			}
		}
		

		meta.setInChestZone(false);
		meta.setAlive(true);
		meta.setArena(null);
		
		IGetter.sendGamesCacheToMySQL(player.getName());
		IGetter.sendStatsCacheToMySQL(player.getName());
	}
	
    @EventHandler
	public void onFoodChange(FoodLevelChangeEvent e){
    	if (e.getEntity() instanceof Player) {
    		e.setCancelled(true);
    	}
	}
    
	@EventHandler
	public void onDropItem(PlayerDropItemEvent e) {
		Player player = (Player) e.getPlayer();

		GamePlayer meta = GamePlayer.getMeta(player);
		
		if (player.hasPermission("comu.helper")) {
			return;
		}
		
		if (meta.getArena() == null) {		
			e.setCancelled(true);
			
			player.updateInventory();
			
			return;
		}
		
		if (Game.getArena(meta.getArena()).getStatus() == IStatus.IN_GAME) {
			if (e.getItemDrop().getItemStack().getType() == Material.BEACON) {
				e.setCancelled(true);
				
				player.updateInventory();
				
				return;
			}
			
			return;
		}	
		
		e.setCancelled(true);
		
		player.updateInventory();
		
		return;
	}
	
	@EventHandler
	public void onInventoryMove(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();
		GamePlayer meta = GamePlayer.getMeta(player);

		if (player.hasPermission("comu.helper")) {
			return;
		}
		
		if (meta.getArena() == null) {		
			e.setCancelled(true);
			
			player.updateInventory();
			
			return;
		}
		
		if (Game.getArena(meta.getArena()).getStatus() == IStatus.IN_GAME) {
			return;
		}	
		
		e.setCancelled(true);
		
		player.updateInventory();
		
		return;
	}

	
    @EventHandler
	public void onPlayerDamage(EntityDamageEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		
		if (GamePlayer.getMeta((Player)e.getEntity()).getArena() == null) {
			e.setCancelled(true);
			
			return;
		}else {
			Game game = Game.getArena(GamePlayer.getMeta((Player)e.getEntity()).getArena());
			
			if (game.getStatus() != IStatus.IN_GAME) {
				e.setCancelled(true);
			}else {
				if (game.getTime() > -15) {
					e.setCancelled(true);
				}
			}
		}
	}
    
//    @EventHandler
//	public void onPlayerChat(AsyncPlayerChatEvent e) {
//		e.setCancelled(true);
//		
//		Player player = e.getPlayer();
//		GamePlayer meta = GamePlayer.getMeta(player);
//		
//		if (meta.getArena() == null) {
//			for (Player players : Bukkit.getOnlinePlayers()) {
//				if (GamePlayer.getMeta(players).getArena() == null) {
//					players.sendMessage(
//							RFTB.getLevelManager().getColor(RFTB.getLevelManager().getLevelPlayer(player)) +
//							"(" + RFTB.getLevelManager().getLevelPlayer(player) + ") " + TagManager.getTag(player) + CGPlayerData.getCGPlayer(player).getTagColor()+ 
//							Utils.getPrefix(player, "§e") + "§e" + player.getName() + "§f: " + e.getMessage());
//				}
//			}
//		}else {
//			for (Player players : Game.getArena(meta.getArena()).getPlayers()) {
//				if (GamePlayer.getMeta(players).getArena() != null) {
//					players.sendMessage(
//							RFTB.getLevelManager().getColor(RFTB.getLevelManager().getLevelPlayer(player)) +
//							"(" + RFTB.getLevelManager().getLevelPlayer(player) + ") " + TagManager.getTag(player) + CGPlayerData.getCGPlayer(player).getTagColor()+ 
//							Utils.getPrefix(player, "§e") + "§e" + player.getName() + "§f: " + e.getMessage());
//				}
//			}
//		}
//    }
	
	@EventHandler
	public void onPlayerAttack(EntityDamageByEntityEvent e) {		
		if (!(e.getDamager() instanceof Player)) {
			return;
		}
		
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		
		Player damager = (Player) e.getDamager();
		Player other = (Player) e.getEntity();		
		
		GamePlayer damagerMeta = GamePlayer.getMeta(damager);
		GamePlayer otherMeta = GamePlayer.getMeta(other);
		
		if (damagerMeta.getArena() == null) {
			return;
		}
		
		if (damagerMeta.getArena() != otherMeta.getArena()) {
			return;
		}
		
		Game game = Game.getArena(damagerMeta.getArena());
		
		if (game.getBeastPlayer() != damager &&
			game.getBeastPlayer() != other) {
			
			e.setCancelled(true);
		}
	}
	
	@EventHandler (priority = EventPriority.LOW)
	public void onPlayerDeath(PlayerDeathEvent e) {
		if (!(e.getEntity() instanceof Player)) {	
			return;
		}
		
		e.getDrops().clear();
		
		Player player = (Player) e.getEntity();
		
		new BukkitRunnable() {
			public void run() {
				player.spigot().respawn();
			}
		}.runTaskLater(RFTB.getInstance(), 1l);
		
		GamePlayer meta = GamePlayer.getMeta(player);
		
		if (meta.getArena() == null) {
			return;
		}	

		Player killer = player.getKiller();
		
		Game game = Game.getArena(meta.getArena());	
		Player beast = game.getBeastPlayer();
		meta.setAlive(false);
		
		if (!game.isStop()) {
			if (player.getKiller() != null) {
				if (beast == player) {
					game.setWinner(IWinner.RUNNERS);
					
					IStats.get(killer).upStat(StatsType.BEAST_KILLS, 1);
					IStats.get(player).upStat(StatsType.DEATHS, 1);
					
					for (Player gamePlayer : game.getPlayers()) {
						gamePlayer.sendMessage("§c¡" + player.getKiller().getName() + " asesinó a la bestia!");
						gamePlayer.playSound(gamePlayer.getLocation(), Sound.ANVIL_BREAK, 1, 1);
					}
					
					
					RFTB.getGameManager().endGame(game.getName(), IWinner.RUNNERS);
				}else {				
					IStats.get(beast).upStat(StatsType.KILLS, 1);
					IStats.get(player).upStat(StatsType.DEATHS, 1);
					
					for (Player gamePlayer : game.getPlayers()) {
						gamePlayer.sendMessage("§c¡" + player.getName() + " murió acribillado por la bestia!");
					}
					
					if (game.getPlayersAlive().size() - 1 == 0) {
						game.setWinner(IWinner.BEAST);
						
						RFTB.getGameManager().endGame(game.getName(), IWinner.BEAST);	
					}
				}
			}else {
				if (player == beast) {
					game.setWinner(IWinner.RUNNERS);
					
					IStats.get(player).upStat(StatsType.DEATHS, 1);
					
					for (Player gamePlayer : game.getPlayers()) {
						gamePlayer.sendMessage("§c¡La bestia murio de manera tonta!");
						gamePlayer.playSound(gamePlayer.getLocation(), Sound.ANVIL_BREAK, 1, 1);
					}
					
					RFTB.getGameManager().endGame(game.getName(), IWinner.RUNNERS);		
				}else {
					IStats.get(player).upStat(StatsType.DEATHS, 1);
					
					for (Player gamePlayer : game.getPlayers()) {
						gamePlayer.sendMessage("§c¡" + player.getName() + " murió de manera tonta!");
					}
					
					if (game.getPlayersAlive().size() - 1 == 0) {
						game.setWinner(IWinner.BEAST);
						
						RFTB.getGameManager().endGame(game.getName(), IWinner.BEAST);	
					}
				}
			}		
		}
	}
	
	@EventHandler (priority = EventPriority.LOW)
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		Player player = e.getPlayer();
		
		if (GamePlayer.getMeta(player).getArena() != null) {
			player.setGameMode(GameMode.SPECTATOR);
		}
		
		e.setRespawnLocation(Game.getArena(GamePlayer.getMeta(player).getArena()).getSpectator());
	}
	
	@EventHandler
	public void onDoubleJump(PlayerToggleFlightEvent e) {		
		Player player = e.getPlayer();
		GamePlayer meta = GamePlayer.getMeta(player);
		Game game = Game.getArena(meta.getArena());
		
		if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) { 
			return;
		}
		
		if (!meta.isAlive()) {
			return;
		}
		
		if (meta.getArena() == null) {
			return;
		}
		
		if (game.getStatus() != IStatus.IN_GAME) {
			return;
		}
		
		player.setAllowFlight(false);
		player.setFlying(false);
				
		if (meta.getDoubleJump() == 0) {
			return;
		}
				
		player.setVelocity(player.getLocation().getDirection().setY(1).multiply(1.2));
		player.playSound(player.getLocation(), Sound.ZOMBIE_INFECT, 1.2F, 2.2F);
		
		meta.setDoubleJump(meta.getDoubleJump() - 1);	
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Player player = e.getPlayer();
		GamePlayer meta = GamePlayer.getMeta(player);
		Game game = Game.getArena(meta.getArena());
		
		if (player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) {
			return;
		}
		
		if (!meta.isAlive()) {
			return;
		}
		
		if (meta.getArena() == null) {
			return;
		}
		
		if (game.getStatus() != IStatus.IN_GAME) {
			return;
		}
		
		if (player.isFlying()) {
			player.setAllowFlight(false);
			player.setFlying(false);
			
			return;
		}
		
		if (!player.getAllowFlight()) {
			if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR) {
				if (meta.getDoubleJump() != 0) {
					player.setAllowFlight(true);
								
					return;
				}
			}
		}
	}
}
