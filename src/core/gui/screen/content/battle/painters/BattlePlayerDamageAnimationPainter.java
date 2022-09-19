package core.gui.screen.content.battle.painters;

import java.awt.Graphics2D;

import core.Log;
import core.events.battle.BattleMap;
import core.gui.interfaces.Painter;
import core.gui.screen.content.battle.Battle;
import core.gui.screen.content.battle.animations.DamageAnimation;
import core.gui.screen.content.battle.painters.elements.LabelRect;
import core.obj.pokemon.battle.BattlePokemon;
import core.obj.pokemon.moves.attack.AttackMove;
import lombok.Getter;

public class BattlePlayerDamageAnimationPainter extends Painter<Battle> {

	private final LabelRect labelRect;
	private final BattlePlayerPokemonLabelPainter ref;
	@Getter
	private DamageAnimation animation;
	
	
	public BattlePlayerDamageAnimationPainter(Battle parent, BattlePlayerPokemonLabelPainter ref) {
		super(parent);
		this.ref = ref;
		labelRect = new LabelRect();
	}
	
	
	public void setDamageAnimation() {
		BattleMap map = parent.getBattle().getMap();
		AttackMove move = map.get(BattleMap.ENEMY_MOVE, AttackMove.class);
		BattlePokemon enemyPkm = map.get(BattleMap.ENEMY_PKM, BattlePokemon.class);
		BattlePokemon playerPkm = map.get(BattleMap.PLAYER_PKM, BattlePokemon.class);
		
		map.put(BattleMap.ATK, enemyPkm);
		map.put(BattleMap.DEF, playerPkm);
		
		int damage = move.calculateDamage(map);
		Log.log("Player Damage: " + damage);
		animation = new DamageAnimation(parent, playerPkm.getData(), damage, ref);
	}
	
	
	@Override
	public void paint(Graphics2D g) {
		labelRect.paint(g);
	}
}
