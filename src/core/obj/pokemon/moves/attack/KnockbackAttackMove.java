package core.obj.pokemon.moves.attack;

import core.enums.Types;
import lombok.Getter;

@Getter
public class KnockbackAttackMove extends AttackMove {

	protected final int knokback;
	
	public KnockbackAttackMove(String name, Types type, int precision, int pp, int damage, int knockback) {
		super(name, type, precision, pp, damage);
		this.knokback = knockback;
	}

}
