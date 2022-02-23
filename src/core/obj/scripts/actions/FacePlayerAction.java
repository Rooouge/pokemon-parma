package core.obj.scripts.actions;

import core.enums.Directions;
import core.gui.GridPosition;
import core.obj.entities.overworld.OverworldEntity;
import core.obj.entities.overworld.PlayerOverworldEntity;
import core.obj.entities.player.Player;
import core.obj.scripts.ScriptAction;
import jutils.global.Global;

public class FacePlayerAction extends ScriptAction {

	private final OverworldEntity entity;
	
	
	public FacePlayerAction(OverworldEntity entity) {
		super(false, NO_DELAY);
		this.entity = entity;
	}
	
	
	@Override
	public void execute() {
		super.execute();
		Directions dir = getPlayerRelativeDirection();
		if(dir != null) {
			entity.getData().setFacing(dir);
		}
	}
	
	
	private Directions getPlayerRelativeDirection() {
		PlayerOverworldEntity pEntity = Global.get("player", Player.class).getOverworldEntity();
		GridPosition pPos = pEntity.getData().getPos();
		GridPosition ePos = entity.getData().getPos();
		
		if(pPos.row == ePos.row + 1)
			return Directions.DOWN;
		if(pPos.row == ePos.row - 1)
			return Directions.UP;
		if(pPos.column == ePos.column - 1)
			return Directions.LEFT;
		if(pPos.column == ePos.column + 1)
			return Directions.RIGHT;
		
		return null;
	}

}
