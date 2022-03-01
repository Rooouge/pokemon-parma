package core.gui.screen.content.exploration.events.exploration;

import javax.sound.sampled.Clip;

import core.enums.GameStates;
import core.events.exploration.ExplorationKeyEvent;
import core.files.SoundsHandler;

public class ExplorationStartMenuKeyEvent extends ExplorationKeyEvent {

	private final GameStates toSet;
	private final Clip clip;
	
	
	public ExplorationStartMenuKeyEvent(int keyCode, GameStates state) {
		super(keyCode);
		this.state = state;
		
		if(state.equals(GameStates.EXPLORATION))
			toSet = GameStates.EXPLORATION_START_MENU;
		else
			toSet = GameStates.EXPLORATION;
		
		clip = SoundsHandler.get(SoundsHandler.SPACE_PRESS);
	}

	@Override
	public void execute() {
		GameStates.set(toSet);
		SoundsHandler.playSound(clip);
	}
}
