package core.events;

import core.enums.GameStates;
import core.gui.screen.GlobalKeyEventHandler;

public class ChangeStateKeyEvent extends GlobalKeyEvent {
	
	protected final GameStates toSet;
	protected Runnable extra;
	
	
	public ChangeStateKeyEvent(int keyCode, GameStates state, GameStates toSet) {
		super(keyCode);
		this.state = state;
		this.toSet = toSet;
	}
	
	
	public ChangeStateKeyEvent withExtra(Runnable extra) {
		this.extra = extra;
		return this;
	}
	

	@Override
	public void execute() {
		if(extra != null)
			extra.run();
		
		GlobalKeyEventHandler.get(toSet).onLoad();
		GameStates.set(toSet);
	}
}
