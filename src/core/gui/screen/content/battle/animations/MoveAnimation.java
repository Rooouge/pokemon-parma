package core.gui.screen.content.battle.animations;

import java.awt.Graphics2D;
import java.io.IOException;

import javax.sound.sampled.Clip;

import core.enums.GameStates;
import core.files.SoundsHandler;
import core.gui.screen.content.battle.Battle;
import core.gui.screen.painters.animations.GUIAnimation;

class MoveAnimation extends GUIAnimation<Battle> {

	protected int flag;
	protected Clip clip;
	protected Runnable end;
	
	
	public MoveAnimation(Battle parent, String resName) throws IOException {
		super(parent, resName, "battle/animations", 150, true);
		flag = 0;
	}
	
	public MoveAnimation withClip(Clip clip) {
		this.clip = clip;
		return this;
	}
	
	public MoveAnimation onEnd(Runnable end) {
		this.end = end;
		return this;
	}
	
	
	@Override
	public void onStart() {
		if(clip != null)
			SoundsHandler.playSound(clip);
	}

	@Override
	public void tick() {
		if(tick % (duration / animation.length) == 0) {
			flag++;
		}
	}

	@Override
	public void onEnd() {
		if(end != null)
			end.run();
		
		GameStates.set(GameStates.BATTLE_ENEMY_DAMAGE);
	}

	@Override
	public void paint(Graphics2D g) {
		g.drawImage(animation[flag < animation.length ? flag : animation.length-1], 0, 0, null);
	}

}
