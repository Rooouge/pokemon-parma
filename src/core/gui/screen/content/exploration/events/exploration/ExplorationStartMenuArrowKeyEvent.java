package core.gui.screen.content.exploration.events.exploration;

import javax.sound.sampled.Clip;

import core.enums.GameStates;
import core.events.exploration.ExplorationKeyEvent;
import core.files.SoundsHandler;

public class ExplorationStartMenuArrowKeyEvent extends ExplorationKeyEvent {

	private final Runnable ref;
	private final Clip clip;
	
	
	public ExplorationStartMenuArrowKeyEvent(int keyCode, Runnable ref) {
		super(keyCode);
		this.ref = ref;
		state = GameStates.EXPLORATION_START_MENU;
		clip = SoundsHandler.get(SoundsHandler.SPACE_PRESS);
	}

	@Override
	public void execute() {
		ref.run();
		SoundsHandler.playSound(clip);
	}

}
