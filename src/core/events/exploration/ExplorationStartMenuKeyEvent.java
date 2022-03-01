package core.events.exploration;

import java.util.function.Function;

import javax.sound.sampled.Clip;

import core.enums.GameStates;
import core.events.GlobalKeyEvent;
import core.files.SoundsHandler;
import core.gui.interfaces.OnKeyPressHandler;

public class ExplorationStartMenuKeyEvent extends GlobalKeyEvent {

	
	private final GameStates toSet;
	private final Clip clip;
	private final Function<GameStates, OnKeyPressHandler> ref;
	
	
	public ExplorationStartMenuKeyEvent(int keyCode, GameStates state, Function<GameStates, OnKeyPressHandler> ref) {
		super(keyCode);
		this.state = state;
		this.ref = ref;
		
		if(state.equals(GameStates.EXPLORATION))
			toSet = GameStates.EXPLORATION_START_MENU;
		else
			toSet = GameStates.EXPLORATION;
		
		clip = SoundsHandler.get(SoundsHandler.SPACE_PRESS);
	}

	@Override
	public void execute() {
		ref.apply(toSet).onLoad();
		GameStates.set(toSet);
		SoundsHandler.playSound(clip);
	}
}
