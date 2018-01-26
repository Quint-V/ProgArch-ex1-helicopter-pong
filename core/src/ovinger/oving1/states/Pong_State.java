package ovinger.oving1.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import ovinger.oving1.GameDemo;
import ovinger.oving1.pongObjects.PongBall;
import ovinger.oving1.pongObjects.PongPad;

/**
 * Created by VWW on 25.01.2018.
 */

public class Pong_State extends State { // write like Task_1_2_state?
	
	private BitmapFont font;
	private ShapeRenderer shapeRenderer;
	private PongBall ball;
	private PongPad player, computer;
	private boolean GAME_OVER;
	private int PLAYER_SCORE, COMPUTER_SCORE;
	
	
	public Pong_State(GameStateManager gsm) {
		super(gsm);
		font = new BitmapFont();
		player = new PongPad(true);
		computer = new PongPad(false);
		ball = new PongBall();
		cam.setToOrtho(false, GameDemo.WIDTH, GameDemo.HEIGHT);
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		GAME_OVER = false; PLAYER_SCORE = 0; COMPUTER_SCORE = 0;
	}
	
	private void newGame(boolean playerWin){
		if (playerWin) PLAYER_SCORE++; else COMPUTER_SCORE++;
		if (PLAYER_SCORE==21 || COMPUTER_SCORE==21) return;	// let update()/render() finish.
		player = new PongPad(true);
		computer = new PongPad(false);
		ball = new PongBall();
	}
	
	@Override
	protected void handleInput() {
		if (Gdx.input.isKeyJustPressed(Input.Keys.R))
			gsm.set(new MenuState(gsm));
		// TODO: let player pad move at a steady pace towards position touched on the screen.
	}
	
	@Override
	public void update(float dt) {
		handleInput();
		if (! GAME_OVER){ // PLAY
			// TODO: make computer player drift at a steady speed towards the ball's position

		
			
		
		} else { // GAME OVER
		
		}
	}
	
	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		if (GAME_OVER){
			font.draw(sb, "Task 3", cam.position.x, GameDemo.HEIGHT*3f/6);
		}
		sb.end();
		font.draw(sb, "Press R to reset", GameDemo.WIDTH*1/12,GameDemo.HEIGHT*0.05f);
	}
	
	@Override
	public void dispose() {
		System.out.print("\n Play state disposed.");
		
	}
}