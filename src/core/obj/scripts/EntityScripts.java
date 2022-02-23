package core.obj.scripts;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
public class EntityScripts extends ArrayList<Script> {

	@Getter
	@Setter
	private int state;
	private final File stateFile;
	
	public EntityScripts(File file) throws IOException {
		stateFile = new File(file.getParentFile(), file.getName() + ".state");
		
		initState();
	}
	
	
	private void readState(int state) throws IOException {
		this.state = state;
//		System.out.println(stateFile.getName() + " = " + stateFile.exists());
		
		if(!stateFile.exists())
			stateFile.createNewFile();
		
		try (
			FileWriter w = new FileWriter(stateFile);
		) {
			w.write("0");
		}
	}
	
	private void initState() throws IOException {
		if(!stateFile.exists()) {
			readState(0);
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
