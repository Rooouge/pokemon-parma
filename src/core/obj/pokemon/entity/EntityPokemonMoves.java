package core.obj.pokemon.entity;

import java.util.ArrayList;

import core.obj.pokemon.moves.Move;

@SuppressWarnings("serial")
public class EntityPokemonMoves extends ArrayList<Move> {

	private static final int MAX = 4;
	
	
	@Override
	public boolean add(Move e) {
		if(size() >= MAX)
			return false;
		
		return super.add(e);
	}
	
	
	@Override
	public boolean contains(Object o) {
		if(o instanceof Move) {
			Move om = (Move) o;
			
			for(Move m : this) {
				if(m.getName().equalsIgnoreCase(om.getName()))
					return true;
			}
			
			return false;
		}
		
		return super.contains(o);
	}
	
	public boolean alreadyKnows(Move move) {
		return contains(move);
	}

}
