package core.gui.screen.content.battle.painters;

import java.awt.Graphics2D;

import core.events.battle.WildPokemonBattle;
import core.gui.interfaces.Painter;
import core.gui.screen.content.battle.Battle;
import core.gui.screen.content.battle.painters.elements.LabelRect;

public class BattleIntroPainter extends Painter<Battle> {

	private final LabelRect labelRect;
	
	
	public BattleIntroPainter(Battle parent) {
		super(parent);
		
		String enemyName = ((WildPokemonBattle) parent.getBattle()).getEntityPokemon().getData().getDisplayName();
		labelRect = new LabelRect();
		labelRect.setTopText("Appare " + enemyName + " selvatico!");
	}
	
	
	@Override
	public void paint(Graphics2D g) {
		labelRect.paint(g);
	}

}
