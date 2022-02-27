package core.obj.scripts.statescripts;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import core.obj.scripts.Script;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
public class StateScripts extends ArrayList<Script> {

	@Getter
	@Setter
	protected int state;
	protected final File stateFile;
	
	
	public StateScripts(File stateFile) throws IOException {
		this.stateFile = stateFile;
		
		initState();
	}
	
	
	private void writeState(int state) throws IOException {
		this.state = state;
//		System.out.println(stateFile.getName() + " = " + stateFile.exists());
		
		try (
			FileWriter w = new FileWriter(stateFile);
		) {
			w.write("0");
		}
	}
	
	private void initState() throws IOException {
		if(!stateFile.exists() && stateFile.createNewFile()) {
			writeState(0);
		}
		else {
			try (
				FileReader r = new FileReader(stateFile);
			) {
				int state = Integer.parseInt("" + (char) r.read());
				this.state = state;
			}
		}	
	}
	
	public Script getCurrent() {
		return get(state);
	}
}
