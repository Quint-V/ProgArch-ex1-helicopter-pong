package ovinger.oving1.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import ovinger.oving1.GameDemo;
import ovinger.oving1.pongObjects.PongBall;
import ovinger.oving1.pongObjects.PongPad;

/**
 * Created by VWW on 25.01.2018.
 */

public class PongState extends State { // write like Task_1_2_state?
	
	private BitmapFont font;
	private ShapeRenderer shapeRenderer;
	private PongBall ball;
	private PongPad player, computer;
	private boolean GAME_OVER;
	private int PLAYER_SCORE, COMPUTER_SCORE, round, scoreToWin;
	private float TIME_PASSED;
	private Music bgm;
	
	
	public PongState() {
		super();
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.1f);
		font = new BitmapFont(true);
		shapeRenderer = new ShapeRenderer();

		player = new PongPad(true);
		computer = new PongPad(false);
		ball = new PongBall();
		cam.setToOrtho(true, GameDemo.WIDTH, GameDemo.HEIGHT);

		GAME_OVER = false;
		PLAYER_SCORE = 0;
		COMPUTER_SCORE = 0;
		TIME_PASSED = 0;
		round = 1;
		scoreToWin = 1;

		bgm = Gdx.audio.newMusic(Gdx.files.internal("tetrisremix.mp3"));
		bgm.setLooping(true);
		bgm.setVolume(0.2f);
		bgm.play();
	}
	
	private void playerWinRound(boolean win) {
		if (win) PLAYER_SCORE++; else COMPUTER_SCORE++;
		if (PLAYER_SCORE == scoreToWin || COMPUTER_SCORE == scoreToWin) {GAME_OVER = true; return; }   //
		// finished
		player = new PongPad(true);
		computer = new PongPad(false);
		ball = new PongBall();
		round++;
		TIME_PASSED = 0;
	}
	
	@Override
	protected void handleInput() {
		if (Gdx.input.isKeyJustPressed(Input.Keys.R)) gsm.set(new MenuState());
		
		if (!GAME_OVER)
			if (Gdx.input.isTouched()) {
				int pointer = Gdx.input.getY();
				if (pointer > player.getCenter().y &&
						player.getPosition().y + player.getHitbox().getHeight() < GameDemo.HEIGHT*3/4)
					player.moveUp();
				else if  (pointer < player.getCenter().y &&
						player.getPosition().y > GameDemo.HEIGHT/4) player.moveDown();
			}
	}
	
	@Override
	public void update(float dt) {
		if (!GAME_OVER) {
			//TODO: speed up the ball slightly, every 8 seconds.
			handleInput();
			TIME_PASSED++;
			if (TIME_PASSED % 10 == 0 && ball.getVelocity().len() < 500) {
				ball.getVelocity().scl(
						(ball.getVelocity().len() + (float) Math.sqrt((Math.log(TIME_PASSED)) )) / ball
								.getVelocity().len());
//				System.out.println("Ball speed: " + ball.getVelocity().len());
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
	
	@Override
	public void render(SpriteBatch sb) {
		shapeRenderer.setProjectionMatrix(cam.combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		
		shapeRenderer.rect(GameDemo.WIDTH/10, GameDemo.HEIGHT/4, GameDemo.WIDTH*8/10,
						   GameDemo.HEIGHT*2/4);
		shapeRenderer.rect(GameDemo.WIDTH/10-1, GameDemo.HEIGHT/4-1, GameDemo.WIDTH*8/10+2,
						   GameDemo.HEIGHT*2/4+2);
		shapeRenderer.end();
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		font.setColor(Color.WHITE);
		font.draw(sb, "Press R to reset", GameDemo.WIDTH/12,GameDemo.HEIGHT*0.05f);
		font.draw(sb, "Player score: " + PLAYER_SCORE, cam.position.x+100, GameDemo.HEIGHT*1/6);
		font.draw(sb, "Computer score: " + COMPUTER_SCORE, cam.position.x-200,
				  GameDemo.HEIGHT*1/6);
		sb.end();
		if (GAME_OVER){
			sb.begin();
			font.draw(sb, "GAME OVER", cam.position.x-40, GameDemo.HEIGHT*2f/6);
			if (PLAYER_SCORE == scoreToWin) font.draw(sb, "You won!",
													  cam.position.x-20, GameDemo.HEIGHT*3f/6-50);
			else font.draw(sb, "GIT GUD SKRUB", cam.position.x-50,
						   GameDemo.HEIGHT*3f/6-50);
			sb.end();
		}
		else {
			if (computer.getVelocity() == computer.getBaseVelocity()) {
				sb.begin();
				font.draw(sb, "DIFFICULTY INCREASE COUNTDOWN: " + (1550-(int)TIME_PASSED), cam
								  .position.x-150,
						  GameDemo.HEIGHT*1/6+30);
				sb.end();
			}
			if (computer.getVelocity() > computer.getBaseVelocity() && TIME_PASSED % 40 < 30) {
				sb.begin();
				font.draw(sb, "DIFFICULTY INCREASING! ", cam.position.x-100,
						  GameDemo.HEIGHT*1/6+30);
				sb.end();
			}
			shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			shapeRenderer.rect(player.getPosition().x, player.getPosition().y,
							   player.getWidth(), player.getHeight());
			
			shapeRenderer.rect(computer.getPosition().x, computer.getPosition().y,
							   computer.getWidth(), computer.getHeight());
			
			shapeRenderer.circle(ball.getPosition().x, ball.getPosition().y, ball.getRadius());
			shapeRenderer.end();
		}
	}
	
	@Override
	public void dispose() {
		shapeRenderer.dispose();
		font.dispose();
		bgm.stop(); bgm.dispose();
		System.out.print("\n Play state (Pong) disposed.");
	}
}