package com.felipe221.rftb.game;

public class GameSkins {
	public enum Skin{
		CREEPER, ZOMBIE, ARCHER, GOLEM, PLAYER
	}
	
	public static Skin getSkinFromNumber(int number) {
		if (number == 1) {
			return Skin.ZOMBIE;
		}
		
		if (number == 2) {
			return Skin.ARCHER;
		}
		
		if (number == 3) {
			return Skin.CREEPER;
		}
	
		if (number == 4) {
			return Skin.GOLEM;
		}
		
		if (number == 0) {
			return Skin.PLAYER;
		}
		
		return Skin.PLAYER;
	}
	
	public static String getName(Skin skin) {
		return skin.toString().substring(0, 1) + skin.toString().substring(1).toLowerCase();
	}
}
