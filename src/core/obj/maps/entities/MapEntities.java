package core.obj.maps.entities;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Node;

import core.Log;
import core.enums.Directions;
import core.enums.MovementsDescriptor;
import core.gui.GridPosition;
import core.obj.entities.EntityHandler;
import core.obj.entities.overworld.OverworldEntity;
import core.obj.entities.overworld.OverworldEntityData;
import core.obj.maps.Map;
import core.obj.scripts.Scripts;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
public class MapEntities extends ArrayList<OverworldEntity> {

	private final Map map;
	@Setter
	private MapEntitiesHandler handler;
	
	
	public MapEntities(Map map, Node root) throws Exception {
		super();
		this.map = map;
		
		for(Node entityNode : root.selectNodes("entity")) {
			String name = entityNode.selectSingleNode("name").getStringValue();
			OverworldEntity entity = EntityHandler.get(name);
			OverworldEntityData eData = entity.getData();
			
			Node spawnpointNode = entityNode.selectSingleNode("spawnpoint");
			int row = Integer.parseInt(spawnpointNode.selectSingleNode("row").getStringValue());
			int column = Integer.parseInt(spawnpointNode.selectSingleNode("column").getStringValue());
			int facing = Integer.parseInt(spawnpointNode.selectSingleNode("facing").getStringValue());
			GridPosition pos = new GridPosition(row, column);
			
			Node visibleNode = entityNode.selectSingleNode("visible");
			if(visibleNode != null) {
				boolean visible = Boolean.valueOf(visibleNode.getStringValue());
				eData.setVisible(visible);
			} else {
				eData.setVisible(true);
			}
			
			Node movementDescriptorNode = entityNode.selectSingleNode("movements");
			if(movementDescriptorNode != null && !movementDescriptorNode.getStringValue().trim().isEmpty()) {
				MovementsDescriptor md = MovementsDescriptor.getFromXmlDesc(movementDescriptorNode.getStringValue());
				eData.setMovementsDescriptor(md);
			} else {
				eData.setMovementsDescriptor(MovementsDescriptor.STILL);
			}
			
			
			spawn(entity, pos, Directions.getFromIndex(facing));
			Log.log("Added Entity '" + name + "' with position " + eData.getPos());
		}
		
		for(OverworldEntity entity : this) {
			OverworldEntityData eData = entity.getData();
			eData.setScripts(Scripts.get(map, entity, this));
		}
	}
	
	
	public boolean spawn(OverworldEntity e) {
		return spawn(e, map.getData().getSpawnpoint(), e.getData().getFacing());
	}
	
	public boolean spawn(OverworldEntity e, GridPosition pos) {
		return spawn(e, pos, e.getData().getFacing());
	}
	
	public boolean spawn(OverworldEntity e, GridPosition pos, Directions facing) {
		boolean result = add(e, pos);
		
		
		if(result) {
			OverworldEntityData eData = e.getData();
			eData.setPos(pos);
			eData.setOriginalPos(new GridPosition(pos.row, pos.column));
			eData.setLoc(map.getData().getLocationFromPos(pos));
			eData.setFacing(facing);
			eData.setOriginalFacing(Directions.getFromIndex(facing.getIndex()));
		}
		
		return result;
	}
	
	public boolean despawn(OverworldEntity e) {
		return remove(e);
	}
	
	public void respawnAll() {
		List<OverworldEntity> temp = new ArrayList<>(this);
		
		clear();
		for(OverworldEntity e : temp) {
			spawn(e, e.getData().getOriginalPos(), e.getData().getOriginalFacing());
		}
	}
	
	public boolean add(OverworldEntity e, GridPosition pos) {
		int index;
		boolean flag = true;
		
		for(index = 0; index < size() && flag; index++) {
			OverworldEntity entity = get(index);
			GridPosition ePos = entity.getData().getPos();
			
			if(pos.row < ePos.row)
				flag = false;
			if(pos.row == ePos.row && pos.column < ePos.column)
				flag = false;
			
			if(!flag)
				index--;
		}
		
		List<OverworldEntity> list = new ArrayList<>();
		list.add(e);
		return addAll(index, list);
	}
	
	public OverworldEntity getFromEntityIdentifiers(String name, int variant) {
		for(OverworldEntity entity : this) {
			OverworldEntityData eData = entity.getData();
			
			if(name.equalsIgnoreCase(eData.getName()) && variant == eData.getVariant())
				return entity;
		}
		
		return null;
	}
	
	public OverworldEntity getEntityFromPos(GridPosition pos) {
		for(OverworldEntity entity : this) {
			OverworldEntityData eData = entity.getData();
			
			if(pos.equals(eData.getPos()))
				return entity;
		}
		
		return null;
	}
	
	/*
	 * Movements
	 */
	
	public void checkPos(int index, Directions dir) {
		int prev = index-1;
		int post = index+1;
		GridPosition indexPos = get(index).getData().getPos();
		boolean condition = false;
		GridPosition checkPos;
		
		if(prev >= 0) {
			do {
				checkPos = get(prev).getData().getPos();
				
				if(indexPos.compare(checkPos) == -1) {
					switchEntities(prev, index);
					index--;
					prev--;
				}
			} while (condition && prev >= 0);
			
		}
		
		if(post < size()) {
			do {
				checkPos = get(post).getData().getPos();
				
				if(indexPos.compare(checkPos) == 1) {
					switchEntities(index, post);
					index++;
					post++;
				}
			} while (condition && post < size());
		}
		
	}
	
	private void switchEntities(int pos1, int pos2) {
		if(pos1 == pos2)
			return;
		if(pos1 > pos2) {
			int temp = pos2;
			pos2 = pos1;
			pos1 = temp;
		}
		
		OverworldEntity temp = remove(pos2);
		add(pos1, temp);
	}
	
	
	@Override
	public String toString() {
		if(isEmpty())
			return "";
		
		String toReturn = "---------------------";
		for(int i = 0; i < size(); i++) {
			OverworldEntityData data = get(i).getData();
			toReturn += "\n[" + i + "] " + data.getName() + " " + data.getPos();
		}
		
		return toReturn;
	}
}
