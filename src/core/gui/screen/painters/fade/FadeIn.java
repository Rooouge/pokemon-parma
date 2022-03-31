package core.gui.screen.painters.fade;

import java.awt.Color;
import java.awt.Graphics2D;

import core.enums.GameStates;
import lombok.Setter;

public class FadeIn extends Fade {	
	
	@Setter
	protected GameStates start;
	
	
	@Override
	public void paint(Graphics2D g) {
		if(tick == 0)
			GameStates.set(start);
		
		Color c = new Color(0f, 0f, 0f, 1f - (1f*tick)/fadeTime);
//		System.out.println("[IN]   " + c + " [a=" + c.getAlpha() + "]");
		g.setColor(c);
		g.fillRect(0, 0, dim.width, dim.height);
		
		tick++;
		if(tick >= fadeTime) {
			stopReset();
		}
	}
	
}
