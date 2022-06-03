package core.enums;

import jutils.global.Global;

public enum GameStates {

	NONE,
	FADE_IN,
	FADE_OUT,
	
	EXPLORATION,
	EXPLORATION_ENTITY_SCRIPT,
	EXPLORATION_START_MENU,
	EXPLORATION_WILD,
	
	POKEDEX,
	
	BATTLE_INTRO,
	BATTLE_OPTIONS,
	BATTLE_FIGHT_OPTIONS,
	BATTLE_FIRST_MOVE,
	BATTLE_SECOND_MOVE,
	BATTLE_POST_MOVES,
	BATTLE_CHECK_OVER;

	
	public static final String KEY = "state";
	
	
	public static GameStates current() {
		return Global.get(KEY, GameStates.class);
	}
	
	public static void set(GameStates state) {
		Global.add(KEY, state);
	}
}
