package core.gui.screen.content.exploration.transitions;

import java.io.IOException;
import java.util.ArrayList;

import core.gui.screen.content.exploration.Exploration;

@SuppressWarnings("serial")
public class BattleTransitions extends ArrayList<BattleTransition>{

	private static BattleTransitions instance;
	
	
	public static void init(Exploration parent) throws IOException {
		instance = new BattleTransitions();
		instance.add(new BattleTransition(parent, "transition-0"));
	}
	
	
	public static BattleTransition getTransition(int index) {
		return instance.get(index);
	}
}
