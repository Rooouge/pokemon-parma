package core.gui.screen.content.battle.painters.superclasses;

import java.awt.Graphics2D;
import java.io.IOException;

import core.gui.interfaces.Painter;
import core.gui.screen.content.battle.Battle;
import core.gui.screen.content.battle.animations.MoveAnimations;
import core.gui.screen.content.battle.painters.elements.LabelRect;
import core.obj.entities.player.Player;
import core.obj.pokemon.moves.Dictionary;
import lombok.Getter;

@Getter
public class BattleMoveAnimationPainter extends Painter<Battle> {

	private final LabelRect labelRect;
	private final boolean enemy;
	private MoveAnimations animations;
	
	
	protected BattleMoveAnimationPainter(Battle parent, boolean enemy) {
		super(parent);
		labelRect = new LabelRect();
		this.enemy = enemy;
	}
	
	
	public LabelRect buildLabel(String moveName) {
		String pkmName = enemy ? parent.getBattle().getEntityPokemon().getData().getDisplayName() : Player.instance().getTeam().get(0).getData().getDisplayName();
		labelRect.setTopText(pkmName + " usa");
		
		moveName = Dictionary.instance().get(moveName).toUpperCase();
		if(!moveName.endsWith("!"))
			moveName += "!";
		labelRect.setBottomText(moveName);
		
		return labelRect;
	}
	
	public void readMoveAnimations(String moveName) throws IOException {
		animations = new MoveAnimations(parent, moveName, enemy);
	}
	
	@Override
	public void paint(Graphics2D g) {
		labelRect.paint(g);
//		System.out.println(animations);
//		if(animations != null)
//			System.out.println(animations.getMoveName());
	}
}
