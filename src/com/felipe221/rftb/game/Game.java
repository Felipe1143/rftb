package com.felipe221.rftb.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import com.felipe221.rftb.RFTB;
import com.felipe221.rftb.Utils;
import com.felipe221.rftb.game.GameVote.HEARTS;
import com.felipe221.rftb.game.GameVote.MODE;
import com.felipe221.rftb.game.GameVote.TIME;
import com.felipe221.rftb.object.IStatus;
import com.felipe221.rftb.object.IWinner;

public class Game {
	public static HashMap<String, Game> games = new HashMap<String, Game>();
	
	private int max;
	private int min;
	private int time;
	
	private String arenaName;
	
	private IStatus status;
	
	private Location spawn;
	private Location spectator;
	private Location chest;
	private Location beast;
	private Location start;
	private Location end;
	private Location beastwait;
	
	private boolean portalToChest;
	private boolean portalToBack;
	
	private Player beastPlayer;
	private IWinner winner;
	
	private boolean stop;
	
	public Game(String name, int max, int min) {
		arenaName = name;
		status = IStatus.WAITING;
		beastPlayer = null;
		winner = null;
		
		setMaxPlayers(max);
		setMinPlayers(min);	
		
		setTime(RFTB.getConfigManager().getConfig().getInt("Game.Start time"));
		setStop(false);
		
		if (RFTB.getConfigManager().getMaps().contains("Games." + name + ".Spawn")){
			setSpawn(Utils.getLocationFromPath("Games." + name + ".Spawn"));
		}else {
			setSpawn(null);
		}
		
		if (RFTB.getConfigManager().getMaps().contains("Games." + name + ".Chest zone")){
			setChestZone(Utils.getLocationFromPath("Games." + name + ".Chest zone"));
		}else {
			setChestZone(null);
		}
		
		if (RFTB.getConfigManager().getMaps().contains("Games." + name + ".Spectation point")){
			setSpectator(Utils.getLocationFromPath("Games." + name + ".Spectation point"));
		}else {
			setSpectator(null);
		}
		
		if (RFTB.getConfigManager().getMaps().contains("Games." + name + ".Beast point")){
			setBeastPoint(Utils.getLocationFromPath("Games." + name + ".Beast point"));
		}else {
			setBeastPoint(null);
		}
		
		if (RFTB.getConfigManager().getMaps().contains("Games." + name + ".Start point")){
			setStartPoint(Utils.getLocationFromPath("Games." + name + ".Start point"));
		}else {
			setStartPoint(null);
		}
		
		if (RFTB.getConfigManager().getMaps().contains("Games." + name + ".End point")){
			setEndPoint(Utils.getLocationFromPath("Games." + name + ".End point"));
		}else {
			setEndPoint(null);
		}
		
		if (RFTB.getConfigManager().getMaps().contains("Games." + name + ".Beast wait point")){
			setBeastWaitPoint(Utils.getLocationFromPath("Games." + name + ".Beast wait point"));
		}else {
			setBeastWaitPoint(null);
		}
		
		if (RFTB.getConfigManager().getMaps().contains("Games." + name + ".Portal point to chest")){
			portalToChest = true;
		}else {
			portalToChest = false;
		}

		if (RFTB.getConfigManager().getMaps().contains("Games." + name + ".Portal point to back")){
			portalToBack = true;
		}else {
			portalToBack = false;
		}
		
		
		RFTB.getConfigManager().getMaps().set("Games." + name + ".Min players", min);
		RFTB.getConfigManager().getMaps().set("Games." + name + ".Max players", max);
		
		RFTB.getConfigManager().saveMaps();
		
		games.put(arenaName, this);
	}
	
	public static Game getArena(String name) {
		return games.get(name);
	}	
	
	public int getMaxPlayers() {
		return max;
	}

	public void setMaxPlayers(int max) {
		this.max = max;
	}

	public int getMinPlayers() {
		return min;
	}

	public void setMinPlayers(int min) {
		this.min = min;
	}

	public String getName() {
		return arenaName;	
	}
	
	public IStatus getStatus() {
		return status;
	}

	public void setStatus(IStatus status) {
		this.status = status;
	}
	
	public Location getSpawn() {
		return spawn;
	}

	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}
	
	public ArrayList<Sign> getSigns() {
		ArrayList<Sign> signList = new ArrayList<Sign>();
		HashMap<String, Location> clon = RFTB.getSignManager().getSigns();
		
		for (String map : clon.keySet()) {
			if (this.getName().equals(map)) {
				if (clon.get(map).getBlock().getType() == Material.SIGN_POST) {
					Sign sign = (Sign) clon.get(map).getBlock();
					
					signList.add(sign);
				}
			}
		}
		
		return signList;
	}

	public List<Player> getPlayers(){
		List<Player> playersList = new ArrayList<Player>();
		
		for (Player players : Bukkit.getOnlinePlayers()) {
			GamePlayer meta = GamePlayer.getMeta(players);
				
			if (meta.getArena() != null) {
				if (meta.getArena().equals(arenaName)){
					playersList.add(players);
				}
			}
		}
		
		return playersList;
	}
	
	public List<Player> getPlayersAlive(){
		List<Player> playersList = new ArrayList<Player>();
		
		for (Player players : Bukkit.getOnlinePlayers()) {
			GamePlayer meta = GamePlayer.getMeta(players);
				
			if (meta.getArena() != null) {
				if (meta.getArena().equals(arenaName)){
					if (meta.isAlive()) {
						playersList.add(players);
					}
				}
			}
		}
		
		return playersList;
	}

	public Location getSpectator() {
		return spectator;
	}

	public void setSpectator(Location spectator) {
		this.spectator = spectator;
	}

	public Location getChestZone() {
		return chest;
	}

	public void setChestZone(Location chest) {
		this.chest = chest;
	}

	public Location getStartPoint() {
		return start;
	}

	public void setStartPoint(Location start) {
		this.start = start;
	}

	public Location getBeastPoint() {
		return beast;
	}

	public void setBeastPoint(Location beast) {
		this.beast = beast;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public Location getEndPoint() {
		return end;
	}
	
	public int getToEndTime() {
		if (RFTB.getConfigManager().getConfig().getInt("Game.Time limit") != -1){
			return (15 * 60) + time;
		}else {
			return 0;
		}
	}

	public void setEndPoint(Location end) {
		this.end = end;
	}
	
	public boolean hasPortalToChest() {
		return portalToChest;
	}

	public void setPortalToChest(boolean portal) {
		this.portalToChest = portal;
	}
	
	public boolean hasPortalToBack() {
		return portalToBack;
	}

	public void setPortalToBack(boolean portal) {
		this.portalToBack = portal;
	}

	public Player getBeastPlayer() {
		return beastPlayer;
	}

	public void setBeastPlayer(Player beastPlayer) {
		this.beastPlayer = beastPlayer;
	}
	
	public IWinner getWinner() {
		return winner;
	}

	public void setWinner(IWinner winner) {
		this.winner = winner;
	}
	
	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	public boolean isPlayable() {
		if (getSpawn() != null &&
			getEndPoint() != null &&
			getStartPoint() != null &&
			getBeastPoint() != null &&
			getChestZone() != null &&
			getSpectator() != null &&
			getBeastWaitPoint() != null &&
			Utils.getLobbyPoint() != null &&
			hasPortalToBack() == true &&
			hasPortalToChest() == true) {
			
			return true;
		}
		
		return false;
	}

	public Location getBeastWaitPoint() {
		return beastwait;
	}

	public void setBeastWaitPoint(Location beastwait) {
		this.beastwait = beastwait;
	}
}
