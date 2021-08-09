package com.felipe221.rftb.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import com.felipe221.rftb.RFTB;
import com.felipe221.rftb.Utils;
import com.felipe221.rftb.game.GameVote.TIME;
import com.felipe221.rftb.object.IStatus;

public class GameVote {	
	public enum MODE{
		NORMAL, FAST, NULL;
	}
	
	public enum TIME{
		STORM, DIA, NOCHE, NULL;
	}
	
	public enum HEARTS{
		NORMAL, DOUBLE, NULL;
	}
	
	public enum TYPE{
		HEARTS, TIME, MODE, NULL;
	}
	
	public static HashMap<String, GameVote> vote = new HashMap<String, GameVote>();
	
	private HashMap<MODE, Integer> mode;
	private HashMap<TIME, Integer> time;
	private HashMap<HEARTS, Integer> hearts;
	
	private HashMap<String, HashMap<TYPE, String>> playerVotes = new HashMap<String, HashMap<TYPE, String>>();
	
	private boolean running;
	
	public GameVote(String map) {	
		hearts = new HashMap<HEARTS, Integer>();
		time = new HashMap<TIME, Integer>();
		mode = new HashMap<MODE, Integer>();
		
		for (MODE modes : MODE.values()) {
			mode.put(modes, 0);
		}
		
		for (TIME times : TIME.values()) {
			time.put(times, 0);
		}
		
		for (HEARTS heartss : HEARTS.values()) {
			hearts.put(heartss, 0);
		}
		
		playerVotes = new HashMap<String, HashMap<TYPE, String>>();
		
		running = true;
	}
	
	public static void reloadGame(String map) {
		vote.remove(map);
	}
	
	public void voteMode(Player voter, MODE type) {
		HashMap<TYPE, String> votesType = null;
		Game game = Game.getArena(GamePlayer.getMeta(voter).getArena());
		
		if (playerVotes.containsKey(voter.getName())){
			votesType = playerVotes.get(voter.getName());
		}else {
			votesType = new HashMap<TYPE, String>();
		}
		
		if (!votesType.containsKey(TYPE.MODE)) {
			votesType.put(TYPE.MODE, type.toString());
			playerVotes.put(voter.getName(), votesType);
			
			int votes = mode.get(type) + 1;
			
			mode.put(type, votes);
			
			voter.sendMessage("§e§l>> §f¡Votaste correctamente por el modo de juego §a" + (type == MODE.FAST ? "Rápido" : "Normal") + "§f!");
		}else {
			voter.sendMessage("§e§l>> §f¡Ya votaste por el modo!");
			
			voter.playSound(voter.getLocation(), Sound.VILLAGER_NO, 1, 1);
		}
	}
	
	public void voteHeart(Player voter, HEARTS type) {
		HashMap<TYPE, String> votesType = null;
		Game game = Game.getArena(GamePlayer.getMeta(voter).getArena());
		
		if (playerVotes.containsKey(voter.getName())){
			votesType = playerVotes.get(voter.getName());
		}else {
			votesType = new HashMap<TYPE, String>();
		}
		
		if (!votesType.containsKey(TYPE.HEARTS)) {
			votesType.put(TYPE.HEARTS, type.toString());
			playerVotes.put(voter.getName(), votesType);
			
			int votes = hearts.get(type) + 1;
			
			hearts.put(type, votes);
			
			voter.sendMessage("§e§l>> §f¡Votaste correctamente por la vida §a" + (type == HEARTS.DOUBLE ? "Doble" : "Normal") + "§f!");
		}else {
			voter.sendMessage("§e§l>> §f¡Ya votaste por la cantidad de corazones!");
			
			voter.playSound(voter.getLocation(), Sound.VILLAGER_NO, 1, 1);
		}
	}
	
	public void voteTime(Player voter, TIME type) {
		HashMap<TYPE, String> votesType = null;
		Game game = Game.getArena(GamePlayer.getMeta(voter).getArena());
		
		if (playerVotes.containsKey(voter.getName())){
			votesType = playerVotes.get(voter.getName());
		}else {
			votesType = new HashMap<TYPE, String>();
		}
		
		if (!votesType.containsKey(TYPE.TIME)) {
			votesType.put(TYPE.TIME, type.toString());
			playerVotes.put(voter.getName(), votesType);
			
			int votes = time.get(type) + 1;
			
			time.put(type, votes);
		
			if (type == TIME.DIA) {
				voter.sendMessage("§e§l>> §f¡Votaste correctamente por el tiempo §aDía§f!");
			}else if (type == TIME.NOCHE) {
				voter.sendMessage("§e§l>> §f¡Votaste correctamente por el tiempo §aNoche§f!");
			}else if (type == TIME.STORM) {
				voter.sendMessage("§e§l>> §f¡Votaste correctamente por el tiempo §aLluvioso§f!");
			}		
		}else {
			voter.sendMessage("§e§l>> §f¡Ya votaste por el tiempo!");
			
			voter.playSound(voter.getLocation(), Sound.VILLAGER_NO, 1, 1);
		}
	}
	
	public String getWinner(TYPE type) {
		if (type == TYPE.MODE) {
			String winner = "NULL";
			Integer highest = -1;
			
			for (MODE modea : mode.keySet()) {				
				int totalVotes = mode.get(modea);
				
				if (totalVotes > highest && totalVotes != 0) {
					winner = modea.toString();
					highest = totalVotes;
				}
			}
			
			return winner;
		}else if (type == TYPE.TIME) {
			String winner = "NULL";
			Integer highest = -1;
			
			for (TIME timea : time.keySet()) {				
				int totalVotes = time.get(timea);
				
				if (totalVotes > highest && totalVotes != 0) {
					winner = timea.toString();
					highest = totalVotes;
				}
			}
			
			return winner;
		}else if (type == TYPE.HEARTS) {
			String winner = "NULL";
			Integer highest = -1;
			
			for (HEARTS heartsa : hearts.keySet()) {				
				int totalVotes = hearts.get(heartsa);
				
				if (totalVotes > highest && totalVotes != 0) {
					winner = heartsa.toString();
					highest = totalVotes;
				}
			}
			
			return winner;
		}
		
		return null;
	}

	public void end() {
		running = false;
	}

	public boolean isRunning() {
		return running;
	}
	
	public void removePlayer(Player player) {
		if (playerVotes.containsKey(player.getName())) {
			HashMap<TYPE, String> votesType = playerVotes.get(player.getName());
			
			if (votesType.containsKey(TYPE.MODE)) {
				String modeString = votesType.get(TYPE.MODE);
				MODE modeRemove = MODE.valueOf(modeString);
				
				int votes = mode.get(modeRemove) - 1;
				
				mode.put(modeRemove, votes);				
			}
			
			if (votesType.containsKey(TYPE.TIME)) {
				String timeString = votesType.get(TYPE.TIME);
				TIME timeRemove = TIME.valueOf(timeString);
				
				int votes = time.get(timeRemove) - 1;
				
				time.put(timeRemove, votes);				
			}
			
			if (votesType.containsKey(TYPE.HEARTS)) {
				String heartsString = votesType.get(TYPE.HEARTS);
				HEARTS heartsRemove = HEARTS.valueOf(heartsString);
				
				int votes = hearts.get(heartsRemove) - 1;
				
				hearts.put(heartsRemove, votes);				
			}
		}
		
		playerVotes.remove(player.getName());
	}
	
	public boolean hasPlayer(Player player) {
		if (playerVotes.containsKey(player.getName())) {
			return true;
		}else {
			return false;
		}
	}
	
    public static GameVote getArena(String map) {
        if (!vote.containsKey(map)) {
            vote.put(map, new GameVote(map));
        }
        
        return vote.get(map);
    }
	
	public Integer getVoteMode(MODE type) {
		return mode.get(type);
	}	
	
	public Integer getVoteTime(TIME type) {
		return time.get(type);
	}	
	
	public Integer getVoteHearths(HEARTS type) {
		return hearts.get(type);
	}	
}
