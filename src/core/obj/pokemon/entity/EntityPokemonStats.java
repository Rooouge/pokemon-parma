package core.obj.pokemon.entity;

import java.util.EnumMap;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import core.Log;
import core.enums.Stats;
import core.files.XMLHandler;
import core.obj.pokemon.pokedex.Pokedex;
import lombok.Getter;

@SuppressWarnings("serial")
@Getter
public class EntityPokemonStats extends EnumMap<Stats, Integer> {
	
	protected final EntityPokemonIVs IVs;
	protected final EntityPokemonEVs EVs;
	
	
	protected EntityPokemonStats() {
		super(Stats.class);
		
		IVs = EntityPokemonIVs.generate();
		EVs = EntityPokemonEVs.generate();
	}
	
	
	public static EntityPokemonStats create(int id, int level) throws Exception {
		Log.log("Generating stats for " + Pokedex.instance().baseData(id).getName() + " (" + id + ")");
		
		EntityPokemonStats stats = new EntityPokemonStats();
		
		Document doc = new SAXReader().read(XMLHandler.getFile("pokemon", "base/pokemon_base_values"));
		Element root = doc.getRootElement();
		Node node = root.selectSingleNode("pokemon[@id='" + id + "']");
		
		for(Stats stat : Stats.values()) {
			if(stat.equals(Stats.HP))
				continue;
			
			/*
			 * For HP:
			 * (((2 * BS) + IV + (EV / 4)) * L / 100) + 10 + L
			 * 
			 * For Stats:
			 * ((((2 * BS) + IV + (EV / 4)) * L / 100) + 5) * N
			 */
			double BS = 1.0*Integer.parseInt(node.selectSingleNode(stat.getXmlNode()).getStringValue());
			double IV = 1.0*stats.IVs.get(stat);
			double EV = 1.0*stats.EVs.get(stat);
			double L = 1.0*level;
			int value = 0;
			
			if(stat.equals(Stats.TOT_HP)) {
				value = (int) ((((2.0 * BS) + IV + (EV / 4.0)) * L / 100.0) + 10.0 + L);
			} else {
				/*
				 * TODO: Add nature handling
				 */
				double N = 1.0;
				value = (int) (((((2 * BS) + IV + (EV / 4)) * L / 100) + 5) * N);
			}
			
			stats.put(stat, value);
			Log.log("- " + stat.getLabel() + ": " + value);
		}
		
		stats.put(Stats.HP, stats.get(Stats.TOT_HP));
		
		return stats;
	}
	
	
	public void hpDamage(int damage) {
		int current = get(Stats.HP);
		put(Stats.HP, current - damage);
	}
	
	public boolean isKO() {
		int current = get(Stats.HP);
		
		if(current < 0)
			put(Stats.HP, 0);
		
		return current <= 0;
	}
	
	public void logHp() {
		jutils.log.Log.info("Hp: " + get(Stats.HP) + "/" + get(Stats.TOT_HP));
	}
}
