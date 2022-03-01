package core.gui.screen.content.exploration.painters;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import core.fonts.Font;
import core.gui.interfaces.Painter;
import core.gui.screen.content.ContentSettings;
import core.gui.screen.content.Exploration;
import core.obj.entities.player.Player;
import jutils.global.Global;

public class ExplorationStartMenuPainter extends Painter<Exploration> {

	private final int arc;
	private final Rectangle black;
	private final Rectangle white;
	private final List<StartMenuItems> items;
	private final int x;
	private final int firstY;
	private int selected;
	
	
	
	public ExplorationStartMenuPainter(Exploration parent) {
		super(parent);
		
		Dimension dim = ContentSettings.dimension;

		int size = ContentSettings.tileResize;
		int padding = size*4;
		
		int right = dim.width - ContentSettings.tileSize/2;
		int left = right - ContentSettings.tileSize*3;
		int top = ContentSettings.tileSize/2;
		int bottom = dim.height - top;
		

		font = new Font(font.deriveFont(3f/4f*font.getSize()).deriveFont(Font.BOLD));
		selected = 0;
		
		x = left + ContentSettings.tileSize/2;
		firstY = top + ContentSettings.tileSize/2 + font.height();
		
		arc = size;
		
		black = new Rectangle(left, top, right - left, bottom - top);
		white = new Rectangle(black.x + size, black.y + size, black.width - 2*size, black.height - 2*size);
		
		items = new ArrayList<>();
		items.add(new StartMenuItems("Pokédex"));
		items.add(new StartMenuItems("Pokémon"));
		items.add(new StartMenuItems(Player.instance().getName()));
	}


	@Override
	public void paint(Graphics2D g) {
		g.setColor(Color.black);
		g.fillRoundRect(black.x, black.y, black.width, black.height, arc, arc);
		g.setColor(Color.white);
		g.fillRoundRect(white.x, white.y, white.width, white.height, arc, arc);
		
		g.setFont(font);
		g.setColor(Color.black);
		for(int i = 0; i < items.size(); i++) {
			StartMenuItems item = items.get(i);
			
			int y = firstY + ContentSettings.tileSize*i;
			
			String label = item.getLabel();
			if(i == selected)
				label = "> " + label;
			g.drawString(label, x, y);
		}
	}
	
	
	public void before() {
		selected--;
		if(selected < 0)
			selected = items.size()-1;
	}
	
	public void after() {
		selected++;
		if(selected >= items.size())
			selected = 0;
	}
}
