package core.exceptions;

import java.io.IOException;

@SuppressWarnings("serial")
public class ParsingActionException extends IOException {

	public ParsingActionException(String file, int index, String line) {
		super("[" + file + "] Failed to parse line " + index + ": " + line);
	}
}
