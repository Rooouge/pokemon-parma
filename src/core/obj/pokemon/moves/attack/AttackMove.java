package core.obj.pokemon.moves.attack;

import core.enums.Types;
import core.obj.pokemon.moves.Move;
import lombok.Getter;

@Getter
public class AttackMove extends Move {

	protected final int damage;
	
	public AttackMove(String name, Types type, int precision, int pp, int damage) {
		super(name, type, precision, pp);
		this.damage = damage;
	}

}
