package core.obj.animation;

import core.files.TiledImage;
import core.obj.actions.SequenceActionWithDelay;

public abstract class Animation extends SequenceActionWithDelay {

	public static final int STANDARD_TIMES = 4;
	public static final int STANDARD_DELAY = 4;
	
	protected TiledImage[] images; // Sprite used in animation
	
	
	protected Animation(int times, int numOfSprite, int delay) {
		super(times, delay, SequenceActionWithDelay.DELAY_POST);
		
		images = new TiledImage[numOfSprite];
		
		setSprite();
	}
	
	
	protected abstract void setSprite();
	protected abstract TiledImage getImage();
}
