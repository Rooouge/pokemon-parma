package core.obj.scripts;

import java.io.File;
import java.util.ArrayList;

import core.enums.GameStates;
import core.obj.entities.overworld.OverworldEntity;
import lombok.Getter;

@SuppressWarnings("serial")
@Getter
public class Script extends ArrayList<ScriptAction> {

	private final File file;
	private final OverworldEntity owner;
	private int index;
	
	
	public Script(File file, OverworldEntity owner) {
		super();
		this.file = file;
		this.owner = owner;
			
		index = 0;
	}
	
	
	public boolean execute() {
		get(index).execute();
		index++;
		
		boolean isOver = isOver();
		if(isOver)
			onEnd();
		
		return isOver;
	}
	
	public void onEnd() {
		index = 0;
		GameStates.set(GameStates.EXPLORATION);
	}
	
	public boolean isOver() {
		return index >= size();
	}
	
	public ScriptAction getNextAction() {
		if(isOver())
			return null;
		
		return get(index);
	}
	
	
	@Override
	public String toString() {
		String s = "Owner: " + owner.getData().getName() + "-" + owner.getData().getVariant();
		
		for(int i = 0; i < size(); i++) {
			s += "\n[" + i + "] " + get(i).getClass().getSimpleName();
		}
		
		return s;
	}
}
