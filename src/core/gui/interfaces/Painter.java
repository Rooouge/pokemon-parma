package core.gui.interfaces;

import java.awt.Graphics2D;

import core.fonts.Font;
import core.fonts.Fonts;
import jutils.gui.ColoredPanel;

public abstract class Painter<T extends ColoredPanel> {
	
	protected final T parent;
	protected Font font;
	
	
	public Painter(T parent) {
		this.parent = parent;
		font = Fonts.SCRIPT_TEXT_FONT;
	}
	

	public abstract void paint(Graphics2D g);
}
