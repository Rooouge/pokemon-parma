package core.gui.screen.content.battle.painters.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

import core.fonts.Font;
import core.fonts.Fonts;
import core.gui.GUIUtils;
import core.gui.screen.content.ContentSettings;

public class OptionsRect {

	private final Rectangle backgroundRect;
	private final Color backgroundColor;
	private final Rectangle innerRect;
	private final int innerArcW;
	private final int innerArcH;
	private final Color innerColor;
	private final Rectangle topBorderRect;
	private final Color topBorderColor;
	private final Rectangle leftBorderRect;
	private final Color leftBorderColor;
	private final String[] options;
	private final Font optionsFont;
	private final Color optionsColor;
	private final Point[] points;
	private final Polygon[] selectors;
	private final Color selectorColor;
	private final int selectorBorders;
	private final Color selectorBorderColor;
	private int selected;
	
	
	public OptionsRect() {
		Dimension dim = ContentSettings.dimension;
		int tile = ContentSettings.tileSize;
		int resize = ContentSettings.tileResize;
		
		backgroundRect = new Rectangle(3*dim.width/5, dim.height - 2*tile, 2*dim.width/5, 2*tile);
		backgroundColor = new Color(70, 64, 76);
		
		int margin = tile/8;
		innerRect = new Rectangle(backgroundRect.x + margin, backgroundRect.y + margin, backgroundRect.width - 2*margin, backgroundRect.height - 2*margin);
		innerArcW = margin*2;
		innerArcH = innerArcW;
		innerColor = Color.white;
		
		topBorderRect = new Rectangle(backgroundRect.x, backgroundRect.y, backgroundRect.width, resize);
		topBorderColor = Color.black;
		leftBorderRect = new Rectangle(backgroundRect.x, topBorderRect.y, resize, backgroundRect.height);
		leftBorderColor = topBorderColor;
		
		
		options = new String[] {"LOTTA","ZAINO", "POKÈMON", "FUGA"};
		optionsFont = new Font(Fonts.BATTLE_FONT.deriveFont(3f*Fonts.BATTLE_FONT.getSize2D()/2f));
		optionsColor = Color.black;
		points = new Point[4];
		points[0] = new Point(innerRect.x + 4*margin, innerRect.y + (int) (2*innerRect.getHeight()/5));
		points[1] = new Point(innerRect.x + (int) (innerRect.getWidth()) - 4*margin - optionsFont.getWidth(options[1]), points[0].y);
		points[2] = new Point(points[0].x, innerRect.y + (int) (4*innerRect.getHeight()/5));
		points[3] = new Point(points[1].x, points[2].y);
		
		selectors = new Polygon[4];
		int h = optionsFont.height();
		for(int i = 0; i < selectors.length; i++) {
			selectors[i] = new Polygon(
					new int[] {points[i].x - 3*margin, points[i].x - margin, points[i].x - 3*margin}, 
					new int[] {points[i].y - 4*h/5, points[i].y - h/2, points[i].y - h/5}, 
					3
			);
		}
		selectorColor = Color.red;
		selectorBorderColor = Color.black;
		selectorBorders = resize/2;
		selected = 0;
	}
	
	
	public void paint(Graphics2D g) {
		GUIUtils.fillRect(g, backgroundRect, backgroundColor);
		GUIUtils.fillRoungRect(g, innerRect, innerColor, innerArcW, innerArcH);
		GUIUtils.fillRect(g, topBorderRect, topBorderColor);
		GUIUtils.fillRect(g, leftBorderRect, leftBorderColor);
		
		g.setColor(optionsColor);
		for(int i = 0; i < options.length; i++) {
			g.drawString(options[i], points[i].x, points[i].y);
		}
		
		g.setColor(selectorColor);
		g.fillPolygon(selectors[selected]);
		g.setColor(selectorBorderColor);
		for(int i = 0; i < selectorBorders; i++) {
			Polygon s = selectors[selected];
			int[] xs = s.xpoints;
			int[] ys = s.ypoints;
			int n = s.npoints;
			
			int[] x = new int[n];
			int[] y = new int[n];
			x[0] = xs[0] + i;
			x[1] = xs[1] - i;
			x[2] = xs[2] + i;
			y[0] = ys[0] + i;
			y[1] = ys[1];
			y[2] = ys[2] - i;
			
			g.drawPolygon(x, y, n);
		}
	}
	
	
	public void down() {
		if(selected < 2)
			selected+=2;
	}
	
	public void up() {
		if(selected > 1)
			selected -=2;
	}
	
	public void left() {
		if(selected % 2 != 0)
			selected--;
	}
	
	public void right() {
		if(selected % 2 == 0)
			selected++;
	}
}
