package core.gui.screen.content.exploration;

import java.awt.event.KeyEvent;
import java.util.HashMap;

import core.enums.Directions;
import core.enums.GameStates;
import core.events.exploration.ExplorationKeyEvent;
import core.events.exploration.ExplorationSpaceBarEvent;
import core.gui.interfaces.OnKeyPressHandler;
import core.gui.screen.content.Exploration;
import core.gui.screen.content.exploration.events.exploration.EntityFacingKeyEvent;
import core.gui.screen.content.exploration.events.exploration.EntityMovementKeyEvent;
import core.gui.screen.content.exploration.events.exploration.EntityRunningKeyEvent;
import core.gui.screen.content.exploration.events.exploration.ExplorationStartMenuKeyEvent;
import core.obj.entities.overworld.PlayerOverworldEntity;
import core.obj.entities.player.Player;
import jutils.global.Global;
import lombok.Getter;

public class ExplorationKeyPressHandler extends OnKeyPressHandler {

	@Getter
	private final Exploration exploration;
	private final HashMap<Integer, ExplorationKeyEvent> keyMap;
	
	
	public ExplorationKeyPressHandler(Exploration exploration) {
		this.exploration = exploration;
		
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
		keyMap.put(KeyEvent.VK_ENTER, new ExplorationStartMenuKeyEvent(KeyEvent.VK_ENTER, GameStates.EXPLORATION));
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
					if(evt instanceof ExplorationSpaceBarEvent || evt instanceof ExplorationStartMenuKeyEvent) {
						pressed = false;
						evt.end();
					}
				}
			}
		}
	}
	
	public void setNoEventActive() {
		pressed = false;
		for(ExplorationKeyEvent evt : keyMap.values()) {
			evt.end();
		}
	}
}
