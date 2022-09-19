package core.gui.screen.content.battle.painters;

import java.awt.Graphics2D;

import core.gui.interfaces.Painter;
import core.gui.screen.content.battle.Battle;
import core.gui.screen.content.battle.painters.elements.LabelRect;

public class BattleCriticalHitPainter extends Painter<Battle> {

	private final LabelRect labelRect;
	
	
	public BattleCriticalHitPainter(Battle parent) {
		super(parent);
		
		labelRect = new LabelRect("Brutto colpo!", "");
	}
	
	
	@Override
	public void paint(Graphics2D g) {
		labelRect.paint(g);
	}
	
}
