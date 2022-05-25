package core.gui.screen.content.battle.keyhandlers;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import core.enums.GameStates;
import core.events.ChangeContentKeyEvent;
import core.events.ChangeStateKeyEvent;
import core.events.GlobalKeyEvent;
import core.gui.interfaces.OnKeyPressHandler;
import core.gui.screen.content.battle.Battle;
import core.gui.screen.content.exploration.Exploration;
import core.gui.screen.content.exploration.keyhandlers.ExplorationKeyHandler;
import jutils.global.Global;

public class BattleIntroKeyHandler extends OnKeyPressHandler<Battle> {

	private final Map<Integer, GlobalKeyEvent> keyMap;
	
	
	public BattleIntroKeyHandler(Battle battle) {
		super(battle);
		keyMap = new HashMap<>();
		keyMap.put(KeyEvent.VK_F12, new ChangeContentKeyEvent(KeyEvent.VK_F12, GameStates.BATTLE_INTRO, GameStates.EXPLORATION, Exploration.class, null).withExtra(() -> {
			Exploration exp = Global.get("content", Exploration.class);
			exp.getKeyHandler().get(GameStates.EXPLORATION, ExplorationKeyHandler.class).setNoEventActive();
		}));
		
		onLoad();
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
					if(evt instanceof ChangeStateKeyEvent) {
						evt.end();
					}
				}
			}
		}
	}

}
