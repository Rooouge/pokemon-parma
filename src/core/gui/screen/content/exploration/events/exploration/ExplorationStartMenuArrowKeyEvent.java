package core.gui.screen.content.exploration.events.exploration;

import core.enums.GameStates;
import core.events.exploration.ExplorationKeyEvent;

public class ExplorationStartMenuArrowKeyEvent extends ExplorationKeyEvent {

	
	public ExplorationStartMenuArrowKeyEvent(int keyCode) {
		super(keyCode);
		state = GameStates.EXPLORATION_START_MENU;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

}
