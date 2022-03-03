package core.events.exploration;

import java.util.function.Supplier;

import core.enums.GameStates;
import core.events.GlobalKeyEvent;
import core.gui.screen.GameScreen;
import core.gui.screen.content.Pokedex;
import core.gui.screen.content.exploration.painters.StartMenuItems;

public class ExplorationStartMenuSpacebarEvent extends GlobalKeyEvent {

	private final Supplier<StartMenuItems> ref;
	
	
	public ExplorationStartMenuSpacebarEvent(int keyCode, Supplier<StartMenuItems> ref) {
		super(keyCode);
		this.ref = ref;
		state = GameStates.EXPLORATION_START_MENU;
	}

	@Override
	public void execute() {
		ref.get().changeState();
		
		try {
			GameScreen.instance().switchContent(Pokedex.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
