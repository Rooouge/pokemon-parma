package core.obj.pokemon.moves.attack;

import core.enums.MoveTypes;
import core.enums.Types;
import core.obj.pokemon.moves.Move;
import lombok.Getter;

@Getter
public class AttackMove extends Move {

	protected final int damage;
	
	protected AttackMove(String name, Types type, MoveTypes moveType, int precision, int pp, int damage) {
		super(name, type, moveType, precision, pp);
		this.damage = damage;
	}

}
