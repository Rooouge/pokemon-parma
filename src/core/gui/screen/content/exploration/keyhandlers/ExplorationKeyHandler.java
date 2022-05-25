package core.gui.screen.content.exploration.keyhandlers;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import core.enums.Directions;
import core.enums.GameStates;
import core.events.ChangeStateKeyEvent;
import core.events.GlobalKeyEvent;
import core.events.exploration.EntityFacingKeyEvent;
import core.events.exploration.EntityMovementKeyEvent;
import core.events.exploration.EntityRunningKeyEvent;
import core.events.exploration.ExplorationSpaceBarEvent;
import core.gui.interfaces.OnKeyPressHandler;
import core.gui.screen.content.exploration.Exploration;
import core.obj.entities.overworld.PlayerOverworldEntity;
import core.obj.entities.player.Player;
import jutils.global.Global;

public class ExplorationKeyHandler extends OnKeyPressHandler<Exploration> {
	
	private final Map<Integer, GlobalKeyEvent> keyMap;
	
	
	public ExplorationKeyHandler(Exploration exploration) {
		super(exploration);
		
		PlayerOverworldEntity entity = Global.get("player", Player.class).getOverworldEntity();
		
		keyMap = new HashMap<>();
		keyMap.put(KeyEvent.VK_S, new EntityMovementKeyEvent(KeyEvent.VK_S, Directions.DOWN, entity, this));
		keyMap.put(KeyEvent.VK_W, new EntityMovementKeyEvent(KeyEvent.VK_W, Directions.UP, entity, this));
		keyMap.put(KeyEvent.VK_A, new EntityMovementKeyEvent(KeyEvent.VK_A, Directions.LEFT, entity, this));
		keyMap.put(KeyEvent.VK_D, new EntityMovementKeyEvent(KeyEvent.VK_D, Directions.RIGHT, entity, this));
		keyMap.put(KeyEvent.VK_DOWN, new EntityFacingKeyEvent(KeyEvent.VK_DOWN, Directions.DOWN, entity, this));
		keyMap.put(KeyEvent.VK_UP, new EntityFacingKeyEvent(KeyEvent.VK_UP, Directions.UP, entity, this));
		keyMap.put(KeyEvent.VK_LEFT, new EntityFacingKeyEvent(KeyEvent.VK_LEFT, Directions.LEFT, entity, this));
		keyMap.put(KeyEvent.VK_RIGHT, new EntityFacingKeyEvent(KeyEvent.VK_RIGHT, Directions.RIGHT, entity, this));
		keyMap.put(KeyEvent.VK_R, new EntityRunningKeyEvent(KeyEvent.VK_R, null, entity, this));
		keyMap.put(KeyEvent.VK_SPACE, new ExplorationSpaceBarEvent(KeyEvent.VK_SPACE, entity, this));
		keyMap.put(KeyEvent.VK_ENTER, new ChangeStateKeyEvent(KeyEvent.VK_ENTER, GameStates.EXPLORATION, GameStates.EXPLORATION_START_MENU));
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
			keyMap.get(e.getKeyCode()).end();
		}
	}
	
	@Override
	public void update() {
		if(pressed) {
			for(GlobalKeyEvent evt : keyMap.values()) {
				if(evt.isActive()) {
//					System.out.println(this.getClass().getSimpleName() + ": pressed");
					evt.execute();
					if(evt instanceof ExplorationSpaceBarEvent || evt instanceof ChangeStateKeyEvent) {
						pressed = false;
						evt.end();
					}
				}
			}
		}
	}
	
	
	public void setNoEventActive() {
		pressed = false;
		for(GlobalKeyEvent evt : keyMap.values()) {
			evt.end();
		}
	}
}
