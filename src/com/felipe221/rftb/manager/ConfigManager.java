package com.felipe221.rftb.manager;

import java.io.File;
import java.io.IOException;

import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import com.felipe221.rftb.RFTB;
import com.felipe221.rftb.Utils;

import net.md_5.bungee.api.ChatColor;

public class ConfigManager {
	public static  File config = new File(RFTB.getInstance().getDataFolder(), "config.yml");
	public static File maps = new File(RFTB.getInstance().getDataFolder(), "maps.yml");
	public static File messages = new File(RFTB.getInstance().getDataFolder(), "messages.yml");
	public static File signs = new File(RFTB.getInstance().getDataFolder(), "signs.yml");
	public static File stats = new File(RFTB.getInstance().getDataFolder(), "stats.yml");
	public static File chest = new File(RFTB.getInstance().getDataFolder(), "chests.yml");
	
	public static FileConfiguration configyml = YamlConfiguration.loadConfiguration(config);
	public static FileConfiguration mapsyml = YamlConfiguration.loadConfiguration(maps);
	public static FileConfiguration signsyml = YamlConfiguration.loadConfiguration(signs);
	public static FileConfiguration messagesyml = YamlConfiguration.loadConfiguration(messages);
	public static FileConfiguration statsyml = YamlConfiguration.loadConfiguration(stats);
	public static FileConfiguration chestyml = YamlConfiguration.loadConfiguration(chest);
	
	public static void createFiles() {
		if (!config.exists()) {
			config.getParentFile().mkdirs();
			RFTB.getInstance().saveResource("config.yml", false);
			Utils.sendLog("&a¡The config.yml created!");
		}
		
		if (!maps.exists()) {
			maps.getParentFile().mkdirs();
			RFTB.getInstance().saveResource("maps.yml", false);
			Utils.sendLog("&a¡The maps.yml created!");
		}
		
		if (!signs.exists()) {
			signs.getParentFile().mkdirs();
			RFTB.getInstance().saveResource("signs.yml", false);
			Utils.sendLog("&a¡The signs.yml created!");
		}
		
		if (!messages.exists()) {
			messages.getParentFile().mkdirs();
			RFTB.getInstance().saveResource("messages.yml", false);
			Utils.sendLog("&a¡The messages.yml created!");
		}
		
		if (!stats.exists()) {
			stats.getParentFile().mkdirs();
			RFTB.getInstance().saveResource("stats.yml", false);
			Utils.sendLog("&a¡The stats.yml created!");
		}
		
		if (!chest.exists()) {
			chest.getParentFile().mkdirs();
			RFTB.getInstance().saveResource("chests.yml", false);
			Utils.sendLog("&a¡The stats.yml created!");
		}
		
		try {
			configyml.load(config);
			mapsyml.load(maps);
			signsyml.load(signs);
			messagesyml.load(messages);
			statsyml.load(stats);
			chestyml.load(chest);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		
	}
	
	public  FileConfiguration getConfig() {
		return configyml;
	}
	
	public  FileConfiguration getMaps() {
		return mapsyml;
	}
	
	public FileConfiguration getSigns() {
		return signsyml;
	}
	
	public  FileConfiguration getMessages() {
		return messagesyml;
	}
	
	public  FileConfiguration getStats() {
		return statsyml;
	}
	
	public  FileConfiguration getChests() {
		return chestyml;
	}
	
	public  void saveConfig() {
		try {
			configyml.save(config);
		} catch (Exception e) {
			printException(e, "config.yml");
        }
    }
	
	public  void saveMaps() {
		try {
			mapsyml.save(maps);
		} catch (Exception e) {
			printException(e, "maps.yml");
        }
    }
	
	public  void saveStats() {
		try {
			statsyml.save(stats);
		} catch (Exception e) {
			printException(e, "stats.yml");
        }
    }
	
	public  void saveSigns() {
		try {
			signsyml.save(signs);
		} catch (Exception e) {
			printException(e, "signs.yml");
        }
    }
	
	public  void saveMessages() {
		try {
			messagesyml.save(messages);
		} catch (Exception e) {
			printException(e, "messages.yml");
        }
    }
	
	public  void saveChests() {
		try {
			chestyml.save(chest);
		} catch (Exception e) {
			printException(e, "chests.yml");
        }
    }

    public  void reloadConfig() {
    	try {
    		configyml.load(config);
    	} catch (Exception e) {
    		printException(e, "config.yml");
        }
    }
    
    public  void reloadMaps() {
    	try {
    		mapsyml.load(maps);
    	} catch (Exception e) {
    		printException(e, "maps.yml");
        }
    }
    
    public  void reloadSigns() {
    	try {
    		signsyml.load(signs);
    	} catch (Exception e) {
    		printException(e, "signs.yml");
        }
    }
    
    public  void reloadStats() {
    	try {
    		signsyml.load(stats);
    	} catch (Exception e) {
    		printException(e, "signs.yml");
        }
    }
    
    public  void reloadMessages() {
    	try {
    		messagesyml.load(messages);
    	} catch (Exception e) {
    		printException(e, "messages.yml");
        }
    }
    
    public  void reloadChests() {
    	try {
    		chestyml.load(chest);
    	} catch (Exception e) {
    		printException(e, "chests.yml");
        }
    }
    
    
    public  String get(String file, String get) {
    	if (file.equals("messages.yml")) {
    		return get.replaceAll("&", "§");    		
    	}
    	
    	if (file.equals("signs.yml")) {
    		return get.replaceAll("&", "§");    
    	}
    	
    	if (file.equals("config.yml")) {
    		return get.replaceAll("&", "§");    
    	}
    	
    	if (file.equals("maps.yml")) {
    		return get.replaceAll("&", "§");
    	}
    	
    	if (file.equals("stats.yml")) {
    		return get.replaceAll("&", "§");
    	}
    	
		return null;
    }
    
    private  void printException(Exception e, String filename) {
        if (e instanceof IOException) {
        	Utils.sendLog("I/O exception while handling " + filename);
        } else if (e instanceof InvalidConfigurationException) {
            Utils.sendLog("Invalid configuration in " + filename);
        }
        e.printStackTrace();
    }
}
