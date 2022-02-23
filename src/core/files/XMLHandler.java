package core.files;

import java.io.File;
import java.io.IOException;

import lombok.experimental.UtilityClass;

@UtilityClass
public class XMLHandler {
	
	public File getFile(String dirName, String name) throws IOException {
		return FileHandler.getFile(dirName, name, "xml");
	}
}
