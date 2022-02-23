package core.obj.scripts.actions;

import core.obj.entities.overworld.OverworldEntity;
import core.obj.scripts.ScriptAction;

public class SetStateAction extends ScriptAction {

	private final OverworldEntity entity;
	private final int state;
	
	
	public SetStateAction(OverworldEntity entity, int state) {
		super(false, NO_DELAY);
		this.entity = entity;
		this.state = state;
	}
	
	
	@Override
	public void execute() {
		super.execute();
		
		entity.getData().getScripts().setState(state);
	}
}
