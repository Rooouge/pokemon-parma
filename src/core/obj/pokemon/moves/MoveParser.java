package core.obj.pokemon.moves;

import org.dom4j.Node;

import core.enums.Types;
import core.obj.pokemon.moves.attack.PhysicalAttackMove;
import core.obj.pokemon.moves.attack.SpecialAttackMove;
import core.obj.pokemon.moves.status.StatusMove;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MoveParser {

	public PhysicalAttackMove parsePhysical(Node m) {
		return new PhysicalAttackMove(
				m.valueOf("@name"), 
				Types.getFromName(m.valueOf("@type")),
				Integer.parseInt(m.valueOf("@accuracy")), 
				Integer.parseInt(m.valueOf("@pp")), 
				Integer.parseInt(m.valueOf("@power"))
		);
	}
	
	public SpecialAttackMove parseSpecial(Node m) {
		return new SpecialAttackMove(
				m.valueOf("@name"), 
				Types.getFromName(m.valueOf("@type")),
				Integer.parseInt(m.valueOf("@accuracy")), 
				Integer.parseInt(m.valueOf("@pp")), 
				Integer.parseInt(m.valueOf("@power"))
		);
	}
	
	public StatusMove parseStatus(Node m) {
		return new StatusMove(
				m.valueOf("@name"), 
				Types.getFromName(m.valueOf("@type")), 
				Integer.parseInt(m.valueOf("@accuracy")), 
				Integer.parseInt(m.valueOf("@pp"))
		);
	}
}
