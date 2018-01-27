package ovinger.oving1.pongObjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import ovinger.oving1.GameDemo;

/**
 * Created by VWW on 26.01.2018.
 */

public class PongBall {
	
	private Vector2 position, velocity;
	private Circle hitbox;
	private static final float START_VELOCITY = GameDemo.WIDTH/6, SPEEDUP = 25f, MAX_SPEED =
			GameDemo.WIDTH;
	
	public PongBall(){
		position = new Vector2(GameDemo.WIDTH/2, GameDemo.HEIGHT/2);
		velocity = new Vector2(START_VELOCITY, 0);
		hitbox = new Circle(position.x, position.y, 4);
	}
	
	public void update(float dt){
		float boundary = hitbox.radius/4;
		if (position.y+hitbox.radius<=boundary || position.y>=GameDemo.HEIGHT-boundary)
			velocity.y *= -1;
		velocity.scl(dt);
		position.add(velocity.x, velocity.y);
		velocity.scl(1 / dt);
		hitbox.setPosition(position.x, position.y);
	}
	
	// TODO: use direction to determine collision type
	public boolean collidesWithPad(PongPad pad){
		if (velocity.x > 0) return pad.getHitbox().contains(position.x + hitbox.radius, position.y);
		else return pad.getHitbox().contains(position.x - hitbox.radius, position.y);
	}
	
	public void bounce(PongPad pad){ velocity.x *= -1; }
	
	public Vector2 getPosition() {
		return position;
	}
	
	public Circle getHitbox() {
		return hitbox;
	}
}