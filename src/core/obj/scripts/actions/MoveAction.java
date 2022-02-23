package core.obj.scripts.actions;

import core.enums.Directions;
import core.gui.screen.content.Exploration;
import core.obj.entities.overworld.OverworldEntity;
import core.obj.scripts.ScriptAction;
import jutils.global.Global;

public class MoveAction extends ScriptAction {

	private final OverworldEntity entity;
	private final Directions dir;
	
	
	public MoveAction(OverworldEntity entity, Directions dir) {
		super(false, ScriptAction.STANDARD_DELAY);
		this.entity = entity;
		this.dir = dir;
	}
	
	
	@Override
	public void execute() {
		super.execute();
		
		Global.get("content", Exploration.class).getEntityHandler().addMovementAction(entity, dir);
//		System.out.println(entity.getData().getName());
	}
}
