package core.events;

import core.enums.GameStates;
import core.gui.screen.GameScreen;
import core.gui.screen.content.Content;
import jutils.gui.ColoredPanel;

public class ChangeContentKeyEvent extends ChangeStateKeyEvent {

	private final Class<? extends Content<? extends ColoredPanel>> clazz;
	
	
	public ChangeContentKeyEvent(int keyCode, GameStates state, GameStates toSet, Class<? extends Content<? extends ColoredPanel>> clazz) {
		super(keyCode, state, toSet);
		this.clazz = clazz;
	}
	

	@Override
	public void execute() {
		try {
			GameScreen.instance().switchContent(clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		super.execute();
	}

}
