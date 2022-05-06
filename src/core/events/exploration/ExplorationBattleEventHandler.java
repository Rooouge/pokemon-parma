package core.events.exploration;

import core.Log;
import core.enums.GameStates;
import core.enums.TileMovements;
import core.events.battle.WildPokemonBattle;
import core.gui.screen.GameScreen;
import core.gui.screen.content.battle.Battle;
import core.gui.screen.painters.ScreenPainter;
import core.obj.maps.wild.WildPokemonEvent;
import jutils.global.Global;
import jutils.threads.Threads;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ExplorationBattleEventHandler {

	public void wildBattle(WildPokemonEvent event, TileMovements tile) {
		Threads.run(() -> {
			Log.log(event.generate().toString());
			
			GameScreen screen = GameScreen.instance();
			ScreenPainter painter = screen.getPainter();
			
			painter.fadeOut(() -> {
				try {
					screen.switchContent(Battle.class);
					Global.get("content", Battle.class).setEvent(new WildPokemonBattle(event, tile));
					painter.fadeIn(GameStates.BATTLE_INTRO);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		});
	}
	
}
