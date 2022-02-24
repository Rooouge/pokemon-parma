package core.obj.scripts.actions;

import core.gui.GridPosition;
import core.obj.entities.overworld.OverworldEntity;
import core.obj.maps.entities.MapEntities;
import core.obj.scripts.ScriptAction;

public class TeleportAction extends ScriptAction {

	private final OverworldEntity entity;
	private final GridPosition pos;
	private final MapEntities entities;
	
	
	public TeleportAction(OverworldEntity entity, MapEntities entities, GridPosition pos) {
		super(false, STANDARD_DELAY);
		this.entity = entity;
		this.pos = pos;
		this.entities = entities;
	}
	
	
	@Override
	public void execute() {
		super.execute();
		
		entities.despawn(entity);
		entities.spawn(entity, pos);
	}

}
