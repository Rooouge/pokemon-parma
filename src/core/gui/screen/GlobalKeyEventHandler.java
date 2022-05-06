package core.gui.screen;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.EnumMap;
import java.util.Map;

import core.enums.GameStates;
import core.gui.GUIUtils;
import core.gui.interfaces.OnKeyPressHandler;
import jutils.global.Global;
import jutils.gui.ColoredPanel;

public class GlobalKeyEventHandler implements KeyListener {

	public static final String KEY = GlobalKeyEventHandler.class.getName();
	
	public static GlobalKeyEventHandler instance() {
		return Global.get(GlobalKeyEventHandler.KEY, GlobalKeyEventHandler.class);
	}
	
	
	protected Map<GameStates, OnKeyPressHandler<? extends ColoredPanel>> handlers;
	protected boolean sleep; // Allow only one key pressed at a time
	protected KeyEvent event;
	
	
	public GlobalKeyEventHandler() {		
		handlers = new EnumMap<>(GameStates.class);
		sleep = false;
	}
	
	
	public void add(OnKeyPressHandler<? extends ColoredPanel> l, GameStates state) {
		if(!handlers.containsValue(l))		
			handlers.put(state, l);
	}
	
	public <T> T get(GameStates state, Class<T> clazz) {
		return clazz.cast(handlers.get(state));
	}
	
	public static OnKeyPressHandler<? extends ColoredPanel> get(GameStates state) {
		return instance().handlers.get(state);
	}
	
	public void remove(OnKeyPressHandler<? extends ColoredPanel> l, GameStates state) {
		if(!handlers.containsValue(l) || handlers.isEmpty())
			handlers.remove(state, l);
	}
	
	public void update() throws Exception {
		OnKeyPressHandler<? extends ColoredPanel> handler = handlers.get(Global.get("state", GameStates.class));
		if(handler == null)
			return;
		
		handler.update();
		
		if(event == null)
			return;
		
		
		if(handler != null) {
			switch (event.getID()) {
			case KeyEvent.KEY_PRESSED:
				handler.keyPressed(event);
				event.consume();
				break;
			case KeyEvent.KEY_RELEASED:
				handler.keyReleased(event);
				event.consume();
				event = null;
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// Empty
	}


	@Override
	public void keyPressed(KeyEvent e) {
		event = e;
	}


	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_F2)
			GUIUtils.screenshot();
		else if(event != null && event.getKeyCode() == e.getKeyCode())
			event = e;
	}
}
