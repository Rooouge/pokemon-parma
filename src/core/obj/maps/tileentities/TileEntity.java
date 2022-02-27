package core.obj.maps.tileentities;

import org.dom4j.Node;

import core.obj.entities.Entity;
import core.obj.maps.Map;
import jutils.asserts.AssertException;
import lombok.Getter;

@Getter
public class TileEntity extends Entity<TileEntityData> {

	public TileEntity(Map map, Node root) throws AssertException {
		data = new TileEntityData(map, root);
	}
}
