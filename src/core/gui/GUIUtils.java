package core.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import core.gui.screen.GameScreen;
import core.gui.screen.content.ContentSettings;
import jutils.asserts.Assert;
import jutils.config.Config;
import jutils.log.Log;
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
	
	public void fillRect(Graphics2D g, Rectangle r, Color c) {
		Color pre = g.getColor();
		
		g.setColor(c);
		g.fillRect(r.x, r.y, r.width, r.height);
		
		g.setColor(pre);
	}
	
	public void fillRoundedRect(Graphics2D g, Rectangle r, Color c, int arcW, int arcH) {
		Color pre = g.getColor();
		
		g.setColor(c);
		g.fillRoundRect(r.x, r.y, r.width, r.height, arcW, arcH);
		
		g.setColor(pre);
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
	
	
	public void screenshot() {
		Log.info("Saving screenshot...");
		try {
			File folder = new File(Config.getValue("screenshot-output"));
			Assert.isTrue(folder.exists() && folder.isDirectory(), "Impossibile salvare screenshot: la cartella specificata non esiste o non è una cartella");
			
			String filename = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(new Date()) + ".png";
			File file = new File(folder, filename);
			
			BufferedImage img = new BufferedImage(ContentSettings.dimension.width, ContentSettings.dimension.height, BufferedImage.TYPE_INT_RGB);
			GameScreen.instance().getContent().paint(img.getGraphics());
			ImageIO.write(img, "png", file);
			
			Log.info("Screenshot created: " + file.getAbsolutePath());
		} catch(Exception e) {
			Log.error("Failed to create screenshot: " + e.getLocalizedMessage());
			e.printStackTrace();
		}
	}
}
