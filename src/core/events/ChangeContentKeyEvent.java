package core.events;

import core.enums.GameStates;
import core.files.SoundsHandler;
import core.gui.screen.GameScreen;
import core.gui.screen.content.Content;
import core.gui.screen.painters.ScreenPainter;
import jutils.gui.ColoredPanel;
import jutils.strings.Strings;
import jutils.threads.Threads;

public class ChangeContentKeyEvent extends ChangeStateKeyEvent {

	private final Class<? extends Content<? extends ColoredPanel>> clazz;
	private final String soundKey;
	
	
	public ChangeContentKeyEvent(int keyCode, GameStates state, GameStates toSet, Class<? extends Content<? extends ColoredPanel>> clazz, String soundKey) {
		super(keyCode, state, toSet);
		this.clazz = clazz;
		this.soundKey = soundKey;
	}
	

	@Override
	public void execute() {
		Threads.run(() -> {
			GameScreen screen = GameScreen.instance();
			ScreenPainter painter = screen.getPainter();
			
			painter.fadeOut(() -> {
				try {
					if(!Strings.isVoid(soundKey))
						SoundsHandler.playSound(soundKey);
					
					screen.switchContent(clazz);
					painter.fadeIn(toSet);
					super.execute();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		});
	}
	
}
