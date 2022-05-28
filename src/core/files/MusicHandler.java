package core.files;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import core.obj.maps.Map;
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
	
	
	public void playMapMusic(Map map) {
		Threads.run(() -> {
			MapData data = map.getData();
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
	
	public void stopMapMusic(Map map) {
		Clip music = map.getData().getMusic();
		music.stop();
		music.setFramePosition(0);
	}
	
	
	public void playMusic(Clip music) {
		Threads.run(() -> {
			music.setFramePosition(0);
			music.start();
		});
	}
	
	public void stopMusic(Clip music) {
		music.stop();
		music.setFramePosition(0);
	}
}
