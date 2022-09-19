package core.gui.screen.content.battle.animations;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.sound.sampled.Clip;

import core.enums.GameStates;
import core.files.SoundsHandler;
import core.gui.screen.content.battle.Battle;
import core.gui.screen.painters.animations.GUIAnimation;

public class MoveAnimation extends GUIAnimation<Battle> {

	protected int flag;
	protected Clip clip;
	protected Runnable end;
	protected boolean enemy;
	
	
	public MoveAnimation(Battle parent, String resName) throws IOException {
		super(parent, resName, "battle/animations", true);
		flag = 0;
	}
	
	public MoveAnimation(Battle parent, String resName, BufferedImage[] animation, boolean enemy) throws IOException {
		super(parent, resName, animation, true);
		
//		System.out.println("--- SAVE " + resName + " ---");
//		
//		BufferedImage img = new BufferedImage(animation[0].getWidth(), animation.length*animation[0].getHeight(), BufferedImage.TYPE_INT_ARGB);
//		System.out.println(img.getWidth() + "x" + img.getHeight());
//		Graphics g = img.getGraphics();
//		
//		for(int i = 0; i < animation.length; i++) {
//			BufferedImage bi = animation[i];
//			g.drawImage(bi, 0, i*bi.getHeight(), null);
//		}
//		
//		ImageIO.write(img, "png", new File("C:\\Users\\Andrea Rossi\\Desktop\\test\\" + resName + ".png"));
		
		flag = 0;
		this.enemy = enemy;
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
		
		GameStates.set(enemy ? GameStates.BATTLE_PLAYER_DAMAGE : GameStates.BATTLE_ENEMY_DAMAGE);
	}

	@Override
	public void paint(Graphics2D g) {
		g.drawImage(animation[flag < animation.length ? flag : animation.length-1], 0, 0, null);
	}

}
