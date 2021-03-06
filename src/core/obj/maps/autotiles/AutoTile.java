package core.obj.maps.autotiles;

import core.files.TiledImage;
import core.gui.GridPosition;
import core.gui.XYLocation;
import core.gui.screen.content.ContentSettings;
import core.obj.animation.Animation;
import lombok.Getter;
import lombok.Setter;

@Getter
public class AutoTile extends Animation {

	public static final int TYPE_BOTTOM = 0;
	public static final int TYPE_TOP = 1;
	
	
	private final int type;
	private final String resName;
	@Setter
	protected GridPosition originalPos;
	@Setter
	private GridPosition pos;
	@Setter
	private XYLocation loc;
	private final TiledImage sourceImage;
	
	
	public AutoTile(String resName, TiledImage sourceImage, int type, int times, int numOfSprite, int delay) {
		super(times, numOfSprite, delay);
		this.type = type;
		this.resName = resName;
		this.sourceImage = sourceImage;
		
		setSprite();
	}


	@Override
	protected void setSprite() {
		if(sourceImage == null)
			return;
		
		int size = ContentSettings.tileSize;
		
		for(int i = 0; i < images.length; i++) {
			images[i] = new TiledImage(sourceImage.getImage().getSubimage(size*i, 0, size, size));
		}
	}
	
	@Override
	public void onEnd() {
		tick = 0;
	}

	@Override
	public TiledImage getImage() {
		return images[tick];
	}
}
