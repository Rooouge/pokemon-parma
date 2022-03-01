package core.obj.scripts.actions;

import core.enums.Directions;
import core.enums.GameStates;
import core.events.exploration.EntityMovementAction;
import core.gui.screen.GlobalKeyEventHandler;
import core.gui.screen.content.ContentSettings;
import core.gui.screen.content.Exploration;
import core.gui.screen.content.exploration.ExplorationKeyPressHandler;
import core.obj.scripts.ScriptAction;
import jutils.global.Global;

public class MoveCameraAction extends ScriptAction {

	private final Directions dir;
	
	
	public MoveCameraAction(Directions dir) {
		super(false, STANDARD_DELAY);		
		this.dir = dir;
	}
	
	
	@Override
	public void execute() {
		super.execute();
		
		Exploration exploration = Global.get("content", Exploration.class);
		exploration.addAction(new EntityMovementAction(
				ContentSettings.tileOriginalSize, 
				exploration.getActiveMaps(), 
				dir, 
				GlobalKeyEventHandler.instance().get(GameStates.EXPLORATION, ExplorationKeyPressHandler.class),
				false)
		);
		exploration.getEntityHandler().addCameraMovementCounterpart(dir);
	}
}
