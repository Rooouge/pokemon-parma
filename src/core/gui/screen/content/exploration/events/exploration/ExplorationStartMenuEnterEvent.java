package core.gui.screen.content.exploration.events.exploration;

import java.util.function.Supplier;

import core.enums.GameStates;
import core.events.exploration.ExplorationKeyEvent;
import core.gui.screen.content.exploration.painters.StartMenuItems;

public class ExplorationStartMenuEnterEvent extends ExplorationKeyEvent {

	private final Supplier<StartMenuItems> ref;
	
	
	public ExplorationStartMenuEnterEvent(int keyCode, Supplier<StartMenuItems> ref) {
		super(keyCode);
		this.ref = ref;
		state = GameStates.EXPLORATION_START_MENU;
	}

	@Override
	public void execute() {
		ref.get().changeState();
	}

}
