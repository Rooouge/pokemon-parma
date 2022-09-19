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
import core.gui.screen.content.battle.keyhandlers.BattleFightOptionsKeyHandler;
import core.gui.screen.content.battle.painters.elements.LabelRect;
import core.obj.entities.player.Player;
import core.obj.pokemon.battle.BattlePokemon;
import core.obj.pokemon.entity.EntityPokemonStats;
import core.obj.pokemon.moves.Move;
import jutils.log.Log;
import jutils.threads.Threads;

public class BattleSetOrderPainter extends Painter<Battle> {

	private final LabelRect labelRect;
	private final BattlePlayerMoveAnimationPainter bpmap;
	private final BattleEnemyMoveAnimationPainter bemap;
	
	/**
	 * Serve unicamente per disegnare qualcosa mentre
	 * recupero le risorse negli stati successivi
	 */
	public BattleSetOrderPainter(Battle parent) {
		super(parent);

		labelRect = new LabelRect();
		bpmap = (BattlePlayerMoveAnimationPainter) parent.getPaintersListsMap().get(GameStates.BATTLE_PLAYER_MOVE).get(7);
		bemap = (BattleEnemyMoveAnimationPainter) parent.getPaintersListsMap().get(GameStates.BATTLE_ENEMY_MOVE).get(7);
	}
	
	
	@Override
	public void paint(Graphics2D g) {
		labelRect.paint(g);
	}
	
	
	public void setOrder(Move playerMove, BattleFightOptionsKeyHandler bfokh) {
		GameStates toSet = calculatePriority(playerMove);
		
		if(toSet.equals(GameStates.BATTLE_PLAYER_MOVE))
			loadPlayer(playerMove, bfokh, toSet);
		else 
			loadEnemy(toSet);		
	}
	
	private void loadPlayer(Move playerMove, BattleFightOptionsKeyHandler bfokh, GameStates toSet) {
		LabelRect bpmapLabelRect = bpmap.buildLabel(playerMove.getName());
		labelRect.setTopText(bpmapLabelRect.getTopText());
		labelRect.setBottomText(bpmapLabelRect.getBottomText());
		
		Threads.run(() -> {
			try {
				bpmap.readMoveAnimations(playerMove.getName());
				GameStates.set(toSet);
				bfokh.setFlag(false);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
	
	private void loadEnemy(GameStates toSet) {
		Move enemyMove = parent.getBattle().getMap().get(BattleMap.ENEMY_MOVE, Move.class);
		LabelRect bemapLabelRect = bemap.buildLabel(enemyMove.getName());
		labelRect.setTopText(bemapLabelRect.getTopText());
		labelRect.setBottomText(bemapLabelRect.getBottomText());
		
		Threads.run(() -> {
			try {
				bemap.readMoveAnimations(enemyMove.getName());
				GameStates.set(toSet);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
	
	private GameStates calculatePriority(Move playerMove) {		
		BattleEvent evt = parent.getBattle();
		BattleMap map = new BattleMap();
		evt.setMap(map);
		
		map.put(BattleMap.IS_FIRST_ATTACK, Boolean.TRUE);
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
			return put(map, toSet);
		
		/*
		 * Checking stats
		 */
		
		EntityPokemonStats playerData = player.getData().getEntityData().getStats();
		EntityPokemonStats enemyData = enemy.getData().getEntityData().getStats();
		
		int playerSpeed = playerData.get(Stats.SPEED);
		int enemySpeed = enemyData.get(Stats.SPEED);
		
		if((toSet = compare(playerSpeed, enemySpeed)) != null)
			return put(map, toSet);
		
		/*
		 * Random
		 */
		
		return new Random().nextInt(2) == 0 ? put(map, GameStates.BATTLE_PLAYER_MOVE) : put(map, GameStates.BATTLE_ENEMY_MOVE);
	}
	
	private GameStates compare(int p, int e) {
//		System.out.println("P: " + p + " --- E: " + e);
		
		if(p > e)
			return GameStates.BATTLE_PLAYER_MOVE;
		if(p < e)
			return GameStates.BATTLE_ENEMY_MOVE;
		
		return null;
	}
	
	private GameStates put(BattleMap map, GameStates toSet) {
		switch (toSet) {
		case BATTLE_PLAYER_MOVE:
			map.put(BattleMap.SECOND_ATTACK, GameStates.BATTLE_ENEMY_MOVE);
			break;
		case BATTLE_ENEMY_MOVE:
			map.put(BattleMap.SECOND_ATTACK, GameStates.BATTLE_PLAYER_MOVE);
			break;
		default:
			Log.error("Invalid Gamestate: " + toSet);
			break;
		}
		
		return toSet;
	}
}
