package core.obj.animation;

import core.enums.Directions;
import core.gui.GridPosition;
import core.gui.XYLocation;
import core.obj.actions.SequenceAction;
import core.obj.entities.overworld.OverworldEntityData;
import core.obj.maps.MapEntities;

public class OverworldMovementAction extends SequenceAction {

	private final OverworldEntityData data;
	private final int index;
	private final Directions dir;
	private final int pixels;
	private final MapEntities entities;
	
	
	public OverworldMovementAction(int times, OverworldEntityData data, int index, Directions dir, int pixels, MapEntities entities) {
		super(times);
		this.data = data;
		this.index = index;
		this.dir = dir;
		this.pixels = pixels;
		this.entities = entities;
	}
	
	
	@Override
	public void onStart() {
		super.onStart();

		int dc = dir.getDc();
		int dr = dir.getDr();
		GridPosition pos = data.getPos();
		data.setPos(pos.row - dr, pos.column - dc);
		entities.checkPos(index, dir);
		
//		System.out.println(entities.toString()); //This comments is good with the one in MapEntitiesHandler.update() [row ~ 65]
	}
	
	@Override
	public void update() throws Exception {
		super.update();
		
		int dc = dir.getDc();
		int dr = dir.getDr();
		XYLocation loc = data.getLoc();
		
		loc.x -= (dc * pixels);
		loc.y -= (dr * pixels);
	}
	
}
