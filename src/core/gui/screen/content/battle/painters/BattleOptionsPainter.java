package core.gui.screen.content.battle.painters;

import java.awt.Graphics2D;

import core.gui.interfaces.Painter;
import core.gui.screen.content.battle.Battle;
import core.gui.screen.content.battle.painters.elements.LabelRect;
import core.gui.screen.content.battle.painters.elements.OptionsRect;
import core.obj.entities.player.Player;
import lombok.Getter;

public class BattleOptionsPainter extends Painter<Battle> {

	private final LabelRect labelRect;
	@Getter
	private final OptionsRect optionsRect;
	
	
	public BattleOptionsPainter(Battle parent) {
		super(parent);

		String playerPkmName = Player.instance().getTeam().get(0).getData().getDisplayName();
		labelRect = new LabelRect();
		labelRect.setTopText("Cosa deve fare");
		labelRect.setBottomText(playerPkmName + "?");
		
		optionsRect = new OptionsRect();
	}
	
	
	@Override
	public void paint(Graphics2D g) {
		labelRect.paint(g);
		optionsRect.paint(g);
	}

}
