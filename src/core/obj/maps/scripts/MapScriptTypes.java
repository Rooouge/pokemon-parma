package core.obj.maps.scripts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MapScriptTypes {

	EXEC_ON_ENTER("onEnter");
	
	
	private final String value;
	
	
	public static MapScriptTypes getFromValue(String type) {
		for(MapScriptTypes t : values()) {
			if(t.value.equalsIgnoreCase(type))
				return t;
		}
		
		return null;
	}
}
