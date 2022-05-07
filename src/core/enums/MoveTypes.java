package core.enums;

public enum MoveTypes {
	PHYSICAL,
    SPECIAL,
    STATE,
    CLIMATE,
    CHANGE;
	
	public boolean equals(MoveTypes type) {
        return this.name() == type.name();
    }
}
