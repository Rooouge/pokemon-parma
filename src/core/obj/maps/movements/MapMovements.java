package core.obj.maps.movements;

import java.io.IOException;

import org.dom4j.Node;

import core.enums.Directions;
import core.enums.GameStates;
import core.gui.GridPosition;
import core.gui.XYLocation;
import core.gui.screen.content.ContentSettings;
import core.gui.screen.content.exploration.Exploration;
import core.gui.screen.content.exploration.painters.ExplorationPainter;
import core.obj.entities.overworld.OverworldEntity;
import core.obj.entities.overworld.OverworldEntityData;
import core.obj.entities.overworld.PlayerOverworldEntity;
import core.obj.maps.Map;
import core.obj.maps.MapData;
import core.obj.maps.autotiles.AutoTile;
import core.obj.maps.autotiles.MapAutoTiles;
import core.obj.maps.entities.MapEntities;
import jutils.global.Global;
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
		Exploration exploration = Global.get("content", Exploration.class);
		ExplorationPainter painter = (ExplorationPainter) exploration.getPainters().get(GameStates.EXPLORATION);
		
		if(activeMap)
			painter.setMovingDown(direction.equals(Directions.DOWN));
		
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
			if(activeMap) {
				painter.setMovingDown(false);
				entities.checkPos(indexToCheck, direction); //Resets entity drawing order
				
				// Checking possible wild Pokémon spawn
				map.getWild().wildPokemonAttempt();
			}
		}
		
		
//		System.out.println(data.getPos());
//		System.out.println("Moved Map '" + data.getName() + "' at " + new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()));
	}
	
	public boolean isAligned(XYLocation loc) {
		return (loc.x % ContentSettings.tileSize == 0) && (loc.y % ContentSettings.tileSize  == 0);
	}
	
}
