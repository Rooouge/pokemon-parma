package core.obj.scripts;

import core.enums.GameStates;
import core.gui.screen.content.exploration.Exploration;
import core.gui.screen.content.exploration.keypresshandlers.ExplorationEntityScriptKeyPressHandler;
import core.gui.screen.content.exploration.painters.ExplorationEntityScriptPainter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ScriptExecutor {

	
	public boolean execute(Script script, Exploration exploration) {
		ScriptAction action = script.getNextAction();
		
		if(action != null) {			
			if(!action.isStarted()) {
				ExplorationEntityScriptPainter.class.cast(exploration.getPainters().get(GameStates.EXPLORATION_ENTITY_SCRIPT)).setAction(action);
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
	
	public boolean execute(Script script, ExplorationEntityScriptKeyPressHandler handler) {
		return execute(script, handler.getParent());
	}
}
