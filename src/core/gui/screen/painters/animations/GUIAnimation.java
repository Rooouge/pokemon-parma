package core.gui.screen.painters.animations;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import core.files.ImageHandler;
import core.gui.screen.content.Content;
import jutils.gui.ColoredPanel;
import lombok.Getter;

@Getter
public abstract class GUIAnimation<T extends Content<? extends ColoredPanel>> {

	protected final T parent;
	protected final String resName;
	protected final BufferedImage[] animation;
	protected final int duration;
	protected final boolean forceLock;
	protected int tick;
	
	
	protected GUIAnimation(T parent, String resName, String dir, int duration, boolean forceLock) throws IOException {
		this.parent = parent;
		this.resName = resName;
		this.duration = duration;
		this.forceLock = forceLock;
		animation = ImageHandler.getAnimation(resName, dir);
		tick = 0;
	}
	
	protected GUIAnimation(T parent, int duration, boolean forceLock) {
		this.parent = parent;
		this.duration = duration;
		this.forceLock = forceLock;
		resName = null;
		animation = null;
		tick = 0;
	}
	
	
	public void update() {
		if(isOver()) {
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
	
	public boolean isOver() {
		return tick == duration;
	}
	
	
	public abstract void onStart();
	public abstract void tick();
	public abstract void onEnd();
	public abstract void paint(Graphics2D g);
}
