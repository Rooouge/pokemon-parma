package core.gui.screen.painters.fade;

import java.awt.Color;
import java.awt.Graphics2D;

public class FadeOut extends Fade {

	public void paint(Graphics2D g) {
		g.setColor(new Color(0f, 0f, 0f, (1f*tick)/fadeTime));
		g.fillRect(0, 0, dim.width, dim.height);
		
		tick++;
		if(tick >= fadeTime) {			
			stopReset();
		}
	}

}
