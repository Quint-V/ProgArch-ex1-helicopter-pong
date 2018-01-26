package ovinger.oving1.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Vector3;

import ovinger.oving1.GameDemo;

/**
 * Created by VWW on 21.01.2018.
 */

public class Heli {

    private static final int DEFAULT_X_SPEED = 150, DEFAULT_Y_SPEED = 150*9/16;
    private Vector3 position, velocity;

    private Texture texture;
    private Anim heliAnim;
    private Rectangle Hitbox;
    private boolean FACING_RIGHT;

    public Heli(int x, int y){
        position = new Vector3(x,y, 0);
        velocity = new Vector3(DEFAULT_X_SPEED, DEFAULT_Y_SPEED, 0);
        texture = new Texture("heliAnim.png");
        heliAnim = new Anim(new TextureRegion(texture), 4, 0.4f);
        Hitbox = new Rectangle(x,y, heliAnim.getFrame().getRegionWidth(),
                               heliAnim.getFrame().getRegionHeight());
        FACING_RIGHT = false;
        fixOrientation();
    }
    
    public Heli(int x, int y, int speedX, int speedY){
        position = new Vector3(x,y, 0);
        velocity = new Vector3(speedX, speedY, 0);
        texture = new Texture("heliAnim.png");
        heliAnim = new Anim(new TextureRegion(texture), 4, 0.4f);
        Hitbox = new Rectangle(x,y, heliAnim.getFrame().getRegionWidth(),
                               heliAnim.getFrame().getRegionHeight());
        FACING_RIGHT = false;
        fixOrientation();
    }
    
    public int getHeight() {return heliAnim.getFrame().getRegionHeight();}
    public int getWidth() {return heliAnim.getFrame().getRegionWidth();}
    
    public void setPosition(int x, int y){
        position.x = x;
        position.y = y;
    }

    public void setVelocity(float x, float y){
        velocity.x=x; velocity.y=y; fixOrientation();
    }
    
    public void update(float dt) {
        heliAnim.update(dt);
        float slowFactor = 0.01f;
        if (Math.abs(velocity.x) > 70) velocity.x -= slowFactor * velocity.x;
        if (Math.abs(velocity.y) > 50) velocity.y -= slowFactor * velocity.y;
//        if (velocity.y > 20) velocity.y -= slow; else if (velocity.y < -20) velocity.y += slow;
        velocity.scl(dt);
        position.add(velocity.x, velocity.y, 0);
        velocity.scl(1 / dt);
        Hitbox.setPosition(position.x, position.y);
        int boundary = 2;
        if (position.x <= boundary) goRight();
        else if (position.y - getHeight() <= boundary ) goUp();
        else if (position.x + getWidth() >= GameDemo.WIDTH - boundary) goLeft();
        else if (position.y >= GameDemo.HEIGHT - boundary) goDown();
    }

    public void fixOrientation(){
        if (velocity.x >= 0) {
            if (!FACING_RIGHT) {
                heliAnim.flipFrames();
                FACING_RIGHT = true;
            }
        }
        else if (FACING_RIGHT) { // and moving left
            heliAnim.flipFrames();
            FACING_RIGHT = false;
        }
    }
    
    public void reverseX(){ if (velocity.x > 0) goLeft(); else goRight();
    }
    
    public void reverseY(){ if (velocity.y > 0) goDown(); else goUp();
    }
    
    public void goLeft(){ velocity.x = -1*Math.abs(velocity.x); fixOrientation(); }
    public void goRight(){ velocity.x = Math.abs(velocity.x); fixOrientation(); }
    
    public void goUp(){ velocity.y = Math.abs(velocity.y); }
    public void goDown(){ velocity.y = -1 * Math.abs(velocity.y); }
   
    public boolean collides(Rectangle hitbox){
        return hitbox.overlaps(Hitbox);
    }

    public TextureRegion getTexture(){
        return heliAnim.getFrame();
    }

    public Rectangle getHitbox(){
        return Hitbox;
    }

    public Vector3 getPosition(){
        return position;
    }
    
    public Vector3 getVelocity(){ return velocity; }

    public void dispose(){
        texture.dispose();
    }

}
