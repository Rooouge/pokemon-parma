package core.obj.pokemon;

import org.dom4j.Node;

import core.enums.Types;
import lombok.Getter;

@Getter
public class PokemonBaseData {

	protected final int id;
	protected final String name;
	protected final Types mainType;
	protected final Types secondaryType;
	
	
	public PokemonBaseData(Node root) {
		id = Integer.parseInt(root.valueOf("@id"));
		name = root.valueOf("@name");
		mainType = Types.getFromName(root.valueOf("@maintype"));
		secondaryType = Types.getFromName(root.valueOf("@secondarytype"));
	}
}
