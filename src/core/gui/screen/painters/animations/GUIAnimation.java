package core.gui.screen.painters.animations;

import java.awt.Graphics2D;

import core.gui.screen.content.Content;
import jutils.gui.ColoredPanel;
import lombok.Getter;

@Getter
public abstract class GUIAnimation {

	protected final Content<? extends ColoredPanel> parent;
	protected final int duration;
	protected final boolean forceLock;
	protected int tick;
	
	
	protected GUIAnimation(Content<? extends ColoredPanel> parent, int duration, boolean forceLock) {
		this.parent = parent;
		this.duration = duration;
		this.forceLock = forceLock;
		tick = 0;
	}
	
	public void update() {
		if(tick == duration) {
			onEnd();
			parent.animationOver();
			tick = 0;
			return;
		}
		
//		System.out.println(tick);
		
		if(tick == 0)
			onStart();		
		
		tick();
		tick++;
		
	}
	
	
	public abstract void onStart();
	public abstract void tick();
	public abstract void onEnd();
	public abstract void paint(Graphics2D g);
}
