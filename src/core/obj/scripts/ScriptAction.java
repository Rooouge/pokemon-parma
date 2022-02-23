package core.obj.scripts;

import core.obj.animation.Animation;
import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class ScriptAction {
	
	public static final int NO_DELAY = 0;
	public static final int STANDARD_DELAY = Animation.STANDARD_DELAY*4;
	
	protected final boolean needPress;
	protected final int delay;
	protected int delayTick;
	@Setter
	protected boolean started;
	
	
	public ScriptAction(boolean needPress, int delay) {
		this.needPress = needPress;
		this.delay = delay;
		delayTick = 0;
		started = false;
	}
	
	
	public void onStart() {
		delayTick = 0;
		started = true;
	}
	
	public void addDelayTick() {
		delayTick++;
	}
	
	public void execute() {
		delayTick = 0;
	}
	
}
