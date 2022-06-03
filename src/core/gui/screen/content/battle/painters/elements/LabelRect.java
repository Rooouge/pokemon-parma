package core.gui.screen.content.battle.painters.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import core.fonts.Font;
import core.fonts.Fonts;
import core.gui.GUIUtils;
import core.gui.screen.content.ContentSettings;
import jutils.strings.Strings;
import lombok.Setter;

public class LabelRect {

	private final Rectangle backgroundRect;
	private final Color backgroundColor;
	private final Rectangle innerRect;
	private final int innerArcW;
	private final int innerArcH;
	private final Color innerColor;
	private final Rectangle mainRect;
	private final int mainArcW;
	private final int mainArcH;
	private final Color mainColor;
	private final Rectangle topBorderRect;
	private final Color topBorderColor;
	
	private final Font textFont;
	private final Color textColor;
	private final Point topTextPoint;
	private final Point bottomTextPoint;
	@Setter
	private String topText;
	@Setter
	private String bottomText;
	
	
	public LabelRect() {
		Dimension dim = ContentSettings.dimension;
		int tile = ContentSettings.tileSize;
		int resize = ContentSettings.tileResize;
		
		backgroundRect = new Rectangle(0, dim.height - 2*tile, dim.width, 2*tile);
		backgroundColor = new Color(70, 64, 76);
		
		int margin = tile/8;
		int arcW = margin*4;
		int arcH = arcW/2;
		innerRect = new Rectangle(backgroundRect.x + margin, backgroundRect.y + margin, backgroundRect.width - 2*margin, backgroundRect.height - 2*margin);
		innerArcW = arcW;
		innerArcH = arcH;
		innerColor = new Color(226, 74, 53);
		
		mainRect = new Rectangle(innerRect.x + margin, innerRect.y + margin/2, innerRect.width - 2*margin, innerRect.height - margin);
		mainArcW = arcW/2;
		mainArcH = arcH;
		mainColor = new Color(104, 160, 160);
		
		topBorderRect = new Rectangle(backgroundRect.x, backgroundRect.y, backgroundRect.width, resize);
		topBorderColor = Color.black;
		
		topTextPoint = new Point(mainRect.x + 2*margin, mainRect.y + (int) (2*mainRect.getHeight()/5));
		bottomTextPoint = new Point(mainRect.x + 2*margin, mainRect.y + (int) (4*mainRect.getHeight()/5));
		textFont = new Font(Fonts.BATTLE_FONT.deriveFont(3f*Fonts.BATTLE_FONT.getSize2D()/2f));
		textColor = Color.white;
	}
	
	
	public void paint(Graphics2D g) {
		GUIUtils.fillRect(g, backgroundRect, backgroundColor);
		GUIUtils.fillRoundedRect(g, innerRect, innerColor, innerArcW, innerArcH);
		GUIUtils.fillRoundedRect(g, mainRect, mainColor, mainArcW, mainArcH);
		GUIUtils.fillRect(g, topBorderRect, topBorderColor);
		
		g.setFont(textFont);
		g.setColor(textColor);
		if(!Strings.isVoid(topText))
			g.drawString(topText, topTextPoint.x, topTextPoint.y);
		if(!Strings.isVoid(bottomText))
			g.drawString(bottomText, bottomTextPoint.x, bottomTextPoint.y);
	}
}
