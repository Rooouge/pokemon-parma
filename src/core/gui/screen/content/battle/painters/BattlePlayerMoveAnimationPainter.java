package core.gui.screen.content.battle.painters;

import java.awt.Graphics2D;

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
	
	
	public void setMoveAnimations(MoveAnimations animations) {
		this.animations = animations;
		String pkmName = Player.instance().getTeam().get(0).getData().getDisplayName();
		labelRect.setTopText(pkmName + " usa");
		labelRect.setBottomText(Dictionary.instance().get(animations.getMoveName()).toUpperCase());
	}
	
	@Override
	public void paint(Graphics2D g) {
		labelRect.paint(g);
	}
	
}
