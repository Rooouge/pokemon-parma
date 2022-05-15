package core.obj.pokemon.moves.attack;

import core.enums.MoveTypes;
import core.enums.Types;

public class SpecialAttackMove extends AttackMove {

	public SpecialAttackMove(String name, Types type, int precision, int pp, int damage) {
		super(name, type, MoveTypes.SPECIAL, precision, pp, damage);
		
	}

}
