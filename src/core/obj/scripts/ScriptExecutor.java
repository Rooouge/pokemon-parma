package core.obj.scripts;

import core.enums.GameStates;
import core.gui.screen.content.exploration.ExplorationEntityScriptKeyPressHandler;
import core.gui.screen.content.exploration.painters.ExplorationEntityScriptPainter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ScriptExecutor {

	
	public boolean execute(Script script, ExplorationEntityScriptKeyPressHandler handler) {
		ScriptAction action = script.getNextAction();
		
		if(action != null) {			
			if(!action.isStarted()) {
				ExplorationEntityScriptPainter.class.cast(handler.getExploration().getPainters().get(GameStates.EXPLORATION_ENTITY_SCRIPT)).setAction(action);
				action.onStart();
			}
			
			if(action.getDelayTick() < action.getDelay()) {
				action.addDelayTick();
				return false;
			}
			
			script.execute();
			action.setStarted(false);
			
			return script.getNextAction().isNeedPress();
		}
		
		return true;
	}
}
