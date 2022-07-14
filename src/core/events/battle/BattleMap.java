package core.events.battle;

import java.util.HashMap;
import java.util.Map;

public class BattleMap {

	public static final String BATTLE_CLASS_KEY = "battle_class_key";
	public static final String PLAYER_PKM = "player_pkm";
	public static final String ENEMY_PKM = "enemy_pkm";
	public static final String ATK = "atk";
	public static final String DEF = "def";
	public static final String PLAYER_MOVE = "player_move";
	public static final String ENEMY_MOVE = "enemy_move";
	
	private Map<String, Object> map;
	
	
	public BattleMap() {
		map = new HashMap<>();
	}
	
	
	public void put(String key, Object obj) {
		map.put(key, obj);
	}
	
	public <T> T remove(String key, Class<T> clazz) {
		return clazz.cast(map.remove(key));
	}
	
	public <T> T get(String key, Class<T> clazz) {
		return clazz.cast(map.get(key));
	}
	
	public void clear() {
		map.clear();
	}
}
