package core.obj.pokemon.entity;

import core.enums.Genders;
import core.obj.pokemon.PokemonBaseData;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityPokemonData {

	protected final PokemonBaseData baseData;
	protected final EntityPokemonStats stats;
	protected int level;
	protected String nickname;
	protected Genders gender;
	protected boolean shiny;
	
	
	public EntityPokemonData(PokemonBaseData baseData) throws Exception {
		this.baseData = baseData;
		stats = EntityPokemonStats.create(baseData.getId());
	}
	
	
	public String getDisplayName() {
		return nickname == null ? baseData.getName() : nickname;
	}
}
