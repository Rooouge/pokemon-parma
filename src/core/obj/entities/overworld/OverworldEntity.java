package core.obj.entities.overworld;

import java.io.IOException;

import org.dom4j.Node;

import core.files.TiledImage;
import core.obj.animation.Animation;
import core.obj.animation.OverworldMovementAnimationAction;
import core.obj.entities.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OverworldEntity extends Entity<OverworldEntityData> {

	protected final OverworldMovementAnimationAction animationAction;
	
	
	public OverworldEntity(Node root) throws IOException {
		data = new OverworldEntityData(root);
		animationAction = new OverworldMovementAnimationAction(this, Animation.STANDARD_TIMES);
	}
	
	protected OverworldEntity(String name, boolean hasRunning) throws IOException {
		data = new OverworldEntityData(name);
		animationAction = new OverworldMovementAnimationAction(this, Animation.STANDARD_TIMES);
	}
	
	
	/*
	 * Abstracts
	 */
	
	public TiledImage getImageToDraw() {
		return animationAction.getImage();
	}
}
