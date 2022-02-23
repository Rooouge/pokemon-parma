package core.gui.interfaces;

import java.awt.event.KeyEvent;

public abstract class OnKeyPressHandler {

	protected boolean pressed;
	
	
	public OnKeyPressHandler() {
		pressed = false;
	}
	
	
	public abstract void keyPressed(KeyEvent e) throws Exception;
	public abstract void keyReleased(KeyEvent e);
	public abstract void update();

}
