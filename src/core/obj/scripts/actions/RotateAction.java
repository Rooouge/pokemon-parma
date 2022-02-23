package core.obj.scripts.actions;

import core.obj.entities.overworld.OverworldEntity;
import core.obj.entities.overworld.OverworldEntityData;
import core.obj.scripts.ScriptAction;

public class RotateAction extends ScriptAction {

	private final OverworldEntityData data;
	private final int rotationValue;
	
	
	public RotateAction(OverworldEntity entity, int rotationValue) {
		super(false, STANDARD_DELAY);
		
		data = entity.getData();
		this.rotationValue = rotationValue;
	}
	
	
	@Override
	public void execute() {
		super.execute();
		
		data.setFacing(data.getFacing().rotate(rotationValue));
	}
}
