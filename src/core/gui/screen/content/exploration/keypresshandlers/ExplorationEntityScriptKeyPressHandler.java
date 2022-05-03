package core.gui.screen.content.exploration.keypresshandlers;

import java.awt.event.KeyEvent;
import java.util.HashMap;

import core.events.ScriptKeyEvent;
import core.gui.interfaces.OnKeyPressHandler;
import core.gui.screen.content.exploration.Exploration;
import core.obj.scripts.ScriptExecutor;
import core.obj.scripts.statescripts.EntityScripts;

public class ExplorationEntityScriptKeyPressHandler extends OnKeyPressHandler<Exploration> {
	
	private final HashMap<Integer, ScriptKeyEvent> keyMap;
	private EntityScripts scripts;
	private int scriptState;
	private boolean needPress;
	private boolean released;
	

	public ExplorationEntityScriptKeyPressHandler(Exploration exploration) {
		super(exploration);
		
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

}
