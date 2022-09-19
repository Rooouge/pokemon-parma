package core.gui.screen.content.battle.painters;

import java.awt.Graphics2D;

import core.gui.screen.content.battle.Battle;
import core.gui.screen.content.battle.painters.superclasses.BattleMoveAnimationPainter;

public class BattleEnemyMoveAnimationPainter extends BattleMoveAnimationPainter {

	public BattleEnemyMoveAnimationPainter(Battle parent) {
		super(parent, true);
	}

	
	@Override
	public void paint(Graphics2D g) {
		super.paint(g);
	}
}
