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
import core.gui.screen.content.battle.painters.BattleCriticalHitPainter;
import core.gui.screen.content.battle.painters.BattleDamageDescriptionPainter;
import core.gui.screen.content.battle.painters.BattleEnemyDamageAnimationPainter;
import core.gui.screen.content.battle.painters.BattleEnemyMoveAnimationPainter;
import core.gui.screen.content.battle.painters.BattleEnemyPokemonLabelPainter;
import core.gui.screen.content.battle.painters.BattleEnemyPokemonPainter;
import core.gui.screen.content.battle.painters.BattleEnemyPokemonTerrainPainter;
import core.gui.screen.content.battle.painters.BattleFightOptionsPainter;
import core.gui.screen.content.battle.painters.BattleIntroPainter;
import core.gui.screen.content.battle.painters.BattleOptionsPainter;
import core.gui.screen.content.battle.painters.BattlePlayerDamageAnimationPainter;
import core.gui.screen.content.battle.painters.BattlePlayerMoveAnimationPainter;
import core.gui.screen.content.battle.painters.BattlePlayerPokemonLabelPainter;
import core.gui.screen.content.battle.painters.BattlePlayerPokemonPainter;
import core.gui.screen.content.battle.painters.BattlePlayerPokemonTerrainPainter;
import core.gui.screen.content.battle.painters.BattleSetOrderPainter;
import core.gui.screen.content.battle.painters.superclasses.BattleMoveAnimationPainter;
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
		List<Painter<Battle>> bipPainters = new ArrayList<>();
		bipPainters.add(bbp);
		bipPainters.add(bpptp);
		bipPainters.add(beptp);
		bipPainters.add(bppp);
		bipPainters.add(bepp);
		bipPainters.add(bpplp);
		bipPainters.add(beplp);
		bipPainters.add(bip);
		paintersListsMap.put(GameStates.BATTLE_INTRO, bipPainters);
		
		// OPTIONS
		BattleOptionsPainter bop = new BattleOptionsPainter(this);
		List<Painter<Battle>> bopPainters = new ArrayList<>();
		bopPainters.add(bbp);
		bopPainters.add(bpptp);
		bopPainters.add(beptp);
		bopPainters.add(bppp);
		bopPainters.add(bepp);
		bopPainters.add(bpplp);
		bopPainters.add(beplp);
		bopPainters.add(bop);
		paintersListsMap.put(GameStates.BATTLE_OPTIONS, bopPainters);
		
		// FIGHT_OPTIONS
		BattleFightOptionsPainter bfop = new BattleFightOptionsPainter(this);
		List<Painter<Battle>> bfopPainters = new ArrayList<>();
		bfopPainters.add(bbp);
		bfopPainters.add(bpptp);
		bfopPainters.add(beptp);
		bfopPainters.add(bppp);
		bfopPainters.add(bepp);
		bfopPainters.add(bpplp);
		bfopPainters.add(beplp);
		bfopPainters.add(bfop);
		paintersListsMap.put(GameStates.BATTLE_FIGHT_OPTIONS, bfopPainters);
		
		// PLAYER_MOVE
		BattlePlayerMoveAnimationPainter bpmap = new BattlePlayerMoveAnimationPainter(this);
		List<Painter<Battle>> bpmapPainters = new ArrayList<>();
		bpmapPainters.add(bbp);
		bpmapPainters.add(bpptp);
		bpmapPainters.add(beptp);
		bpmapPainters.add(bppp);
		bpmapPainters.add(bepp);
		bpmapPainters.add(beplp);
		bpmapPainters.add(bpplp);
		bpmapPainters.add(bpmap);
		paintersListsMap.put(GameStates.BATTLE_PLAYER_MOVE, bpmapPainters);
		
		// ENEMY_MOVE
		BattleEnemyMoveAnimationPainter bemap = new BattleEnemyMoveAnimationPainter(this);
		List<Painter<Battle>> bemapPainters = new ArrayList<>();
		bemapPainters.add(bbp);
		bemapPainters.add(bpptp);
		bemapPainters.add(beptp);
		bemapPainters.add(bppp);
		bemapPainters.add(bepp);
		bemapPainters.add(beplp);
		bemapPainters.add(bpplp);
		bemapPainters.add(bemap);
		paintersListsMap.put(GameStates.BATTLE_ENEMY_MOVE, bemapPainters);
		
		// PLAYER_DAMAGE
		BattlePlayerDamageAnimationPainter bpdap = new BattlePlayerDamageAnimationPainter(this, bpplp);
		List<Painter<Battle>> bpdapPainters = new ArrayList<>();
		bpdapPainters.add(bbp);
		bpdapPainters.add(bpptp);
		bpdapPainters.add(beptp);
		bpdapPainters.add(bppp);
		bpdapPainters.add(bepp);
		bpdapPainters.add(beplp);
		bpdapPainters.add(bpplp);
		bpdapPainters.add(bpdap);
		paintersListsMap.put(GameStates.BATTLE_PLAYER_DAMAGE, bpdapPainters);
		
		// ENEMY_DAMAGE
		BattleEnemyDamageAnimationPainter bedap = new BattleEnemyDamageAnimationPainter(this, beplp);
		List<Painter<Battle>> bedapPainters = new ArrayList<>();
		bedapPainters.add(bbp);
		bedapPainters.add(bpptp);
		bedapPainters.add(beptp);
		bedapPainters.add(bppp);
		bedapPainters.add(bepp);
		bedapPainters.add(beplp);
		bedapPainters.add(bpplp);
		bedapPainters.add(bedap);
		paintersListsMap.put(GameStates.BATTLE_ENEMY_DAMAGE, bedapPainters);
		
		// SET_ORDER
		BattleSetOrderPainter bsop = new BattleSetOrderPainter(this);
		List<Painter<Battle>> bsopPainters = new ArrayList<>();
		bsopPainters.add(bbp);
		bsopPainters.add(bpptp);
		bsopPainters.add(beptp);
		bsopPainters.add(bppp);
		bsopPainters.add(bepp);
		bsopPainters.add(bpplp);
		bsopPainters.add(beplp);
		bsopPainters.add(bsop);
		paintersListsMap.put(GameStates.BATTLE_SET_ORDER, bsopPainters);
		
		// CRITICAL_HIT
		BattleCriticalHitPainter bchp = new BattleCriticalHitPainter(this);
		List<Painter<Battle>> bchpPainters = new ArrayList<>();
		bchpPainters.add(bbp);
		bchpPainters.add(bpptp);
		bchpPainters.add(beptp);
		bchpPainters.add(bppp);
		bchpPainters.add(bepp);
		bchpPainters.add(bpplp);
		bchpPainters.add(beplp);
		bchpPainters.add(bchp);
		paintersListsMap.put(GameStates.BATTLE_CRITICAL_HIT, bchpPainters);
		
		// DAMAGE_DESCRIPTION
		BattleDamageDescriptionPainter bddp = new BattleDamageDescriptionPainter(this);
		List<Painter<Battle>> bmdpPainters = new ArrayList<>();
		bmdpPainters.add(bbp);
		bmdpPainters.add(bpptp);
		bmdpPainters.add(beptp);
		bmdpPainters.add(bppp);
		bmdpPainters.add(bepp);
		bmdpPainters.add(beplp);
		bmdpPainters.add(bpplp);
		bmdpPainters.add(bddp);
		paintersListsMap.put(GameStates.BATTLE_DAMAGE_DESCRIPTION, bmdpPainters);
	}
	
	@Override
	public void reload() {
		// Empty
	}
	
	@Override
	protected void paintComponent(Graphics2D g) {
		if(currentState.equals(GameStates.BATTLE_PLAYER_MOVE)) {
			paintMoveAnimation(g, (BattlePlayerMoveAnimationPainter) paintersListsMap.get(currentState).get(7));
			return;
		}
		if(currentState.equals(GameStates.BATTLE_ENEMY_MOVE)) {
			paintMoveAnimation(g, (BattleEnemyMoveAnimationPainter) paintersListsMap.get(currentState).get(7));
			return;
		}
		
		if(!currentState.name().startsWith("BATTLE"))
			return;
		
		for(Painter<Battle> p : paintersListsMap.get(currentState)) {
			p.paint(g);
		}
	}
	
	private void paintMoveAnimation(Graphics2D g, BattleMoveAnimationPainter bmap) {
		List<Painter<Battle>> painters = paintersListsMap.get(currentState);
		MoveAnimations ma = bmap.getAnimations();
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
	}
	
	@Override
	public void update() throws Exception {
		currentState = GameStates.current();
//		System.out.println(currentState.name());
		List<Painter<Battle>> painters = paintersListsMap.get(currentState);
		
		switch (currentState) {
		case BATTLE_PLAYER_MOVE:
			BattlePlayerMoveAnimationPainter bpmap = (BattlePlayerMoveAnimationPainter) painters.get(7);
			MoveAnimations pma = bpmap.getAnimations();
			if(pma != null)
				pma.update();
			break;

		case BATTLE_ENEMY_MOVE:
			BattleEnemyMoveAnimationPainter bemap = (BattleEnemyMoveAnimationPainter) painters.get(7);
			MoveAnimations ema = bemap.getAnimations();
			if(ema != null)
				ema.update();
			break;
		
		case BATTLE_PLAYER_DAMAGE:
			BattlePlayerDamageAnimationPainter bpdap = (BattlePlayerDamageAnimationPainter) painters.get(7);
			DamageAnimation pda = bpdap.getAnimation();
			if(pda != null)
				pda.update();
			break;
			
		case BATTLE_ENEMY_DAMAGE:
			BattleEnemyDamageAnimationPainter bedap = (BattleEnemyDamageAnimationPainter) painters.get(7);
			DamageAnimation eda = bedap.getAnimation();
			if(eda != null)
				eda.update();
			break;		
			
		default:
			break;
		}
		
		super.update();
	}
}
