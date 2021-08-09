package com.felipe221.rftb;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.json.JSONObject;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comugamers.core.CGPlayerData;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;


public class Utils {
	public static void sendLog(String msg) {
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
	}
	public static String Color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
	
	public static void command(Player player, String cmd) {
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd.replaceAll("%player%", player.getName()));
	}
	
	public static void sendAnnouncement(Player p, String msg) {
		String s = ChatColor.translateAlternateColorCodes('&', msg);

		IChatBaseComponent icbc = ChatSerializer.a("{\"text\": \"" + s + "\"}");
		PacketPlayOutChat bar = new PacketPlayOutChat(icbc, (byte) 2);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(bar);
	}
	
    public static ArrayList<String> fromString(String string){
    	ArrayList<String> arrayFinal = new ArrayList<String>();
    	
    	String[] params = string.split("//");  	
    	
    	for (String s: params) {
    		arrayFinal.add(s);
    	}
    	
    	return arrayFinal;
    }
    
    public static String getPrefix(Player p, String Color) {
		String prefix = "";
		
		if (p.hasPermission("comu.owner")){
            prefix = "§4§lCREADOR §4";
        }
        else if (p.hasPermission("comu.encargadocreativo")){
            prefix = "§2§lJ§a§lCREA §a";
        }
        else if (p.hasPermission("comu.developer")){
            prefix = "§2§lDEVELOPER §2";
        }
        else if (p.hasPermission("comu.manager")){
            prefix = "§5§lMANAGER §5";
        } 
        else if (p.hasPermission("comu.config")){
            prefix = "§a§lCONFIG §a";
        }
        else if (p.hasPermission("comu.auxm")){
            prefix = "§d§lAUX-M §d";
        }
        else if (p.hasPermission("comu.moderador")){
            prefix = "§6§lMOD §6";
        }
        else if (p.hasPermission("comu.helper")){
            prefix = "§e§lHELPER §e";
        }
        else if (p.hasPermission("comu.soporte")){
            prefix = "§b§lSOPORTE §b";
        }
        else if (p.hasPermission("comu.mfamoso")){
            prefix = "§d§lM-FAMOSO §d";
        }
        else if (p.hasPermission("comu.partner")){
            prefix = "§a§lPARTNER §a";
        }
        else if (p.hasPermission("comu.famoso")){
            prefix = "§d§lFAMOSO §d";
        }
        else if (p.hasPermission("comu.streamer")){
            prefix = "§5§lTWITCH §5";
        }
        else if (p.hasPermission("comu.youtuber")){
            prefix = "§6§lYOUTUBER §6";
        }
        else if (p.hasPermission("comu.miniyt")){
            prefix = "§f§lMINIYT §f";
        }
        else if (p.hasPermission("comu.builder")){
            prefix = "§9§lBUILDER §9";
        }
        else if (p.hasPermission("comu.ultra")){
            prefix = CGPlayerData.getCGPlayer(p).getPrefixColor() + "§lULTRA " + CGPlayerData.getCGPlayer(p).getPrefixColor();
        }
        else if (p.hasPermission("comu.ultimate")){
            prefix = "§d§lULTIMATE §d";
        }
        else if (p.hasPermission("comu.elite")){
            prefix = "§b§lELITE §b";
        }
        else if (p.hasPermission("comu.vip")){
            prefix = "§a§lVIP §a";
        } else {
            prefix = Color + "";
        }
		
		return prefix + "";
	}
    
    public static void sendPacket(Player player, Object packet) {
	    try {
	        Object handle = player.getClass().getMethod("getHandle").invoke(player);
	        Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
	        playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	public static Class<?> getNMSClass(String name) {
	    try {
	        return Class.forName("net.minecraft.server."
	                + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + "." + name);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	 enum ServerPackage {

	        MINECRAFT("net.minecraft.server." + getServerVersion()),
	        CRAFTBUKKIT("org.bukkit.craftbukkit." + getServerVersion());

	        private final String path;

	        ServerPackage(String path) {
	            this.path = path;
	        }

	        public static String getServerVersion() {
	            return Bukkit.getServer().getClass().getPackage().getName().substring(23);
	        }

	        @Override
	        public String toString() {
	            return path;
	        }

	        public Class<?> getClass(String className) throws ClassNotFoundException {
	            return Class.forName(this.toString() + "." + className);
	        }

	    }


	    public static void setHeaderAndFooter(Player player, String header, String footer) {
	        PacketContainer tab = new PacketContainer(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);
	        tab.getModifier().writeDefaults();
	        tab.getChatComponents().write(0, WrappedChatComponent.fromText(Color(header)));
	        tab.getChatComponents().write(1, WrappedChatComponent.fromText(Color(footer)));
	        try {
	        	RFTB.getProtocolManager().sendServerPacket(player, tab);
	        } catch (InvocationTargetException e) {
	            e.printStackTrace();
	        }
	    }

	    public static void mandarTitle(Player player, String title, String subtitle, int FADE_IN, int SHOW_LENGTH, int FADE_OUT) {
	        PacketContainer packet = new PacketContainer(PacketType.Play.Server.TITLE);
	        packet.getTitleActions().write(0, EnumWrappers.TitleAction.TIMES);
	        packet.getIntegers().write(0, FADE_IN);
	        packet.getIntegers().write(1, SHOW_LENGTH);
	        packet.getIntegers().write(2, FADE_OUT);
	        try {
	            RFTB.getProtocolManager().sendServerPacket(player, packet);
	        } catch (InvocationTargetException e) {
	            e.printStackTrace();
	        }
	        if (title != null) {
	            PacketContainer titlePacket = new PacketContainer(PacketType.Play.Server.TITLE);
	            titlePacket.getTitleActions().write(0, EnumWrappers.TitleAction.TITLE);
	            titlePacket.getChatComponents().write(0, WrappedChatComponent.fromText(Color(title)));
	            try {
	                RFTB.getProtocolManager().sendServerPacket(player, titlePacket);
	            } catch (InvocationTargetException e) {
	                e.printStackTrace();
	            }
	        }
	        if (subtitle != null) {
	            PacketContainer subtitlePacket = new PacketContainer(PacketType.Play.Server.TITLE);
	            subtitlePacket.getTitleActions().write(0, EnumWrappers.TitleAction.SUBTITLE);
	            subtitlePacket.getChatComponents().write(0, WrappedChatComponent.fromText(Color(subtitle)));
	            try {
	            	RFTB.getProtocolManager().sendServerPacket(player, subtitlePacket);
	            } catch (InvocationTargetException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    public static void sendActionbar(Player player, String tras) {
	        if (Bukkit.getServer().getClass().getPackage().getName().substring(23).startsWith("v1_8")){
	            WrappedChatComponent text = WrappedChatComponent.fromText(Color(tras));
	            PacketContainer chatPacket =  RFTB.getProtocolManager().createPacket(PacketType.Play.Server.CHAT);
	            chatPacket.getChatComponents().write(0, text);
	            chatPacket.getBytes().write(0, (byte) 2);
	            try {
	            	RFTB.getProtocolManager().sendServerPacket(player, chatPacket);
	            } catch (InvocationTargetException e) {
	                throw new RuntimeException(e);
	            }
	        } else {
	            JSONObject json = new JSONObject();
	            json.put("text", Color(tras));
	            try {
	                Class<?> clsIChatBaseComponent = ServerPackage.MINECRAFT.getClass("IChatBaseComponent");
	                Class<?> clsChatMessageType = ServerPackage.MINECRAFT.getClass("ChatMessageType");
	                Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
	                Object playerConnection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);
	                Object chatBaseComponent = ServerPackage.MINECRAFT.getClass("IChatBaseComponent$ChatSerializer").getMethod("a", String.class).invoke(null, json.toString());
	                Object chatMessageType = clsChatMessageType.getMethod("valueOf", String.class).invoke(null, "GAME_INFO");
	                Object packetPlayOutChat = ServerPackage.MINECRAFT.getClass("PacketPlayOutChat").getConstructor(clsIChatBaseComponent, clsChatMessageType).newInstance(chatBaseComponent, chatMessageType);
	                playerConnection.getClass().getMethod("sendPacket", ServerPackage.MINECRAFT.getClass("Packet")).invoke(playerConnection, packetPlayOutChat);
	            } catch (Throwable e) {
	                throw new RuntimeException(e);
	            }
	        }


	    }

	    public static void sendToAll(String text) {
	        for (Player player : Bukkit.getOnlinePlayers()) {
	            sendActionbar(player, text);
	        }
	    }
	    
	    public static ItemStack GameProfile(String user) {
	        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short)3 );
	        if(user == null)return skull;
	        SkullMeta headMeta = (SkullMeta) skull.getItemMeta();
	        headMeta.setOwner(user);
	        skull.setItemMeta(headMeta);
	        return skull;
	    }

	
	public static void send(Player player, String title, String subtitle) {
	    try {
	        Object chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class)
	                .invoke(null, "{\"text\": \"" + title + "\"}");
	        Constructor<?> titleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(
	                getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"),
	                int.class, int.class, int.class);
	        Object packet = titleConstructor.newInstance(
	                getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null), chatTitle,
	                40, 60, 40);

	        Object chatsTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", String.class)
	                .invoke(null, "{\"text\": \"" + subtitle + "\"}");
	        Constructor<?> timingTitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(
	                getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"),
	                int.class, int.class, int.class);
	        Object timingPacket = timingTitleConstructor.newInstance(
	                getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null), chatsTitle,
	                40, 60, 40);

	        sendPacket(player, packet);
	        sendPacket(player, timingPacket);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
    
    public static Location getLobbyPoint() {
    	if (RFTB.getConfigManager().getMaps().contains("Lobby.World")) {
	    	String world = RFTB.getConfigManager().getMaps().getString("Lobby.World");
			double x = RFTB.getConfigManager().getMaps().getDouble("Lobby.X");
			double y = RFTB.getConfigManager().getMaps().getDouble("Lobby.Y");
			double z = RFTB.getConfigManager().getMaps().getDouble("Lobby.Z");
			float yaw = (float) RFTB.getConfigManager().getMaps().getDouble("Lobby.Yaw");
			float pitch = (float) RFTB.getConfigManager().getMaps().getDouble("Lobby.Pitch");
			
			Location loc = new Location(Bukkit.getWorld(world), x,y,z,yaw,pitch);

			return loc;
    	}else {
    		return null;
    	}
    }
    
    public static Location getLocationFromPath(String path) {  	
    	Location loc = null;
    	
    	String world = RFTB.getConfigManager().getMaps().getString(path + ".World");
		double x = RFTB.getConfigManager().getMaps().getDouble(path + ".X");
		double y = RFTB.getConfigManager().getMaps().getDouble(path + ".Y");
		double z = RFTB.getConfigManager().getMaps().getDouble(path + ".Z");
		double yaw = RFTB.getConfigManager().getMaps().getDouble(path + ".Yaw");
		double pitch = RFTB.getConfigManager().getMaps().getDouble(path + ".Pitch");
		
		if (yaw == 0.0) {
			loc = new Location(Bukkit.getWorld(world), x,y,z);   	
		}else {
			loc = new Location(Bukkit.getWorld(world), x,y,z,(float)yaw, (float)pitch);
		}
		
		sendLog("SPAWN: " + world + " LOC:" +loc);
		return loc;
    }
    
    public static void spawnFirework(Location loc) {
		Random colour = new Random();

		Firework fw = loc.getWorld().spawn(loc, Firework.class);
		FireworkMeta fwMeta = fw.getFireworkMeta();

		Type fwType = Type.BALL_LARGE;

		int c1i = colour.nextInt(17) + 1;
		int c2i = colour.nextInt(17) + 1;

		Color c1 = getFWColor(c1i);
		Color c2 = getFWColor(c2i);

		FireworkEffect effect = FireworkEffect.builder().withFade(c2).withColor(c1).with(fwType).build();

		fwMeta.addEffect(effect);
		fwMeta.setPower(1);
		fw.setFireworkMeta(fwMeta);
	}
    
    public static Color getFWColor(int c) {
		switch (c) {
		case 1:
			return Color.TEAL;
		default:
		case 2:
			return Color.WHITE;
		case 3:
			return Color.YELLOW;
		case 4:
			return Color.AQUA;
		case 5:
			return Color.BLACK;
		case 6:
			return Color.BLUE;
		case 7:
			return Color.FUCHSIA;
		case 8:
			return Color.GRAY;
		case 9:
			return Color.GREEN;
		case 10:
			return Color.LIME;
		case 11:
			return Color.MAROON;
		case 12:
			return Color.NAVY;
		case 13:
			return Color.OLIVE;
		case 14:
			return Color.ORANGE;
		case 15:
			return Color.PURPLE;
		case 16:
			return Color.RED;
		case 17:
			return Color.SILVER;
		}
	}
    
	public static String timeString(long time) {
		long hours = time / 3600L;
		long minutes = (time - hours * 3600L) / 60L;
		long seconds = time - hours * 3600L - minutes * 60L;
		
		if (hours >= 1) {
			return String.format("%02d" + ":" + "%02d" + ":" + "%02d", hours, minutes, seconds).replace("-", "");
		}else {
			return String.format("%02d" + ":" + "%02d", minutes, seconds).replace("-", "");
		}
	}
}
