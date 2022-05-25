package core.enums;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public enum Types {
	
	NULL("null", new String[] {}, new String[] {}, new String[] {}),
    NORMAL("normale", new String[] {"spettro"}, new String[] {"roccia", "acciaio"}, new String[] {}),
    FIGHTING("lotta", new String[] {"spettro"}, new String[] {"volante", "veleno", "coleottero", "psico"}, new String[] {"normale", "roccia", "acciaio", "ghiaccio", "buio"}),
    FLYING("volante", new String[] {}, new String[] {"roccia", "acciaio", "elettro"}, new String[] {"volante", "coleottero", "erba"}),
    POISON("veleno", new String[] {"acciaio"}, new String[] {"veleno", "terra", "roccia", "spettro"}, new String[] {"erba"}),
    GROUND("terra", new String[] {"volante"}, new String[] {"coleottero", "erba"}, new String[] {"veleno", "roccia", "acciaio", "fuoco", "elettro"}),
    ROCK("roccia", new String[] {}, new String[] {"lotta", "terra", "acciaio"}, new String[] {"volante", "insetto", "fuoco", "ghiaccio"}),
    BUG("coleottero", new String[] {}, new String[] {"lotta", "volante", "veleno", "spettro", "acciaio", "fuoco"}, new String[] {"erba", "psico", "buio"}),
    GHOST("spettro", new String[] {"normale"}, new String[] {"buio"}, new String[] {"spettro", "psico"}),
    STEEL("acciaio", new String[] {}, new String[] {"acciaio", "fuoco", "acqua", "elettro"}, new String[] {"roccia", "ghiaccio"}),
    FIRE("fuoco", new String[] {}, new String[] {"roccia", "fuoco", "acqua", "drago"}, new String[] {"coleottero", "acciaio", "erba", "ghiaccio"}),
    WATER("acqua", new String[] {}, new String[] {"acqua", "erba", "drago"}, new String[] {"terra", "roccia", "fuoco"}),
    GRASS("erba", new String[] {}, new String[] {"volante", "veleno", "insetto", "acciaio", "fuoco", "erba", "drago"}, new String[] {"terra", "roccia", "acqua"}),
    ELECTRIC("elettro", new String[] {"terra"}, new String[] {"erba", "elettro", "drago"}, new String[] {"volante", "acqua"}),
    PSYCHIC("psico", new String[] {"buio"}, new String[] {"acciaio", "psico"}, new String[] {"lotta", "veleno"}),
    ICE("ghiaccio", new String[] {}, new String[] {"acciaio", "fuoco", "acqua", "ghiaccio"}, new String[] {"volante", "terra", "erba", "drago"}),
    DRAGON("drago", new String[] {}, new String[] {"acciaio"}, new String[] {"drago"}),
    DARK("buio", new String[] {}, new String[] {"lotta", "buio"}, new String[] {"spettro", "psico"});

    private final String name;
    private String[] raw0x;
    private String[] raw05x;
    private String[] raw2x;
    private List<Types> mod0x;
    private List<Types> mod05x;
    private List<Types> mod2x;
    
    
    private Types(String name, String[] raw0x, String[] raw05x, String[] raw2x) {
		this.name = name;
		this.raw0x = raw0x;
		this.raw05x = raw05x;
		this.raw2x = raw2x;
	}
    
    
    public double getMultiplierForType(Types type) {
    	if(mod05x.contains(type))
    		return 0.5;
    	if(mod2x.contains(type))
    		return 2.0;
    	
    	return 1.0;
    }
    
    /*
     * Static
     */
    
    public static void init() {
    	for(Types t : values()) {
    		initList(t.raw0x, t.mod0x);
    		initList(t.raw05x, t.mod05x);
    		initList(t.raw2x, t.mod2x);
    	}
    }

    private static List<Types> initList(String[] raw, List<Types> mod) {
    	mod = new ArrayList<>();
    	
    	for(String s : raw) {
    		mod.add(getFromName(s));
    	}
    	
    	raw = null;
    	
    	return mod;
    }
    
    public static Types getFromName(String name) {
    	for(Types t : values()) {
    		if(t.name.equalsIgnoreCase(name))
    			return t;
    	}
    	
    	return null;
    }
	
}
