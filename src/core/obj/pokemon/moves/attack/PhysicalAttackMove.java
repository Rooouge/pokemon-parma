package core.obj.pokemon.moves.attack;

import core.enums.MoveTypes;
import core.enums.Types;

public class PhysicalAttackMove extends AttackMove {

	protected PhysicalAttackMove(String name, Types type, int precision, int pp, int damage) {
		super(name, type, MoveTypes.PHYSICAL, precision, pp, damage);
	}

	
}
