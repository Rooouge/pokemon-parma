package core.gui.screen.content.exploration;

import java.awt.event.KeyEvent;
import java.util.HashMap;

import core.enums.GameStates;
import core.events.exploration.ExplorationKeyEvent;
import core.gui.interfaces.OnKeyPressHandler;
import core.gui.screen.content.Exploration;
import core.gui.screen.content.exploration.events.exploration.ExplorationStartMenuKeyEvent;
import lombok.Getter;

public class ExplorationStartMenuKeyPressHandler extends OnKeyPressHandler {

	@Getter
	private final Exploration exploration;
	private final HashMap<Integer, ExplorationKeyEvent> keyMap;
	
	
	public ExplorationStartMenuKeyPressHandler(Exploration exploration) {
		this.exploration = exploration;

		keyMap = new HashMap<>();
		keyMap.put(KeyEvent.VK_ESCAPE, new ExplorationStartMenuKeyEvent(KeyEvent.VK_ESCAPE, GameStates.EXPLORATION_START_MENU));
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) throws Exception {
		if(!pressed) {
			if(keyMap.containsKey(e.getKeyCode()) && !keyMap.get(e.getKeyCode()).isActive()) {
				ExplorationKeyEvent evt = keyMap.get(e.getKeyCode());
				
				if(evt.canActivate()) {
					evt.start();
					pressed = true;
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(keyMap.containsKey(e.getKeyCode())) {
			keyMap.get(e.getKeyCode()).end();
		}
	}

	@Override
	public void update() {
		if(pressed) {
			for(ExplorationKeyEvent evt : keyMap.values()) {
				if(evt.isActive()) {
//					System.out.println(this.getClass().getSimpleName() + ": pressed");
					evt.execute();
					if(evt instanceof ExplorationStartMenuKeyEvent) {
						pressed = false;
						evt.end();
					}
				}
			}
		}
	}

}
