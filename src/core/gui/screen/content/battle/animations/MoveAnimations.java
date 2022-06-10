package core.gui.screen.content.battle.animations;

import java.awt.Graphics2D;
import java.io.IOException;

import core.enums.GameStates;
import core.files.SoundsHandler;
import core.gui.screen.content.battle.Battle;
import core.gui.screen.content.battle.painters.BattleEnemyDamageAnimationPainter;
import lombok.Getter;

@Getter
public class MoveAnimations {

	protected final MoveAnimation background;
	protected final MoveAnimation foreground;
	protected final String moveName;
	
	
	public MoveAnimations(Battle parent, String moveName) throws IOException {
		this.moveName = moveName;
		moveName = moveName.toLowerCase().replace(" ", "_").replace(".", "_");
		background = new MoveAnimation(parent, moveName + "_bg");
		foreground = new MoveAnimation(parent, moveName)
				.withClip(SoundsHandler.get("moves/" + moveName))
				.onEnd(() -> {
					BattleEnemyDamageAnimationPainter bedap = (BattleEnemyDamageAnimationPainter) parent.getPaintersListsMap().get(GameStates.BATTLE_ENEMY_DAMAGE).get(5);
					bedap.setDamageAnimation(parent.getBattle().getMap());
				});
	}
	
	
	public void update() {
		background.update();
//		System.out.println("[BG] Flag: " + background.flag + " (" + background.getTick() + " | " + background.getAnimation().length + ")");
		foreground.update();
//		System.out.println("[FG] Flag: " + foreground.flag + " (" + foreground.getTick() + " | " + foreground.getAnimation().length + ")");
	}
	
	public void paintBackground(Graphics2D g) {
		background.paint(g);
	}
	
	public void paintForeground(Graphics2D g) {
		foreground.paint(g);
	}
	
}
