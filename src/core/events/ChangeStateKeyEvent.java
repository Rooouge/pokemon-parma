package core.events;

import core.enums.GameStates;
import core.gui.screen.GlobalKeyEventHandler;

public class ChangeStateKeyEvent extends GlobalKeyEvent {
	
	protected final GameStates toSet;
	
	
	public ChangeStateKeyEvent(int keyCode, GameStates state, GameStates toSet) {
		super(keyCode);
		this.state = state;
		this.toSet = toSet;
	}
	

	@Override
	public void execute() {
		GlobalKeyEventHandler.get(toSet).onLoad();
		GameStates.set(toSet);
	}
}
