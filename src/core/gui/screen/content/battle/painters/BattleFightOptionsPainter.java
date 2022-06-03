package core.gui.screen.content.battle.painters;

import java.awt.Graphics2D;

import core.gui.interfaces.Painter;
import core.gui.screen.content.battle.Battle;
import core.gui.screen.content.battle.painters.elements.FightOptionsRect;
import core.obj.entities.player.Player;
import lombok.Getter;

public class BattleFightOptionsPainter extends Painter<Battle> {

	@Getter
	private final FightOptionsRect fightOptionsRect;
	
	
	public BattleFightOptionsPainter(Battle parent) {
		super(parent);
		
		fightOptionsRect = new FightOptionsRect(Player.instance().getTeam().get(0));
	}
	
	
	@Override
	public void paint(Graphics2D g) {
		fightOptionsRect.paint(g);
	}

}
