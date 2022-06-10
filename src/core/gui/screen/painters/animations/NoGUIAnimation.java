package core.gui.screen.painters.animations;

import java.awt.Graphics2D;

import core.gui.screen.content.Content;
import jutils.gui.ColoredPanel;


public abstract class NoGUIAnimation<T extends Content<? extends ColoredPanel>> extends GUIAnimation<T> {

	
	protected NoGUIAnimation(T parent, int duration, boolean forceLock) {
		super(parent, duration, forceLock);
	}
	
	
	@Override
	public void paint(Graphics2D g) {
		// Empty
	}
	
}
