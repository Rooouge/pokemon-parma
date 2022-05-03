package core.gui.screen.content.battle.painters;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.io.IOException;

import core.events.battle.WildPokemonBattle;
import core.gui.interfaces.Painter;
import core.gui.screen.content.battle.Battle;

public class BattleBackgroundPainter extends Painter<Battle> {

	private final GradientPaint gradient;
	
	
	public BattleBackgroundPainter(Battle parent) throws IOException {
		super(parent);
		
		WildPokemonBattle wildBattle = (WildPokemonBattle) parent.getBattle();
		
		switch (wildBattle.getTile()) {
		case WP_GRASS:
			gradient = new GradientPaint(0, 0, new Color(128,224,128), 0, parent.getHeight()/4, new Color(200,255,200));
			break;
		default:
			gradient = new GradientPaint(0, 0, new Color(188,255,255), 0, parent.getHeight()/4, Color.white);
			break;
		}
	}
	
	
	
	
	@Override
	public void paint(Graphics2D g) {
		g.setPaint(gradient);
		g.fillRect(0, 0, parent.getWidth(), parent.getHeight());
	}

}
