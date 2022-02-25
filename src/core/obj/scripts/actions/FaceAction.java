package core.obj.scripts.actions;

import java.util.List;

import core.obj.scripts.ScriptAction;
import core.obj.scripts.ScriptCompiler.EntityDirection;

public class FaceAction extends ScriptAction {

	private final List<EntityDirection> facings;
	
	
	public FaceAction(List<EntityDirection> facings) {
		super(false, NO_DELAY);
		this.facings = facings;
	}
	
	
	@Override
	public void execute() {
		super.execute();
		
		for(EntityDirection facing : facings) {
			facing.getEntity().getData().setFacing(facing.getDir());
		}
	}
	
}
