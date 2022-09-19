package core.obj.pokemon.battle;

import core.enums.Types;
import core.obj.pokemon.entity.EntityPokemonData;
import core.obj.pokemon.moves.Move;
import core.obj.pokemon.moves.attack.SpecialAttackMove;
import lombok.Getter;

@Getter
public class BattlePokemon {

	protected final BattlePokemonData data;
	
	
	public BattlePokemon(EntityPokemonData entityData) {
		data = new BattlePokemonData(entityData);
	}
	
	
	public Move chooseEnemyMove() {
		/*
		 * Test
		 */
		
		return new SpecialAttackMove("Aerial Ace", Types.FLYING, 100, 25, 20);
	}
}
