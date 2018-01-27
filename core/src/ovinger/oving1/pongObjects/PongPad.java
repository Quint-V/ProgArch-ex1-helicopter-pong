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
	private static final float BASE_VELOCITY = GameDemo.HEIGHT/4;
	private boolean isPlayer;
	private float padWidth, padHeight;
	
	public PongPad(boolean player){
		if (player){
			center = new Vector2(GameDemo.WIDTH * 8/10, GameDemo.HEIGHT/2);
			isPlayer = true;
			velocity = GameDemo.HEIGHT/2;
		}
		else {
			center = new Vector2(GameDemo.WIDTH * 2/10, GameDemo.HEIGHT/2);
			isPlayer = false;
			velocity = GameDemo.HEIGHT/4;
		}
		padWidth = 10; padHeight = 72;
		hitbox = new Rectangle(center.x- padWidth /2, center.y- padHeight /2, padWidth, padHeight);
		position = new Vector2(center.x- padWidth /2, center.y- padHeight /2);
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
	public float getWidth() { return padWidth;}
	public float getHeight() { return padHeight; }

	public void increaseVelocity(float speedY) { velocity += speedY; }
	public float getVelocity() {return velocity;}
	public float getBaseVelocity() {return BASE_VELOCITY;}
}
