package core.gui.screen.content.exploration;

import java.awt.event.KeyEvent;
import java.util.HashMap;

import core.gui.interfaces.OnKeyPressHandler;
import core.gui.screen.content.Exploration;
import core.gui.screen.content.exploration.events.entityscript.ScriptKeyEvent;
import core.obj.scripts.ScriptExecutor;
import core.obj.scripts.statescripts.EntityScripts;
import lombok.Getter;

public class ExplorationEntityScriptKeyPressHandler extends OnKeyPressHandler {
	
	@Getter
	private final Exploration exploration;
	private final HashMap<Integer, ScriptKeyEvent> keyMap;
	private EntityScripts scripts;
	private int scriptState;
	private boolean needPress;
	private boolean released;
	

	public ExplorationEntityScriptKeyPressHandler(Exploration exploration) {
		this.exploration = exploration;
		
		keyMap = new HashMap<>();
		keyMap.put(KeyEvent.VK_SPACE, new ScriptKeyEvent(KeyEvent.VK_SPACE));
	}
	
	
	public void setScripts(EntityScripts scripts) {
		this.scripts = scripts;
		scriptState = scripts.getState();
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(!pressed) {
			if(keyMap.containsKey(e.getKeyCode()) && !keyMap.get(e.getKeyCode()).isActive()) {
				ScriptKeyEvent evt = keyMap.get(e.getKeyCode());
				pressed = true;
				needPress = false;
				released = false;
				evt.start();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		released = true;
	}

	@Override
	public void update() {
		if(!pressed && needPress)
			return;
		if(!released)
			return;		
		
		for(ScriptKeyEvent evt : keyMap.values()) {
			if(!needPress || evt.isActive()) {
//				System.out.println(needPress + " --- " + evt.isActive() + ", " + pressed);
//				System.out.println("State: " + scriptState);
				evt.setActive(false);
				needPress = ScriptExecutor.execute(scripts.get(scriptState), this);
				pressed = false;
//				System.out.println(needPress + " --- " + evt.isActive() + ", " + pressed);
//				System.out.println("------------------------------");
			}
		}
	}

	@Override
	public void onLoad() {
		// Empty
	}

}
