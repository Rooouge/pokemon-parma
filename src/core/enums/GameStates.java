package core.enums;

import jutils.global.Global;

public enum GameStates {

	NONE,
	EXPLORATION,
	EXPLORATION_FADE_IN,
	EXPLORATION_FADE_OUT,
	EXPLORATION_ENTITY_SCRIPT,
	EXPLORATION_START_MENU;

	
	public static final String KEY = "state";
	
	
	public static GameStates current() {
		return Global.get(KEY, GameStates.class);
	}
	
	public static void set(GameStates state) {
		Global.add(KEY, state);
	}
}
