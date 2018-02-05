package ovinger.oving1.rendringProcesses;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import ovinger.oving1.GameDemo;
import ovinger.oving1.pongObjects.PongBall;
import ovinger.oving1.pongObjects.PongPad;

/**
 * Created by VWW on 05.02.2018.
 */

public class PongView {
	
	private static final PongView renderer = new PongView();
	private ShapeRenderer shapeRenderer;
	private OrthographicCamera cam;
	private BitmapFont font;
	
	public static PongView getInstance(){return renderer;}
	
	private PongView(){
		super();
		shapeRenderer = new ShapeRenderer();
		cam = new OrthographicCamera();
		cam.setToOrtho(false, GameDemo.WIDTH, GameDemo.HEIGHT);
		font = new BitmapFont();
		
	}
	
	public void render(SpriteBatch sb, boolean GAME_OVER, int PLAYER_SCORE, int COMPUTER_SCORE,
					   float TIME_PASSED, int scoreToWin,
					   PongBall ball, PongPad player, PongPad computer)
	{
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
		font.draw(sb, "Player score: " + PLAYER_SCORE, cam.position.x+100, GameDemo
				.HEIGHT*1/6);
		font.draw(sb, "Computer score: " + COMPUTER_SCORE, cam.position.x-200,
				  GameDemo.HEIGHT*1/6);
		sb.end();
		if (GAME_OVER){
			sb.begin();
			font.draw(sb, "GAME OVER", cam.position.x-40, GameDemo.HEIGHT*4f/6);
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
	
	public void dispose(){
		shapeRenderer.dispose();
		font.dispose();
	}
}
