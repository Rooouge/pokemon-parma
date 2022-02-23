package core.obj.actions;

public class SequenceActionWithDelay extends SequenceAction {

	public static final int DELAY_PRE = 0;
	public static final int DELAY_POST = -1;
	
	protected int delay;
	protected final int type;
	protected int delayTick;
	protected boolean delayPost;
	
	
	public SequenceActionWithDelay(int times, int delay, int type) {
		super(times);
		this.delay = delay;
		this.type = type;
		
		delayPost = type == DELAY_POST;

		tick = 0;
		delayTick = 0;
	}
	
	
	@Override
	public void onStart() {
		super.onStart();
		tick = 0;
		delayTick = 0;
	}
	
	@Override
	public boolean execute() throws Exception {
		if(delayPost && !started)
			return super.execute();
		
		if(delayTick < delay) {
			delayTick++;
			return false;
		}
		
//		System.out.println("Tick = " + tick + " | DelayTick = " + delayTick);
		delayTick = 0;
		return super.execute();
	}
}
