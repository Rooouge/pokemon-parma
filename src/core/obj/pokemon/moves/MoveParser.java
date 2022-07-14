package core.obj.pokemon.moves;

import org.dom4j.Node;

import core.obj.pokemon.moves.attack.PhysicalAttackMove;
import core.obj.pokemon.moves.attack.SpecialAttackMove;
import core.obj.pokemon.moves.status.StatusMove;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MoveParser {

	public PhysicalAttackMove parsePhysical(Node m) {
		return new PhysicalAttackMove(m);
	}
	
	public SpecialAttackMove parseSpecial(Node m) {
		return new SpecialAttackMove(m);
	}
	
	public StatusMove parseStatus(Node m) {
		return new StatusMove(m);
	}
}
