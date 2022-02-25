package core.gui.screen.content.exploration.painters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import core.fonts.Font;
import core.fonts.Fonts;
import core.gui.screen.content.ContentSettings;
import lombok.Getter;

public class OnMapChangeLabel {

	private final Font font = Fonts.SCRIPT_TEXT_FONT;
	private final int ticks = 250;
	
	private int tick;
	@Getter
	private boolean active;
	private String mapName;
	private Rectangle black;
	private Rectangle white;
	private Point line;
	private int arc;
	
	
	public void set(String mapLabel, Graphics2D g) {
		tick = 0;
		active = true;
		this.mapName = mapLabel;
		
		int size = ContentSettings.tileResize;
		int padding = size*4;

		g.setFont(font);
		int w = g.getFontMetrics().stringWidth(mapLabel);
		int h = g.getFontMetrics().getHeight();
		
		int left = ContentSettings.tileSize/2;
		int right = left + w + 2*padding;
		int top = left;
		int bottom = top + h + 2*padding;
		
		arc = size*4;
		
		black = new Rectangle(left, top, right - left, bottom - top);
		white = new Rectangle(black.x + size, black.y + size, black.width - 2*size, black.height - 2*size);
		line = new Point(black.x + padding, black.y + padding + h);
	}
	
	public void draw(Graphics2D g) {
		Color cBlack = new Color(0f, 0f, 0f, 0.5f);
		Color cWhite = new Color(1f, 1f, 1f, 0.5f);
		
		if(tick < 50) {
			cBlack = new Color(0f, 0f, 0f, tick/50f);
			cWhite = new Color(1f, 1f, 1f, tick/50f);
		} else if(tick >= ticks - 50) {
			cBlack = new Color(0f, 0f, 0f, (ticks - tick)/50f);
			cWhite = new Color(1f, 1f, 1f, (ticks - tick)/50f);
		} else {
			cBlack = Color.black;
			cWhite = Color.white;
		}
		
		
		g.setColor(cBlack);
		g.fillRoundRect(black.x, black.y, black.width, black.height, arc, arc);
		g.setColor(cWhite);
		g.fillRoundRect(white.x, white.y, white.width, white.height, arc, arc);
		g.setColor(cBlack);
		g.setFont(font);
		g.drawString(mapName, line.x, line.y);
		
		tick++;
		if(tick == ticks)
			active = false;
	}
}
