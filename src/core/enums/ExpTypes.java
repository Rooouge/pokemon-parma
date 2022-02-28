package core.enums;

import lombok.Getter;

@Getter
public enum ExpTypes {

	SLOW,
	MEDIUM_SLOW,
	MEDIUM_FAST,
	FAST,
	ERRATIC,
	FLUCTUATING;
	
	
	public static ExpTypes getFromXmlValue(String xmlValue) {
		xmlValue = xmlValue.replace(" ", "_").trim();
		for(ExpTypes t : values()) {
			if(t.name().equalsIgnoreCase(xmlValue))
				return t;
		}
		
		return null;
	}
}
