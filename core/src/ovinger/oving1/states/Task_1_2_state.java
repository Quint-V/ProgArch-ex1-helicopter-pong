package ovinger.oving1.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ovinger.oving1.GameDemo;
import ovinger.oving1.sprites.Heli;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;


/**
 * Created by VWW on 21.01.2018.
 */

public class Task_1_2_state extends State { // write like Task_1_2_state?
    
    private Heli helicopter;
    private BitmapFont font;
    private Music bgm;
    
    public Task_1_2_state(GameStateManager gsm){
        super(gsm);
        Texture base = new Texture("heli1.png");
        font = new BitmapFont();
        helicopter = new Heli(GameDemo.WIDTH/2-base.getWidth()+100, GameDemo
                .HEIGHT/2-base.getHeight()-base.getHeight());
        cam.setToOrtho(false, GameDemo.WIDTH, GameDemo.HEIGHT);
        base.dispose();
        
        bgm = Gdx.audio.newMusic(Gdx.files.internal("rosahelikopter.mp3"));
        bgm.setVolume(0.4f);
        bgm.setLooping(true);
        bgm.play();
        bgm.setPosition(33);
    }

    
    @Override
    protected void handleInput() {  // when touching, let heli drift towards pointer.
        if (Gdx.input.isKeyJustPressed(Input.Keys.R))
            gsm.set(new MenuState(gsm));
        if (Gdx.input.isTouched()) {
                Vector2 diff = new Vector2(
                        Gdx.input.getX() - helicopter.getPosition().x - helicopter.getWidth() / 2,
                        Gdx.input.getY() - ( helicopter.getPosition().y - helicopter.getHeight() / 2 ));
                helicopter.setVelocity(diff.x, diff.y * .7f);
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        helicopter.update(dt);
        cam.update();
    }
    

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(helicopter.getTexture(), helicopter.getPosition().x,
                GameDemo.HEIGHT-helicopter.getPosition().y);
        font.draw(sb, "\nHelicopter 1\n x: " + ( (int) helicopter.getPosition().x ) +
                          "\n y: " + (800-(int) helicopter.getPosition().y),
                  20,GameDemo.HEIGHT-20);
        font.draw(sb, "Press R to reset", GameDemo.WIDTH/12,GameDemo.HEIGHT*0.05f);
        sb.end();
    }

    @Override
    public void dispose() {
        helicopter.dispose();
        font.dispose();
        bgm.stop(); bgm.dispose();
        System.out.print("\n Play state (tasks 1,2) disposed.");
    }
}
