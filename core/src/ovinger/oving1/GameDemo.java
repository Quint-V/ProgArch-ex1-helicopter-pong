package ovinger.oving1;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ovinger.oving1.states.GameStateManager;
import ovinger.oving1.states.MenuState;

public class GameDemo extends ApplicationAdapter {

	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;
	public static final String TITLE = "Helicopter";
	private SpriteBatch batch;
	private GameStateManager gsm = GameStateManager.getInstance();

	@Override
	public void create () {
		batch = new SpriteBatch();
		Gdx.gl.glClearColor(0.2f, 0.72f, 1, 1);
		gsm.push(new MenuState());
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}
	
	@Override
	public void dispose () {
		super.dispose();
	}
}
