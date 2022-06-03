package core.obj.pokemon.moves;

import java.util.HashMap;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import core.files.XMLHandler;
import jutils.global.Global;

@SuppressWarnings("serial")
public class Dictionary extends HashMap<String, String> {

	public static final String KEY = "dictionary";
	
	
	public static void init() throws Exception {
		Dictionary d = new Dictionary();
		
		Document doc = new SAXReader().read(XMLHandler.getFile("pokemon", "moves/dictionary"));
		Element root = doc.getRootElement();
		
		for(Node move : root.selectNodes("move")) {
			String key = move.valueOf("@eng").trim().toLowerCase();
			String value = move.getText().trim().toLowerCase();
			d.put(key, value);
			
//			System.out.println(" - " + key + ": " + value + " (CHECK: " + d.get(key) + ")");
		}
		
		Global.add(KEY, d);
	}
	
	public static Dictionary instance() {
		return Global.get(KEY, Dictionary.class);
	}
	
	@Override
	public String get(Object key) {
		return super.get(("" + key).toLowerCase());
	}
}
