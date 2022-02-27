package core.obj.scripts.actions;

import core.Log;
import core.obj.scripts.ScriptAction;

public class LogAction extends ScriptAction {

	private final String log;
	
	
	public LogAction(String log) {
		super(false, NO_DELAY);
		this.log = log;
	}
	
	
	@Override
	public void execute() {
		super.execute();
		
		Log.log(log);
	}

}
