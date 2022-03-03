package core.events.exploration;

import java.util.List;

import core.enums.Directions;
import core.gui.GridPosition;
import core.gui.screen.content.ContentSettings;
import core.gui.screen.content.exploration.keypresshandlers.ExplorationKeyPressHandler;
import core.obj.actions.SequenceAction;
import core.obj.entities.player.Player;
import core.obj.maps.Map;
import core.obj.maps.autotiles.AutoTile;
import core.obj.maps.tileentities.TileEntity;
import core.obj.scripts.Script;
import core.obj.scripts.ScriptExecutor;
import jutils.global.Global;

public class EntityMovementAction extends SequenceAction {

	private final List<Map> activeMaps;
	private final Directions dir;
	private final int pixels;
	private final ExplorationKeyPressHandler handler;
	
	
	public EntityMovementAction(int times, List<Map> activeMaps, Directions dir, ExplorationKeyPressHandler handler, boolean running) {
		super(running ? times/2 : times);
		this.activeMaps = activeMaps;
		this.dir = dir;
		this.handler = handler;
		
		pixels = ContentSettings.tileSize / (running ? times/2 : times);
//		System.out.println(this.times + " - " + this.pixels);
	}

	@Override
	public void onStart() {
		super.onStart();
	}
	
	@Override
	public void onEnd() {
//		System.out.println("end");
		handler.setNoEventActive();
		GridPosition newPos = Global.get("player", Player.class).getOverworldEntity().getData().getPos();
		
		Map map = activeMaps.get(0);
		
		AutoTile at = map.getAutoTiles().getFromPos(newPos);
		if(at != null)
			handler.getParent().toAddAction(at);
		
		
		TileEntity te = map.getTileEntities().getFromPos(newPos);
//		System.out.println(newPos + ": " + te);
		if(te != null) {
			Script script = te.getData().getScripts().getCurrent();
			do {
				ScriptExecutor.execute(script, handler.getParent());
			} while(script.getIndex() != 0);
		}		
		
	}
	
	@Override
	public void update() throws Exception {
		super.update();
		
		for(int i = 0; i < activeMaps.size(); i++) {
			Map map = activeMaps.get(i);
			map.getMovement().move(dir, pixels, i == 0);
		}
	}

}
