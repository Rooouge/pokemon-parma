package core.gui.screen.content.battle.painters;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.Random;

import core.enums.GameStates;
import core.enums.Stats;
import core.events.battle.BattleEvent;
import core.events.battle.BattleMap;
import core.events.battle.WildPokemonBattle;
import core.gui.interfaces.Painter;
import core.gui.screen.content.battle.Battle;
import core.gui.screen.content.battle.painters.elements.LabelRect;
import core.obj.entities.player.Player;
import core.obj.pokemon.battle.BattlePokemon;
import core.obj.pokemon.entity.EntityPokemonStats;
import core.obj.pokemon.moves.Move;

public class BattleSetOrderPainter extends Painter<Battle> {

	private final LabelRect labelRect;
	private final BattlePlayerMoveAnimationPainter bpmap;
	
	/**
	 * Serve unicamente per disegnare qualcosa mentre
	 * recupero le risorse negli stati successivi
	 */
	public BattleSetOrderPainter(Battle parent) {
		super(parent);

		labelRect = new LabelRect();
		bpmap = (BattlePlayerMoveAnimationPainter) parent.getPaintersListsMap().get(GameStates.BATTLE_PLAYER_MOVE).get(7);
	}
	
	
	@Override
	public void paint(Graphics2D g) {
		labelRect.paint(g);
	}
	
	
	public void startCalculation(Move playerMove) throws IOException {
		GameStates toSet = calculatePriority(playerMove);
		bpmap.readMoveAnimations(playerMove.getName());
		
		System.out.println(toSet.name());
		GameStates.set(toSet);
	}
	
	private GameStates calculatePriority(Move playerMove) {
		LabelRect bpmapLabelRect = bpmap.buildLabel(playerMove.getName());
		labelRect.setTopText(bpmapLabelRect.getTopText());
		labelRect.setBottomText(bpmapLabelRect.getBottomText());
		
		BattleEvent evt = parent.getBattle();
		BattleMap map = new BattleMap();
		evt.setMap(map);
		
		map.put(BattleMap.BATTLE_CLASS_KEY, WildPokemonBattle.KEY);
		map.put(BattleMap.PLAYER_PKM, new BattlePokemon(Player.instance().getTeam().get(0).getData()));
		map.put(BattleMap.ENEMY_PKM, new BattlePokemon(evt.getEntityPokemon().getData()));
		
		
		GameStates toSet;
		
		BattlePokemon player = map.get(BattleMap.PLAYER_PKM, BattlePokemon.class);
		BattlePokemon enemy = map.get(BattleMap.ENEMY_PKM, BattlePokemon.class);
		
		/*
		 * Checking move priority
		 */
		
		Move enemyMove = enemy.chooseEnemyMove();
		map.put(BattleMap.PLAYER_MOVE, playerMove);
		map.put(BattleMap.ENEMY_MOVE, enemyMove);
		
		int playerMovePriority = playerMove.getPriority();
		int enemyMovePriority = enemyMove.getPriority();
		
		if((toSet = compare(playerMovePriority, enemyMovePriority)) != null)
			return toSet;
		
		/*
		 * Checking stats
		 */
		
		EntityPokemonStats playerData = player.getData().getEntityData().getStats();
		EntityPokemonStats enemyData = enemy.getData().getEntityData().getStats();
		
		int playerSpeed = playerData.get(Stats.SPEED);
		int enemySpeed = enemyData.get(Stats.SPEED);
		
		if((toSet = compare(playerSpeed, enemySpeed)) != null)
			return toSet;
		
		/*
		 * Random
		 */
		
		return new Random().nextInt(2) == 0 ? GameStates.BATTLE_PLAYER_MOVE : GameStates.BATTLE_ENEMY_MOVE;
	}
	
	private GameStates compare(int p, int e) {
//		System.out.println("P: " + p + " --- E: " + e);
		
		if(p > e)
			return GameStates.BATTLE_PLAYER_MOVE;
		if(p < e)
			return GameStates.BATTLE_ENEMY_MOVE;
		
		return null;
	}
}
