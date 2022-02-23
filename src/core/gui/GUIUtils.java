package core.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import core.gui.screen.content.ContentSettings;
import lombok.experimental.UtilityClass;

@UtilityClass
public class GUIUtils {

	public void drawRect(Graphics g, int x, int y, int w, int h, Color color, int thickness) {
		Color preColor = g.getColor();
		g.setColor(color);
		
		for(int i = 0; i < thickness; i++) {
			g.drawRect(x + i, y + i, w - (2*i), h - (2*i));
		}
		
		g.setColor(preColor);
	}
	
	public void drawString(Graphics g, String string, int x, int y, Color color) {
		Color preColor = g.getColor();
		g.setColor(color);
		g.setFont(g.getFont().deriveFont(12f).deriveFont(Font.BOLD));
		g.drawString(string, x, y);
		g.setColor(preColor);
	}
	
	
	public BufferedImage getGridImage() {
		int w = ContentSettings.horTiles;
		int h = ContentSettings.verTiles;
		int tileSize = ContentSettings.tileSize;
		BufferedImage gridImage = new BufferedImage(100*tileSize, 100*tileSize, BufferedImage.TYPE_INT_ARGB);
		Graphics g = gridImage.getGraphics();
		
		g.setColor(new Color(0,0,0,0));
		g.fillRect(0, 0, gridImage.getWidth(), gridImage.getHeight());
		
		Color gridColor = new Color(255,0,0,128);
		g.setColor(gridColor);
		for(int y = 0; y < h; y++) {
			for(int x = 0; x < w; x++) {
				g.drawRect(x*tileSize, y*tileSize, tileSize-1, tileSize-1);
				GUIUtils.drawRect(g, x*tileSize, y*tileSize, tileSize-1, tileSize-1, gridColor, 2);
			}
		}
		
		return gridImage;
	}
}
