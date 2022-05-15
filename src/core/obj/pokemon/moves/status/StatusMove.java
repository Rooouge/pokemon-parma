package core.obj.pokemon.moves.status;

import core.enums.MoveTypes;
import core.enums.Types;
import core.obj.pokemon.moves.Move;

public class StatusMove extends Move {

	public StatusMove(String name, Types type, int precision, int pp) {
		super(name, type, MoveTypes.STATUS, precision, pp);
	}

}
