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
    private int pointerY;

    public MenuState(GameStateManager gsm){
        super(gsm);
        cam.setToOrtho(false, GameDemo.WIDTH, GameDemo.HEIGHT);
        playBtn = new Texture("playbtn.png");
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            pointerY = Gdx.input.getY();
            if (pointerY < 200-playBtn.getHeight()) {
//            if (GameDemo.HEIGHT-(200 + playBtn.getHeight()/2) < pointerY &&
//					pointerY < GameDemo.HEIGHT - 200 + playBtn.getHeight()) {
                gsm.set(new PlayState(gsm));
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
        sb.begin();
        sb.draw(playBtn, cam.position.x - playBtn.getWidth() / 2 ,
                GameDemo.HEIGHT - (200 - playBtn.getHeight()) );
        sb.end();
    }

    @Override
    public void dispose() {
        playBtn.dispose();
        System.out.print("\n Menu state disposed.");
    }

}
