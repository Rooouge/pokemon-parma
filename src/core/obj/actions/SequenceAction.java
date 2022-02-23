package core.obj.actions;

import lombok.Getter;

public class SequenceAction extends Action {

	@Getter
	protected int times;
	@Getter
	protected int tick;
	
	
	protected SequenceAction(int times) {
		this.times = times;
	}
	
	
	@Override
	public void onStart() {
		tick = 0;
	}
	
	@Override
	public void update() throws Exception {
		tick++;
	}
	
	@Override
	public void onEnd() {
		// Empty
	}
	
	@Override
	public boolean isOver() {
//		System.out.println("Ticks: " + ticks + ", Times: " + times);
		return tick >= times;
	}
	
}
