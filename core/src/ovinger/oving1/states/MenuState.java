package ovinger.oving1.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ovinger.oving1.GameDemo;

/**
 * Created by VWW on 21.01.2018.
 */

public class MenuState extends State{

    private Texture playBtn;

    public MenuState(GameStateManager gsm){
        super(gsm);
        cam.setToOrtho(true, GameDemo.WIDTH/2, GameDemo.HEIGHT/2);
        playBtn = new Texture("playbtn.png");
    }

    @Override
    protected void handleInput() {
        int pointerY = Gdx.input.getY();
        if (Gdx.input.justTouched()) {
            if (0 < pointerY && pointerY < 200) {
                gsm.set(new Task1State(gsm));
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.draw(playBtn, cam.position.x - playBtn.getWidth() / 2 , 200-playBtn.getHeight()/2);
    }

    @Override
    public void dispose() {

    }

}
