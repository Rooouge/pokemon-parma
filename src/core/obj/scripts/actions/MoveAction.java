package core.obj.scripts.actions;

import java.util.List;

import core.enums.Directions;
import core.enums.GameStates;
import core.events.exploration.EntityMovementAction;
import core.gui.screen.GlobalKeyEventHandler;
import core.gui.screen.content.ContentSettings;
import core.gui.screen.content.exploration.Exploration;
import core.gui.screen.content.exploration.keyhandlers.ExplorationKeyHandler;
import core.obj.entities.overworld.OverworldEntity;
import core.obj.entities.overworld.PlayerOverworldEntity;
import core.obj.maps.entities.MapEntitiesHandler;
import core.obj.scripts.ScriptAction;
import core.obj.scripts.ScriptCompiler.EntityDirection;
import jutils.global.Global;

public class MoveAction extends ScriptAction {

	private final List<EntityDirection> movements;
	
	
	public MoveAction(List<EntityDirection> movements) {
		super(false, STANDARD_DELAY);
		this.movements = movements;
	}
	
	
	@Override
	public void execute() {
		super.execute();
		
		Exploration exploration = Global.get("content", Exploration.class);
		MapEntitiesHandler handler = exploration.getEntityHandler();
		
		for(EntityDirection move : movements) {
			OverworldEntity entity = move.getEntity();
			Directions dir = move.getDir();
			
			if(entity instanceof PlayerOverworldEntity) {
				entity.getData().setFacing(dir);
				exploration.addAction(entity.getAnimationAction());
				exploration.addAction(new EntityMovementAction(
						ContentSettings.tileOriginalSize, 
						exploration.getActiveMaps(), 
						dir, 
						GlobalKeyEventHandler.instance().get(GameStates.EXPLORATION, ExplorationKeyHandler.class),
						false)
				);
			}
			else {
				handler.addMovementAction(entity, dir);
			}
		}
	}
	
}
