package core.gui.screen.content.battle.keyhandlers;

import java.awt.event.KeyEvent;
import java.util.HashMap;

import core.enums.GameStates;
import core.events.ChangeStateKeyEvent;
import core.events.GlobalKeyEvent;
import core.events.KeyEventWithRunnable;
import core.gui.interfaces.OnKeyPressHandler;
import core.gui.screen.content.battle.Battle;
import core.gui.screen.content.battle.painters.BattleOptionsPainter;
import core.gui.screen.content.battle.painters.elements.OptionsRect;

public class BattleOptionsKeyHandler extends OnKeyPressHandler<Battle> {

	private final HashMap<Integer, GlobalKeyEvent> keyMap;
	
	
	public BattleOptionsKeyHandler(Battle parent) {
		super(parent);
		
		BattleOptionsPainter painter = (BattleOptionsPainter) parent.getPainterLists().get(GameStates.BATTLE_OPTIONS).get(5);
		OptionsRect rect = painter.getOptionsRect();
		
		keyMap = new HashMap<>();
		keyMap.put(KeyEvent.VK_DOWN, new KeyEventWithRunnable(KeyEvent.VK_DOWN, rect::down));
		keyMap.put(KeyEvent.VK_UP, new KeyEventWithRunnable(KeyEvent.VK_UP, rect::up));
		keyMap.put(KeyEvent.VK_LEFT, new KeyEventWithRunnable(KeyEvent.VK_LEFT, rect::left));
		keyMap.put(KeyEvent.VK_RIGHT, new KeyEventWithRunnable(KeyEvent.VK_RIGHT, rect::right));
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
