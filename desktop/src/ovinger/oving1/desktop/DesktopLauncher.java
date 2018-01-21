package ovinger.oving1.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ovinger.oving1.GameDemo;

public class DesktopLauncher {

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = GameDemo.WIDTH;
		config.height = GameDemo.HEIGHT;
		config.title = GameDemo.TITLE;
		new LwjglApplication(new GameDemo(), config);
	}
}
