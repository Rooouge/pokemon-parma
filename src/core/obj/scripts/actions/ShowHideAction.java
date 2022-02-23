package core.obj.scripts.actions;

import core.obj.entities.overworld.OverworldEntity;
import core.obj.scripts.ScriptAction;

public class ShowHideAction extends ScriptAction {

	private final OverworldEntity entity;
	private final boolean value;
	
	
	public ShowHideAction(OverworldEntity entity, boolean value) {
		super(false, NO_DELAY);
		this.entity = entity;
		this.value = value;
	}

	
	@Override
	public void execute() {
		super.execute();
		
		entity.getData().setVisible(value);
	}
}
