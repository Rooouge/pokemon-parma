package core.events;

import javax.sound.sampled.Clip;

import core.enums.GameStates;
import core.files.SoundsHandler;
import core.gui.screen.GlobalKeyEventHandler;

public class ChangeStateKeyEvent extends GlobalKeyEvent {
	
	private final GameStates toSet;
	private final Clip clip;
	
	
	public ChangeStateKeyEvent(int keyCode, GameStates state, GameStates toSet) {
		super(keyCode);
		this.state = state;
		this.toSet = toSet;
		clip = SoundsHandler.get(SoundsHandler.SPACE_PRESS);
	}
	

	@Override
	public void execute() {
		GlobalKeyEventHandler.get(toSet).onLoad();
		GameStates.set(toSet);
		SoundsHandler.playSound(clip);
	}
}
