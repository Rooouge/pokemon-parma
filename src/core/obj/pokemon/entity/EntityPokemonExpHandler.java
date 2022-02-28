package core.obj.pokemon.entity;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import core.enums.ExpTypes;
import core.files.XMLHandler;
import lombok.Getter;

@Getter
public class EntityPokemonExpHandler {

	private final ExpTypes type;
	private int exp;
	
	
	public EntityPokemonExpHandler(int id) throws Exception {
		Document doc = new SAXReader().read(XMLHandler.getFile("pokemon", "exp/pokemon_exp_types"));
		Element root = doc.getRootElement();
		Node node = root.selectSingleNode("pokemon[@id='" + id + "']");
		
		type = ExpTypes.getFromXmlValue(node.getStringValue());
		setExp(1);
	}
	
	
	public void setExp(int level) {
		exp = ExpHandler.instance().get(type).get(level);
	}
}
