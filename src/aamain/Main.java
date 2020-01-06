package aamain;

import java.awt.Font;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;
import event.*;
import static org.lwjgl.opengl.GL11.*;

public class Main {
	public static RTSGame MAIN_GAME_INSTANCE;
	public static int currentFPS = 60;
	
	public static void main(String[] args) {
		try {
			Display.setFullscreen(true);
			Display.create();
		} catch (LWJGLException e) { e.printStackTrace(); }
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 0, 1);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glTranslatef(.375f, .375f, 0);
		glDisable(GL_DEPTH_TEST);
		
		Color.glClearColor(Color.CYAN);
		glClear(GL_COLOR_BUFFER_BIT);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		glShadeModel(GL_SMOOTH);
		glDisable(GL_LIGHTING); 
		glClearDepth(1);
		
		Text game = new Text(new Font(Text.DEFAULT_FONT.getFontName(), Font.PLAIN, 40), "A Stupendabuliscious RTS Game-Thingy!", Color.BLACK);
		Text loading = new Text(new Font(Text.DEFAULT_FONT.getFontName(), Font.PLAIN, 40), "Loading...", Color.BLACK);
		Rect.CONTAINER.add(game, new CoordinateConstraints(Text.centerTextX(Rect.CONTAINER, game), Text.centerTextY(Rect.CONTAINER, game),
				game.getWidth(), game.getHeight()));
		Rect.CONTAINER.add(loading, new CoordinateConstraints(Text.centerTextX(Rect.CONTAINER, loading),
				Text.centerTextY(Rect.CONTAINER, loading) + game.getHeight(), loading.getWidth(), loading.getHeight()));
		
		glClear(GL_COLOR_BUFFER_BIT);
		Rect.CONTAINER.draw();
		Display.update();
		
		startGameInstance();
		
		glClear(GL_COLOR_BUFFER_BIT);
		Rect.CONTAINER.draw();
		Display.update();
		
		while(!Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			glClear(GL_COLOR_BUFFER_BIT);
			checkEvents();
			Rect.CONTAINER.draw();
			Display.sync(currentFPS);
			Display.update();
		}
		
		System.out.println("Escape pressed");
		System.exit(0);
	}
	
	private static void startGameInstance() {
		Input.initialize();
		MAIN_GAME_INSTANCE = new RTSGame();
		Rect.CONTAINER.removeAll();
		MAIN_GAME_INSTANCE.display();
	}
	
	private static void checkEvents() {
		Input.refreshMouseStatus();
		MAIN_GAME_INSTANCE.checkEvents();
	}
}
