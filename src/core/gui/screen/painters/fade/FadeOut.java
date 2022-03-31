package core.gui.screen.painters.fade;

import java.awt.Color;
import java.awt.Graphics2D;

import lombok.Setter;

public class FadeOut extends Fade {

	@Setter
	private Runnable onEnd;
	
	
	public void paint(Graphics2D g) {
		Color c = new Color(0f, 0f, 0f, (1f*tick)/fadeTime);
//		System.out.println("[OUT]   " + c + " [a=" + c.getAlpha() + "]");
		g.setColor(c);
		g.fillRect(0, 0, dim.width, dim.height);
		
		tick++;
		if(tick >= fadeTime) {			
			stopReset();
			if(onEnd != null) {
				onEnd.run();
				onEnd = null;
			}
		}
	}

}
