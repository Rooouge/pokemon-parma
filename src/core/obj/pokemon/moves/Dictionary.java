package core.obj.pokemon.moves;

import java.util.HashMap;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import core.files.XMLHandler;
import jutils.global.Global;

@SuppressWarnings("serial")
public class Dictionary extends HashMap<String, String> {

	public static final String KEY = "dictionary";
	
	
	public static void init() {
		Dictionary d = new Dictionary();
		Global.add(KEY, d);
	}
	
	public static Dictionary instance() {
		return Global.get(KEY, Dictionary.class);
	}
	
	
	public String translate(String eng) throws Exception {
		Dictionary dic = instance();
		
		if(dic.containsKey(eng))
			return dic.get(eng);
		
		
		Document doc = new SAXReader().read(XMLHandler.getFile("pokemon/moves", "dictionary"));
		Element root = doc.getRootElement();
		
		return root.selectSingleNode("move[@eng='" + eng + "']").getText();
	}
	
}
