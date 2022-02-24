package core.gui.screen.content.exploration.painters;

import java.awt.Graphics2D;
import java.util.List;

import core.files.TiledImage;
import core.gui.XYLocation;
import core.gui.interfaces.Painter;
import core.gui.screen.content.ContentSettings;
import core.gui.screen.content.Exploration;
import core.obj.entities.overworld.OverworldEntity;
import core.obj.entities.overworld.OverworldEntityData;
import core.obj.maps.Map;
import core.obj.maps.MapData;
import core.obj.maps.autotiles.AutoTile;
import core.obj.maps.autotiles.MapAutoTiles;
import core.obj.maps.entities.MapEntities;
import core.obj.maps.images.MapImages;

public class ExplorationPainter extends Painter<Exploration> {
	
	private final OnMapChangeLabel onMapChange;
	
	public ExplorationPainter(Exploration parent) {
		super(parent);
		onMapChange = new OnMapChangeLabel();
	}
	

	@Override
	public void paint(Graphics2D g) {
		List<Map> activeMaps = parent.getActiveMaps();
		int m;
		int size = activeMaps.size();
		
		
		for(m = 0; m < size; m++) {
			// Drawing layer 0
			Map map = activeMaps.get(m);
			MapData mData = map.getData();
			MapImages mImages = map.getImages();
			XYLocation mLoc = mData.getLoc();
			
			g.drawImage(mImages.get(0).getImage(), mLoc.x, mLoc.y, null);
			
			//Drawing autotiles
			MapAutoTiles autoTiles = map.getAutoTiles();
			for(AutoTile at : autoTiles) {
				XYLocation atLoc = at.getLoc();
				
				g.drawImage(at.getImage().getImage(), atLoc.x, atLoc.y, null);
			}
		}
		
		for(m = 0; m < size; m++) {
			// Drawing entities
			Map map = activeMaps.get(m);
			MapEntities mEntities = map.getEntities();
			
			for(int e = 0; e < mEntities.size(); e++) {
				OverworldEntity entity = mEntities.get(e);
				OverworldEntityData eData = entity.getData();
				
				if(eData.isVisible()) {
					TiledImage eImage = entity.getImageToDraw();
					int x = 0;
					int y = 0;
					XYLocation eLoc = eData.getLoc();
					
					x = eLoc.x - ContentSettings.tileSize*eImage.getHorTilesDelta();
					y = eLoc.y - ContentSettings.tileSize*eImage.getVerTilesDelta();
					g.drawImage(eImage.getImage(), x, y, null);
				}
			}
			
			// Drawing layer 1
			MapData mData = map.getData();
			MapImages mImages = map.getImages();
			XYLocation mLoc = mData.getLoc();
			
			if(mImages.size() < 1)
				continue;
			
			g.drawImage(mImages.get(1).getImage(), mLoc.x, mLoc.y, null);
		}
		
		/*
		for(m = 0; m < activeMaps.size(); m++) {
			Map map = activeMaps.get(m);
			MapData mData = map.getData();
			MapEntities mEntities = map.getEntities();
			GridPosition mPos = mData.getPos();
			
			g.setColor(Color.white);
			g.drawString(mData.getName() + ": " + mPos, 20, 500 + (m*20));
			
			for(int e = 0; e < mEntities.size(); e++) {
				OverworldEntity entity = mEntities.get(e);
				OverworldEntityData eData = entity.getData();
				GridPosition ePos = eData.getPos();
				
				if(entity instanceof PlayerOverworldEntity) {
					g.drawString(eData.getName() + ": " + ePos, 20, 600 + (m*20));
					break;
				}
			}			
		}
		*/
		
		if(parent.isOnMapChange()) {
			onMapChange.set(parent.getActiveMap().getData().getLabel(), g);
			parent.setOnMapChange(false);
		}
		
		if(onMapChange.isActive())
			onMapChange.draw(g);
	}
	
}
