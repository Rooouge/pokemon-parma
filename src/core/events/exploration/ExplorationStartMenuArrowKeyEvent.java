package core.events.exploration;

import core.enums.GameStates;
import core.events.GlobalKeyEvent;

public class ExplorationStartMenuArrowKeyEvent extends GlobalKeyEvent {

	private final Runnable ref;
//	private final Clip clip;
	
	
	public ExplorationStartMenuArrowKeyEvent(int keyCode, Runnable ref) {
		super(keyCode);
		this.ref = ref;
		state = GameStates.EXPLORATION_START_MENU;
//		clip = SoundsHandler.get(SoundsHandler.SPACE_PRESS);
	}

	@Override
	public void execute() {
		ref.run();
//		SoundsHandler.playSound(clip);
	}

}
