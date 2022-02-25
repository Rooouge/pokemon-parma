package core.obj.scripts.actions;

import java.util.List;

import core.obj.entities.overworld.OverworldEntity;
import core.obj.maps.entities.MapEntities;
import core.obj.scripts.ScriptAction;
import core.obj.scripts.ScriptCompiler.EntityPosition;

public class TeleportAction extends ScriptAction {

	private final List<EntityPosition> positions;
	private final MapEntities entities;
	
	
	public TeleportAction(List<EntityPosition> positions, MapEntities entities) {
		super(false, STANDARD_DELAY);
		this.positions = positions;
		this.entities = entities;
	}
	
	
	@Override
	public void execute() {
		super.execute();
		
		for(EntityPosition ep : positions) {
			OverworldEntity e = ep.getEntity();
			
			entities.despawn(e);
			entities.spawn(e, ep.getPos());
		}
	}

}
