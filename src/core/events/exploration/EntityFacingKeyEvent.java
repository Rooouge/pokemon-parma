package core.events.exploration;

import core.enums.Directions;
import core.enums.GameStates;
import core.gui.screen.content.exploration.keyhandlers.ExplorationKeyHandler;
import core.obj.entities.overworld.OverworldEntity;

public class EntityFacingKeyEvent extends ExplorationEntityKeyEvent {
	
	public EntityFacingKeyEvent(int keyCode, Directions dir, OverworldEntity entity, ExplorationKeyHandler handler) {
		super(keyCode, dir, entity, handler, GameStates.EXPLORATION);
	}
	
	
	public void execute() {
		entity.getData().setFacing(dir);
		active = false;
		handler.setNoEventActive();
	}
}
