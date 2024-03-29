package core.files;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SoundsHandler {

	public final String PRESS = "press";
	public final String DAMAGE = "damage";
	
	private Map<String, Clip> clips;
	
	
	public void init() {
		clips = new HashMap<>();
		
		register(PRESS, getSound(PRESS));
		register(DAMAGE, getSound(DAMAGE));
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
		clip.start();
	}

	public void playSound(String key) {
		playSound(get(key));
	}
}
