package core;

import jutils.log.LogLevel;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Log {
	
	private LogLevel logLevel = new LogLevel("2DCORE", System.out);
	
	
	public void log(String msg) {
		jutils.log.Log.println(logLevel, msg);
	}

}
