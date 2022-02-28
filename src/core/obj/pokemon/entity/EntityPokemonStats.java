package core.obj.pokemon.entity;

import java.util.EnumMap;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import core.Log;
import core.enums.Stats;
import core.files.XMLHandler;
import core.obj.pokemon.pokedex.PokedexHandler;

@SuppressWarnings("serial")
public class EntityPokemonStats extends EnumMap<Stats, Integer> {
	
	public EntityPokemonStats() {
		super(Stats.class);
	}
	
	
	public static EntityPokemonStats create(int id) throws Exception {
		Log.log("Parsing stats for " + PokedexHandler.get().baseData(id).getName() + " (" + id + ")");
		
		EntityPokemonStats stats = new EntityPokemonStats();
		
		Document doc = new SAXReader().read(XMLHandler.getFile("pokemon", "base/pokemon_base_values"));
		Element root = doc.getRootElement();
		Node node = root.selectSingleNode("pokemon[@id='" + id + "']");
		
		for(Stats stat : Stats.values()) {
			int value = Integer.parseInt(node.selectSingleNode(stat.getXmlNode()).getStringValue());
			stats.put(stat, value);
			Log.log("- " + stat.getLabel() + ": " + value);
		}
		
		return stats;
	}

}
