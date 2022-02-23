package core.enums;

import jutils.global.Global;

public enum GameStates {

	
	EXPLORATION,
	EXPLORATION_ENTITY_SCRIPT;

	public static final String KEY = "state";
	
	
	public static GameStates current() {
		return Global.get(KEY, GameStates.class);
	}
	
	public static void set(GameStates state) {
		Global.add(KEY, state);
	}
}
