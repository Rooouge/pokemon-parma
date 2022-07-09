package core.events;

import java.util.HashMap;

@SuppressWarnings("serial")
public class KeyMap extends HashMap<Integer, GlobalKeyEvent> {

	public GlobalKeyEvent put(GlobalKeyEvent value) {
		return super.put(value.keyCode, value);
	}
}
