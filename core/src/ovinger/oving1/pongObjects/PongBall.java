package ovinger.oving1.pongObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import ovinger.oving1.GameDemo;

/**
 * Created by VWW on 26.01.2018.
 */

public class PongBall {
	
	private Vector2 position, velocity;
	private Circle hitbox;
	private static final float START_VELOCITY = GameDemo.WIDTH/6+20;
	private Sound boing;
	
	public PongBall(){
		position = new Vector2(GameDemo.WIDTH/2, GameDemo.HEIGHT/2);
		velocity = new Vector2(-START_VELOCITY, 0);
		hitbox = new Circle(position.x, position.y, 5);
		boing = Gdx.audio.newSound(Gdx.files.internal("Mario_Coin.ogg"));
	}
	
	
	public void update(float dt){
		velocity.scl(dt);
		position.add(velocity.x, velocity.y);
		velocity.scl(1/dt);
		hitbox.setPosition(position.x, position.y);
	}
	
	// TODO: use direction to determine collision type
	public boolean collidesWithPad(PongPad pad){
		if (pad.isPlayer())
			return pad.getHitbox().contains(position.x + hitbox.radius, position.y);
		else return pad.getHitbox().contains(position.x - hitbox.radius, position.y);
	}

	// max angular offset from x axis: 75 deg
	
	public void bounceOffPlayer(PongPad pad) {
		velocity.setAngle(180-velocity.angle());
		float turn = (pad.getCenter().y - this.position.y);
		velocity.rotate(turn);
		if (velocity.x > 0) velocity.x = Math.abs(velocity.x)*-1;
		boing.play(0.3f);
/*		if (velocity.angle() > 270+75) velocity.setAngle(270+75);
		if (velocity.angle() < 270-75) velocity.setAngle(270-75); /**/
	}
	public void bounceOffCPU(PongPad pad) {
		velocity.setAngle(180-velocity.angle());
		float turn = (pad.getCenter().y - this.position.y);
		velocity.rotate(turn);
		if (velocity.x < 0) velocity.x = Math.abs(velocity.x);
		boing.play(0.3f);
/*		if (velocity.angle() < 90-75) velocity.setAngle(90-75);
		if (velocity.angle() > 90+75) velocity.setAngle(90+75); /**/
	}

	public void bounceOffWall() { velocity.y *= -1;}
	
	public Vector2 getPosition() { return position; }
	public float getRadius() { return hitbox.radius; }
	
	public Vector2 getVelocity(){ return velocity; }
	public void setVelocity(float x, float y){velocity.x=x; velocity.y=y;}
	
	public Circle getHitbox() {
		return hitbox;
	}
}
