package core.obj.pokemon.pokedex;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import core.Log;
import core.files.XMLHandler;
import core.obj.pokemon.PokemonBaseData;
import jutils.global.Global;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PokedexHandler {

	public final String KEY = "pokedex";
	
	
	public Pokedex get() {
		return Global.get(KEY, Pokedex.class);
	}
	
	public void create() throws Exception {
		Pokedex pokedex = new Pokedex();
		pokedex.add(null); //No pkmn with id = 0
		
		Document doc = new SAXReader().read(XMLHandler.getFile("pokemon", "pokedex-create"));
		Element root = doc.getRootElement();
		
		for(Node n : root.selectNodes("pokemon")) {
			PokemonPokedex p = new PokemonPokedex(n);
			pokedex.add(p);
			
			PokemonBaseData data = p.getData().getBaseData();
			Log.log("- Founded Pokémon '" + data.getName() + "' with Id: " + data.getId());
		}
		
		Global.add(KEY, pokedex);
	}
	
}
