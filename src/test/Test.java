package test;

import java.util.Random;

import core.Core;
import core.Log;
import core.enums.Genders;
import core.obj.entities.player.Player;
import core.obj.pokemon.entity.EntityPokemon;
import core.obj.pokemon.pokedex.Pokedex;

public class Test {

	public static void main(String[] args) {
		try {
			Core.init();
			int id = new Random().nextInt(Pokedex.instance().size());
			Log.log("Player pokemon with id: " + id);
			Player.instance().getTeam().add(new EntityPokemon(Pokedex.instance().baseData(id)).gender(Genders.MALE).level(45).shiny(false));
			Core.run();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
