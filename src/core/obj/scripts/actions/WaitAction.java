package core.obj.scripts.actions;

import core.obj.scripts.ScriptAction;

public class WaitAction extends ScriptAction {

	public WaitAction(int delay) {
		super(false, delay*STANDARD_DELAY);
	}
	
}
