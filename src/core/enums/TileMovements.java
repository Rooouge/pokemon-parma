package core.enums;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import jutils.gui.Colors;
import lombok.Getter;

@Getter
public enum TileMovements {

	OBSTACLE(-1, Colors.convertRGBToARGB(Color.red, 128)),
	WALK_0(0, Colors.convertRGBToARGB(Colors.gray(224), 128)),
	WALK_1(1, Colors.convertRGBToARGB(Colors.gray(192), 128)),
	WALK_2(2, Colors.convertRGBToARGB(Colors.gray(160), 128)),
	WALK_3(3, Colors.convertRGBToARGB(Colors.gray(128), 128)),
	WP_GRASS(4, Colors.convertRGBToARGB(Color.green, 128));
	
	
	private static final List<TileMovements> CAN_MOVE;
	private static final List<TileMovements> CAN_SPAWN;
	
	static {
		CAN_MOVE = new ArrayList<>();
		CAN_MOVE.add(WALK_0);
		CAN_MOVE.add(WALK_1);
		CAN_MOVE.add(WALK_2);
		CAN_MOVE.add(WALK_3);
		CAN_MOVE.add(WP_GRASS);
		
		CAN_SPAWN = new ArrayList<>();
		CAN_SPAWN.add(WP_GRASS);
	}
	
	
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
	
	/*
	 * Statics
	 */
	
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
	
	public static boolean canMove(TileMovements tile) {
		return CAN_MOVE.contains(tile);
	}
	
	public static boolean canMove(int value) {
		return canMove(getFromValue(value));
	}
	
	public static boolean canSpawn(TileMovements tile) {
		return CAN_SPAWN.contains(tile);
	}
	
	public static boolean canSpawn(int value) {
		return canSpawn(getFromValue(value));
	}
}
