package core.gui.screen.content.painters;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import core.enums.GameStates;
import core.gui.interfaces.Painter;
import core.gui.screen.content.ContentSettings;
import jutils.config.Config;
import jutils.gui.ColoredPanel;

public class FadeInPainter<T extends ColoredPanel> extends Painter<T> {

	private final int fadeTime;
	private final GameStates state;
	private final Dimension dim;
	private int tick;
	
	
	public FadeInPainter(T parent, GameStates state) {
		super(parent);
		
		fadeTime = Integer.parseInt(Config.getValue("screen.fade"));
		this.state = state;
		dim = ContentSettings.dimension;
	}
	

	@Override
	public void paint(Graphics2D g) {
		g.setColor(new Color(0f, 0f, 0f, 1f - (1f*tick)/fadeTime));
		g.fillRect(0, 0, dim.width, dim.height);
		
		tick++;
		if(tick >= fadeTime) {
			tick = 0;
			GameStates.set(state);
		}
	}
	
}
