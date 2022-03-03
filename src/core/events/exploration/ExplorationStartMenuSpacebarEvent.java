package core.events.exploration;

import java.util.function.Supplier;

import core.enums.GameStates;
import core.events.ChangeContentKeyEvent;
import core.gui.screen.content.Pokedex;
import core.gui.screen.content.exploration.painters.StartMenuItems;

public class ExplorationStartMenuSpacebarEvent extends ChangeContentKeyEvent {

	private final Supplier<StartMenuItems> ref;
	
	
	public ExplorationStartMenuSpacebarEvent(int keyCode, Supplier<StartMenuItems> ref) {
		super(keyCode, GameStates.EXPLORATION_START_MENU, ref.get().getState(), Pokedex.class);
		this.ref = ref;
	}
	

	@Override
	public void execute() {
		ref.get().changeState();
		super.execute();
	}

}
