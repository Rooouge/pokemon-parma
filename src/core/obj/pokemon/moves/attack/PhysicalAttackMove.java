package core.obj.pokemon.moves.attack;

import org.dom4j.Node;

import core.enums.MoveTypes;
import core.enums.Stats;
import core.enums.Types;
import core.events.battle.BattleMap;

public class PhysicalAttackMove extends AttackMove {

	
	public PhysicalAttackMove(Node m) {
		super(m, MoveTypes.PHYSICAL, Integer.parseInt(m.valueOf("@power")));
	}
	
	public PhysicalAttackMove(String name, Types type, int precision, int pp, int damage) {
		super(name, type, MoveTypes.PHYSICAL, precision, pp, damage);
	}

	
	@Override
	public int calculateDamage(BattleMap map) {
		return DamageCalculator.calculate(map, this, Stats.ATK, Stats.DEF);
	}

	
}
