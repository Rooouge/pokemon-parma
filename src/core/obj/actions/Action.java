package core.obj.actions;

public abstract class Action {

	protected boolean started;
	
	
	protected Action() {
		started = false;
	}
	
	
	public boolean execute() throws Exception {
		if(!started) {
			onStart();
			started = true;
		}
		
		update();
		
		boolean isOver = isOver();
		if(isOver)
			onEnd();
		
		return isOver;
	}

	public abstract void onStart();
	public abstract void update() throws Exception;
	public abstract void onEnd();
	public abstract boolean isOver();
}
