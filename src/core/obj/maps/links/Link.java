package core.obj.maps.links;

import core.enums.Directions;

public class Link {

	protected final String mReg1;
	protected final String mReg2;
	protected final Directions dir;
	protected final int offset;
	
	
	public Link(String mReg1, String mReg2, Directions dir, int offset) {
		this.mReg1 = mReg1;
		this.mReg2 = mReg2;
		this.dir = dir;
		this.offset = offset;
	}
	
	
	public int getOffset(String mapName) {
		if(mapName.equals(mReg2))
			return offset;
		return -offset;
	}
	
	public Directions getDir(String mapName) {
		if(mapName.equals(mReg1))
			return dir;
		return dir.getOpposite();
	}
	
	public String getNeighborName(String mapName) {
		return mapName.equals(mReg1) ? mReg2 : mReg1;
	}
	
	public boolean isInLink(String mapName) {
		return mapName.equals(mReg1) || mapName.equals(mReg2);
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Link) {
			Link l = (Link) obj;
			
			return (this.mReg1.equals(l.mReg1) && this.mReg2.equals(l.mReg2)) || (this.mReg1.equals(l.mReg2) && this.mReg2.equals(l.mReg1));
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return "(" + mReg1 + "," + mReg2 + " = " + dir + ")";
	}
}
