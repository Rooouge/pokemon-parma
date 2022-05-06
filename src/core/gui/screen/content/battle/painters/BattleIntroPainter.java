package core.gui.screen.content.battle.painters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import core.events.battle.WildPokemonBattle;
import core.fonts.Font;
import core.fonts.Fonts;
import core.gui.GUIUtils;
import core.gui.interfaces.Painter;
import core.gui.screen.content.ContentSettings;
import core.gui.screen.content.battle.Battle;
import jutils.strings.Strings;

public class BattleIntroPainter extends Painter<Battle> {

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
	private String topText;
	private String bottomText;
	
	
	public BattleIntroPainter(Battle parent) {
		super(parent);
		
		backgroundRect = new Rectangle(0, ContentSettings.dimension.height - 2*ContentSettings.tileSize, ContentSettings.dimension.width, 2*ContentSettings.tileSize);
		backgroundColor = new Color(70, 64, 76);
		
		int margin = ContentSettings.tileSize/8;
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
		
		topBorderRect = new Rectangle(backgroundRect.x, backgroundRect.y, backgroundRect.width, ContentSettings.tileResize);
		topBorderColor = Color.black;
		
		
		topTextPoint = new Point(mainRect.x + 2*margin, mainRect.y + (int) (2*mainRect.getHeight()/5));
		bottomTextPoint = new Point(mainRect.x + 2*margin, mainRect.y + (int) (4*mainRect.getHeight()/5));
		textFont = new Font(Fonts.BATTLE_FONT.deriveFont(3f*Fonts.BATTLE_FONT.getSize2D()/2f));
		textColor = Color.white;
		
		String enemyName = ((WildPokemonBattle) parent.getBattle()).getEntityPokemon().getData().getDisplayName();
		topText = "Appare " + enemyName + " selvatico!";
	}
	
	
	@Override
	public void paint(Graphics2D g) {
		GUIUtils.fillRect(g, backgroundRect, backgroundColor);
		GUIUtils.fillRoungRect(g, innerRect, innerColor, innerArcW, innerArcH);
		GUIUtils.fillRoungRect(g, mainRect, mainColor, mainArcW, mainArcH);
		GUIUtils.fillRect(g, topBorderRect, topBorderColor);
		
		g.setFont(textFont);
		g.setColor(textColor);
		if(!Strings.isVoid(topText))
			g.drawString(topText, topTextPoint.x, topTextPoint.y);
		if(!Strings.isVoid(bottomText))
			g.drawString(bottomText, bottomTextPoint.x, bottomTextPoint.y);
	}

}
