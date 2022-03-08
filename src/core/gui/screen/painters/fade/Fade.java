package core.gui.screen.painters.fade;

import java.awt.Dimension;
import java.awt.Graphics2D;

import core.gui.screen.content.ContentSettings;
import jutils.config.Config;
import lombok.Getter;
import lombok.Setter;

public abstract class Fade {

	protected final Dimension dim;
	@Setter
	protected int fadeTime;
	protected int tick;
	@Getter
	@Setter
	protected boolean active;
	
	
	public Fade() {
		fadeTime = Integer.parseInt(Config.getValue("screen.fade"));
		dim = ContentSettings.dimension;
		active = false;
	}
	
	
	protected void stop() {
		active = false;
	}
	
	protected void reset() {
		tick = 0;
	}
	
	protected void stopReset() {
		stop();
		reset();
	}
	
	
	public abstract void paint(Graphics2D g);
}
