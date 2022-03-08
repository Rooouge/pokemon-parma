package core.gui.screen.painters;

import java.awt.Graphics2D;

import core.enums.GameStates;
import core.fonts.Font;
import core.fonts.Fonts;
import core.gui.screen.GameScreen;
import core.gui.screen.painters.fade.FadeIn;
import core.gui.screen.painters.fade.FadeOut;

public class ScreenPainter {

	protected final GameScreen parent;
	protected final FadeIn fadeIn;
	protected final FadeOut fadeOut;
	protected Font font;
	
	
	public ScreenPainter(GameScreen parent) {
		this.parent = parent;
		fadeIn = new FadeIn();
		fadeOut = new FadeOut();
		font = Fonts.SCRIPT_TEXT_FONT;
	}
	
	
	public void paint(Graphics2D g) {
		if(fadeIn.isActive()) {
			fadeIn.paint(g);
		} else if(fadeOut.isActive()) {
			fadeOut.paint(g);
		}
	}
	
	
	public void fadeIn(GameStates start) {
		fadeIn.setStart(start);
		fadeIn.setActive(true);
	}
	
	public void fadeOut() {
		fadeOut.setActive(true);
	}
	
	public boolean isFadingIn() {
		return fadeIn.isActive();
	}
	
	public boolean isFadingOut() {
		return fadeOut.isActive();
	}
}
