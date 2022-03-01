package core.gui.screen.content.exploration;

import java.awt.event.KeyEvent;
import java.util.HashMap;

import core.enums.GameStates;
import core.events.GlobalKeyEvent;
import core.events.exploration.ExplorationStartMenuArrowKeyEvent;
import core.events.exploration.ExplorationStartMenuEnterEvent;
import core.events.exploration.ExplorationStartMenuKeyEvent;
import core.gui.interfaces.OnKeyPressHandler;
import core.gui.screen.GlobalKeyEventHandler;
import core.gui.screen.content.Exploration;
import core.gui.screen.content.exploration.painters.ExplorationStartMenuPainter;
import lombok.Getter;

public class ExplorationStartMenuKeyPressHandler extends OnKeyPressHandler {

	@Getter
	private final Exploration exploration;
	private final HashMap<Integer, GlobalKeyEvent> keyMap;
	
	
	public ExplorationStartMenuKeyPressHandler(Exploration exploration) {
		this.exploration = exploration;
		
		ExplorationStartMenuPainter painter = (ExplorationStartMenuPainter) exploration.getPainters().get(GameStates.EXPLORATION_START_MENU);
		
		keyMap = new HashMap<>();
		keyMap.put(KeyEvent.VK_ESCAPE, new ExplorationStartMenuKeyEvent(KeyEvent.VK_ESCAPE, GameStates.EXPLORATION_START_MENU, GlobalKeyEventHandler::get));
		keyMap.put(KeyEvent.VK_UP, new ExplorationStartMenuArrowKeyEvent(KeyEvent.VK_UP, painter::before));
		keyMap.put(KeyEvent.VK_DOWN, new ExplorationStartMenuArrowKeyEvent(KeyEvent.VK_DOWN, painter::after));
		keyMap.put(KeyEvent.VK_ENTER, new ExplorationStartMenuEnterEvent(KeyEvent.VK_ENTER, painter::get));
	}
	
	
	@Override
	public void onLoad() {
		pressed = true;
	}
	
	@Override
	public void keyPressed(KeyEvent e) throws Exception {
		if(!pressed) {
			if(keyMap.containsKey(e.getKeyCode()) && !keyMap.get(e.getKeyCode()).isActive()) {
				GlobalKeyEvent evt = keyMap.get(e.getKeyCode());
				
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
			if(!keyMap.get(e.getKeyCode()).isActive())
				pressed = false;
			else
				keyMap.get(e.getKeyCode()).end();
		}
	}

	@Override
	public void update() {
		if(pressed) {
			for(GlobalKeyEvent evt : keyMap.values()) {
				if(evt.isActive()) {
					evt.execute();
					pressed = false;
					if(evt instanceof ExplorationStartMenuKeyEvent) {
						evt.end();
					}
				}
			}
		}
	}

}
