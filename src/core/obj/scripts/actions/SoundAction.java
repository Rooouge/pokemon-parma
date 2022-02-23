package core.obj.scripts.actions;

import javax.sound.sampled.Clip;

import core.files.SoundsHandler;
import core.obj.scripts.ScriptAction;

public class SoundAction extends ScriptAction {

	private final Clip sound;
	
	
	public SoundAction(Clip sound) {
		super(false, NO_DELAY);
		this.sound = sound;
	}
	
	
	@Override
	public void execute() {
		super.execute();
		
		SoundsHandler.playSound(sound);
	}
}
