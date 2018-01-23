package ovinger.oving1.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ovinger.oving1.GameDemo;
import ovinger.oving1.sprites.Heli;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;


/**
 * Created by VWW on 21.01.2018.
 */

public class PlayState extends State { // write like PlayState?
    
    private Heli heli_1, heli_2;
    private BitmapFont font;
    private Texture base;

    public PlayState(GameStateManager gsm){
        super(gsm);
        base = new Texture("heli1.png");
        font = new BitmapFont();
        heli_1 = new Heli(GameDemo.WIDTH/2-base.getWidth(), GameDemo
                .HEIGHT/2-base.getHeight());
        heli_2 = new Heli(GameDemo.WIDTH/2-base.getWidth(), GameDemo
                .HEIGHT/4-base.getHeight());
        cam.setToOrtho(false, GameDemo.WIDTH, GameDemo.HEIGHT);
        base.dispose();
    }

    
    @Override
    protected void handleInput() {  // when touching, allow the pointer to drag the helicopters around.
        
        if (Gdx.input.isTouched()) {
            float x = Gdx.input.getX();
            float y = Gdx.input.getY();
            if (2 < x && x + 2 < GameDemo.WIDTH - heli_1.getWidth() &&
                        heli_1.getHeight() < y + 2 && y + 2 < GameDemo.HEIGHT)
/*            if (heli_1.getPosition().x < x && x < heli_1.getPosition().x + heli_1.getWidth()
                    && heli_1.getPosition().y < y && y < heli_1.getPosition().y +
                    heli_1.getHeight()
                    ){
                /**/
                    cam.unproject(heli_1.getPosition().set(Gdx.input.getX(), Gdx.input.getY(), 0));
                    heli_1.setVelocity(Gdx.input.getDeltaX()*10, Gdx.input.getDeltaY() * -10);
                }
        }
    
    

    @Override
    public void update(float dt) {
        handleInput();
        heli_1.update(dt);
        heli_2.update(dt);
        cam.update();
        }


    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(heli_1.getTexture(), heli_1.getPosition().x, heli_1.getPosition().y);
        sb.draw(heli_2.getTexture(), heli_2.getPosition().x, heli_2.getPosition().y);

        font.draw(sb, "\nHelicopter 1\n x: " + ( (int) heli_1.getPosition().x ) +
                          "\n y: " + ((int) heli_1.getPosition().y),
                  20,GameDemo.HEIGHT-20);
        sb.end();
    }

    @Override
    public void dispose() {
        heli_1.dispose();
        System.out.print("\n Play state disposed.");
    }
}
