package ovinger.oving1.pongObjects;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import ovinger.oving1.GameDemo;

/**
 * Created by VWW on 26.01.2018.
 *
 * Player starts on the left. Computer starts on the right.
 */

public class PongPad {

	// PlayBox size: GameDemo.HEIGHT/2, centered.
	private Rectangle hitbox;
	private Vector2 position; // position = center
	private float velocity;
	private static final float MAX_VELOCITY = 150;
	
	public PongPad(boolean player){
		if (player) position = new Vector2(GameDemo.WIDTH /10, GameDemo.HEIGHT/2);
		else position = new Vector2(GameDemo.WIDTH * 9/10, GameDemo.HEIGHT/2);
		hitbox = new Rectangle(position.x-2, position.y-15, 4, 30);
		velocity = GameDemo.HEIGHT/6;
	}
	
	public void moveUp(){ position.add(0, Gdx.graphics.getDeltaTime() * velocity); }

	public void moveDown(){ position.add(0, Gdx.graphics.getDeltaTime() * -velocity); }
	
	public void computerMove(PongBall ball){
		if (ball.getPosition().y > position.y) moveUp(); else moveDown(); }
	
	public void moveUp(float dt){
		position.add(0, velocity*dt);
	}

	public void setPosition(float x, float y){ position.x=x; position.y=y; }
	
	public Vector2 getPosition() { return position; }
	
	public Rectangle getHitbox() {	return hitbox; }
	
	public float getVelocity() {	return velocity; }
	
	public void setVelocity(float speedY) { velocity = speedY; }
}
