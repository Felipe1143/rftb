package com.mancosyt.rftb.manager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import com.comugamers.core.CGPlayerData;
import com.mancosyt.rftb.RFTB;
import com.mancosyt.rftb.Utils;
import com.mancosyt.rftb.game.Game;
import com.mancosyt.rftb.game.GamePlayer;
import com.mancosyt.rftb.object.IStats;
import com.mancosyt.rftb.object.IStats.StatsType;
import com.mancosyt.rftb.object.IStatus;
import com.mancosyt.rftb.object.IWinner;

public class ScoreboardManager {
	static int change = 0;
    private static HashMap<UUID, ScoreboardManager> players = new HashMap<>();
    
    public static boolean hasScore(Player player) {
        return players.containsKey(player.getUniqueId());
    }
    
    public static ScoreboardManager createScore(Player player) {
        return new ScoreboardManager(player);
    }
    
    public static ScoreboardManager getByPlayer(Player player) {
        return players.get(player.getUniqueId());
    }

    public static ScoreboardManager removeScore(Player player) {
        return players.remove(player.getUniqueId());
    }
    
    private Scoreboard scoreboard;
    private Objective sidebar;
    public HashMap<String, Score> scores = new HashMap();
    public HashMap<String, Team> teams = new HashMap();

    private ScoreboardManager(Player player) {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        sidebar = scoreboard.registerNewObjective("sidebar", "dummy");
        sidebar.setDisplaySlot(DisplaySlot.SIDEBAR);
        
        for(int i=1; i<=15; i++) {
            Team team = scoreboard.registerNewTeam("SLOT_" + i);
            team.addEntry(genEntry(i));
        }
                       
        player.setScoreboard(scoreboard);
        players.put(player.getUniqueId(), this);
    }
        
    public void setTitle(String title) {
        title = ChatColor.translateAlternateColorCodes('&', title);
        sidebar.setDisplayName(title.length()>32 ? title.substring(0, 32) : title);
    }

    public void setSlot(int slot, String text) {
        Team team = scoreboard.getTeam("SLOT_" + slot);
        String entry = genEntry(slot);
        if(!scoreboard.getEntries().contains(entry)) {
            sidebar.getScore(entry).setScore(slot);
        }

        text = ChatColor.translateAlternateColorCodes('&', text);
        String pre = getFirstSplit(text);
        String suf = getFirstSplit(ChatColor.getLastColors(pre) + getSecondSplit(text));
        team.setPrefix(pre);
        team.setSuffix(suf);
    }

    public void removeSlot(int slot) {
        String entry = genEntry(slot);
        if(scoreboard.getEntries().contains(entry)) {
            scoreboard.resetScores(entry);
        }
    }
    
    public static void LoadScoreboard() {
    	Bukkit.getScheduler().runTaskTimerAsynchronously(RFTB.getInstance(), new BukkitRunnable() {
			int i = 0;
			@Override
			public void run() {
			    String t = "Cargando...";
				switch (i){
	                case (0):
	                case(1):
	                case(2):
	                case(3):
	                case(4):
	                case(5):
	                case(6):
	                    t = "&b&lCOMUGAMERS";
	                    i++;
	                    break;
	                case(7):
	                    t = "&3&lC&f&lO&b&lMUGAMERS";
	                    i++;
	                    break;
	                case(8):
	                    t = "&f&lC&3&lO&f&lM&b&lUGAMERS";
	                    i++;
	                    break;
	                case(9):
	                    t = "&b&lC&f&lO&3&lM&f&lU&b&lGAMERS";
	                    i++;
	                    break;
	                case(10):
	                    t = "&b&lCO&f&lM&3&lU&f&lG&b&lAMERS";
	                    i++;
	                    break;
	                case(11):
	                    t = "&b&lCOM&f&lU&3&lG&f&lA&b&lMERS";
	                    i++;
	                    break;
	                case(12):
	                    t = "&b&lCOMU&f&lG&3&lA&f&lM&b&lERS";
	                    i++;
	                    break;
	                case(13):
	                    t = "&b&lCOMUG&f&lA&3&lM&f&lE&b&lRS";
	                    i++;
	                    break;
	                case(14):
	                    t = "&b&lCOMUGA&f&lM&3&lE&f&lR&b&lS";
	                    i++;
	                    break;
	                case(15):
	                    t = "&b&lCOMUGAM&f&lE&3&lR&f&lS";
	                    i++;
	                    break;
	                case(16):
	                    t = "&b&lCOMUGAME&f&lR&3&lS";
	                    i++;
	                    break;
	                case(17):
	                    t = "&b&lCOMUGAMER&f&lS";
	                    i++;
	                    break;
	                case(18):
	                    t = "&3&lC&b&lO&f&lMUGAMERS";
	                    i++;
	                    break;
	                case(19):
	                    t = "&b&lC&3&lO&b&lM&f&lUGAMERS";
	                    i++;
	                    break;
	                case(20):
	                    t = "&f&lC&b&lO&3&lM&b&lU&f&lGAMERS";
	                    i++;
	                    break;
	                case(21):
	                    t = "&f&lCO&b&lM&3&lU&b&lG&f&lAMERS";
	                    i++;
	                    break;
	                case(22):
	                    t = "&f&lCOM&b&lU&3&lG&b&lA&f&lMERS";
	                    i++;
	                    break;
	                case(23):
	                    t = "&f&lCOMU&b&lG&3&lA&b&lM&f&lERS";
	                    i++;
	                    break;
	                case(24):
	                    t = "&f&lCOMUG&b&lA&3&lM&b&lE&f&lRS";
	                    i++;
	                    break;
	                case(25):
	                    t = "&f&lCOMUGA&b&lM&3&lE&b&lR&f&lS";
	                    i++;
	                    break;
	                case(26):
	                    t = "&f&lCOMUGAM&b&lE&3&lR&b&lS";
	                    i++;
	                    break;
	                case(27):
	                    t = "&f&lCOMUGAME&b&lR&3&lS";
	                    i++;
	                    break;
	                case(28):
	                    t = "&f&lCOMUGAMER&b&lS";
	                    i++;
	                    break;
	                case(29):
	                    t = "&b&lCOMUGAMERS";
	                    i++;
	                    break;
	                case(30):
	                    t = "&b&lCOMUGAMERS";
	                    i++;
	                    break;
	                case(31):
	                    t = "&f&lCOMUGAMERS";
	                    i++;
	                    break;
	                case(32):
	                    t = "&f&lCOMUGAMERS";
	                    i++;
	                    break;
	                case(33):
	                    t = "&b&lCOMUGAMERS";
	                    i++;
	                    break;
	                case(34):
	                    t = "&b&lCOMUGAMERS";
	                    i++;
	                    break;
	                case(35):
	                    t = "&f&lCOMUGAMERS";
	                    i++;
	                    break;
	                case(36):
	                    t = "&f&lCOMUGAMERS";
	                    i = 0;
	                    break;
	            }
				change++;
				
				if (change == 200) {
					change = 0;
				}
				
				for(Player player : Bukkit.getOnlinePlayers()) {
					ScoreboardManager helper = ScoreboardManager.getByPlayer(player);
					
					helper.setTitle(ChatColor.translateAlternateColorCodes('&', t));
				}
			}
		},0, 2);
    	
		Bukkit.getScheduler().scheduleSyncRepeatingTask(RFTB.getInstance(), new BukkitRunnable() {
			@Override
			public void run() {
				for (Player player : Bukkit.getOnlinePlayers()) {
					ScoreboardManager helper = ScoreboardManager.getByPlayer(player);
					
					ScoreboardManager.setScoreboard(player);
				}				
			}
		},0, 0L);		
    }

    public void setSlotsFromList(List<String> list) {
        while(list.size()>15) {
            list.remove(list.size()-1);
        }
        
        int slot = list.size();

        if(slot<15) {
            for(int i=(slot +1); i<=15; i++) {
                removeSlot(i);
            }
        }

        for(String line : list) {
            setSlot(slot, line);
            slot--;
        }
    }

    private String genEntry(int slot) {
        return ChatColor.values()[slot].toString();
    }

    private String getFirstSplit(String s) {
        return s.length()>16 ? s.substring(0, 16) : s;
    }

    private String getSecondSplit(String s) {
        if(s.length()>32) {
            s = s.substring(0, 32);
        }
        return s.length()>16 ? s.substring(16) : "";
    }
    
    public static String getPorcentage(int value, int max) {	
		double f = (value * 100) / max;
		
		DecimalFormat df = new DecimalFormat("####0.0");
		String intf = df.format(f);
		
		return intf;
	}
    
    public static void setScoreboard(Player player) {		
		if(ScoreboardManager.hasScore(player)) {
			ScoreboardManager helper = ScoreboardManager.getByPlayer(player); 
			GamePlayer meta = GamePlayer.getMeta(player);
			
			if (meta.getArena() == null) {
				List<String> scoreboard = new ArrayList<>();
	            
				scoreboard.add("");
				scoreboard.add("§f Nivel: " + RFTB.getLevelManager().getColor(RFTB.getLevelManager().getLevelPlayer(player)) + RFTB.getLevelManager().getLevelPlayer(player));
				
				int xpLevel = RFTB.getLevelManager().getXPNext(RFTB.getLevelManager().getLevelPlayer(player)) - RFTB.getLevelManager().getXP(RFTB.getLevelManager().getLevelPlayer(player));
				int xpPlayer = RFTB.getLevelManager().getXPNext(RFTB.getLevelManager().getLevelPlayer(player)) - IStats.get(player).getStat(StatsType.XP) - xpLevel;
	
				scoreboard.add("§f Próximo: §e" + (RFTB.getLevelManager().getLevelPlayer(player) + 1) + " §7(" + getPorcentage(-xpPlayer, xpLevel) + "%)");
				
				scoreboard.add("§f");
				scoreboard.add("§6» §fBestias asesinadas: §6" + IStats.get(player).getStat(StatsType.BEAST_KILLS));
				scoreboard.add("§c» §fMuertes:§c " + IStats.get(player).getStat(StatsType.DEATHS));
				scoreboard.add("§e» §fVictorias: §e" + IStats.get(player).getStat(StatsType.WINS));
				scoreboard.add("§b» §fPartidas: §b" + IStats.get(player).getStat(StatsType.GAMES));
				scoreboard.add("§f");
				scoreboard.add("§f Skin de bestia: §aCreeper");
				scoreboard.add("§f");
				scoreboard.add("  §ewww.comugamers.com  ");
							 
				helper.setSlotsFromList(scoreboard);
			}else {
				Game game = Game.getArena(meta.getArena());
				
				if (game.getStatus() == IStatus.WAITING) {
					List<String> scoreboard = new ArrayList<>();
					
					scoreboard.add("");
					scoreboard.add("§f Mapa: §a" + game.getName());
					scoreboard.add("§f");
					scoreboard.add("§f Jugadores faltantes: §a" + (game.getMinPlayers() - game.getPlayers().size()));
					scoreboard.add("§f");
					scoreboard.add("  §ewww.comugamers.com  ");
					
					helper.setSlotsFromList(scoreboard);
				}else if (game.getStatus() == IStatus.STARTING) {
					List<String> scoreboard = new ArrayList<>();
					
					scoreboard.add("");
					scoreboard.add("§f Mapa: §a" + game.getName());
					scoreboard.add("§f");
					scoreboard.add("§f Jugadores: §e" + game.getPlayers().size() + "/" + game.getMaxPlayers());
					scoreboard.add("§f Bestia: §c" + (game.getBeastPlayer() == null ? "Sin seleccionar" : game.getBeastPlayer().getName()));
					scoreboard.add("§f");
					scoreboard.add("§f Iniciando en: §b" + (game.getTime() + "s"));
					scoreboard.add("§f");
					scoreboard.add("  §ewww.comugamers.com  ");
					
					helper.setSlotsFromList(scoreboard);
				}else if (game.getStatus() == IStatus.IN_GAME) {
					List<String> scoreboard = new ArrayList<>();

					scoreboard.add("");
					scoreboard.add("§f Mapa: §a" + game.getName());
					scoreboard.add("§f");
					scoreboard.add("§f Corredores: §e" + (game.getPlayersAlive().size() - 1));
					scoreboard.add("§f Bestia: ");
					scoreboard.add(" §7§m-->§c§o "+ game.getBeastPlayer().getName());
					scoreboard.add("§f");
					scoreboard.add("§f Termina en: §c" + Utils.timeString(game.getToEndTime()));
					scoreboard.add("§f");
					scoreboard.add("  §ewww.comugamers.com  ");
					
					helper.setSlotsFromList(scoreboard);
				}else if (game.getStatus() == IStatus.RESTART) {
					List<String> scoreboard = new ArrayList<>();
					
					scoreboard.add("");
					scoreboard.add("§f Mapa: §a" + game.getName());
					scoreboard.add("§f");
					scoreboard.add("§f Ganador: §a" + (game.getWinner() == IWinner.BEAST ? "Bestia" : "Corredores"));
					scoreboard.add("§f");
					scoreboard.add("§f Tiempo jugado: §b" + Utils.timeString(-game.getTime()));
					if (player.getGameMode() == GameMode.SURVIVAL) {
						scoreboard.add("§f Reiniciando en: §c" + player.getLevel());
					}else {
						scoreboard.add("§f Reiniciando...");						
					}
					scoreboard.add("§f ");
					scoreboard.add("  §ewww.comugamers.com  ");
					
					helper.setSlotsFromList(scoreboard);
				}
			}
		}
	}
}

