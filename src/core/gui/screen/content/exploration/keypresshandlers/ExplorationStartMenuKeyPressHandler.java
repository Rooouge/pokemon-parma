package core.gui.screen.content.exploration.keypresshandlers;

import java.awt.event.KeyEvent;
import java.util.HashMap;

import core.enums.GameStates;
import core.events.ChangeStateKeyEvent;
import core.events.GlobalKeyEvent;
import core.events.exploration.ExplorationStartMenuArrowKeyEvent;
import core.events.exploration.ExplorationStartMenuSpacebarEvent;
import core.gui.interfaces.OnKeyPressHandler;
import core.gui.screen.content.Exploration;
import core.gui.screen.content.exploration.painters.ExplorationStartMenuPainter;

public class ExplorationStartMenuKeyPressHandler extends OnKeyPressHandler<Exploration> {
	
	private final HashMap<Integer, GlobalKeyEvent> keyMap;
	
	
	public ExplorationStartMenuKeyPressHandler(Exploration exploration) {
		super(exploration);
		
		ExplorationStartMenuPainter painter = (ExplorationStartMenuPainter) exploration.getPainters().get(GameStates.EXPLORATION_START_MENU);
		
		keyMap = new HashMap<>();
		keyMap.put(KeyEvent.VK_ESCAPE, new ChangeStateKeyEvent(KeyEvent.VK_ESCAPE, GameStates.EXPLORATION_START_MENU, GameStates.EXPLORATION));
		keyMap.put(KeyEvent.VK_UP, new ExplorationStartMenuArrowKeyEvent(KeyEvent.VK_UP, painter::before));
		keyMap.put(KeyEvent.VK_DOWN, new ExplorationStartMenuArrowKeyEvent(KeyEvent.VK_DOWN, painter::after));
		keyMap.put(KeyEvent.VK_SPACE, new ExplorationStartMenuSpacebarEvent(KeyEvent.VK_SPACE, painter::get));
	}
	
	@Override
	public void keyPressed(KeyEvent e) throws Exception {
		if(!pressed && !firstLoad) {
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
//		if(keyMap.containsKey(e.getKeyCode())) {
//			if(!keyMap.get(e.getKeyCode()).isActive())
//				pressed = false;
//			else
//				keyMap.get(e.getKeyCode()).end();
//		}
		if(firstLoad)
			firstLoad = false;
		
		if(keyMap.containsKey(e.getKeyCode())) {
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
					if(evt instanceof ChangeStateKeyEvent) {
						evt.end();
					}
				}
			}
		}
	}

}
