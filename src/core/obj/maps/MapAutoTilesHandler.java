package core.obj.maps;

import java.util.Date;
import java.util.Random;

import core.gui.screen.content.Exploration;
import core.obj.maps.tiles.AutoTile;

public class MapAutoTilesHandler {

	private final MapAutoTiles autoTiles;
	private final Exploration parent;
	private final long delay;
	private final Random random;
	private final int chance;
	private Date start;
	
	
	public MapAutoTilesHandler(MapAutoTiles autoTiles, int chance, Exploration parent) {
		this.autoTiles = autoTiles;
		this.chance = chance;
		this.parent = parent;
		
		delay = 5000;
		random = new Random();
	}
	
	
	public void update() {
		if(start == null)
			start = new Date();
		
		if(new Date().getTime() - start.getTime() >= delay) {
			for(int i = 0; i < autoTiles.size(); i++) {
				if(random.nextInt(chance) == 0) {
					AutoTile at = autoTiles.get(i);
					
					parent.addAction(at);
					break;
				}	
			}
			
			start = null;
		}
	}
	
}