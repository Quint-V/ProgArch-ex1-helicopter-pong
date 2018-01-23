package ovinger.oving1.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import ovinger.oving1.GameDemo;

/**
 * Created by VWW on 21.01.2018.
 */

public class Heli {

    private static final int DEFAULT_X_SPEED = 150, DEFAULT_Y_SPEED = 150*9/16;
    private Vector2 position, velocity;

    private Texture texture;
    private Anim heliAnim;
    private Rectangle Hitbox;

    public Heli(int x, int y){
        position = new Vector2(x,y);
        velocity = new Vector2(DEFAULT_X_SPEED, DEFAULT_Y_SPEED);
        texture = new Texture("heliAnim.png");
        heliAnim = new Anim(new TextureRegion(texture), 4, 0.4f);
        Hitbox = new Rectangle(x,y, heliAnim.getFrame().getRegionX(),
                               heliAnim.getFrame().getRegionY());
    }
    
    public void setPosition(int x, int y){
        position.x = x;
        position.y = y;
    }

    public void update(float dt){
        heliAnim.update(dt);
        velocity.scl(dt);
        position.add(velocity.x, velocity.y);
        velocity.scl(1/dt);
        Hitbox.setPosition(position.x, position.y);
    }
    
    public void reverseX(){
        velocity.x = -1 * velocity.x;
    }

    public void reverseY(){
        velocity.y = -1 * velocity.y;
    }
   
    public boolean collides(Rectangle hitbox){
        return hitbox.overlaps(Hitbox);
    }

    public TextureRegion getTexture(){
        return heliAnim.getFrame();
    }

    public Rectangle getHitbox(){
        return Hitbox;
    }

    public Vector2 getPosition(){
        return position;
    }

    public void dispose(){
        texture.dispose();
    }

}
