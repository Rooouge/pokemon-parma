package core.gui.interfaces;

import java.awt.event.KeyEvent;

import core.events.GlobalKeyEvent;
import jutils.gui.ColoredPanel;
import lombok.Getter;

public abstract class OnKeyPressHandler<T extends ColoredPanel> {

	@Getter
	protected final T parent;
	protected boolean pressed;
	protected boolean firstLoad;
	
	
	public OnKeyPressHandler(T parent) {
		this.parent = parent;
		pressed = false;
		firstLoad = true;
	}
	
	
	public void onLoad() {
		firstLoad = true;
		
		GlobalKeyEvent.schedule(250, () -> {
			firstLoad = false;
		});
	}
	
	public abstract void keyPressed(KeyEvent e) throws Exception;
	public abstract void keyReleased(KeyEvent e);
	public abstract void update();

}
