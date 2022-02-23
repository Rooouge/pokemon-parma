package core.gui.screen.content.exploration.painters;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import core.fonts.Font;
import core.fonts.Fonts;
import core.gui.interfaces.Painter;
import core.gui.screen.content.ContentSettings;
import core.gui.screen.content.Exploration;
import core.obj.scripts.ScriptAction;
import core.obj.scripts.actions.WaitPressAction;
import core.obj.scripts.actions.TextAction;
import jutils.global.Global;

public class ExplorationEntityScriptPainter extends Painter<Exploration> {
	
	private ScriptAction action;
	
	private final int left;
	private final int top;
	private final int bottom;
	private final int right;
	private final int size;
	private final int arc;
	
	private final Rectangle black;
	private final Rectangle white;
	private final Point line1;
	private final Point line2;
	private final int padding;
	
//	private Thread painter;
//	private int index;
	
	
	public ExplorationEntityScriptPainter(Exploration parent) {
		super(parent);
		
		Dimension dim = ContentSettings.dimension;
		
		left = ContentSettings.tileSize/2;
		right = dim.width - left;
		bottom = dim.height - left;
		top = bottom - ContentSettings.tileSize*2;
		size = ContentSettings.tileResize;
		arc = size*4;
		padding = size*4;
		
		black = new Rectangle(left, top, right - left, bottom - top);
		white = new Rectangle(black.x + size, black.y + size, black.width - 2*size, black.height - 2*size);
		
		
		Font font = Fonts.SCRIPT_TEXT_FONT;
		int height = (int)(font.getStringBounds("SAMPLE", font.getFrc())).getHeight();
		
		line1 = new Point(white.x + padding, white.y + padding + height);
		line2 = new Point(line1.x, white.y + white.height - padding);
		
		Global.add(TextAction.MAX_WIDTH, Integer.valueOf(white.width - 2*padding));
//		index = 1;
	}
	
	
	@Override
	public void paint(Graphics2D g) {
		if(action != null && action instanceof TextAction) {
			TextAction tAction = (TextAction) action;
			
//			System.out.println(tAction.getText());
			
			g.setColor(Color.black);
			g.fillRoundRect(black.x, black.y, black.width, black.height, arc, arc);
			g.setColor(Color.white);
			g.fillRoundRect(white.x, white.y, white.width, white.height, arc, arc);
			
			/*
			int l1 = tAction.getLine1().length();
			int l2 = tAction.getLine2().length();
			
			if(index >= l1 + l2) {
				System.out.println("Over");
				g.setFont(Fonts.SCRIPT_TEXT_FONT);
				g.setColor(Color.black);
				g.drawString(tAction.getLine1(), line1.x, line1.y);
				g.drawString(tAction.getLine2(), line2.x, line2.y);
			}
			
			if(painter == null || !painter.isAlive()) {
				painter = Threads.run(() -> {
					g.setFont(Fonts.SCRIPT_TEXT_FONT);
					g.setColor(Color.black);
					
					if(index <= l1) {
						System.out.println(tAction.getLine1().substring(0, index));
						g.drawString(tAction.getLine1().substring(0, index), line1.x, line1.y);
					} else {
						System.out.println(tAction.getLine2().substring(0, index - l1));
						g.drawString(tAction.getLine1(), line1.x, line1.y);
						g.drawString(tAction.getLine2().substring(0, index - l1), line2.x, line2.y);
					}
					
					if(index < l1 + l2)
						index++;
					if(index == l1 + l2) {
						Thread.currentThread().interrupt();
						System.out.println("interrupt");
					}
				});
			}
			*/
			

//			g.setColor(Color.red);
//			g.drawLine(line1.x, line1.y, white.width - padding, line1.y);
//			g.drawLine(line2.x, line2.y, white.width - padding, line2.y);
			
			g.setFont(Fonts.SCRIPT_TEXT_FONT);
			g.setColor(Color.black);
			g.drawString(tAction.getLine1(), line1.x, line1.y);
			g.drawString(tAction.getLine2(), line2.x, line2.y);
		}
	}
	
	
	public void setAction(ScriptAction action) {
		if(action instanceof WaitPressAction && this.action instanceof TextAction)
			return;
		
		this.action = action;
	}
}
