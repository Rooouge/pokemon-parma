package core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MovementsDescriptor {
	
	STILL(-1,"still"),
	ALL(0,"all"),
	FACING(1,"facing"),
	FACING_HOR(2,"facing-hor"),
	FACING_VER(3,"facing-ver"),
	MOVING(4,"moving"),
	MOVING_HOR(5,"moving-hor"),
	MOVING_VER(6,"moving-ver");
	
	
	private final int id;	
	private final String xmlDesc;

	
	public static MovementsDescriptor getFromXmlDesc(String desc) {
		for(MovementsDescriptor md : MovementsDescriptor.values()) {
			if(md.getXmlDesc().equalsIgnoreCase(desc))
				return md;
		}
		
		return null;
	}
}
