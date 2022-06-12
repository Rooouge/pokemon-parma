package core.obj.pokemon.entity;

import java.util.EnumMap;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import core.enums.Stats;
import core.files.XMLHandler;
import lombok.Getter;

@SuppressWarnings("serial")
@Getter
public class EntityPokemonEVs extends EnumMap<Stats, Integer> {

	public static final int LIMIT = 510;
	public static final int LIMIT_SINGLE = 255;
	
	
	protected int total;
	
	
	protected EntityPokemonEVs() {
		super(Stats.class);
		total = 0;
	}
	
	
	public static EntityPokemonEVs generate() {
		EntityPokemonEVs map = new EntityPokemonEVs();
		map.put(Stats.ATK, 0);
		map.put(Stats.DEF, 0);
		map.put(Stats.SP_ATK, 0);
		map.put(Stats.SP_DEF, 0);
		map.put(Stats.SPEED, 0);
		map.put(Stats.TOT_HP, 0);
		return map;
	}
	
	
	public int updateEvsAndRetreiveExpToAdd(int enemyId) throws Exception {
		Document doc = new SAXReader().read(XMLHandler.getFile("pokemon", "base/pokemon_evs"));
		Element root = doc.getRootElement();
		Node node = root.selectSingleNode("pokemon[@id='" + enemyId + "']");
		
		for(Stats stat : Stats.values()) {
			if(stat.equals(Stats.HP))
				continue;
			if(total >= LIMIT)
				continue;
			
			
			int prev = get(stat);
			if(prev >= LIMIT_SINGLE) {
				put(stat, LIMIT_SINGLE);
				continue;
			}
			int add = Integer.parseInt(node.selectSingleNode(stat.getXmlNode()).getStringValue());
			int val = prev + add;
			
			if(val > LIMIT_SINGLE)
				val = LIMIT_SINGLE;
			if(total + val > LIMIT)
				val = LIMIT - total;
			
			put(stat, val);
			total += val;
		}
		
		return Integer.parseInt(node.selectSingleNode("exp").getStringValue());
	}
}
