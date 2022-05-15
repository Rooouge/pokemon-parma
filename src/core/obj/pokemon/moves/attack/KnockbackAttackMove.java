package core.obj.pokemon.moves.attack;

import core.enums.MoveTypes;
import core.enums.Types;
import lombok.Getter;

@Getter
public class KnockbackAttackMove extends AttackMove {

	protected final int knokback;
	
	public KnockbackAttackMove(String name, Types type, MoveTypes moveType, int precision, int pp, int damage, int knockback) {
		super(name, type, moveType, precision, pp, damage);
		this.knokback = knockback;
	}

}
