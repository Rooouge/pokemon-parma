package core.events.exploration;

import core.enums.Directions;
import core.enums.GameStates;
import core.events.GlobalKeyEvent;
import core.gui.screen.content.exploration.keyhandlers.ExplorationKeyHandler;
import core.obj.entities.overworld.OverworldEntity;
import lombok.Getter;

@Getter
public abstract class ExplorationEntityKeyEvent extends GlobalKeyEvent {

	protected final Directions dir;
	protected final OverworldEntity entity;
	protected final ExplorationKeyHandler handler;
	
	
	protected ExplorationEntityKeyEvent(int keyCode, Directions dir, OverworldEntity entity, ExplorationKeyHandler handler, GameStates state) {
		super(keyCode);
		this.dir = dir;
		this.entity = entity;
		this.handler = handler;
		this.state = state;
	}
	
}
