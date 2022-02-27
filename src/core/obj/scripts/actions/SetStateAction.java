package core.obj.scripts.actions;

import core.obj.entities.Entity;
import core.obj.entities.overworld.OverworldEntity;
import core.obj.maps.tileentities.TileEntity;
import core.obj.scripts.ScriptAction;

public class SetStateAction extends ScriptAction {

	private final Entity<?> entity;
	private final int state;
	
	
	public SetStateAction(Entity<?> entity, int state) {
		super(false, NO_DELAY);
		this.entity = entity;
		this.state = state;
	}
	
	
	@Override
	public void execute() {
		super.execute();
		
		if(entity instanceof OverworldEntity)
			((OverworldEntity) entity).getData().getScripts().setState(state);
		else if(entity instanceof TileEntity)
			((TileEntity) entity).getData().getScripts().setState(state);
	}
}
