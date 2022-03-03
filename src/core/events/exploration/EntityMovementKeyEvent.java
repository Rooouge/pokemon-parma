package core.events.exploration;

import core.enums.Directions;
import core.enums.GameStates;
import core.gui.screen.content.ContentSettings;
import core.gui.screen.content.Exploration;
import core.gui.screen.content.exploration.keypresshandlers.ExplorationKeyPressHandler;
import core.obj.entities.overworld.OverworldEntity;
import core.obj.maps.MapUtils;

public class EntityMovementKeyEvent extends ExplorationEntityKeyEvent {
	
	
	public EntityMovementKeyEvent(int keyCode, Directions dir, OverworldEntity entity, ExplorationKeyPressHandler handler) {
		super(keyCode, dir, entity, handler, GameStates.EXPLORATION);
	}
	
	
	@Override
	public boolean canActivate() throws Exception {
		return super.canActivate() && MapUtils.canMove(entity, handler.getParent(), dir);
	}
	
	
	
	@Override
	public void execute() {
		Exploration exploration = handler.getParent();
		exploration.addAction(new EntityMovementAction(ContentSettings.tileOriginalSize, exploration.getActiveMaps(), dir, handler, entity.getData().isRunning()));
		exploration.addAction(entity.getAnimationAction());
		active = false;
	}
}
