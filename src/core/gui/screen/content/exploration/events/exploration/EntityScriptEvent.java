package core.gui.screen.content.exploration.events.exploration;

import core.enums.Directions;
import core.enums.GameStates;
import core.events.exploration.ExplorationEntityKeyEvent;
import core.gui.screen.content.exploration.ExplorationKeyPressHandler;
import core.obj.entities.overworld.OverworldEntity;

public class EntityScriptEvent extends ExplorationEntityKeyEvent {

	protected EntityScriptEvent(int keyCode, Directions dir, OverworldEntity entity, ExplorationKeyPressHandler handler) {
		super(keyCode, dir, entity, handler, GameStates.EXPLORATION);
	}

	
	@Override
	public boolean canActivate() throws Exception {
		return super.canActivate();
	}
	
	@Override
	public void start() {
		super.start();
		GameStates.set(GameStates.EXPLORATION_ENTITY_SCRIPT);
	}
	
	@Override
	public void execute() {
		// Empty
	}

}
