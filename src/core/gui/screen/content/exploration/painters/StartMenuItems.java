package core.gui.screen.content.exploration.painters;

import core.enums.GameStates;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class StartMenuItems {

	private final String label;
	private final GameStates state;
	
	
	public void changeState() {
		GameStates.set(state);
	}
	
}
