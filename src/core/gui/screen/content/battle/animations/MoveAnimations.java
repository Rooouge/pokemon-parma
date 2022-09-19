package core.gui.screen.content.battle.animations;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import core.enums.GameStates;
import core.files.ImageHandler;
import core.files.SoundsHandler;
import core.gui.screen.content.battle.Battle;
import core.gui.screen.content.battle.painters.BattleEnemyDamageAnimationPainter;
import core.gui.screen.content.battle.painters.BattlePlayerDamageAnimationPainter;
import lombok.Getter;

public class MoveAnimations {

	protected final List<MoveAnimation> layers;
	@Getter
	protected final String moveName;
	
	
	public MoveAnimations(Battle parent, String moveName, boolean enemy) throws IOException {
		this.moveName = moveName;
		moveName = moveName.toLowerCase().replace(" ", "_").replace(".", "_");
		
		BufferedImage[][] animations = ImageHandler.getAnimations(moveName, "battle/animations", enemy);
		layers = new ArrayList<>();
		
		for(int i = 0; i < animations.length; i++) {
			MoveAnimation ma;
			String resName = moveName + "_" + i;
			
			if(i == 0) {
				ma = new MoveAnimation(parent, resName, animations[i], enemy)
					.withClip(SoundsHandler.get("moves/" + moveName))
					.onEnd(() -> {
						if(enemy) {
							BattlePlayerDamageAnimationPainter bpdap = (BattlePlayerDamageAnimationPainter) parent.getPaintersListsMap().get(GameStates.BATTLE_PLAYER_DAMAGE).get(7);
							bpdap.setDamageAnimation();
						} else {
							BattleEnemyDamageAnimationPainter bedap = (BattleEnemyDamageAnimationPainter) parent.getPaintersListsMap().get(GameStates.BATTLE_ENEMY_DAMAGE).get(7);
							bedap.setDamageAnimation();
						}
					});
			} else
				ma = new MoveAnimation(parent, resName, animations[i], enemy);
			
			layers.add(ma);
		}
	}
	
	
	public void update() {
		for(MoveAnimation ma : layers) {
			ma.update();
		}
	}
	
	public void paintLayer(Graphics2D g, int index) {
		if(index < layers.size())
			layers.get(index).paint(g);
	}
	
}
