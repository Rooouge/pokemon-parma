package test;

import core.Core;
import core.enums.Genders;
import core.obj.entities.player.Player;
import core.obj.pokemon.entity.EntityPokemon;
import core.obj.pokemon.pokedex.Pokedex;

public class Test {

	public static void main(String[] args) {
		try {
			Core.init();
			Player.instance().getTeam().add(new EntityPokemon(Pokedex.instance().baseData(150)).gender(Genders.UNKNOWN).level(70).shiny(true));
			Core.run();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
