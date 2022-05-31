package core.events;

public class KeyEventWithRunnable extends GlobalKeyEvent {

	private final Runnable ref;
	
	
	public KeyEventWithRunnable(int keyCode, Runnable ref) {
		super(keyCode);
		this.ref = ref;
	}
	
	@Override
	public void execute() {
		ref.run();
	}
	
	@Override
	public boolean canActivate() throws Exception {
		return true;
	}
}
