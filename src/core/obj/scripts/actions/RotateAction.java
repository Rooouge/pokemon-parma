package core.obj.scripts.actions;

import java.util.List;

import core.obj.entities.overworld.OverworldEntityData;
import core.obj.scripts.ScriptAction;
import core.obj.scripts.ScriptCompiler.EntityRotation;

public class RotateAction extends ScriptAction {

	private final List<EntityRotation> rotations;
	
	
	public RotateAction(List<EntityRotation> rotations) {
		super(false, STANDARD_DELAY);
		this.rotations = rotations;
	}
	
	
	@Override
	public void execute() {
		super.execute();
		
		for(EntityRotation move : rotations) {
			OverworldEntityData data = move.getEntity().getData();
			data.setFacing(data.getFacing().rotate(move.getRotationValue()));
		}
		
		
	}
}
