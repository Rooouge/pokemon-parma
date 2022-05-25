package core.gui.screen.content.pokedex.keyhandlers;

import core.enums.GameStates;
import core.events.GlobalKeyEvent;
import jutils.threads.Threads;

public class PokedexArrowKeyEvent extends GlobalKeyEvent {

	private final Runnable ref;
	private Thread thread;
	private boolean exec;
	
	
	public PokedexArrowKeyEvent(int keyCode, Runnable ref) {
		super(keyCode);
		this.ref = ref;
		state = GameStates.POKEDEX;
	}

	
	@Override
	public void execute() {
		exec = true;
		thread = Threads.run(() -> {
			do {
//				SoundsHandler.playSound(SoundsHandler.PRESS);
				ref.run();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					if(!e.getMessage().equalsIgnoreCase("sleep interrupted"))
						e.printStackTrace();
				}
			} while(exec);
		});
	}

	@Override
	public boolean isActive() {
		return super.isActive();
	}
	
	@Override
	public void end() {
		super.end();
		
		exec = false;
		thread.interrupt();
	}
}
