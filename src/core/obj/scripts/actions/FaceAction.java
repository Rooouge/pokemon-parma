package core.obj.scripts.actions;

import core.enums.Directions;
import core.obj.entities.overworld.OverworldEntity;
import core.obj.scripts.ScriptAction;

public class FaceAction extends ScriptAction {

	private final OverworldEntity entity;
	private final Directions dir;
	
	
	public FaceAction(OverworldEntity entity, Directions dir) {
		super(false, NO_DELAY);
		this.entity = entity;
		this.dir = dir;
	}
	
	
	@Override
	public void execute() {
		super.execute();
		
		if(dir != null) {
			entity.getData().setFacing(dir);
		}
	}
	
}
