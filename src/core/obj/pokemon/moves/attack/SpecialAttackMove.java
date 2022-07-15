package core.obj.pokemon.moves.attack;

import org.dom4j.Node;

import core.enums.MoveTypes;
import core.enums.Stats;
import core.enums.Types;
import core.events.battle.BattleMap;

public class SpecialAttackMove extends AttackMove {

	
	public SpecialAttackMove(Node m) {
		super(m, MoveTypes.SPECIAL, Integer.parseInt(m.valueOf("@power")));
	}
	
	public SpecialAttackMove(String name, Types type, int precision, int pp, int damage) {
		super(name, type, MoveTypes.SPECIAL, precision, pp, damage);
		
	}

	
	@Override
	public int calculateDamage(BattleMap map) {
		return DamageCalculator.calculate(map, this, Stats.SP_ATK, Stats.SP_DEF);
	}

}
