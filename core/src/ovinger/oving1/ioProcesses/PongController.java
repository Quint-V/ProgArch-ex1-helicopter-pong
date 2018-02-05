package ovinger.oving1.ioProcesses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import ovinger.oving1.GameDemo;
import ovinger.oving1.pongObjects.PongPad;
import ovinger.oving1.states.GameStateManager;
import ovinger.oving1.states.MenuState;

/**
 * Created by VWW on 05.02.2018.
 */

public class PongController {
	
	private static boolean GAME_OVER;
	
	private final static PongController INSTANCE = new PongController();
	public static PongController getInstance() {return INSTANCE;}

	private PongController(){GAME_OVER=false;}
	
	public void handleInput(PongPad player) {
		if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
			GameStateManager gsm = GameStateManager.getInstance();
			gsm.set(new MenuState());
		}
		
		if (!GAME_OVER)
			if (Gdx.input.isTouched()) {
				int pointer = GameDemo.HEIGHT - Gdx.input.getY();
				if (pointer > player.getCenter().y &&
						player.getPosition().y + player.getHitbox().getHeight() < GameDemo.HEIGHT*3/4)
					player.moveUp();
				else if  (pointer < player.getCenter().y &&
						player.getPosition().y > GameDemo.HEIGHT/4) player.moveDown();
			}
	}
	
}
