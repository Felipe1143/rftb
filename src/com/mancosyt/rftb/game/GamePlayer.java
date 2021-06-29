package com.mancosyt.rftb.game;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

public class GamePlayer {
    public static HashMap<String, GamePlayer> metaTable = new HashMap<String, GamePlayer>();

    public static GamePlayer getMeta(Player player) {
        return getMeta(player.getName());
    }

    public static GamePlayer getMeta(String username) {
        if (!metaTable.containsKey(username))
            metaTable.put(username, new GamePlayer());
        return metaTable.get(username);
    }
    
    public static void reset() {
        metaTable.clear();
    }

    private Map<Object,Object> data;
    private String arena;
    private boolean alive;
    private boolean chestzone;
    private int doublejump;
    
    public GamePlayer() {
        setArena(null);
        alive = true;
        chestzone = false;
        doublejump = 0;
    }
    
    public Object getData(Object key) {
		if(data == null) {
			return null;
		}
		
		return data.get(key);
	}
	
	public void setData(Object key, Object value) {
		if(data == null) {
			data = new HashMap<Object,Object>();
		}
	
		data.put(key, value);
	} 

    public void setAlive(boolean b) {
        alive = b;
    }

    public boolean isAlive() {
        return alive;
    }

	public String getArena() {
		return arena;
	}

	public void setArena(String arena) {
		this.arena = arena;
	}

	public boolean inChestZone() {
		return chestzone;
	}

	public void setInChestZone(boolean chestzone) {
		this.chestzone = chestzone;
	}

	public int getDoubleJump() {
		return doublejump;
	}

	public void setDoubleJump(int doublejump) {
		this.doublejump = doublejump;
	}
}
