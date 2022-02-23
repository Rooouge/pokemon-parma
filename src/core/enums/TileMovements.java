package core.enums;

import java.awt.Color;

import jutils.gui.Colors;

public enum TileMovements {

	OBSTACLE(-1, Colors.convertRGBToARGB(Color.red, 128)),
	WALK_0(0, Colors.convertRGBToARGB(Colors.gray(224), 128)),
	WALK_1(1, Colors.convertRGBToARGB(Colors.gray(192), 128)),
	WALK_2(2, Colors.convertRGBToARGB(Colors.gray(160), 128)),
	WALK_3(3, Colors.convertRGBToARGB(Colors.gray(128), 128));
	
	
	private static final TileMovements[] CAN_MOVE_TO = new TileMovements[] {
			WALK_0,
			WALK_1,
			WALK_2,
			WALK_3
	};
	
	
	
	int value;
	Color color;
	
	TileMovements(int value, Color color) {
		this.value = value;
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	
	public boolean equals(TileMovements move) {
		return value == move.value;
	}
	
	
	public static int getFromName(String name) {
		return TileMovements.valueOf(name).value;
	}
	
	public static TileMovements getFromValue(int value) {
		for(TileMovements m : TileMovements.values()) {
			if(m.value == value)
				return m;
		}
		
		return null;
	}
	
	public static boolean canMoveTo(int value) {
		for(TileMovements move : CAN_MOVE_TO) {
			if(value == move.value)
				return true;
		}
		
		return false;
	}
}
