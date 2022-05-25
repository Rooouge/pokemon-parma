package core.events.battle;

import java.util.HashMap;
import java.util.Map;

public class BattleMap {

	private Map<String, Object> map;
	
	
	public BattleMap() {
		map = new HashMap<>();
	}
	
	
	public void add(String key, Object obj) {
		map.put(key, obj);
	}
	
	public <T> T remove(String key, Class<T> clazz) {
		return clazz.cast(map.remove(key));
	}
	
	public <T> T get(String key, Class<T> clazz) {
		return clazz.cast(map.get(key));
	}
}
