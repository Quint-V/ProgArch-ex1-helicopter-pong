package ovinger.oving1.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

/**
 * Created by VWW on 21.01.2018.
 */

public class GameStateManager {

    private Stack<State> states;
    private static GameStateManager INSTANCE = new GameStateManager();
    
    public static GameStateManager getInstance() {
        if (INSTANCE == null) INSTANCE = new GameStateManager();
        return INSTANCE; }

    private GameStateManager(){ states = new Stack<State>(); }

    public void push(State state) { states.push(state);    }

    public void pop() { states.pop().dispose(); }

    public void set(State state){ pop(); push(state); }

    public void update(float dt) {states.peek().update(dt); }

    public void render(SpriteBatch sb){ states.peek().render(sb); }
}
