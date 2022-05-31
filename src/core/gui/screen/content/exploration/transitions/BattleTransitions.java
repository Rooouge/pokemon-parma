package core.gui.screen.content.exploration.transitions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import core.Log;
import core.gui.screen.content.exploration.Exploration;

@SuppressWarnings("serial")
public class BattleTransitions extends ArrayList<BattleTransition>{

	private static BattleTransitions instance;
	
	
	public static void init(Exploration parent) throws IOException {
		instance = new BattleTransitions();
		instance.add(new BattleTransition(parent, "transition-0"));
		instance.add(new BattleTransition(parent, "transition-1"));
	}
	
	
	public static BattleTransition getTransition(int index) {
		BattleTransition t = instance.get(index);
		Log.log("Loaded BattleTransition '" + t.getResName() + "'");
		return t;
	}
	
	public static BattleTransition random() {
		BattleTransition t = instance.get(new Random().nextInt(instance.size()));
		Log.log("Loaded BattleTransition '" + t.getResName() + "'");
		return t;
	}
}
