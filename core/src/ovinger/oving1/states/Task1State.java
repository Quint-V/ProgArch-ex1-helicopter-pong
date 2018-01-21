package ovinger.oving1.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import ovinger.oving1.GameDemo;
import ovinger.oving1.sprites.Heli;

/**
 * Created by VWW on 21.01.2018.
 */

public class Task1State extends State { // write like PlayState?

    private Heli helicopter;


    public Task1State(GameStateManager gsm){
        super(gsm);
        helicopter = new Heli(GameDemo.WIDTH/2, GameDemo.HEIGHT*2/3);
        cam.setToOrtho(false, GameDemo.WIDTH/2, GameDemo.HEIGHT/2);

    }


    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        handleInput();
        helicopter.update(dt);

    }

    @Override
    public void render(SpriteBatch sb) {

    }

    @Override
    public void dispose() {

    }
}
