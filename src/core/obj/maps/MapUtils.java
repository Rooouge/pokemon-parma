package core.obj.maps;

import java.awt.Dimension;

import core.enums.Directions;
import core.enums.TileMovements;
import core.gui.GridPosition;
import core.gui.screen.content.Exploration;
import core.obj.entities.overworld.OverworldEntity;
import core.obj.entities.overworld.OverworldEntityData;
import core.obj.entities.overworld.PlayerOverworldEntity;
import core.obj.entities.player.Player;
import core.obj.maps.links.Link;
import jutils.global.Global;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MapUtils {

	public boolean canMove(OverworldEntity entity, Exploration parent, Directions dir) throws Exception {
		entity.getData().setFacing(dir);
		Map activeMap = parent.getActiveMaps().get(0);
		Dimension mapSize = activeMap.getData().getSize();
		
		GridPosition pos = entity.getData().getPos();
		
		int[][] grid = activeMap.getMovement().getMovementsGrid();
		int nextRow = pos.row + dir.getDr();
		int nextColumn = pos.column + dir.getDc();
		
		
		// Checking links
		MapLinks links = activeMap.getLinks();
		
		if(nextRow < 0 && links.hasLink(Directions.UP)) {
			if(!(entity instanceof PlayerOverworldEntity))
				return false;
			
			Link l = links.getLink(Directions.UP);			
			Map newMap = Maps.getMap(l.getNeighborName(activeMap.getData().getRegistryName()));
			MapData nData = newMap.getData();
			Dimension nSize = nData.getSize();
			PlayerOverworldEntity pEntity = Global.get("player", Player.class).getOverworldEntity();
			OverworldEntityData pData = pEntity.getData();
			
			int newColumn = pData.getPos().column - l.getOffset(nData.getRegistryName());
			int newRow = (int) (nSize.getHeight());
			GridPosition newPos = new GridPosition(newRow, newColumn);
			
			parent.setActiveMap(newMap, pEntity, false);
			newMap.getEntities().spawn(pEntity, newPos);
			
			
			return true;
		}
		if(nextRow >= mapSize.height && links.hasLink(Directions.DOWN)) {
			if(!(entity instanceof PlayerOverworldEntity))
				return false;
			
			Link l = links.getLink(Directions.DOWN);			
			Map newMap = Maps.getMap(l.getNeighborName(activeMap.getData().getRegistryName()));
			MapData nData = newMap.getData();
			PlayerOverworldEntity pEntity = Global.get("player", Player.class).getOverworldEntity();
			OverworldEntityData pData = pEntity.getData();
			
			int newColumn = pData.getPos().column - l.getOffset(nData.getRegistryName());
			int newRow = -1;
			GridPosition newPos = new GridPosition(newRow, newColumn);
			
			parent.setActiveMap(newMap, pEntity, false);
			newMap.getEntities().spawn(pEntity, newPos);
			
			return true;
		}
		if(nextColumn < 0 && links.hasLink(Directions.LEFT)) {
			if(!(entity instanceof PlayerOverworldEntity))
				return false;
			
			return true;
		}
		if(nextColumn >= mapSize.width && links.hasLink(Directions.RIGHT)) {
			if(!(entity instanceof PlayerOverworldEntity))
				return false;
			
			return true;
		}
		
//		System.out.println(entity.getData().getName() + ": " + new GridPosition(nextRow, nextColumn) + " - " + grid.length + "," + grid[0].length);
		if(TileMovements.canMoveTo(grid[nextRow][nextColumn])) {
			GridPosition newPos = new GridPosition(nextRow, nextColumn);
			for(OverworldEntity e : activeMap.getEntities()) {
				if(e.getData().getPos().equals(newPos) && e.getData().isVisible())
					return false;
			}
			
			return true;
		}
					
		return false;
	}
}
