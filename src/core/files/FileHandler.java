package core.files;

import java.io.File;
import java.io.IOException;

import core.exceptions.MissingDirectoryException;
import core.exceptions.MissingFileException;
import jutils.config.Config;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FileHandler {

	public File getFile(String dirName, String name, String ext) throws IOException {
		File dir = new File(Config.getValue(dirName));
		
		if(!dir.exists())
			throw new MissingDirectoryException(dir);
		
		File file = new File(dir, name + "." + ext);
		
		if(!file.exists())
			throw new MissingFileException(file);
		
		return file;
	}
}
