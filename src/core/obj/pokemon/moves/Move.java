package core.obj.pokemon.moves;

import core.enums.MoveTypes;
import core.enums.Types;
import lombok.Getter;

@Getter
public class Move {

	protected final String name;
	protected final Types type;
	protected final MoveTypes moveType;
	protected final int precision;
    protected final int pp;
    protected final int ppMax;
    protected int currentPp;
    
	
    protected Move(String name, Types type, MoveTypes moveType, int precision, int pp) {
		this.name = name;
		this.type = type;
		this.moveType = moveType;
		this.precision = precision;
		this.pp = pp;
		ppMax = 8*pp/5;
		
		currentPp = pp;
	}
    
    
    @Override
    public boolean equals(Object obj) {
    	if(obj instanceof Move) {
    		return ((Move) obj).name.equals(this.name);
    	}
    	
    	return super.equals(obj);
    }
}
