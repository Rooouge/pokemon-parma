package core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OverworldEntityTypes {

	ALLENATORE(0, "Allenatore"),
	ALLENATRICE(1, "Allenatrice");
	
	
	private int index;
	private String label;
	
	
	public static OverworldEntityTypes getFromIndex(int i) {
		for(OverworldEntityTypes t : values()) {
			if(t.index == i)
				return t;
		}
		
		return null;
	}
}
