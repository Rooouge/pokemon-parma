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
import core.gui.screen.content.battle.keyhandlers.BattleIntroKeyHandler;
import core.gui.screen.content.battle.keyhandlers.BattleOptionsKeyHandler;
import core.gui.screen.content.battle.painters.BattleBackgroundPainter;
import core.gui.screen.content.battle.painters.BattleEnemyPokemonLabelPainter;
import core.gui.screen.content.battle.painters.BattleEnemyPokemonPainter;
import core.gui.screen.content.battle.painters.BattleIntroPainter;
import core.gui.screen.content.battle.painters.BattleOptionsPainter;
import core.gui.screen.content.battle.painters.BattlePlayerPokemonLabelPainter;
import core.gui.screen.content.battle.painters.BattlePlayerPokemonPainter;
import lombok.Getter;

@SuppressWarnings("serial")
@Getter
public class Battle extends Content<Battle> {

	private BattleEvent battle;
	private final Map<GameStates, List<Painter<Battle>>> painterLists;
	
	
	public Battle() {
		super(false, 0);
		
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
		keyHandlers.add(new BattleIntroKeyHandler(this), GameStates.BATTLE_INTRO);
		keyHandlers.add(new BattleOptionsKeyHandler(this), GameStates.BATTLE_OPTIONS);
	}

	@Override
	protected void initPainters() throws IOException {
		BattleBackgroundPainter bbp = new BattleBackgroundPainter(this);
		BattlePlayerPokemonPainter bppp = new BattlePlayerPokemonPainter(this);
		BattlePlayerPokemonLabelPainter bpplp = new BattlePlayerPokemonLabelPainter(this);
		BattleEnemyPokemonPainter bepp = new BattleEnemyPokemonPainter(this);
		BattleEnemyPokemonLabelPainter beplp = new BattleEnemyPokemonLabelPainter(this);
		
		// INTRO
		BattleIntroPainter bip = new BattleIntroPainter(this);
		List<Painter<Battle>> introPainter = new ArrayList<>();
		introPainter.add(bbp);
		introPainter.add(bppp);
		introPainter.add(bpplp);
		introPainter.add(bepp);
		introPainter.add(beplp);
		introPainter.add(bip);
		painterLists.put(GameStates.BATTLE_INTRO, introPainter);
		
		// OPTIONS
		BattleOptionsPainter bop = new BattleOptionsPainter(this);
		List<Painter<Battle>> optionsPainter = new ArrayList<>();
		optionsPainter.add(bbp);
		optionsPainter.add(bppp);
		optionsPainter.add(bpplp);
		optionsPainter.add(bepp);
		optionsPainter.add(beplp);
		optionsPainter.add(bop);
		painterLists.put(GameStates.BATTLE_OPTIONS, optionsPainter);
	}
	
	@Override
	public void reload() {
		// Empty
	}
	
	@Override
	protected void paintComponent(Graphics2D g) {
		if(!currentState.name().startsWith("BATTLE"))
			return;
		
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
