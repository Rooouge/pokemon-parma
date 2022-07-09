package core.gui.screen.content.battle.keyhandlers;

import java.awt.event.KeyEvent;

import core.enums.GameStates;
import core.events.ChangeStateKeyEvent;
import core.events.GlobalKeyEvent;
import core.events.KeyEventWithRunnable;
import core.events.KeyMap;
import core.gui.interfaces.OnKeyPressHandler;
import core.gui.screen.content.battle.Battle;
import core.gui.screen.content.battle.painters.BattleOptionsPainter;
import core.gui.screen.content.battle.painters.elements.OptionsRect;

public class BattleOptionsKeyHandler extends OnKeyPressHandler<Battle> {

	private final KeyMap keyMap;
	
	
	public BattleOptionsKeyHandler(Battle parent) {
		super(parent);
		
		BattleOptionsPainter painter = (BattleOptionsPainter) parent.getPaintersListsMap().get(GameStates.BATTLE_OPTIONS).get(5);
		OptionsRect rect = painter.getOptionsRect();
		
		keyMap = new KeyMap();
		keyMap.put(new KeyEventWithRunnable(KeyEvent.VK_DOWN, rect::down));
		keyMap.put(new KeyEventWithRunnable(KeyEvent.VK_UP, rect::up));
		keyMap.put(new KeyEventWithRunnable(KeyEvent.VK_LEFT, rect::left));
		keyMap.put(new KeyEventWithRunnable(KeyEvent.VK_RIGHT, rect::right));
		keyMap.put(new ChangeStateKeyEvent(KeyEvent.VK_SPACE, GameStates.BATTLE_OPTIONS, GameStates.BATTLE_FIGHT_OPTIONS).pressSound());
		
		firstLoad = true;
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
