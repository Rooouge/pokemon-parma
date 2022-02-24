package core.obj.animation;

import core.files.ImageHandler;
import core.files.TiledImage;
import core.obj.entities.overworld.OverworldEntity;
import core.obj.entities.overworld.OverworldEntityData;

public class OverworldMovementAnimationAction extends Animation {

	protected OverworldEntityData entityData;
	protected TiledImage[] facingSprite;
	protected TiledImage[] walkSprite;
	protected TiledImage[] runSprite;
	protected int[] actives;
	protected int originalTimes;
	protected int originalDelay;
	
	private int switcher;
	
	
	public OverworldMovementAnimationAction(OverworldEntity entity, int times) {
		super(times, 0, STANDARD_DELAY);
		originalTimes = times;
		originalDelay = delay;
		entityData = entity.getData();
		setSprite();
		
		switcher = 0;
	}
	
	
	@Override
	protected void setSprite() {
		if(entityData == null)
			return;
		
		images = entityData.getImages();
		
		facingSprite = new TiledImage[4];
		facingSprite[0] = images[0];
		facingSprite[1] = images[1];
		facingSprite[2] = images[2];
		facingSprite[3] = ImageHandler.horizontalFlip(images[2]);
		
		
		walkSprite = new TiledImage[12];
		walkSprite[0] = images[0];
        walkSprite[1] = images[3];
        walkSprite[2] = images[4];
        walkSprite[3] = images[1];
        walkSprite[4] = images[5];
        walkSprite[5] = images[6];
        walkSprite[6] = images[2];
        walkSprite[7] = images[7];
        walkSprite[8] = images[8];
        walkSprite[9] = ImageHandler.horizontalFlip(images[2]);
        walkSprite[10] = ImageHandler.horizontalFlip(images[7]);
        walkSprite[11] = ImageHandler.horizontalFlip(images[8]);
        
        if(images.length > 9) {
	        runSprite = new TiledImage[12];
	        runSprite[0] = walkSprite[0];
	        runSprite[1] = images[10];
	        runSprite[2] = images[11];
	        runSprite[3] = walkSprite[3];
	        runSprite[4] = images[13];
	        runSprite[5] = images[14];
	        runSprite[6] = walkSprite[6];
	        runSprite[7] = images[16];
	        runSprite[8] = images[17];
	        runSprite[9] = walkSprite[9];
	        runSprite[10] =  ImageHandler.horizontalFlip(images[16]);
	        runSprite[11] =  ImageHandler.horizontalFlip(images[17]);
        }
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		int facing = entityData.getFacing().getIndex()*3;
		switcher = switcher == 0 ? 1 : 0;
		actives = new int[] {-1, facing+1+switcher, facing+1+switcher, facing};
		
		boolean running = entityData.isRunning();
		times = running ? originalTimes / 2 : originalTimes;
		delay = running ? originalDelay / 2 : originalDelay;
		System.out.println("------------------");
	}
	
	@Override
	public void update() throws Exception {
		super.update();
	}
	
	@Override
	public void onEnd() {
		started = false;
	}
	
	@Override
	public TiledImage getImage() {
		if(started) {
			int index = actives[tick];
			System.out.println("Tick " + tick + " = " + index);
			return entityData.isRunning() ? runSprite[index] : walkSprite[index];
		}
		
		return facingSprite[entityData.getFacing().getIndex()];
	}
}
