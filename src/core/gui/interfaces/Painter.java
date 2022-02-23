package core.gui.interfaces;

import java.awt.Graphics2D;

import core.gui.screen.content.Content;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class Painter<T extends Content> {
	
	protected final T parent;

	public abstract void paint(Graphics2D g);
}
