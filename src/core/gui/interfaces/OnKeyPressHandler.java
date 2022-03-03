package core.gui.interfaces;

import java.awt.event.KeyEvent;

import jutils.gui.ColoredPanel;
import lombok.Getter;

public abstract class OnKeyPressHandler<T extends ColoredPanel> {

	@Getter
	protected final T parent;
	protected boolean pressed;
	
	
	public OnKeyPressHandler(T parent) {
		this.parent = parent;
		pressed = false;
	}
	
	
	public abstract void onLoad();
	public abstract void keyPressed(KeyEvent e) throws Exception;
	public abstract void keyReleased(KeyEvent e);
	public abstract void update();

}
