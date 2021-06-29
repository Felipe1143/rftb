package com.mancosyt.rftb;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.mancosyt.rftb.commands.RFTBCommand;
import com.mancosyt.rftb.game.Game;
import com.mancosyt.rftb.game.GameManager;
import com.mancosyt.rftb.game.GamePlayer;
import com.mancosyt.rftb.game.GameVote;
import com.mancosyt.rftb.game.GameVote.HEARTS;
import com.mancosyt.rftb.game.GameVote.MODE;
import com.mancosyt.rftb.game.GameVote.TIME;
import com.mancosyt.rftb.listeners.CheckpointListener;
import com.mancosyt.rftb.listeners.ChestListener;
import com.mancosyt.rftb.listeners.InteractListener;
import com.mancosyt.rftb.listeners.MenuListener;
import com.mancosyt.rftb.listeners.PlaceListener;
import com.mancosyt.rftb.listeners.PlayerListener;
import com.mancosyt.rftb.listeners.PortalListener;
import com.mancosyt.rftb.listeners.SignListener;
import com.mancosyt.rftb.listeners.WorldListener;
import com.mancosyt.rftb.manager.ConfigManager;
import com.mancosyt.rftb.manager.DatabaseManager;
import com.mancosyt.rftb.manager.LevelManager;
import com.mancosyt.rftb.manager.ScoreboardManager;
import com.mancosyt.rftb.manager.SignManager;
import com.mancosyt.rftb.object.IChest;

import net.md_5.bungee.api.ChatColor;

public class RFTB extends JavaPlugin{
	private static Plugin plugin;
	private static GameManager manager;
	private static Game game;
	private static GamePlayer meta;
	private static ConfigManager config;
	private static PlayerListener plistener;
	private static LevelManager levels;
	private static SignManager signs;
	private static SignListener signsL;
	private static DatabaseManager db;
    private static ProtocolManager protocolManager;
	
	public static Plugin getInstance() {
		return plugin;
	}

	public static Game getGame() {
		return game;
	}
	
	public static GameManager getGameManager() {
		return manager;
	}
	
	public static GamePlayer getGamePlayer() {
		return meta;
	}
	
	public static PlayerListener getPlayerEvents() {
		return plistener;
	}
	
	public static ConfigManager getConfigManager() {
		return config;
	}
	
	public static LevelManager getLevelManager() {
		return levels;
	}
	
	public static SignManager getSignManager() {
		return signs;
	}
	
	public static SignListener getSignListener() {
		return signsL;
	}	
	
	public static DatabaseManager getDatabaseManager() {
		return db;
	}	
	
	public static ProtocolManager getProtocolManager() {
		return protocolManager;
	}	
	
	@Override
	public void onEnable() {
		plugin = this;
		manager = new GameManager();
		meta = new GamePlayer();
		plistener = new PlayerListener();
		levels = new LevelManager();
		signs = new SignManager();
		signsL = new SignListener();
		config = new ConfigManager();
		protocolManager = ProtocolLibrary.getProtocolManager();
		
		Utils.sendLog("&a-> ¡RFTB v1.0.0 loaded!" );
		Utils.sendLog("&a-> Plugin developed by Felipe221 <-" );
		
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
        	Utils.sendLog("&C-> Please install 'PlaceholderAPI' for all placeholders");
        	
        	this.setEnabled(false);
        } else {
           	Utils.sendLog("&a-> PlaceholderAPI hooked!");
        }
        
        getCommand("rftb").setExecutor(new RFTBCommand(this));
        
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new SignListener(), this);
        getServer().getPluginManager().registerEvents(new PlaceListener(), this);
        getServer().getPluginManager().registerEvents(new PortalListener(), this);
        getServer().getPluginManager().registerEvents(new ChestListener(), this);
        getServer().getPluginManager().registerEvents(new InteractListener(), this);
        getServer().getPluginManager().registerEvents(new CheckpointListener(), this);
        getServer().getPluginManager().registerEvents(new WorldListener(), this);
        
        IChest.LoadChests();
		ConfigManager.createFiles();
		GameManager.LoadGames();
		SignManager.LoadSigns();	
		LevelManager.register();
		SignManager.SignUpdate();
		ScoreboardManager.LoadScoreboard();
		
		for (World world : Bukkit.getWorlds()) {
			for (Entity entity : world.getEntities()) {
				entity.remove();
			}
		}
		
		String host = "167.114.157.40";
		Integer port = 3306;
		String name = "s328_EDLB";
		String user = "u328_cQhJQgbASx";
		String pass = "MqaYkf8sjUHqOU7NeCdjPxS3";
		
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerOutgoingPluginChannel(this, "RedisBungee");      
        
		db = new DatabaseManager(host, port, name, user, pass, this);

		getDatabaseManager().query("CREATE TABLE IF NOT EXISTS `Stats` ( `name` varchar(16) NOT NULL, `skin` INT(16) NOT NULL, `beast_kills` INT(16) NOT NULL, `xp` INT(16) NOT NULL, `games` INT(16) NOT NULL, `wins` INT(16) NOT NULL, `deaths` INT(16) NOT NULL, `kills` INT(16) NOT NULL, `gamesArray` VARCHAR(10000) NOT NULL, UNIQUE KEY `name` (`name`) ) ENGINE=InnoDB DEFAULT CHARSET=latin1;");	

	}
	
	@Override
	public void onDisable() {
		
	}
	
	public static String getVars(Player player, String map, String get) {
		if (map != null && player != null) {
			Game game = Game.getArena(map);
			GameVote vote = GameVote.getArena(map);
			GamePlayer meta = GamePlayer.getMeta(player);
			
			String returned = get.replaceAll("%players%", "" + game.getPlayers().size())
					.replaceAll("%min%", "" + game.getMinPlayers())
					.replaceAll("%max%", "" + game.getMaxPlayers())
					.replaceAll("%map%", "" + game.getName())
					.replaceAll("%arena%", "" + game.getName())
					.replaceAll("%time%", "" + game.getTime())
					.replaceAll("%seconds%", "" + game.getTime())
					.replaceAll("%map_time%", "" + Utils.timeString(-game.getTime()))
					.replaceAll("%end_time%", "" + Utils.timeString(game.getToEndTime()))
					.replaceAll("%beast%", "" + (game.getBeastPlayer() == null ? "" : game.getBeastPlayer().getName()))
					.replaceAll("%mode_fast%", "" + vote.getVoteMode(MODE.FAST))
					.replaceAll("%mode_normal%", "" + vote.getVoteMode(MODE.NORMAL))
					.replaceAll("%time_normal%", "" + vote.getVoteTime(TIME.DIA))
					.replaceAll("%time_storm%", "" + vote.getVoteTime(TIME.STORM))
					.replaceAll("%hearts_double%", "" + vote.getVoteHearths(HEARTS.DOUBLE))
					.replaceAll("%hearts_normal%", "" + vote.getVoteHearths(HEARTS.NORMAL))
					.replaceAll("%jumps%", "" + meta.getDoubleJump())
					.replaceAll("%beast_distance%", "" + (game.getBeastPlayer() == null ? 0 : (int) player.getLocation().distance(game.getBeastPlayer().getLocation())))
					.replaceAll("%player%", "" + player.getName());
			
			
			
			return ChatColor.translateAlternateColorCodes('&', returned);
		}
		
		if (map == null && player != null){			
			GamePlayer meta = GamePlayer.getMeta(player);

			return ChatColor.translateAlternateColorCodes('&', get);
		}
		
		if (map != null && player == null) {
			Game game = Game.getArena(map);
			GameVote vote = GameVote.getArena(map);
			
			String returned = get.replaceAll("%players%", "" + game.getPlayers().size())
					.replaceAll("%min%", "" + game.getMinPlayers())
					.replaceAll("%max%", "" + game.getMaxPlayers())
					.replaceAll("%map%", "" + game.getName())
					.replaceAll("%arena%", "" + game.getName())
					.replaceAll("%time%", "" + game.getTime())
					.replaceAll("%seconds%", "" + game.getTime())
					.replaceAll("%beast%", "" + (game.getBeastPlayer() == null ? "" : game.getBeastPlayer().getName()))
					.replaceAll("%map_time%", "" + Utils.timeString(-game.getTime()))
					.replaceAll("%end_time%", "" + Utils.timeString(game.getToEndTime()))
					.replaceAll("%mode_fast%", "" + vote.getVoteMode(MODE.FAST))
					.replaceAll("%mode_normal%", "" + vote.getVoteMode(MODE.NORMAL))
					.replaceAll("%time_normal%", "" + vote.getVoteTime(TIME.DIA))
					.replaceAll("%time_storm%", "" + vote.getVoteTime(TIME.STORM))
					.replaceAll("%hearts_double%", "" + vote.getVoteHearths(HEARTS.DOUBLE))
					.replaceAll("%hearts_normal%", "" + vote.getVoteHearths(HEARTS.NORMAL));
					
			return ChatColor.translateAlternateColorCodes('&', returned);	
		}
		
		if (map == null && player == null) {
			String returned = get;

			return ChatColor.translateAlternateColorCodes('&', returned);	
		}
		
		return null;
	}		
}
