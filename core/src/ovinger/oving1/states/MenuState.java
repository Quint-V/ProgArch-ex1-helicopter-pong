package ovinger.oving1.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import ovinger.oving1.GameDemo;

/**
 * Created by VWW on 21.01.2018.
 */

public class MenuState extends State{

    private BitmapFont text;
    private ShapeRenderer shapeRenderer;
    static private boolean projectionMatrixSet;

    public MenuState(GameStateManager gsm){
        super(gsm);
        cam.setToOrtho(false, GameDemo.WIDTH, GameDemo.HEIGHT);
        text = new BitmapFont();
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            if (Gdx.input.getY() < GameDemo.HEIGHT*1/3){
                gsm.set(new Task_1_2_state(gsm));
                System.out.println("Tasks 1, 2 running");
            }
            else if (Gdx.input.getY() < GameDemo.HEIGHT*2/3){
                gsm.set(new Task_3_state(gsm));
                System.out.println("Task 3 running");
            }
            else if (Gdx.input.getY() < GameDemo.HEIGHT ){
                gsm.set(new PongState(gsm));
                System.out.println("Pong running");
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        if (!projectionMatrixSet) shapeRenderer.setProjectionMatrix(sb.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(0.5f, 0, 0, 0.1f));   // Red
        shapeRenderer.rect(0, GameDemo.HEIGHT/3*2, GameDemo.WIDTH*1, GameDemo.HEIGHT/3);
        shapeRenderer.setColor(new Color(0,.5f,0, 0.1f)); // Green
        shapeRenderer.rect(0, GameDemo.HEIGHT/3, GameDemo.WIDTH, GameDemo.HEIGHT/3);
        shapeRenderer.setColor(new Color(0.2f, 0.32f, 0.62f, 0.1f));    // Blue
        shapeRenderer.rect(0, GameDemo.HEIGHT, GameDemo.WIDTH, GameDemo.HEIGHT/3);
        shapeRenderer.end();
        sb.begin();
        text.draw(sb, "Press R to reset", GameDemo.WIDTH*1/5,GameDemo.HEIGHT*0.5f);
        text.draw(sb, "Tasks 1 & 2", cam.position.x,GameDemo.HEIGHT*5/6);
        text.draw(sb, "Task 3", cam.position.x, GameDemo.HEIGHT*3f/6);
        text.draw(sb, "Pong", cam.position.x,GameDemo.HEIGHT*1f/6);
        sb.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        text.dispose();
        System.out.println("\n Menu state disposed.");
    }

}
