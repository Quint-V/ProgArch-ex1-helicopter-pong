package ovinger.oving1.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import ovinger.oving1.GameDemo;

/**
 * Created by VWW on 21.01.2018.
 */

public class Heli {

    private Texture heli;
    private static final int DEFAULT_X_SPEED = 100, DEFAULT_Y_SPEED = 50;
    private Vector2 position, velocity;
    private Rectangle Hitbox;
//    private Anim heliAnim;
    private Texture f1, f2, f3, f4;
    private int pointer;
    private Array<Texture> frames;

    public Heli(int x, int y){
        position = new Vector2(x,y);
        velocity = new Vector2(DEFAULT_X_SPEED, DEFAULT_Y_SPEED);
        frames = new Array<Texture>();
        frames.add(new Texture("heli1.png"));
        frames.add(new Texture("heli2.png"));
        frames.add(new Texture("heli3.png"));
        frames.add(new Texture("heli4.png"));
        pointer = 0;
        heli = frames.get(0);
        Hitbox = new Rectangle(x,y, heli.getWidth(), heli.getHeight());
    }

    public void update(float dt){
        heli = frames.get(pointer);
        velocity.scl(dt);
        position.add(velocity.x, velocity.y);
        if (position.x <= 0 || position.x + heli.getWidth() >= GameDemo.WIDTH){
            velocity.x = -1 * velocity.x;
        }
        if (position.y <= 0 || position.y + heli.getHeight() >= GameDemo.HEIGHT){
            velocity.y = -1 * velocity.y;
        }
        velocity.scl(1/dt);
        Hitbox.setPosition(position.x, position.y);
    }

    public Rectangle getHitbox(){
        return Hitbox;
    }

    public Vector2 getPosition(){
        return position;
    }

    public void dispose(){
        frames.clear();
        heli.dispose();
    }

}
