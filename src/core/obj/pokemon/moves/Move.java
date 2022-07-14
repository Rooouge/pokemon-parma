package core.obj.pokemon.moves;

import org.dom4j.Node;

import core.enums.MoveTypes;
import core.enums.Types;
import jutils.strings.Strings;
import lombok.Getter;

@Getter
public class Move {

	protected final String name;
	protected final Types type;
	protected final MoveTypes moveType;
	protected final int precision;
    protected final int pp;
    protected final int ppMax;
    protected int priority;
    protected int currentPp;
    
	
    protected Move(Node m, MoveTypes moveType) {
	    this(
	    	m.valueOf("@name"), 
			Types.getFromName(m.valueOf("@type")),
			moveType,
			Integer.parseInt(m.valueOf("@accuracy")),
			Integer.parseInt(m.valueOf("@pp"))
		);
	    
	    String priorVal = m.valueOf("@priority");
	    priority = (priorVal == null || Strings.isVoid(priorVal)) ? 0 : Integer.parseInt(priorVal);
    }
    
    protected Move(String name, Types type, MoveTypes moveType, int precision, int pp) {
		this.name = name;
		this.type = type;
		this.moveType = moveType;
		this.precision = precision;
		this.pp = pp;
		ppMax = 8*pp/5;
		priority = 0;
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
