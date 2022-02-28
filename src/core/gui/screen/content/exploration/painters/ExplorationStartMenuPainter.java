package core.gui.screen.content.exploration.painters;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import core.gui.interfaces.Painter;
import core.gui.screen.content.ContentSettings;
import core.gui.screen.content.Exploration;

public class ExplorationStartMenuPainter extends Painter<Exploration> {

	private final int arc;
	private final Rectangle black;
	private final Rectangle white;
	
	
	public ExplorationStartMenuPainter(Exploration parent) {
		super(parent);
		
		Dimension dim = ContentSettings.dimension;

		int size = ContentSettings.tileResize;
		int padding = size*4;
		
		int left = (ContentSettings.horTiles - 3)*ContentSettings.tileSize;
		int right = left + 2*ContentSettings.tileSize;
		int top = ContentSettings.tileSize;
		int bottom = dim.height - top;
		
		arc = size;
		
		black = new Rectangle(left, top, right - left, bottom - top);
		white = new Rectangle(black.x + size, black.y + size, black.width - 2*size, black.height - 2*size);
		
	}


	@Override
	public void paint(Graphics2D g) {
		g.setColor(Color.black);
		g.fillRoundRect(black.x, black.y, black.width, black.height, arc, arc);
		g.setColor(Color.white);
		g.fillRoundRect(white.x, white.y, white.width, white.height, arc, arc);
	}
}
