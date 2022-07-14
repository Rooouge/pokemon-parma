package core.events;

import core.enums.GameStates;
import core.files.SoundsHandler;
import core.gui.interfaces.OnKeyPressHandler;
import core.gui.screen.GlobalKeyEventHandler;
import jutils.gui.ColoredPanel;

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
	
	public ChangeStateKeyEvent pressSound() {
		extra = () -> SoundsHandler.playSound(SoundsHandler.PRESS);
		return this;
	}
	

	@Override
	public void execute() {
		if(extra != null)
			extra.run();
		
		OnKeyPressHandler<? extends ColoredPanel> listener = GlobalKeyEventHandler.get(toSet);
		if(listener != null)
			listener.onLoad();
		
		if(!toSet.equals(GameStates.NONE))
			GameStates.set(toSet);
	}
}
