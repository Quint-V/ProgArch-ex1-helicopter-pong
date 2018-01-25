package ovinger.oving1.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import ovinger.oving1.GameDemo;
import ovinger.oving1.sprites.Heli;

/**
 * Created by VWW on 25.01.2018.
 */

public class Task_3_state extends State{
	
	
	private Array<Heli> helicopter;
	private Texture base;
	private static int offset = 0;
	
	public Task_3_state(GameStateManager gsm){
		super(gsm);
		base = new Texture("heli1.png");
		helicopter = new Array<Heli>();

		helicopter.add(new Heli(GameDemo.WIDTH/2-base.getWidth()+70,
								GameDemo.HEIGHT*5/6,
								-30, -15));
		
		helicopter.add(new Heli(GameDemo.WIDTH/2-base.getWidth(),
								GameDemo.HEIGHT* 4/7,
								-20, -20));

		helicopter.add(new Heli(GameDemo.WIDTH/2 - base.getWidth() - 100,
								GameDemo.HEIGHT * 5/6 ,
								30,30));

		helicopter.add(new Heli(GameDemo.WIDTH/2-base.getWidth()-150,
								GameDemo.HEIGHT*2/6,
								45, 10));

		cam.setToOrtho(false, GameDemo.WIDTH, GameDemo.HEIGHT);
		base.dispose();
	}
	
	
	@Override
	protected void handleInput() {  // when touching, let heli drift towards pointer.
		if (Gdx.input.isKeyJustPressed(Input.Keys.R))
			gsm.set(new MenuState(gsm));
	}
	
	@Override
	public void update(float dt) {
		handleInput();
		for (int i = 0; i < helicopter.size; i++){
		Heli h1 = helicopter.get(i);
		h1.update(dt);
		for (int j = 0; j < helicopter.size; j++){
		Heli h2 = helicopter.get(j);
		if ( i!= j) {
			if (h1.getHitbox().overlaps(h2.getHitbox())) {
				Vector3 p1 = h1.getPosition();
				Vector3 p2 = h1.getPosition();
				int DX = h1.getWidth(), DY = h1.getHeight();
					if ( p1.x + DX == p2.x - offset){	// p1 hits p2 from left
						h1.goLeft(); h2.goRight();
					}
					else if (p1.x == p2.x + DX - offset){ // p1 hits p2, from the right
						h1.goRight(); h2.goLeft();
					}
					else if (p1.y == p2.y + DY - offset ){ // p1 hits p2 from top
						h1.goUp(); h2.goDown();
					}
					else if (p1.y + DY - offset == p2.y){ // p1 hits p2 from bottom
						h1.goDown(); h2.goUp();
					}
				}
			}
		}
		}
		cam.update();
	}
	
	
	
	
	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		for (Heli h:helicopter) {
			h.update(Gdx.graphics.getDeltaTime());

			sb.draw(h.getTexture(), h.getPosition().x,
					GameDemo.HEIGHT-h.getPosition().y);
		}
		sb.end();
	}
	
	@Override
	public void dispose() {
		for (Heli h:helicopter)	h.dispose();
		System.out.print("\n Play state disposed.");
	}
	
}
