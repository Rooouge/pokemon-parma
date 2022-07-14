package core.gui.screen.content.battle.painters;

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
public class BattlePlayerMoveAnimationPainter extends Painter<Battle> {

	
	private final LabelRect labelRect;
	private MoveAnimations animations;
	
	
	public BattlePlayerMoveAnimationPainter(Battle parent) {
		super(parent);
		labelRect = new LabelRect();
	}
	
	
	public LabelRect buildLabel(String moveName) {
		String pkmName = Player.instance().getTeam().get(0).getData().getDisplayName();
		labelRect.setTopText(pkmName + " usa");
		
		moveName = Dictionary.instance().get(moveName).toUpperCase();
		if(!moveName.endsWith("!"))
			moveName += "!";
		labelRect.setBottomText(moveName);
		
		return labelRect;
	}
	
	public void readMoveAnimations(String moveName) throws IOException {
		System.out.println(moveName);
		animations = new MoveAnimations(parent, moveName);
	}
	
	@Override
	public void paint(Graphics2D g) {
		labelRect.paint(g);
//		System.out.println(animations);
//		if(animations != null)
//			System.out.println(animations.getMoveName());
	}
	
}
