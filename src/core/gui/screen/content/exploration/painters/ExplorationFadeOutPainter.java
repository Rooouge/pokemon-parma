package core.gui.screen.content.exploration.painters;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import core.enums.GameStates;
import core.gui.interfaces.Painter;
import core.gui.screen.content.ContentSettings;
import core.gui.screen.content.Exploration;
import jutils.config.Config;

public class ExplorationFadeOutPainter extends Painter<Exploration> {

	private final int fadeTime;
	private final Dimension dim;
	private int tick;
	
	
	public ExplorationFadeOutPainter(Exploration parent) {
		super(parent);
		
		fadeTime = Integer.parseInt(Config.getValue("screen.fade-in"));
		dim = ContentSettings.dimension;
	}
	

	@Override
	public void paint(Graphics2D g) {
		g.setColor(new Color(0f, 0f, 0f, (1f*tick)/fadeTime));
		g.fillRect(0, 0, dim.width, dim.height);
		
		tick++;
		if(tick >= fadeTime) {
			tick = 0;
			GameStates.set(GameStates.NONE);
		}
	}

}
