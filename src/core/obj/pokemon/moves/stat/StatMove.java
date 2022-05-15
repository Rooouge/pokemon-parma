package core.obj.pokemon.moves.stat;

import core.enums.MoveTypes;
import core.enums.Stats;
import core.enums.Types;
import core.obj.pokemon.moves.Move;
import lombok.Getter;

@Getter
public class StatMove extends Move {

	protected final boolean strong;
	protected final Stats[] stats;
	
	
	public StatMove(String name, Types type, int precision, int pp, boolean strong, Stats... stats) {
		super(name, type, MoveTypes.STAT, precision, pp);
		this.strong = strong;
		this.stats = stats;
	}

}
