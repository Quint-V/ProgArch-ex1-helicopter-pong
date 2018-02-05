package ovinger.oving1.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by VWW on 21.01.2018.
 */

public abstract class State {

    protected OrthographicCamera cam;
    protected Vector2 mouse;
    protected GameStateManager gsm;
    protected BitmapFont font;
    protected Music bgm;
    
    protected State(){
        gsm = GameStateManager.getInstance();
        cam = new OrthographicCamera();
        mouse = new Vector2();
        font = new BitmapFont();
    }

    protected abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb);
    public abstract void dispose();

/**/

}
