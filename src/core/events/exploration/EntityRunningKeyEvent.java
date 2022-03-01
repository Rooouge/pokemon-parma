package core.events.exploration;

import core.enums.Directions;
import core.enums.GameStates;
import core.gui.screen.content.exploration.ExplorationKeyPressHandler;
import core.obj.entities.overworld.OverworldEntity;
import jutils.threads.Threads;

public class EntityRunningKeyEvent extends ExplorationEntityKeyEvent {

	public EntityRunningKeyEvent(int keyCode, Directions dir, OverworldEntity entity, ExplorationKeyPressHandler handler) {
		super(keyCode, dir, entity, handler, GameStates.EXPLORATION);
	}
	

	@Override
	public void execute() {
		entity.getData().setRunning(!entity.getData().isRunning());
//		System.out.println("Running " + entity.getData().isRunning());
		active = false;
		
		Threads.run(() -> {
			try {
				Thread.sleep(250);
				handler.setNoEventActive();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}

	
}
