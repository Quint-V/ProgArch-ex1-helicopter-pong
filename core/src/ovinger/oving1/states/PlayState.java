package ovinger.oving1.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ovinger.oving1.GameDemo;
import ovinger.oving1.sprites.Heli;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

/**
 * Created by VWW on 21.01.2018.
 */

public class PlayState extends State { // write like PlayState?

    private Heli helicopter;
    private BitmapFont font;
    private Texture base;

    public PlayState(GameStateManager gsm){
        super(gsm);
        base = new Texture("heli1.png");
        font = new BitmapFont();
        helicopter = new Heli(GameDemo.WIDTH/2-base.getWidth(), GameDemo.HEIGHT/2-base.getHeight());
        cam.setToOrtho(false, GameDemo.WIDTH, GameDemo.HEIGHT);
        base.dispose();
    }


    @Override
    protected void handleInput() {  // when touching, allow the pointer to drag the helicopter around.
                                    //
        if (Gdx.input.isTouched()){
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();
            if (helicopter.getHitbox().contains(x,y)){
                helicopter.setPosition(x,y);
            }
        }
/**/
    }

    @Override
    public void update(float dt) {
        handleInput();
        helicopter.update(dt);
        
        if (helicopter.getPosition().x <= 0 ||
                (helicopter.getPosition().x + helicopter.getTexture().getRegionWidth()) >= GameDemo.WIDTH){
            helicopter.reverseX();
        }
        if (helicopter.getPosition().y <= 0 ||
                (helicopter.getPosition().y + helicopter.getTexture().getRegionHeight()) >=
                        GameDemo.HEIGHT){
            helicopter.reverseY();
        }
        cam.update();
        
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(helicopter.getTexture(), helicopter.getPosition().x,
                helicopter.getPosition().y);
        font.draw(sb, "\n x: " + ( (int) helicopter.getPosition().x ) + "\n y: " +
                          ((int)helicopter.getPosition().y),
                  20,GameDemo.HEIGHT-20);
        sb.end();
    }

    @Override
    public void dispose() {
        helicopter.dispose();
        System.out.print("\n Play state disposed.");
    }
}
