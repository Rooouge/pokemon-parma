package core.gui.screen.content.battle;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.enums.GameStates;
import core.events.battle.BattleEvent;
import core.gui.interfaces.Painter;
import core.gui.screen.content.Content;
import core.gui.screen.content.battle.painters.BattleBackgroundPainter;
import core.gui.screen.content.battle.painters.BattleEnemyPainter;
import core.gui.screen.content.battle.painters.BattlePlayerPainter;
import lombok.Getter;

@SuppressWarnings("serial")
@Getter
public class Battle extends Content<Battle> {

	private BattleEvent battle;
	private final Map<GameStates, List<Painter<Battle>>> painterLists;
	
	
	public Battle() {
		super(false);
		
		currentState = GameStates.current();
		painterLists = new HashMap<>();
	}
	
	
	public void setEvent(BattleEvent battle) throws IOException {
		this.battle = battle;
		initPainters();
		initKeyHandlers();
	}
	
	@Override
	protected void initKeyHandlers() {
		// Empty
	}

	@Override
	protected void initPainters() throws IOException {
		// BATTLE
		List<Painter<Battle>> battlePainter = new ArrayList<>();
		battlePainter.add(new BattleBackgroundPainter(this));
		battlePainter.add(new BattlePlayerPainter(this));
		battlePainter.add(new BattleEnemyPainter(this));
		
		painterLists.put(GameStates.BATTLE, battlePainter);
	}

	
	@Override
	protected void paintComponent(Graphics2D g) {
		if(!currentState.name().startsWith("BATTLE"))
			return;
		
//		System.out.println(currentState + ": " + painterLists.get(currentState));
		for(Painter<Battle> p : painterLists.get(currentState)) {
			p.paint(g);
		}
	}
	
	@Override
	public void update() throws Exception {
		currentState = GameStates.current();
		
		super.update();
	}
}
