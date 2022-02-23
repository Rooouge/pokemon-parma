package core.gui.screen.content.exploration.events.entityscript;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ScriptKeyEvent {

	protected final int keyCode;
	@Setter
	protected boolean active;
	
	
	public ScriptKeyEvent(int keyCode) {
		this.keyCode = keyCode;
		active = false;
	}
	
	
	public void start() {
		active = true;
	}	
	
}
