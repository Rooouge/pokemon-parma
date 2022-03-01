package core.events.exploration;

import core.enums.Directions;
import core.enums.GameStates;
import core.events.GlobalKeyEvent;
import core.gui.screen.content.exploration.ExplorationKeyPressHandler;
import core.obj.entities.overworld.OverworldEntity;
import lombok.Getter;

@Getter
public abstract class ExplorationEntityKeyEvent extends GlobalKeyEvent {

	protected final Directions dir;
	protected final OverworldEntity entity;
	protected final ExplorationKeyPressHandler handler;
	
	
	protected ExplorationEntityKeyEvent(int keyCode, Directions dir, OverworldEntity entity, ExplorationKeyPressHandler handler, GameStates state) {
		super(keyCode);
		this.dir = dir;
		this.entity = entity;
		this.handler = handler;
		this.state = state;
	}
	
}
