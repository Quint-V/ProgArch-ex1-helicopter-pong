package ovinger.oving1.pongObjects;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import ovinger.oving1.GameDemo;

/**
 * Created by VWW on 26.01.2018.
 *
 * Player starts on the left. Computer starts on the right.
 */

public class PongPad {

	private Rectangle hitbox;
	private Vector2 position, velocity, center;
	private static final float MAX_VELOCITY = 150;
	
	public PongPad(boolean player){
		if (player) position = new Vector2(GameDemo.WIDTH /10, GameDemo.HEIGHT/2);
		else position = new Vector2(GameDemo.WIDTH * 9/10, GameDemo.HEIGHT/2);
		hitbox = new Rectangle(position.x-2, position.y-15, 4, 30);
		velocity = new Vector2(0,0);
	}
	
	public void setPosition(float x, float y){ position.x=x; position.y=y; }
	
	public Vector2 getPosition(){ return position; }
	
	public Rectangle getHitbox() {	return hitbox; }
	
	public Vector2 getVelocity() {	return velocity; }
	
	public void setVelocity(float speedX, float speedY) { velocity.set(speedX, speedY); }
}
