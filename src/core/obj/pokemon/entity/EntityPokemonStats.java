package core.obj.pokemon.entity;

import java.util.EnumMap;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import core.Log;
import core.enums.Natures;
import core.enums.Stats;
import core.files.XMLHandler;
import core.obj.pokemon.battle.BattlePokemon;
import core.obj.pokemon.pokedex.Pokedex;
import lombok.Getter;

@SuppressWarnings("serial")
@Getter
public class EntityPokemonStats extends EnumMap<Stats, Integer> {
	
	protected final EntityPokemonData data;
	protected final EntityPokemonBaseValues BVs;
	protected final EntityPokemonIVs IVs;
	protected final EntityPokemonEVs EVs;
	protected final Natures nature;
	
	
	protected EntityPokemonStats(EntityPokemonData data) {
		super(Stats.class);
		
		this.data = data;
		BVs = new EntityPokemonBaseValues();
		IVs = EntityPokemonIVs.generate();
		EVs = EntityPokemonEVs.generate();
		nature = Natures.random();
		Log.log("Retrieved nature: " + nature.name());
	}
	
	
	public static EntityPokemonStats create(EntityPokemonData data) throws Exception {
		int id = data.getBaseData().getId();
		int level = data.getLevel();
		
		Log.log("Generating stats for " + Pokedex.instance().baseData(id).getName() + " (" + id + ")");
		EntityPokemonStats stats = new EntityPokemonStats(data);
		
		Document doc = new SAXReader().read(XMLHandler.getFile("pokemon", "base/pokemon_base_values"));
		Element root = doc.getRootElement();
		Node node = root.selectSingleNode("pokemon[@id='" + id + "']");
		
		for(Stats stat : Stats.values()) {
			if(stat.equals(Stats.HP))
				continue;
			
			/*
			 * For HP:
			 * (((2 * BS) + IV) * L / 100) + 10 + L
			 * 
			 * For Stats:
			 * ((((2 * BS) + IV) * L / 100) + 5) * N
			 */

			int baseStat = Integer.parseInt(node.selectSingleNode(stat.getXmlNode()).getStringValue());
			stats.BVs.put(stat, baseStat);
			
			double BS = 1.0*baseStat;
//			double IV = 1.0*stats.IVs.get(stat);
			double IV = 0.0;
			double L = 1.0*level;
			int value = 0;
			
			if(stat.equals(Stats.TOT_HP)) {
				value = (int) (Math.floor((((2.0 * BS) + IV) * L / 100.0)) + 10.0 + L);
			} else {
				double N = stats.nature.getModifier(stat);
				value = (int) (Math.floor(((((2.0 * BS) + IV) * L / 100.0) + 5.0)) * N);
			}
			
			stats.put(stat, value);
			Log.log("- " + stat.getLabel() + ": " + value);
		}
		
		stats.put(Stats.HP, stats.get(Stats.TOT_HP));
		
		return stats;
	}
	
	
	public void calculateAfterDefeating(BattlePokemon enemy) throws Exception {
		int enemyId = enemy.getData().getEntityData().getBaseData().getId();
		Log.log("Updating stat of " + data.getBaseData().getName() + " after battle with " + enemy.getData().getEntityData().getBaseData().getName());
		
		EVs.updateEvsAndRetreiveExpToAdd(enemyId);
		
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
			
			double BS = 1.0*BVs.get(stat);
			double IV = 1.0*IVs.get(stat);
			double EV = 1.0*EVs.get(stat);
			double L = 1.0*data.getLevel();
			int value = 0;
			
			if(stat.equals(Stats.TOT_HP)) {
				value = (int) (Math.floor((((2.0 * BS) + IV + Math.floor((EV / 4.0))) * L / 100.0)) + 10.0 + L);
			} else {
				double N = nature.getModifier(stat);
				value = (int) (Math.floor(((((2.0 * BS) + IV + Math.floor((EV / 4.0))) * L / 100.0) + 5.0)) * N);
			}
			
			put(stat, value);
			Log.log("- " + stat.getLabel() + ": " + value);
		}
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
