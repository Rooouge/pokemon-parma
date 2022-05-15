package core.enums;

public enum MoveTypes {
	PHYSICAL,
    SPECIAL,
    STATUS,
    STAT,
    CLIMATE,
    CHANGE;
	
	public boolean equals(MoveTypes type) {
        return this.name() == type.name();
    }
}
