package core.obj.pokemon.moves;

import core.enums.Types;
import lombok.Getter;

@Getter
public class Move {

	protected final String name;
	protected final Types type;
	protected final int precision;
    protected final int pp;
    protected final int ppMax;
    
	
    protected Move(String name, Types type, int precision, int pp) {
		super();
		this.name = name;
		this.type = type;
		this.precision = precision;
		this.pp = pp;
		ppMax = 5*pp/8;
	}
    
}
