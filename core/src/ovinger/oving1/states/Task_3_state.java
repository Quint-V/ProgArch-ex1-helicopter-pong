package ovinger.oving1.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
	private static final int START_VELOCITY = 100;
	private BitmapFont font;
	
	public Task_3_state(GameStateManager gsm){
		super(gsm);
		base = new Texture("heli1.png");
		helicopter = new Array<Heli>();
		font = new BitmapFont();
		Vector3 random = new Vector3().setToRandomDirection().scl(START_VELOCITY);
		helicopter.add(new Heli(GameDemo.WIDTH/2 + 70,
								GameDemo.HEIGHT*5/6,
								(int) random.x, (int) random.y));

		random.setToRandomDirection().scl(START_VELOCITY);
		helicopter.add(new Heli(GameDemo.WIDTH/2 - 170,
								GameDemo.HEIGHT * 5/6 ,
								(int) random.x,(int) random.y));
		
		random.setToRandomDirection().scl(START_VELOCITY);
		helicopter.add(new Heli(GameDemo.WIDTH/2 - 100,
								GameDemo.HEIGHT* 4/7,
								(int) random.x, (int) random.y));
		
		random.setToRandomDirection().scl(START_VELOCITY);
		helicopter.add(new Heli(GameDemo.WIDTH/2 - 170,
								GameDemo.HEIGHT*1/3 ,
								(int) random.x, (int) random.y));
		
		random.setToRandomDirection().scl(START_VELOCITY);
		helicopter.add(new Heli(GameDemo.WIDTH/2 + 70,
								GameDemo.HEIGHT*1/3,
								(int) random.x, (int) random.y));
		
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
						Vector3 p2 = h2.getPosition();
						Vector3 diag = new Vector3(h1.getWidth(), h1.getHeight(), 0);
						float diagSlope = diag.y/diag.x;
						Vector3 diff = new Vector3(p2.x-p1.x, p2.y-p1.y, 0);
						float slope = diff.y/diff.x;
						if (slope == diagSlope) {
							h1.reverseX(); h1.reverseY(); h2.reverseX(); h2.reverseY();
						}
						if (Math.abs(slope) > Math.abs(diagSlope)){	// top-bottom strike
							if (diff.y > 0) {		// h1 hits h2 from below
								h1.goDown(); h2.goUp(); }
							else if (diff.y < 0){	// h1 hits h2 from above
								h1.goUp(); h2.goDown(); }
							}
						else { 	//  side strike
							if (diff.x > 0) { // h1 hits h2 from left
								h1.goLeft(); h2.goRight(); }
							else {h1.goRight(); h2.goLeft(); }
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
		font.draw(sb, "Press R to reset", GameDemo.WIDTH*1/12,GameDemo.HEIGHT*0.05f);
		sb.end();
	}
	
	@Override
	public void dispose() {
		for (Heli h:helicopter)	h.dispose();
		System.out.print("\n Play state (task 3) disposed.");
	}
	
}
