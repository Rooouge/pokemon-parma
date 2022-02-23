package core.exceptions;

import java.io.File;
import java.io.IOException;

@SuppressWarnings("serial")
public class MissingDirectoryException extends IOException {
		
	public MissingDirectoryException(File file) {
		super("Impossibile trovare la cartella: '" + file.getAbsolutePath() + "'");
	}
}
