package ovinger.oving1.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ovinger.oving1.GameDemo;
import ovinger.oving1.ioProcesses.PongController;
import ovinger.oving1.pongObjects.PongBall;
import ovinger.oving1.pongObjects.PongPad;
import ovinger.oving1.rendringProcesses.PongView;

/**
 * Created by VWW on 25.01.2018.
 */

public class PongStateModel extends State { // write like Task_1_2_state?
	
	private PongBall ball;
	private PongPad player, computer;
	private boolean GAME_OVER;
	private int PLAYER_SCORE, COMPUTER_SCORE, scoreToWin;
	private float TIME_PASSED;
	private PongView renderer;
	private PongController controller;
	
	
	PongStateModel() {
		super();
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.1f);
		font = new BitmapFont(true);
		renderer = PongView.getInstance();
		controller = PongController.getInstance();

		player = new PongPad(true);
		computer = new PongPad(false);
		ball = new PongBall();
		cam.setToOrtho(true, GameDemo.WIDTH, GameDemo.HEIGHT);

		GAME_OVER = false;
		PLAYER_SCORE = 0;
		COMPUTER_SCORE = 0;
		TIME_PASSED = 0;
		scoreToWin = 1;

		bgm = Gdx.audio.newMusic(Gdx.files.internal("tetrisremix.mp3"));
		bgm.setLooping(true);
		bgm.setVolume(0.2f);
		bgm.play();
	}
	
	private void playerWinRound(boolean win) {
		if (win) PLAYER_SCORE++; else COMPUTER_SCORE++;
		if (PLAYER_SCORE == scoreToWin || COMPUTER_SCORE == scoreToWin) {GAME_OVER = true; return; }
		player = new PongPad(true);
		computer = new PongPad(false);
		ball = new PongBall();
		TIME_PASSED = 0;
	}
	
	@Override		// functions as controller
	protected void handleInput() {
		controller.handleInput(player);
	}
	
	@Override	// functions as model
	public void update(float dt) {
		if (!GAME_OVER) {
			//TODO: speed up the ball slightly, every 8 seconds.
			handleInput();
			TIME_PASSED++;
			if (TIME_PASSED % 10 == 0 && ball.getVelocity().len() < 500) {
				ball.getVelocity().scl(
						(ball.getVelocity().len() + (float) Math.sqrt((Math.log(TIME_PASSED)) )) / ball
								.getVelocity().len());
			}
			cam.update();
			ball.update(dt);
			computer.computerMoveTo(ball.getPosition().y);
			if (ball.getPosition().y+ball.getRadius() > GameDemo.HEIGHT * 3 / 4) ball.goDown();
			else if (ball.getPosition().y-ball.getRadius() < GameDemo.HEIGHT / 4) ball.goUp();
			
			if (ball.collidesWithPad(player)) ball.bounceOffPlayer(player) ;
			
			else if (ball.collidesWithPad(computer)) ball.bounceOffCPU(computer);
			if (ball.getPosition().x > GameDemo.WIDTH * 9/10) {
//				System.out.println("Player lost round " + round);
				playerWinRound(false);
			}
			else if (ball.getPosition().x < GameDemo.WIDTH / 10){
//				System.out.println("Player won round " + round);
				playerWinRound(true);
			}
		}
		handleInput();
		cam.update();
	}
	
	@Override		// functions as view...
	public void render(SpriteBatch sb) {
		renderer.render(sb, GAME_OVER, PLAYER_SCORE, COMPUTER_SCORE, TIME_PASSED, scoreToWin, ball,
						player, computer);
	}
	
	@Override
	public void dispose() {
		renderer.dispose();
		font.dispose();
		bgm.stop(); bgm.dispose();
		System.out.print("\n Play state (Pong) disposed.");
	}
}