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
import core.gui.screen.content.battle.animations.DamageAnimation;
import core.gui.screen.content.battle.animations.MoveAnimations;
import core.gui.screen.content.battle.keyhandlers.BattleFightOptionsKeyHandler;
import core.gui.screen.content.battle.keyhandlers.BattleIntroKeyHandler;
import core.gui.screen.content.battle.keyhandlers.BattleOptionsKeyHandler;
import core.gui.screen.content.battle.painters.BattleBackgroundPainter;
import core.gui.screen.content.battle.painters.BattleEnemyDamageAnimationPainter;
import core.gui.screen.content.battle.painters.BattleEnemyPokemonLabelPainter;
import core.gui.screen.content.battle.painters.BattleEnemyPokemonPainter;
import core.gui.screen.content.battle.painters.BattleEnemyPokemonTerrainPainter;
import core.gui.screen.content.battle.painters.BattleFightOptionsPainter;
import core.gui.screen.content.battle.painters.BattleIntroPainter;
import core.gui.screen.content.battle.painters.BattleOptionsPainter;
import core.gui.screen.content.battle.painters.BattlePlayerMoveAnimationPainter;
import core.gui.screen.content.battle.painters.BattlePlayerPokemonLabelPainter;
import core.gui.screen.content.battle.painters.BattlePlayerPokemonPainter;
import core.gui.screen.content.battle.painters.BattlePlayerPokemonTerrainPainter;
import core.gui.screen.content.battle.painters.BattleSetOrderPainter;
import lombok.Getter;

@SuppressWarnings("serial")
@Getter
public class Battle extends Content<Battle> {

	private final Map<GameStates, List<Painter<Battle>>> paintersListsMap;
	private BattleEvent battle;
	
	
	public Battle() {
		super(false, 0);
		
		currentState = GameStates.current();
		paintersListsMap = new HashMap<>();
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
		keyHandlers.add(new BattleFightOptionsKeyHandler(this), GameStates.BATTLE_FIGHT_OPTIONS);
	}

	@Override
	protected void initPainters() throws IOException {
		BattleBackgroundPainter bbp = new BattleBackgroundPainter(this);
		BattlePlayerPokemonTerrainPainter bpptp = new BattlePlayerPokemonTerrainPainter(this);
		BattlePlayerPokemonPainter bppp = new BattlePlayerPokemonPainter(this, bpptp.getTerrainImage(), bpptp.getTerrainPoint());
		BattlePlayerPokemonLabelPainter bpplp = new BattlePlayerPokemonLabelPainter(this);
		BattleEnemyPokemonTerrainPainter beptp = new BattleEnemyPokemonTerrainPainter(this);
		BattleEnemyPokemonPainter bepp = new BattleEnemyPokemonPainter(this, beptp.getTerrainImage(), beptp.getTerrainPoint());
		BattleEnemyPokemonLabelPainter beplp = new BattleEnemyPokemonLabelPainter(this);
		
		// INTRO
		BattleIntroPainter bip = new BattleIntroPainter(this);
		List<Painter<Battle>> introPainters = new ArrayList<>();
		introPainters.add(bbp);
		introPainters.add(bpptp);
		introPainters.add(beptp);
		introPainters.add(bppp);
		introPainters.add(bepp);
		introPainters.add(bpplp);
		introPainters.add(beplp);
		introPainters.add(bip);
		paintersListsMap.put(GameStates.BATTLE_INTRO, introPainters);
		
		// OPTIONS
		BattleOptionsPainter bop = new BattleOptionsPainter(this);
		List<Painter<Battle>> optionsPainters = new ArrayList<>();
		optionsPainters.add(bbp);
		optionsPainters.add(bpptp);
		optionsPainters.add(beptp);
		optionsPainters.add(bppp);
		optionsPainters.add(bepp);
		optionsPainters.add(bpplp);
		optionsPainters.add(beplp);
		optionsPainters.add(bop);
		paintersListsMap.put(GameStates.BATTLE_OPTIONS, optionsPainters);
		
		// FIGHT_OPTIONS
		BattleFightOptionsPainter bfop = new BattleFightOptionsPainter(this);
		List<Painter<Battle>> fightoptionsPainters = new ArrayList<>();
		fightoptionsPainters.add(bbp);
		fightoptionsPainters.add(bpptp);
		fightoptionsPainters.add(beptp);
		fightoptionsPainters.add(bppp);
		fightoptionsPainters.add(bepp);
		fightoptionsPainters.add(bpplp);
		fightoptionsPainters.add(beplp);
		fightoptionsPainters.add(bfop);
		paintersListsMap.put(GameStates.BATTLE_FIGHT_OPTIONS, fightoptionsPainters);
		
		// PLAYER_MOVE
		BattlePlayerMoveAnimationPainter bpmap = new BattlePlayerMoveAnimationPainter(this);
		List<Painter<Battle>> playerMovePainters = new ArrayList<>();
		playerMovePainters.add(bbp);
		playerMovePainters.add(bpptp);
		playerMovePainters.add(beptp);
		playerMovePainters.add(bppp);
		playerMovePainters.add(bepp);
		playerMovePainters.add(beplp);
		playerMovePainters.add(bpplp);
		playerMovePainters.add(bpmap);
		paintersListsMap.put(GameStates.BATTLE_PLAYER_MOVE, playerMovePainters);
		
		// ENEMY_MOVE
		
		// SET_ORDER
		BattleSetOrderPainter bsop = new BattleSetOrderPainter(this);
		List<Painter<Battle>> setOrderPainters = new ArrayList<>();
		setOrderPainters.add(bbp);
		setOrderPainters.add(bpptp);
		setOrderPainters.add(beptp);
		setOrderPainters.add(bppp);
		setOrderPainters.add(bepp);
		setOrderPainters.add(bpplp);
		setOrderPainters.add(beplp);
		setOrderPainters.add(bsop);
		paintersListsMap.put(GameStates.BATTLE_SET_ORDER, setOrderPainters);
		
		// PLAYER_DAMAGE
		
		// ENEMY_DAMAGE
		BattleEnemyDamageAnimationPainter bedap = new BattleEnemyDamageAnimationPainter(this, beplp);
		List<Painter<Battle>> enemyDamagePainters = new ArrayList<>();
		enemyDamagePainters.add(bbp);
		enemyDamagePainters.add(bpptp);
		enemyDamagePainters.add(beptp);
		enemyDamagePainters.add(bppp);
		enemyDamagePainters.add(bepp);
		enemyDamagePainters.add(beplp);
		enemyDamagePainters.add(bpplp);
		enemyDamagePainters.add(bedap);
		paintersListsMap.put(GameStates.BATTLE_ENEMY_DAMAGE, enemyDamagePainters);
	}
	
	@Override
	public void reload() {
		// Empty
	}
	
	@Override
	protected void paintComponent(Graphics2D g) {
		if(currentState.equals(GameStates.BATTLE_PLAYER_MOVE)) {
			List<Painter<Battle>> painters = paintersListsMap.get(currentState);
			BattlePlayerMoveAnimationPainter bpmap = (BattlePlayerMoveAnimationPainter) painters.get(7);
			MoveAnimations ma = bpmap.getAnimations();
			if(ma != null) {
				painters.get(0).paint(g);
				ma.paintLayer(g, 0);
				painters.get(1).paint(g);
				painters.get(2).paint(g);
				ma.paintLayer(g, 1);
				painters.get(3).paint(g);
				painters.get(4).paint(g);
				ma.paintLayer(g, 2);
				painters.get(5).paint(g);
				painters.get(6).paint(g);
				painters.get(7).paint(g);
			}
			return;
		}
		
		
		if(!currentState.name().startsWith("BATTLE"))
			return;
		
		if(currentState.equals(GameStates.BATTLE_ENEMY_MOVE))
			return;
		
		for(Painter<Battle> p : paintersListsMap.get(currentState)) {
			p.paint(g);
		}
	}
	
	@Override
	public void update() throws Exception {
		currentState = GameStates.current();
//		System.out.println(currentState.name()w);
		List<Painter<Battle>> painters = paintersListsMap.get(currentState);
		
		switch (currentState) {
		case BATTLE_PLAYER_MOVE:
			
			BattlePlayerMoveAnimationPainter bpmap = (BattlePlayerMoveAnimationPainter) painters.get(7);
			MoveAnimations ma = bpmap.getAnimations();
			if(ma != null)
				ma.update();
			break;

		case BATTLE_ENEMY_DAMAGE:
			
			BattleEnemyDamageAnimationPainter bedap = (BattleEnemyDamageAnimationPainter) painters.get(7);
			DamageAnimation da = bedap.getAnimation();
			if(da != null)
				da.update();
			break;
			
		default:
			break;
		}
		
		super.update();
	}
}
