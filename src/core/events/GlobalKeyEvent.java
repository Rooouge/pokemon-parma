package core.events;

import core.enums.GameStates;
import jutils.global.Global;
import lombok.Getter;

@Getter
public abstract class GlobalKeyEvent {

	protected final int keyCode;
	protected GameStates state;
	protected boolean active;
	
	
	public GlobalKeyEvent(int keyCode) {
		this.keyCode = keyCode;
		active = false;
	}
	
	
	public void start() {
		active = true;
	}
	
	public void end() {
		active = false;
	}
	
	public boolean canActivate() throws Exception {
		return Global.get("state", GameStates.class).equals(state);
	}
	
	
	public abstract void execute();
}
