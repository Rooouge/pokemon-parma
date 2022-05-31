package core.events.exploration;

import java.io.IOException;

import core.enums.TileMovements;
import core.gui.screen.content.exploration.Exploration;
import core.gui.screen.content.exploration.transitions.BattleTransition;
import core.gui.screen.content.exploration.transitions.BattleTransitions;
import core.obj.maps.wild.WildPokemonEvent;
import jutils.global.Global;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ExplorationBattleEventHandler {
	
	public void wildBattle(WildPokemonEvent event, TileMovements tile) throws IOException {
		Exploration exp = Global.get("content", Exploration.class);
		BattleTransition transition = BattleTransitions.random();
		transition.setEvent(event, tile);
		exp.setAnimation(transition);
	}
	
}
