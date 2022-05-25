package core.obj.pokemon.entity;

import core.enums.Genders;
import core.obj.pokemon.PokemonBaseData;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityPokemonData {

	protected final PokemonBaseData baseData;
	protected final EntityPokemonExpHandler exp;
	protected EntityPokemonStats stats;
	protected EntityPokemonMoves moves;
	protected int level;
	protected String nickname;
	protected Genders gender;
	protected boolean shiny;
	
	
	public EntityPokemonData(PokemonBaseData baseData) throws Exception {
		this.baseData = baseData;
		exp = new EntityPokemonExpHandler(baseData.getId());
		moves = new EntityPokemonMoves();
		
		
	}
	
	
	public String getDisplayName() {
		return nickname == null ? baseData.getName() : nickname;
	}
	
	public void setLevel(int level) throws Exception {
		if(level != this.level) {
			exp.setExp(level);
			this.level = level;
			
			moves.generateMovesForWildPokemon(baseData.getId(), level);
			stats = EntityPokemonStats.create(baseData.getId(), level);
		}
	}
}
