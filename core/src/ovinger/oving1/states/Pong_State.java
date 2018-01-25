package ovinger.oving1.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ovinger.oving1.GameDemo;
import ovinger.oving1.sprites.Heli;

/**
 * Created by VWW on 25.01.2018.
 */

public class Pong_State extends State { // write like Task_1_2_state?
	
	private BitmapFont font;
	
	public Pong_State(GameStateManager gsm) {
		super(gsm);
		font = new BitmapFont();
		cam.setToOrtho(false, GameDemo.WIDTH, GameDemo.HEIGHT);
	}
	
	@Override
	protected void handleInput() {
		if (Gdx.input.isKeyJustPressed(Input.Keys.R))
			gsm.set(new MenuState(gsm));
		// TODO: let block move at steady pace towards position touched on the screen.
	}
	
	@Override
	public void update(float dt) {
		handleInput();
		// TODO: make opponent drift at a steady speed towards the ball's position
	}
	
	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(cam.combined);
		sb.begin();

		sb.end();
	}
	
	@Override
	public void dispose() {
		System.out.print("\n Play state disposed.");
		
	}
}