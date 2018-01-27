package ovinger.oving1.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
	private int PLAYER_SCORE, COMPUTER_SCORE, round;
	private float TIME_PASSED;
	
	
	public PongState(GameStateManager gsm) {
		super(gsm);
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.1f);
		font = new BitmapFont();
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
	}
	
	private void playerWinRound(boolean win) {
		if (win) PLAYER_SCORE++; else COMPUTER_SCORE++;
		if (PLAYER_SCORE == 21 || COMPUTER_SCORE == 21) {GAME_OVER = true; return; }   // finished
		player = new PongPad(true);
		computer = new PongPad(false);
		ball = new PongBall();
		round++;
		TIME_PASSED = 0;
	}
	
	@Override
	protected void handleInput() {
		if (Gdx.input.isKeyJustPressed(Input.Keys.R)) gsm.set(new MenuState(gsm));

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
			if (TIME_PASSED % 50 == 0) {
				ball.getVelocity().scl(
					(ball.getVelocity().len() + 5) / ball.getVelocity().len() );
				System.out.println("Ball speedup (Delta count: " + TIME_PASSED + ")");
			}
			cam.update();
			ball.update(dt);
			computer.computerMoveTo(ball.getPosition().y);
			if (ball.getPosition().y+ball.getRadius() > GameDemo.HEIGHT * 3 / 4 ||
					ball.getPosition().y-ball.getRadius() < GameDemo.HEIGHT / 4)
				ball.bounceOffWall();

			if (ball.collidesWithPad(player)) ball.bounceOffPlayer(player) ;

			else if (ball.collidesWithPad(computer)) ball.bounceOffCPU(computer);
			if (ball.getPosition().x > GameDemo.WIDTH * 9/10) {
				System.out.println("Player lost round " + round);
				playerWinRound(false);
			}
			else if (ball.getPosition().x < GameDemo.WIDTH / 10){
				System.out.println("Player won round " + round);
				playerWinRound(true);
			}
		}
		handleInput();
		cam.update();
	}
	
	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		shapeRenderer.setProjectionMatrix(cam.combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		
		shapeRenderer.rect(GameDemo.WIDTH/10, GameDemo.HEIGHT/4, GameDemo.WIDTH*8/10,
						   GameDemo.HEIGHT*2/4);
		shapeRenderer.rect(GameDemo.WIDTH/10-1, GameDemo.HEIGHT/4-1, GameDemo.WIDTH*8/10+2,
						   GameDemo.HEIGHT*2/4+2);
		shapeRenderer.end();
		font.setColor(Color.WHITE);
		font.draw(sb, "Press R to reset", GameDemo.WIDTH/12,GameDemo.HEIGHT*0.05f);
		font.draw(sb, "Player score: " + PLAYER_SCORE, cam.position.x-30, GameDemo.HEIGHT*2/6);
		font.draw(sb, "Computer score: " + COMPUTER_SCORE, cam.position.x+30, GameDemo
				.HEIGHT*4/6);
		if (GAME_OVER){
			font.draw(sb, "GAME OVER", cam.position.x, GameDemo.HEIGHT*3f/6);
			if (PLAYER_SCORE == 21) font.draw(sb, "You won!",
											  cam.position.x, GameDemo.HEIGHT*3f/6-20);
			else font.draw(sb, "GIT GUD SKRUB", cam.position.x,
						   GameDemo.HEIGHT*3f/6-20);
		}
		else {
			shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			shapeRenderer.rect(player.getPosition().x, player.getPosition().y,
							   player.getWidth(), player.getHeight());
			
			shapeRenderer.rect(computer.getPosition().x, computer.getPosition().y,
							   computer.getWidth(), computer.getHeight());
			
			shapeRenderer.circle(ball.getPosition().x, ball.getPosition().y, ball.getRadius());
		}
		shapeRenderer.end();
		sb.end();
	}
	
	@Override
	public void dispose() {
		shapeRenderer.dispose();
		font.dispose();
		System.out.print("\n Play state (Pong) disposed.");
	}
}