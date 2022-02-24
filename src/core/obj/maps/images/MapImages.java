package core.obj.maps.images;

import java.io.IOException;
import java.util.ArrayList;

import core.files.ImageHandler;
import core.files.TiledImage;

@SuppressWarnings("serial")
public class MapImages extends ArrayList<TiledImage> {

	public MapImages(String registryName) throws IOException {
		super();
		
		add(ImageHandler.getMapImage(registryName + "_0"));
		add(ImageHandler.getMapImage(registryName + "_1"));
	}
	
}
