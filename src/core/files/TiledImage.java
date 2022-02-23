package core.files;

import java.awt.image.BufferedImage;

import core.gui.screen.content.ContentSettings;
import lombok.Getter;

@Getter
public class TiledImage {

	private final BufferedImage image;
	private final int width;
	private final int height;
	private final int horTiles;
	private final int verTiles;
	
	
	public TiledImage(BufferedImage image) {
		this.image = image;
		
		width = image.getWidth();
		height = image.getHeight();
		horTiles = width / ContentSettings.tileSize;
		verTiles = height / ContentSettings.tileSize;
	}
	
	
	public int getHorTilesDelta() {
		return horTiles/2;
	}
	
	public int getVerTilesDelta() {
		return verTiles/2;
	}
}
