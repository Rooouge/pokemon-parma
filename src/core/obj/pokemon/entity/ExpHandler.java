package core.obj.pokemon.entity;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import core.Log;
import core.enums.ExpTypes;
import core.files.XMLHandler;
import jutils.global.Global;

@SuppressWarnings("serial")
public class ExpHandler extends EnumMap<ExpTypes, List<Integer>> {

	private static final String KEY = "exp";
	
	
	private ExpHandler() {
		super(ExpTypes.class);
	}

	
	public static void init() throws Exception {
		ExpHandler exp = new ExpHandler();
		
		for(ExpTypes t : ExpTypes.values()) {
			Log.log("- Initializing Exp type: '" + t.name() + "'");
			Document doc = new SAXReader().read(XMLHandler.getFile("pokemon", "exp/" + t.name()));
			Element root = doc.getRootElement();
			
			List<Integer> values = new ArrayList<>();
			values.add(-1);
			
			for(Node node : root.selectNodes("level")) {
				values.add(Integer.parseInt(node.getStringValue()));
			}
			
			exp.put(t, values);
		}
		
		Global.add(KEY, exp);
	}
	
	public static ExpHandler instance() {
		return Global.get(KEY, ExpHandler.class);
	}
}
