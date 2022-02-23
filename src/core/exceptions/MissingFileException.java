package core.exceptions;

import java.io.File;
import java.io.IOException;

@SuppressWarnings("serial")
public class MissingFileException extends IOException {
		
	public MissingFileException(File file) {
		super("Impossibile trovare il file: '" + file.getAbsolutePath() + "'");
	}
}
