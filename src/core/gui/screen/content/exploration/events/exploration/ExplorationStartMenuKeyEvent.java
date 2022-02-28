package core.gui.screen.content.exploration.events.exploration;

import core.enums.GameStates;
import core.events.exploration.ExplorationKeyEvent;

public class ExplorationStartMenuKeyEvent extends ExplorationKeyEvent {

	private GameStates toSet;
	
	public ExplorationStartMenuKeyEvent(int keyCode, GameStates state) {
		super(keyCode);
		this.state = state;
		
		if(state.equals(GameStates.EXPLORATION))
			toSet = GameStates.EXPLORATION_START_MENU;
		else if(state.equals(GameStates.EXPLORATION_START_MENU))
			toSet = GameStates.EXPLORATION;
	}

	@Override
	public void execute() {
		GameStates.set(toSet);
	}
}
