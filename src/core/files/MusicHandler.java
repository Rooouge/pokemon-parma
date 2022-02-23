package core.files;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import core.obj.maps.MapData;
import jutils.threads.Threads;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MusicHandler {

	
	public Clip get(String resName) {
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(FileHandler.getFile("musics", resName, "wav")));
			
			return clip;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public void playMapMusic(MapData data) {
		Threads.run(() -> {
			Clip music = data.getMusic();
			
			music.setFramePosition(0);
			music.start();
			
			while(true) {
				if(music.getMicrosecondPosition() == data.getLoopAt()) {
					music.setMicrosecondPosition(data.getLoopTo());
				}
			}
		});
	}
	
	public void stopMapMusic(MapData data) {
		Clip music = data.getMusic();
		music.stop();
		music.setFramePosition(0);
	}
}
