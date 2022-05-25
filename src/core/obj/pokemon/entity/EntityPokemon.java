package core.obj.pokemon.entity;

import core.enums.Genders;
import core.obj.pokemon.PokemonBaseData;
import lombok.Getter;

@Getter
public class EntityPokemon {

	protected final EntityPokemonData data;
	
	
	public EntityPokemon(PokemonBaseData baseData) throws Exception {
		this.data = new EntityPokemonData(baseData);
	}
	
	public EntityPokemon level(int level) throws Exception {
		data.setLevel(level);
		return this;
	}
	
	public EntityPokemon nickname(String nickname) {
		data.setNickname(nickname);
		return this;
	}
	
	public EntityPokemon gender(Genders gender) {
		data.setGender(gender);
		return this;
	}
	
	public EntityPokemon shiny(boolean shiny) {
		data.setShiny(shiny);
		return this;
	}
}
