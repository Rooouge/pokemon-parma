package core.obj.maps;

import java.io.IOException;

import org.dom4j.Node;

import core.enums.Directions;
import core.gui.GridPosition;
import core.gui.XYLocation;
import core.gui.screen.content.ContentSettings;
import core.obj.entities.overworld.OverworldEntity;
import core.obj.entities.overworld.OverworldEntityData;
import core.obj.entities.overworld.PlayerOverworldEntity;
import core.obj.maps.movements.MovementsParser;
import core.obj.maps.tiles.AutoTile;
import lombok.Getter;

public class MapMovements {

	private Map map;
	@Getter
	private final int[][] movementsGrid;
	
	
	public MapMovements(Map map, Node selectSingleNode) throws IOException {
		this.map = map;
		movementsGrid = new MovementsParser(selectSingleNode, map.getData().getSize()).getMovements();
	}
	
	
	public int getMovement(int r, int c) {
		return movementsGrid[r][c];
	}
	
	public void move(Directions direction, int pixels, boolean activeMap) {
		MapData mapData = map.getData();
		XYLocation loc = mapData.getLoc();
		
		int dc = direction.getDc();
		int dr = direction.getDr();
		
		mapData.getLoc().x -= (dc * pixels);
		mapData.getLoc().y -= (dr * pixels);
		
		MapEntities entities = map.getEntities();
		int indexToCheck = -1;
		for(int i = 0; i < entities.size(); i++) {
			OverworldEntity entity = entities.get(i);
			OverworldEntityData entityData = entity.getData();
			GridPosition pos = entityData.getPos();
			
			if(!(entity instanceof PlayerOverworldEntity)) {
				entityData.getLoc().x -= (dc * pixels);
				entityData.getLoc().y -= (dr * pixels);
			} else {
				if(isAligned(loc)) {
					entityData.setPos(pos.row + dr, pos.column + dc);
					indexToCheck = i;
				}
			}
		}
		
		MapAutoTiles autoTiles = map.getAutoTiles();
		for(AutoTile at : autoTiles) {
			at.getLoc().x -= (dc*pixels);
			at.getLoc().y -= (dr*pixels);
		}
		
		if(isAligned(loc)) {
			mapData.setPosFromLoc();
			if(activeMap)
				entities.checkPos(indexToCheck, direction);
		}
		
		
//		System.out.println(data.getPos());
//		System.out.println("Moved Map '" + data.getName() + "' at " + new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()));
	}
	
	public boolean isAligned(XYLocation loc) {
		return (loc.x % ContentSettings.tileSize == 0) && (loc.y % ContentSettings.tileSize  == 0);
	}
	
}
