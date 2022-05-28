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
	/*
	private boolean inTransition;
	private Date start;
	*/
	public void wildBattle(WildPokemonEvent event, TileMovements tile) throws IOException {
		
		Exploration exp = Global.get("content", Exploration.class);
		BattleTransition transition = BattleTransitions.getTransition(0);
		transition.setEvent(event, tile);
		exp.setAnimation(transition);
		/*
		inTransition = true;
		start = new Date();
		
		Threads.run(() -> {
			Log.log(event.generate().toString());
			try {
				Thread.sleep(2500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			inTransition = false;
		});
		
		Threads.run(() -> {
			while(inTransition) { System.out.println(new Date().getTime() - start.getTime()); }
			
			Exploration exp = Global.get("content", Exploration.class);
			GameScreen screen = GameScreen.instance();
			ScreenPainter painter = screen.getPainter();
			
			painter.fadeOut(() -> {
				try {
					screen.switchContent(Battle.class);
					Global.get("content", Battle.class).setEvent(new WildPokemonBattle(event, tile));
					exp.setForceStop(false);
					painter.fadeIn(GameStates.BATTLE_INTRO);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		});
		*/
	}
	
}
