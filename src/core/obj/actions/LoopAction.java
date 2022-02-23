package core.obj.actions;

import lombok.Setter;

public abstract class LoopAction extends Action {

	protected int ticks;
	@Setter
	protected boolean forceStop;
	
	
	protected LoopAction() {
		ticks = 0;
		forceStop = false;
	}
	
	
	@Override
	public void update() {
		ticks++;
	}
	
	@Override
	public boolean isOver() {
		return forceStop;
	}
}
