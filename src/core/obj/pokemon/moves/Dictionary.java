package core.obj.pokemon.moves;

import java.util.HashMap;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import core.Log;
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
		
		checkFiles(d);
		
		Global.add(KEY, d);
	}
	
	public static Dictionary instance() {
		return Global.get(KEY, Dictionary.class);
	}
	
	private static void checkFiles(Dictionary d) throws Exception {
		Log.log("- Checking Dictionary compatibility with learnsets...");
		Document doc = new SAXReader().read(XMLHandler.getFile("pokemon", "moves/learnsets"));
		Element root = doc.getRootElement();
		
		for(Node pkm : root.selectNodes("pokemon")) {
			String id = pkm.valueOf("@id");
			Node lvUp = pkm.selectSingleNode("levelup");
			for(Node m : lvUp.selectNodes("move")) {
				if(!d.containsKey(m.getText().trim().toLowerCase())) {
					throw new Exception("[LV] Missing " + m.getText() + " (id:" + id + ")");
				}
			}
			
			Node tutor = pkm.selectSingleNode("tutor");
			if(tutor != null) {
				for(Node m : tutor.selectNodes("move")) {
					if(!d.containsKey(m.getText().trim().toLowerCase())) {
						throw new Exception("[TU] Missing " + m.getText() + " (id:" + id + ")");
					}
				}
			}
			
			Node hm = pkm.selectSingleNode("hm");
			if(hm != null) {
				for(Node m : hm.selectNodes("move")) {
					if(!d.containsKey(m.getText().trim().toLowerCase())) {
						throw new Exception("[HM] Missing " + m.getText() + " (id:" + id + ")");
					}
				}
			}
			
			Node tm = pkm.selectSingleNode("tm");
			if(tm != null) {
				for(Node m : tm.selectNodes("move")) {
					if(!d.containsKey(m.getText().trim().toLowerCase())) {
						throw new Exception("[TM] Missing " + m.getText() + " (id:" + id + ")");
					}
				}
			}
		}

		Log.log("- Check complete!");
		Log.log("- Checking Dictionary compatibility with moves...");
		doc = new SAXReader().read(XMLHandler.getFile("pokemon", "moves/moves"));
		root = doc.getRootElement();
		
		for(Node m : root.selectNodes("move")) {
			if(!d.containsKey(m.valueOf("@name").trim().toLowerCase())) {
				System.out.println("[Moves] Missing " + m.valueOf("@name"));
			}
		}
		
		Log.log("- Check complete!");
	}
	
	
	@Override
	public String get(Object key) {
		return super.get(("" + key).toLowerCase());
	}
}
