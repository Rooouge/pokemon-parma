package core.events.exploration;

import core.enums.Directions;
import core.enums.GameStates;
import core.gui.screen.content.exploration.keyhandlers.ExplorationKeyHandler;
import core.obj.entities.overworld.OverworldEntity;

public class EntityScriptEvent extends ExplorationEntityKeyEvent {

	protected EntityScriptEvent(int keyCode, Directions dir, OverworldEntity entity, ExplorationKeyHandler handler) {
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
