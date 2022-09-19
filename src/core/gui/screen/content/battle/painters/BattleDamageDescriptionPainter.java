package core.gui.screen.content.battle.painters;

import java.awt.Graphics2D;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import core.enums.GameStates;
import core.events.battle.BattleMap;
import core.gui.interfaces.Painter;
import core.gui.screen.content.battle.Battle;
import core.gui.screen.content.battle.painters.elements.LabelRect;
import core.obj.pokemon.moves.Move;

public class BattleDamageDescriptionPainter extends Painter<Battle> {

	private final LabelRect labelRect;
	private final BattlePlayerMoveAnimationPainter bpmap;
	private final BattleEnemyMoveAnimationPainter bemap;
	
	
	public BattleDamageDescriptionPainter(Battle parent) {
		super(parent);
		
		labelRect = new LabelRect();
		bpmap = (BattlePlayerMoveAnimationPainter) parent.getPaintersListsMap().get(GameStates.BATTLE_PLAYER_MOVE).get(7);
		bemap = (BattleEnemyMoveAnimationPainter) parent.getPaintersListsMap().get(GameStates.BATTLE_ENEMY_MOVE).get(7);
	}
	
	
	@Override
	public void paint(Graphics2D g) {
		labelRect.paint(g);
	}
	
	/*
	 * if returns false, no texts has to be displayed,
	 * so I'm gonna skip this state
	 */
	public void setRectText() {
		BattleMap map = parent.getBattle().getMap();
		String[] texts = map.get(BattleMap.DAMAGE_DESCRIPTION, String[].class);
		boolean isFirstAttack = map.get(BattleMap.IS_FIRST_ATTACK, Boolean.class);
		
		if(texts == null) {
			changeState(map, isFirstAttack);
		} else {
			labelRect.setTopText(texts[0]);
			labelRect.setBottomText(texts[1]);
			
			new Timer().schedule(new TimerTask() {
				
				@Override
				public void run() {
					changeState(map, isFirstAttack);
				}
				
			}, 1750);
		}
	}
	
	private void changeState(BattleMap map, boolean isFirstAttack) {
		GameStates toSet = isFirstAttack ? map.get(BattleMap.SECOND_ATTACK, GameStates.class) : GameStates.BATTLE_OPTIONS;
		map.put(BattleMap.IS_FIRST_ATTACK, Boolean.FALSE);
//		System.out.println("Next: " + toSet);
		if(isFirstAttack) {
			if(toSet.equals(GameStates.BATTLE_PLAYER_MOVE))
				loadPlayer(map, toSet);
			else
				loadEnemy(map, toSet);
		} else
			afterMoves(toSet);
	}
	
	private void loadPlayer(BattleMap map, GameStates toSet) {
		Move playerMove = map.get(BattleMap.PLAYER_MOVE, Move.class);
		LabelRect bpmapLabelRect = bpmap.buildLabel(playerMove.getName());
		labelRect.setTopText(bpmapLabelRect.getTopText());
		labelRect.setBottomText(bpmapLabelRect.getBottomText());
		
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				try {
					bpmap.readMoveAnimations(playerMove.getName());
					GameStates.set(toSet);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}, 250);
	}
	
	private void loadEnemy(BattleMap map, GameStates toSet) {
		Move enemyMove = parent.getBattle().getMap().get(BattleMap.ENEMY_MOVE, Move.class);
		LabelRect bemapLabelRect = bemap.buildLabel(enemyMove.getName());
		labelRect.setTopText(bemapLabelRect.getTopText());
		labelRect.setBottomText(bemapLabelRect.getBottomText());
		
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				try {
					bemap.readMoveAnimations(enemyMove.getName());
					GameStates.set(toSet);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}, 250);
	}
	
	private void afterMoves(GameStates toSet) {
		GameStates.set(toSet);
	}
}
