package core.events.exploration;

import core.enums.Directions;
import core.enums.GameStates;
import core.events.GlobalKeyEvent;
import core.gui.GridPosition;
import core.gui.screen.GlobalKeyEventHandler;
import core.gui.screen.content.exploration.keypresshandlers.ExplorationEntityScriptKeyPressHandler;
import core.gui.screen.content.exploration.keypresshandlers.ExplorationKeyPressHandler;
import core.obj.entities.overworld.OverworldEntity;
import core.obj.scripts.statescripts.EntityScripts;

public class ExplorationSpaceBarEvent extends GlobalKeyEvent {

	private final OverworldEntity player;
	private final ExplorationKeyPressHandler handler;
	
	
	public ExplorationSpaceBarEvent(int keyCode, OverworldEntity player, ExplorationKeyPressHandler handler) {
		super(keyCode);
		this.player = player;
		this.handler = handler;
		
		this.state = GameStates.EXPLORATION;
	}

	
	@Override
	public void execute() {
		GridPosition targetPos = getPlayerRelativePos();
		OverworldEntity target = handler.getParent().getActiveMap().getEntities().getEntityFromPos(targetPos);
		
//		System.out.println("Target: " + target);
		
		if(target != null && target.getData().isVisible()) {
			EntityScripts scripts = target.getData().getScripts();
			if(scripts != null && !scripts.isEmpty() && scripts.getState() < scripts.size()) {
				active = false;
//				System.out.println("Activated script!");
				GameStates.set(GameStates.EXPLORATION_ENTITY_SCRIPT);
				GlobalKeyEventHandler.instance().get(GameStates.EXPLORATION_ENTITY_SCRIPT, ExplorationEntityScriptKeyPressHandler.class).setScripts(scripts);
			}
		}
		
		if(active)
			handler.setNoEventActive();
	}
	
	private GridPosition getPlayerRelativePos() {
		Directions facing = player.getData().getFacing();
		GridPosition pos = player.getData().getPos();
		
		switch (facing) {
		case DOWN:
			return new GridPosition(pos.row + 1, pos.column);
		case UP:
			return new GridPosition(pos.row - 1, pos.column);
		case LEFT:
			return new GridPosition(pos.row, pos.column - 1);
		case RIGHT:
			return new GridPosition(pos.row, pos.column + 1);
		default:
			return null;
		}
	}

}
