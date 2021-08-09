package com.felipe221.rftb.game;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.felipe221.rftb.RFTB;
import com.felipe221.rftb.Utils;
import com.felipe221.rftb.game.GameVote.HEARTS;
import com.felipe221.rftb.game.GameVote.MODE;
import com.felipe221.rftb.game.GameVote.TIME;
import com.felipe221.rftb.game.GameVote.TYPE;
import com.felipe221.rftb.object.IStats;
import com.felipe221.rftb.object.IStatus;
import com.felipe221.rftb.object.IWinner;
import com.felipe221.rftb.object.IStats.StatsType;

import net.md_5.bungee.api.ChatColor;

public class GameManager {
	
	public static void LoadGames() {     
		Game.games.clear();
		
		if (RFTB.getConfigManager().getMaps().contains("Games")){
			Utils.sendLog("§a§m------------------------------------------");
			Utils.sendLog("&fMaps:");
			
			for(String map : RFTB.getConfigManager().getMaps().getConfigurationSection("Games").getKeys(false)) {
				int min = RFTB.getConfigManager().getMaps().getInt("Games." + map + ".Min players");
				int max = RFTB.getConfigManager().getMaps().getInt("Games." + map + ".Max players");								
								
				if (RFTB.getConfigManager().getMaps().contains("Games." + map + ".Spawn")) {
					Utils.sendLog("&a  -> &f" + map + " &a(MIN: " + min + "/MAX: " + max + ") --> &fSpawn: &aSetted");
				}else{
					Utils.sendLog("&a  -> &f" + map + " &a(MIN: " + min + "/MAX: " + max + ") --> &fSpawn: &cNot setted");
				}				
				
				if (min < 2) {					
					RFTB.getConfigManager().getMaps().set("Games." + map + ".Min players", 2);
					RFTB.getConfigManager().saveMaps();
					
					min = 2;
				}
				
				new Game(map, max, min);
			}
			
			Utils.sendLog("§a§m------------------------------------------");
		}
	}
	
	public void unLoadGame(String game) {
		Game.games.remove(game);
	}
	
	public void loadGame(String game) {
		if (RFTB.getConfigManager().getMaps().contains("Games")){
			int min = RFTB.getConfigManager().getMaps().getInt("Games." + game + ".Min players");
			int max = RFTB.getConfigManager().getMaps().getInt("Games." + game + ".Max players");								
								
			if (RFTB.getConfigManager().getMaps().contains("Games." + game + ".Spawn")) {
				Utils.sendLog("&a  -> &f" + game + " &a(MIN: " + min + "/MAX: " + max + ") --> &fSpawn: &aSetted");
			}else{
				Utils.sendLog("&a  -> &f" + game + " &a(MIN: " + min + "/MAX: " + max + ") --> &fSpawn: &cNot setted");
			}				
				
			if (min < 2) {					
				RFTB.getConfigManager().getMaps().set("Games." + game + ".Min players", 2);
				RFTB.getConfigManager().saveMaps();
					
				min = 2;
			}
				
			new Game(game, max, min);
		}
	}
	
	public ArrayList<Game> getGames() {
		ArrayList<Game> gamesList = new ArrayList<Game>();
		
		for (Game games : Game.games.values()) {
			gamesList.add(games);
		}
		
		return gamesList;
	}
	
	public boolean isExist(String map) {
		for (Game game : getGames()) {
			if (game.getName().equalsIgnoreCase(map)) {
				return true;
			}
		}
		
		return false;
	}
	
	public void onPlayerJoin(Player player, String arena) {
		GamePlayer meta = GamePlayer.getMeta(player);		
		Game game = Game.getArena(arena);
		
		if (!game.isPlayable()) {
			player.sendMessage(Utils.Color("&c&l>> &c¡La arena " + game.getName() + " no se puede jugar!"));
			
			return;
		}
		
		if (meta.getArena() != null) {			
			player.sendMessage("§e§l>> §f¡Ya estás jugando una partida!");
			
			return;
		}
		
		if (game.getStatus() == IStatus.IN_GAME || game.getStatus() == IStatus.RESTART) {
			player.sendMessage("§e§l>> &f¡La arena §a" + game.getName() + " §fya comenzó!");
			
			return;
		}
				
		if ((game.getMaxPlayers() + 5) == game.getPlayers().size()) {
			player.sendMessage("§e§l>> §f¡La arena §a" + game.getName() + " §fya tiene 5 jugadores VIPs usando los espacios libres!");	
			
			return;
		}
		
		if (game.getPlayers().size() == game.getMaxPlayers() && !player.hasPermission("comu.vip")) {				
			player.sendMessage("§e§l>> §f¡La arena §a" + game.getName() + " §festá llena!");
			
			return;
		}
		
		meta.setArena(game.getName());
		meta.setAlive(true);
		meta.setInChestZone(false);
		meta.setDoubleJump(0);
		
		Location spawn = game.getSpawn();
		
		player.getInventory().clear();
		player.getEquipment().clear();
		player.setMaxHealth(20);
		player.setHealth(20);
		player.setAllowFlight(false);
		player.setFlying(false);
		player.setExp(0);
		player.setGameMode(GameMode.SURVIVAL);
		player.getActivePotionEffects().clear();
		player.closeInventory();		
		player.teleport(spawn);
		
		ItemStack vote = new ItemStack(Material.NETHER_STAR);
		ItemMeta votemeta = vote.getItemMeta();		
		votemeta.setDisplayName("§2§lV§a§lotaciones §f- Click Derecho");
		vote.setItemMeta(votemeta);
		
		ItemStack salir = new ItemStack(Material.MAGMA_CREAM);
		ItemMeta salirmeta = salir.getItemMeta();		
		salirmeta.setDisplayName("§4§lS§c§lalir §f- Click Derecho");
		salir.setItemMeta(salirmeta);
		
		player.getInventory().setItem(0, vote);
		player.getInventory().setItem(8, salir);

		player.updateInventory();
		
		for (Player all : Bukkit.getOnlinePlayers()) {
			all.hidePlayer(player);
			player.hidePlayer(all);
		}
		
		for (Player global : game.getPlayers()) {
			global.sendMessage("§e§l>> §f¡El jugador " + Utils.getPrefix(player, "§a") + player.getName() + " §fingresó a la partida! §b(" + game.getPlayers().size() + "/" +game.getMaxPlayers() + ")");
			
			global.showPlayer(player);
			player.showPlayer(global);
		}		
		
		if (game.getStatus() == IStatus.WAITING) {
			if (game.getMinPlayers() == game.getPlayers().size()) {
				startGame(game.getName(), false);
			}
		}
	}
	
	public void onPlayerLeave(Player player, String arena) {
		Game game = Game.getArena(arena);
		GamePlayer meta = GamePlayer.getMeta(player);
		GameVote vote = GameVote.getArena(game.getName());

		if (meta.getArena() == null) {
			return;
		}
		
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
				RFTB.getGameManager().endGame(game.getName(), IWinner.RUNNERS);
				
				for (Player gamePlayers : game.getPlayers()) {
					gamePlayers.sendMessage("§e§l>> §f¡La bestia se acobardó y salió de la partida!");
				}
			}
		}else {
			if (game.getStatus() == IStatus.STARTING) {
				game.setBeastPlayer(null);
				game.setStop(true);
				game.setTime(30);
				game.setStatus(IStatus.WAITING);
				
				if (game.getPlayers().size() >= game.getMinPlayers()) {
					RFTB.getGameManager().startGame(game.getName(), false);
				}		
			}else if (game.getStatus() == IStatus.IN_GAME) {
				if (game.getPlayersAlive().size() - 1 == 0) {
					RFTB.getGameManager().endGame(game.getName(), IWinner.BEAST);
				}
			}
		}
		
		Location lobby = Utils.getLobbyPoint();
		
		player.getInventory().clear();
		player.getEquipment().clear();
		
		PlayerInventory inv = player.getInventory();
		inv.setHelmet(null);
		inv.setChestplate(null);
		inv.setLeggings(null);
		inv.setBoots(null);
		
		player.setMaxHealth(20);
		player.setHealth(20);
		player.setExp(0);
		player.setLevel(0);
		player.setGameMode(GameMode.SURVIVAL);
		player.closeInventory();
		player.getActivePotionEffects().clear();
		player.teleport(lobby);
		
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
			
			player.showPlayer(all);
			
			if (metaAll.getArena() != null) {
				all.hidePlayer(player);
				player.hidePlayer(all);
			}
		}
	
		if (game.getPlayers().size() != 1) {
			List<Player> players = game.getPlayers();
			players.remove(player);
			
			for (Player gamePlayers : players) {
				gamePlayers.sendMessage("§e§l>> §f¡El jugador " + Utils.getPrefix(player, "§e") + "§e" +player.getName() + " §fsalió de la partida! §b(" + game.getPlayers().size() + "/" +game.getMaxPlayers() + ")");
			}
		}
				
		meta.setAlive(false);
		meta.setArena(null);
		meta.setInChestZone(false);
		vote.removePlayer(player);
	}
	
	public void startGame(String arena, boolean forced) {
		Game game = Game.getArena(arena);
			
		game.setStatus(IStatus.STARTING);
		game.setStop(false);
		
		new BukkitRunnable(){		
			int time = 30;		
			int timeBeast = 15;
			int toEnd = 15 * 60;			
			
			public void run(){
				if (game.isStop()) {
					cancel();
				}
				
				if (time == -toEnd) {
					endGame(game.getName(), IWinner.RUNNERS);
						
					cancel();
				}
				
				if (forced == false) {
					if (game.getStatus() == IStatus.STARTING) {
						if (game.getPlayers().size() < game.getMinPlayers()) {
							time = RFTB.getConfigManager().getConfig().getInt("Game.Start time");
							
							game.setStatus(IStatus.WAITING);
							
							cancel();
						}
					}			
				}
				
				if (time == timeBeast) {
					Random random = new Random();
					
					int number = random.nextInt(game.getPlayers().size());
					
					Player beast = game.getPlayers().get(number);
					
					game.setBeastPlayer(beast);
					
					beast.teleport(game.getBeastWaitPoint());
					
					for (Player gamePlayers : game.getPlayers()) {
						gamePlayers.sendMessage("§c§l>> §f¡La bestia fue seleccionada, temanle a §c" + beast.getName() + "§f!");
					
						Utils.mandarTitle(gamePlayers, "&cLA BESTIA ES", "&c&o&n" + beast.getName(), 40, 20, 40);
						
						gamePlayers.playSound(gamePlayers.getLocation(), Sound.ZOMBIE_METAL, 1, 1);
					}
				}				
				
				if (time < 0 && time >= -15) {					
					for (Player player : game.getPlayers()) {
						Utils.sendAnnouncement(player, "&c¡La bestia será liberada en " + (15 + time) + "s!");
					}
				}
				
				if (time < -15) {
					for (Player player : game.getPlayersAlive()) {
						if (game.getBeastPlayer() != player) {
							Utils.sendAnnouncement(player, "&c¡La bestia está a " + (int) player.getLocation().distance(game.getBeastPlayer().getLocation()) + " bloques!");
						}
					}
				}
				
				if (time == -15) {					
					for (Player player : game.getPlayers()) {
						if (game.getBeastPlayer() == player) {
							Bukkit.getScheduler().scheduleSyncDelayedTask(RFTB.getInstance(), new BukkitRunnable(){
								public void run() {
									player.teleport(game.getBeastPoint());									
									player.getInventory().clear();	
									
									player.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));								
									player.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
									player.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
									player.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
									
									player.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));
									
									GameVote vote = GameVote.getArena(game.getName());
									
									String winnerHeart = vote.getWinner(TYPE.HEARTS);
									String winnerTime = vote.getWinner(TYPE.TIME);					
									String winnerMode = vote.getWinner(TYPE.MODE);
									
									HEARTS hearts = HEARTS.valueOf(winnerHeart);
									TIME time = TIME.valueOf(winnerTime);
									MODE mode = MODE.valueOf(winnerMode);
									
									if (hearts == HEARTS.DOUBLE) {
										player.setMaxHealth(40);
										player.setHealth(40);
									}
										
									if (mode == MODE.FAST) {
										player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
									}
								}
							});
						}
					}
				}
					
				if (time > 0) {
					for (Player player : game.getPlayers()) {
						player.setLevel(time);
							
						if (time != timeBeast) {
							player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1, 1);
						}
						
						if (time <= 5) {
							if (RFTB.getConfigManager().getMessages().getBoolean("Time.Chat")) {
								player.sendMessage("§aLa partida iniciará en " + time + (time == 1 ? " segundo" : " segundos" ));
								
								Utils.mandarTitle(player, "", "&c&l" + time, 40, 20, 40);
							}
						}
					}
				}
				
				if (time == 0){
					game.setStatus(IStatus.IN_GAME);
					GameVote vote = GameVote.getArena(game.getName());
					
					vote.end();
					
					String winnerHeart = vote.getWinner(TYPE.HEARTS);
					String winnerTime = vote.getWinner(TYPE.TIME);					
					String winnerMode = vote.getWinner(TYPE.MODE);
					
					HEARTS hearts = HEARTS.valueOf(winnerHeart);
					TIME time = TIME.valueOf(winnerTime);
					MODE mode = MODE.valueOf(winnerMode);
					
					World world = Bukkit.getWorld(game.getEndPoint().getWorld().getName());
					
					Bukkit.getScheduler().scheduleSyncDelayedTask(RFTB.getInstance(), new BukkitRunnable(){
						public void run() {
							if (time == TIME.DIA) {
								world.setThundering(false);
								world.setTime(6000);
							}else if (time == TIME.NOCHE) {
								world.setThundering(false);
								world.setTime(18000);
							}else if (time == TIME.STORM) {
								world.setThundering(true);
								world.setThunderDuration(60 * 5);
							}else {
								world.setThundering(false);
								world.setTime(6000);
							}
						}
					});
					
					for (Player player : game.getPlayers()) {
						player.getInventory().clear();	
						
						player.sendMessage("§e§m---------------------------------");					
						player.sendMessage("§6§l      ESCAPA DE LA BESTIA");
						player.sendMessage("");
						player.sendMessage("§fPartida:");
						if (time == TIME.DIA) {
							player.sendMessage("  §e- §fTiempo: §eDía");
						}else if (time == TIME.NOCHE) {
							player.sendMessage("  §e- §fTiempo: §eNoche");
						}else if (time == TIME.STORM) {
							player.sendMessage("  §9- §fTiempo: §9Lluvioso");
						}else if (time == TIME.NULL) {
							player.sendMessage("  §a- §fTiempo: §aSin votación");
						}
						
						if (hearts == HEARTS.NORMAL) {
							player.sendMessage("  §c- §fCorazones: §cNormales");
						}else if (hearts == HEARTS.DOUBLE) {
							player.sendMessage("  §c- §fCorazones: §cDobles");
						}else if (hearts == HEARTS.NULL) {
							player.sendMessage("  §a- §fCorazones: §aSin votación");
						}
						
						if (mode == MODE.NORMAL) {
							player.sendMessage("  §b- §fModo: §cNormal");
						}else if (mode == MODE.FAST) {
							player.sendMessage("  §b- §fModo: §bRápido");
						}else if (mode == MODE.NULL) {
							player.sendMessage("  §a- §fModo: §aSin votación");
						}

						player.sendMessage("");
						player.sendMessage("§f¡No dejes que te atrape la §cbestia§f, llega hasta al final");
						player.sendMessage("§frecoge el kit y §dasesinala§f!");				
						player.sendMessage("§e§m---------------------------------");
						
						player.setStatistic(Statistic.WALK_ONE_CM, 0);
						player.setStatistic(Statistic.JUMP, 0);
						
						GamePlayer.getMeta(player).setAlive(true);
						
						if (game.getBeastPlayer() != player) {
							Bukkit.getScheduler().scheduleSyncDelayedTask(RFTB.getInstance(), new BukkitRunnable(){
								public void run() {
									player.teleport(game.getStartPoint());							
									player.getInventory().clear();
									
									ItemStack salir = new ItemStack(Material.BEACON);
									ItemMeta salirmeta = salir.getItemMeta();		
									salirmeta.setDisplayName("§aCheckpoint §7(Click derecho)");
									salir.setItemMeta(salirmeta);
		
									player.getInventory().setItem(8, salir);				
									
									if (hearts == HEARTS.DOUBLE) {
										player.setMaxHealth(40);
										player.setHealth(40);
									}
		
									if (mode == MODE.FAST) {
										player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
									}
								}
							});
						}
					}				
				}
				
				game.setTime(time);
				time--;				
			}
		}.runTaskTimerAsynchronously(RFTB.getInstance(), 20L, 20L);
	}
	
	public void endGame(String arena, IWinner winner) {
		Game game = Game.getArena(arena);
		
		game.setStatus(IStatus.RESTART);
		game.setStop(true);
		
		for (Player player : game.getPlayers()) {	
			player.sendMessage("§e§m---------------------------------");					
			player.sendMessage("§6§l           FIN DEL JUEGO");
			player.sendMessage("");	
			player.sendMessage("§fGanador: §a" + (winner == IWinner.BEAST ? "Bestia" : "Corredores"));	
			player.sendMessage("§fSaltos: §b" + player.getStatistic(Statistic.JUMP));	
			player.sendMessage("§a");
			if (winner == IWinner.BEAST) {
				player.sendMessage("§fSe ve que no alcanzaste a correr muy rápido...");	
			}else {
				player.sendMessage("§fCreo que hacerte bestia fue un error...");					
			}
			player.sendMessage("§e§m---------------------------------");
			
			if (winner == IWinner.RUNNERS) {
				if (game.getBeastPlayer() != player) {
					IStats.get(player).upStat(StatsType.WINS, 1);
					IStats.get(player).upStat(StatsType.XP, 30);
					IStats.get(player).upGameWins(arena, 1);
					
					player.sendMessage("§b§l+30 XP (EDLB)");
					player.sendMessage("§a§l+50 GEMAS");
				}
			}else {
				IStats.get(game.getBeastPlayer()).upStat(StatsType.WINS, 1);
				IStats.get(game.getBeastPlayer()).upStat(StatsType.XP, 50);
				IStats.get(game.getBeastPlayer()).upGameWins(arena, 1);
				
				player.sendMessage("§b§l+50 XP (EDLB)");
				player.sendMessage("§a§l+100 GEMAS");
			}
			
			IStats.get(game.getBeastPlayer()).upStat(StatsType.GAMES, 1);
			IStats.get(game.getBeastPlayer()).upGamePlayed(arena, 1);
		}
		
		new BukkitRunnable(){		
			int time = 20;
			
			public void run(){
				if (time != 0) {
					time--;
					
					for (Player player : game.getPlayers()) {
						player.setLevel(time);
						
						if (winner == IWinner.BEAST) {
							if (player == game.getBeastPlayer()) {							
								Utils.spawnFirework(player.getLocation());
								
								Utils.mandarTitle(player, "", "&6&l¡FELICITACIONES!", 40, 20, 40);
							}else{
								Utils.mandarTitle(player, "", "&c&l¡HAS PERDIDO!", 40, 20, 40);
							}
						}else if (winner == IWinner.RUNNERS) {
							if (player != game.getBeastPlayer()) {							
								Utils.spawnFirework(player.getLocation());
								
								Utils.mandarTitle(player, "", "&6&l¡FELICITACIONES!", 40, 20, 40);
							}else {
								Utils.mandarTitle(player, "", "&c&l¡HAS PERDIDO!", 40, 20, 40);
							}
						}
					}
				}else {
					Bukkit.getScheduler().scheduleSyncDelayedTask(RFTB.getInstance(), new BukkitRunnable(){
						public void run() {
							for (Player player : game.getPlayers()) {
								GamePlayer meta = GamePlayer.getMeta(player);
								GameVote vote = GameVote.getArena(arena);
								
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
								
								meta.setAlive(false);
								meta.setArena(null);
								meta.setInChestZone(false);
								vote.removePlayer(player);	
								
								for (Player all : Bukkit.getOnlinePlayers()) {
									GamePlayer metaAll = GamePlayer.getMeta(all);
									
									player.showPlayer(all);
									
									if (metaAll.getArena() != null) {
										all.hidePlayer(player);
										player.hidePlayer(all);
									}
								}
								
								if (Utils.getLobbyPoint() != null) {
						        	player.teleport((Location) Utils.getLobbyPoint());
						        }													
							}
							
							game.setBeastPlayer(null);
							game.setStop(true);
							game.setTime(30);
							game.setWinner(null);
							game.setStatus(IStatus.WAITING);
							
							GameVote.reloadGame(arena);
						}
					});
					
					
					cancel();
				}
			}
		}.runTaskTimerAsynchronously(RFTB.getInstance(), 20L, 20L);
	}
}
