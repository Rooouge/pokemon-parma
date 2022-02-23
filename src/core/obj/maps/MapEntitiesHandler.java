package core.obj.maps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import core.enums.Directions;
import core.enums.MovementsDescriptor;
import core.gui.screen.content.ContentSettings;
import core.gui.screen.content.Exploration;
import core.obj.actions.SequenceAction;
import core.obj.animation.OverworldMovementAction;
import core.obj.entities.overworld.OverworldEntity;
import core.obj.entities.overworld.OverworldEntityData;
import core.obj.entities.overworld.PlayerOverworldEntity;

public class MapEntitiesHandler extends SequenceAction {

	private final MapEntities entities;
	private final int pixels;
	private final Exploration parent;
	private final long delay;
	private final Random random;
	private final int chance;
	private Date start;
	
	
	public MapEntitiesHandler(int times, MapEntities entities, int chance, Exploration parent) {
		super(times);
		this.parent = parent;
		this.entities = entities;
		this.chance = chance;
		
		pixels = ContentSettings.tileSize / times;
		delay = 1000;
		random = new Random();
	}
	
	
	@Override
	public void update() throws Exception {
		super.update();
		
		if(start == null)
			start = new Date();
		
		if(new Date().getTime() - start.getTime() >= delay) {
			for(int i = 0; i < entities.size(); i++) {
				OverworldEntity entity = entities.get(i);
				OverworldEntityData data = entity.getData();
				MovementsDescriptor md = data.getMovementsDescriptor();
				
				if(!(entity instanceof PlayerOverworldEntity) && !md.equals(MovementsDescriptor.STILL)) {
					if((md.equals(MovementsDescriptor.ALL) || md.name().startsWith("FACING")) && random.nextInt(chance) == 0) {
						// Facing
						data.setFacing(tryFacign(md));
						break;
					} else if((md.equals(MovementsDescriptor.ALL) || md.name().startsWith("MOVING")) && random.nextInt(chance*3) == 0) {
						// Movement
						Directions dir = tryMoving(md);
						if(MapUtils.canMove(entity, parent, dir)) {
//							System.out.println("---------------------");
//							System.out.println("Animation for " + entity.getData().getName() + " " + entity.getData().getPos()); //This comments are good with the one in OverworldMovementAction.onStart() [row ~ 38]
							parent.addAction(entity.getAnimationAction());
							parent.addAction(new OverworldMovementAction(times, entity.getData(), i, dir.getOpposite(), pixels, entities)); //parent.getActiveMap().getEntities()
							break;
						}
					}
					
				}
			}
			
			start = null;
		}
	}
	
	public void addMovementAction(OverworldEntity entity, Directions dir) {
		entity.getData().setFacing(dir);
		parent.addAction(entity.getAnimationAction());
		parent.addAction(new OverworldMovementAction(times, entity.getData(), entities.indexOf(entity), dir.getOpposite(), pixels, entities)); //parent.getActiveMap().getEntities()	
	}
	
	
	private Directions tryFacign(MovementsDescriptor md) {
		List<Directions> possibilities = new ArrayList<>();
		
		switch (md) {
		case FACING:
			possibilities.addAll(Arrays.asList(Directions.values()));
			break;
		case FACING_HOR:
			possibilities.add(Directions.LEFT);
			possibilities.add(Directions.RIGHT);
		case FACING_VER:
			possibilities.add(Directions.DOWN);
			possibilities.add(Directions.UP);
		default:
			break;
		}
		
		return possibilities.get(random.nextInt(possibilities.size()));
	}
	
	private Directions tryMoving(MovementsDescriptor md) {
		List<Directions> possibilities = new ArrayList<>();
		
		switch (md) {
		case MOVING:
			possibilities.addAll(Arrays.asList(Directions.values()));
			break;
		case MOVING_HOR:
			possibilities.add(Directions.LEFT);
			possibilities.add(Directions.RIGHT);
		case MOVING_VER:
			possibilities.add(Directions.DOWN);
			possibilities.add(Directions.UP);
		default:
			break;
		}
		
		return possibilities.get(random.nextInt(possibilities.size()));
	}
}
