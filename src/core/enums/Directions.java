package core.enums;

import java.util.Random;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Directions {
    DOWN(0, "down", 1, 0),
    UP(1, "up", -1, 0),
    LEFT(2, "left", 0, -1),
    RIGHT(3, "right", 0, 1);

	
    private final int index;
    private final String name;
    private final int dr;
    private final int dc;
    

    public boolean equals(Directions direction) {
    	if(direction == null)
    		return false;
    	
        return this.index == direction.index;
    }

    public Directions getOpposite() {
        switch (this) {
            case DOWN:
                return UP;
            case UP:
                return DOWN;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
            default:
                return null;
        }
    }
    
    /**
     * Rotation accepted values are:
     * ) 90
     * ) 180
     * ) 270
     */
    public Directions rotate(int value) {
    	Directions[] options = null;
    	
    	switch (this) {
		case DOWN:
			options = new Directions[] {LEFT,UP,RIGHT};
			break;
		case UP:
			options = new Directions[] {RIGHT,DOWN,LEFT};
			break;
		case LEFT:
			options = new Directions[] {UP,RIGHT,DOWN};
			break;
		case RIGHT:
			options = new Directions[] {DOWN,LEFT,UP};
			break;
		default:
			return null;
		}
    	
    	return options[(value/90) - 1];
    }
    
    
    public static Directions getFromName(String name) {
    	for (Directions dir : values()) {
            if(dir.getName().equalsIgnoreCase(name))
                return dir;
        }

        return null;
    }
    
    public static Directions getFromIndex(int i) {
        for (Directions dir : values()) {
            if(dir.getIndex() == i)
                return dir;
        }

        return null;
    }
    
    public static Directions random() {
    	return values()[new Random().nextInt(values().length)];
    }
}
