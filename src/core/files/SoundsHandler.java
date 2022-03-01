package core.files;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SoundsHandler {

	public static final String SPACE_PRESS = "space_press";
	
	private Map<String, Clip> clips;
	
	
	public void init() {
		clips = new HashMap<>();
		
		register(SPACE_PRESS, getSound(SPACE_PRESS));
	}
	
	
	public Clip get(String key) {
		if(!clips.containsKey(key))
			register(key, getSound(key));
		
		Clip clip = clips.get(key);
		clip.setFramePosition(0);
		
		return clip;
	}
	
	public void clear(String key) {
		clips.remove(key);
	}
	
	public void clear(List<String> keys) {
		for(String key : keys) {
			clear(key);
		}
	}
	
	
	public void register(String key, Clip clip) {
		if(!clips.containsKey(key))
			clips.put(key, clip);
	}
	
	private Clip getSound(String fileName) {
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(FileHandler.getFile("sounds", fileName, "wav")));
			
			return clip;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public void playSound(Clip clip) {
		if(clip.isActive() || clip.isRunning()) {
			clip.stop();
		}
		
		clip.setFramePosition(0);
		clip.start();
	}

	public static void playSound(String key) {
		playSound(get(key));
	}
}
