package ovinger.oving1.pongObjects;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import ovinger.oving1.GameDemo;

/**
 * Created by VWW on 26.01.2018.
 *
 * Player starts on the right.
 */

public class PongPad {

	// PlayBox size: GameDemo.HEIGHT/2, centered.
	private Rectangle hitbox;
	private Vector2 position, center;
	private float velocity;
	private static final float MAX_VELOCITY = 250;
	private boolean isPlayer;
	
	public PongPad(boolean player){
		if (player){
			center = new Vector2(GameDemo.WIDTH * 8/10, GameDemo.HEIGHT/2);
			isPlayer = true;
		}
		else {
			center = new Vector2(GameDemo.WIDTH * 2/10, GameDemo.HEIGHT/2);
			isPlayer = false;
		}
		hitbox = new Rectangle(center.x-3, center.y-30, 6, 60);
		position = new Vector2(center.x-3, center.y-30);
		velocity = GameDemo.HEIGHT/4;
	}
	
	boolean isPlayer(){ return this.isPlayer; }
	
	public void moveUp(){
		position.add(0, Gdx.graphics.getDeltaTime() * velocity);
		center.add(0, Gdx.graphics.getDeltaTime() * velocity);
		hitbox.setPosition(position.x, position.y);
	}
	public void moveDown() {
		position.add(0, Gdx.graphics.getDeltaTime() * -velocity);
		center.add(0, Gdx.graphics.getDeltaTime() * -velocity);
		hitbox.setPosition(position.x, position.y);
	}
	
	public void computerMoveTo(float y){
		if (y > this.center.y && GameDemo.HEIGHT*3/4 > position.y + hitbox.getHeight()) moveUp();
		else if (y < center.y && GameDemo.HEIGHT/4 < position.y) moveDown();
	}

	public Vector2 getPosition() { return position; }
	public Vector2 getCenter(){
		return center;}
	
	public Rectangle getHitbox() {	return hitbox; }
	public float getWidth() { return hitbox.getWidth();}
	public float getHeight() { return hitbox.getHeight(); }

	public float getVelocity() {	return velocity; }
	public void setVelocity(float speedY) { velocity = speedY; }
}
