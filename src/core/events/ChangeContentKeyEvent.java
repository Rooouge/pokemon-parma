package core.events;

import core.enums.GameStates;
import core.gui.screen.GameScreen;
import core.gui.screen.content.Content;
import core.gui.screen.painters.ScreenPainter;
import jutils.gui.ColoredPanel;
import jutils.threads.Threads;

public class ChangeContentKeyEvent extends ChangeStateKeyEvent {

	private final Class<? extends Content<? extends ColoredPanel>> clazz;
	
	
	public ChangeContentKeyEvent(int keyCode, GameStates state, GameStates toSet, Class<? extends Content<? extends ColoredPanel>> clazz) {
		super(keyCode, state, toSet);
		this.clazz = clazz;
	}
	

	@Override
	public void execute() {
		Threads.run(() -> {
			try {
				GameScreen screen = GameScreen.instance();
				ScreenPainter painter = screen.getPainter();
				
				painter.fadeOut();
				while(painter.isFadingOut()) { System.out.println("Wait"); }
				
				screen.switchContent(clazz);
				
				painter.fadeIn(toSet);
				super.execute();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
}
