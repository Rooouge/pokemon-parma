package core.gui.screen.content;

import java.awt.Dimension;

import jutils.config.Config;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ContentSettings {

	public int tileOriginalSize;
	public int tileSize;
	public int tileResize;
	public int horTiles;
	public int verTiles;
	public Dimension dimension;
	
	
	public void init() {
		tileOriginalSize = Integer.parseInt(Config.getValue("screen.content.tile-size"));
		tileResize = Integer.parseInt(Config.getValue("screen.content.tile-resize"));
		tileSize = tileOriginalSize*tileResize;
		
		String[] tilesGrid = Config.getValue("screen.content.tiles").split(",");
		horTiles = Integer.parseInt(tilesGrid[0]);
		verTiles = Integer.parseInt(tilesGrid[1]);
		
		dimension = new Dimension(tileSize*horTiles, tileSize*verTiles);
	}
	
	
}
