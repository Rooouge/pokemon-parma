package core.gui.screen.content.exploration.events.exploration;

import java.util.List;

import core.enums.Directions;
import core.gui.screen.content.ContentSettings;
import core.gui.screen.content.exploration.ExplorationKeyPressHandler;
import core.obj.actions.SequenceAction;
import core.obj.maps.Map;

public class EntityMovementAction extends SequenceAction {

	private final List<Map> activeMaps;
	private final Directions dir;
	private final int pixels;
	private final ExplorationKeyPressHandler handler;
	
	
	protected EntityMovementAction(int times, List<Map> activeMaps, Directions dir, ExplorationKeyPressHandler handler, boolean running) {
		super(running ? times/2 : times);
		this.activeMaps = activeMaps;
		this.dir = dir;
		this.handler = handler;
		
		pixels = ContentSettings.tileSize / (running ? times/2 : times);
//		System.out.println(this.times + " - " + this.pixels);
	}

	
	@Override
	public void onEnd() {
		handler.setNoEventActive();
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
