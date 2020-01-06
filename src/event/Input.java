package event;

//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.util.ArrayList;


//import javax.swing.Timer;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import aamain.CoordinateConstraints;
import aamain.Rect;

public class Input {

	//	public static Timer inputCheckTimer;
	//	public static int millisBetweenEventChecks = 10;

	private static ArrayList<MouseListener> mouseListeners = new ArrayList<MouseListener>();

	private static boolean isMouseMoving = false, wasMouseMoving = isMouseMoving;
	private static int mouseX = Mouse.getX(), mouseY = Display.getHeight() - Mouse.getY();
	private static int oldMouseX = mouseX, oldMouseY = mouseY;
	private static long timeOfLastMouseMovementStart = 0, timeOfLastMouseMovement = 0;

	private static boolean isMouseLeftButtonDown = Mouse.isButtonDown(0), isMouseRightButtonDown = Mouse.isButtonDown(1);
	private static boolean wasMouseLeftButtonDown = isMouseLeftButtonDown, wasMouseRightButtonDown = isMouseRightButtonDown;
	private static int lastMouseLeftClickedX = -2, lastMouseLeftClickedY = -2, lastMouseRightClickedX = -2, lastMouseRightClickedY = -2;
	private static long timeOfLastMouseLeftClick = 0, timeOfLastMouseRightClick = 0;
	private static long timeOfLastMouseLeftRelease = 0, timeOfLastMouseRightRelease = 0;

	private static boolean isMouseWheelScrolling = Mouse.getDWheel() != 0, wasMouseWheelScrolling = isMouseWheelScrolling;
	private static int distanceOfCurrentMouseWheelScroll = Mouse.getDWheel(), distanceOfLastMouseWheelScroll = 0;
	private static long timeOfLastMouseWheelScrollStart = 0, timeOfLastMouseWheelScroll;

	private static ArrayList<KeyListener> keyListeners = new ArrayList<KeyListener>();
	
	private static ArrayList<Rect> rectsTrackingWithMouse = new ArrayList<Rect>();

	public static void initialize() {
		//		inputCheckTimer = new Timer(millisBetweenEventChecks, new ActionListener() {
		//			
		//			@Override
		//			public void actionPerformed(ActionEvent e) {
		//				checkEvents();
		//			}
		//		});
	}

	public static void checkEvents() {
		refreshMouseStatus();
			for (int i = 0; i < rectsTrackingWithMouse.size(); i++) {
				float w = rectsTrackingWithMouse.get(i).getPreferredWidth();
				float h = rectsTrackingWithMouse.get(i).getPreferredHeight();
				rectsTrackingWithMouse.get(i).setCoordinates(mouseX - w / 2, mouseY - h / 2, w, h);
			}

		if (isMouseMoving ^ wasMouseMoving) {
			if(wasMouseMoving) {
				for(int i = 0; i < mouseListeners.size(); i++)
					mouseListeners.get(i).mouseStoppedMoving();
				wasMouseMoving = false;
			}
			else {
				for(int i = 0; i < mouseListeners.size(); i++)
					mouseListeners.get(i).mouseStartedMoving();
				wasMouseMoving = true;
			}
		}
		else
			if (isMouseMoving)
				for (int i = 0; i < mouseListeners.size(); i++)
					mouseListeners.get(i).mouseIsMoving(); // TODO MouseEvent
			else
				for (int i = 0; i < mouseListeners.size(); i++)
					mouseListeners.get(i).mouseIsNotMoving();

		if (isMouseLeftButtonDown ^ wasMouseLeftButtonDown) {
			if(wasMouseLeftButtonDown) {
				for(int i = 0; i < mouseListeners.size(); i++)
					mouseListeners.get(i).leftReleased();
				wasMouseLeftButtonDown = false;
			}
			else {
				for(int i = 0; i < mouseListeners.size(); i++)
					mouseListeners.get(i).leftClicked();
				wasMouseLeftButtonDown = true;
				lastMouseLeftClickedX = mouseX;
				lastMouseLeftClickedY = mouseY;
			}
		}
		else
			if (wasMouseLeftButtonDown)
				for(int i = 0; i < mouseListeners.size(); i++)
					mouseListeners.get(i).leftButtonIsDown();
			else
				for(int i = 0; i < mouseListeners.size(); i++)
					mouseListeners.get(i).leftButtonIsUp();

		if (isMouseRightButtonDown ^ wasMouseRightButtonDown) {
			if(wasMouseRightButtonDown) {
				for(int i = 0; i < mouseListeners.size(); i++)
					mouseListeners.get(i).rightReleased();
				wasMouseRightButtonDown = false;
			}
			else {
				for(int i = 0; i < mouseListeners.size(); i++)
					mouseListeners.get(i).rightClicked();
				wasMouseRightButtonDown = true;
				lastMouseRightClickedX = mouseX;
				lastMouseRightClickedY = mouseY;
			}
		}
		else
			if (wasMouseRightButtonDown)
				for(int i = 0; i < mouseListeners.size(); i++)
					mouseListeners.get(i).rightButtonIsDown();
			else
				for(int i = 0; i < mouseListeners.size(); i++)
					mouseListeners.get(i).rightButtonIsUp();

		if (isMouseWheelScrolling ^ wasMouseWheelScrolling) {
			if(wasMouseWheelScrolling) {
				for(int i = 0; i < mouseListeners.size(); i++)
					mouseListeners.get(i).wheelStoppedScrolling();
				wasMouseWheelScrolling = false;
			}
			else {
				for(int i = 0; i < mouseListeners.size(); i++)
					mouseListeners.get(i).rightClicked();
				wasMouseWheelScrolling = true;
			}
		}
		else
			if (isMouseWheelScrolling)
				for (int i = 0; i < mouseListeners.size(); i++)
					mouseListeners.get(i).wheelIsScrolling();
			else
				for (int i = 0; i < mouseListeners.size(); i++)
					mouseListeners.get(i).wheelIsNotScrolling();

		checkKeyEvents();
	}

	public static void refreshMouseStatus() {
		oldMouseX = mouseX;
		oldMouseY = mouseY;
		mouseX = Mouse.getX();
		mouseY = Display.getHeight() - Mouse.getY();
		wasMouseMoving = isMouseMoving;
		isMouseMoving = mouseX != oldMouseX || mouseY != oldMouseY;
		if (isMouseMoving) {
			timeOfLastMouseMovement = System.currentTimeMillis();
			if (!wasMouseMoving)
				timeOfLastMouseMovementStart = System.currentTimeMillis();
		}

		wasMouseLeftButtonDown = isMouseLeftButtonDown;
		wasMouseRightButtonDown = isMouseRightButtonDown;
		isMouseLeftButtonDown = Mouse.isButtonDown(0);
		isMouseRightButtonDown = Mouse.isButtonDown(1);
		if (isMouseLeftButtonDown ^ wasMouseLeftButtonDown)
			if (isMouseLeftButtonDown)
				timeOfLastMouseLeftClick = System.currentTimeMillis();
			else
				timeOfLastMouseLeftRelease = System.currentTimeMillis();
		if (isMouseRightButtonDown ^ wasMouseRightButtonDown)
			if (isMouseRightButtonDown)
				timeOfLastMouseRightClick = System.currentTimeMillis();
			else
				timeOfLastMouseRightRelease = System.currentTimeMillis();

		distanceOfCurrentMouseWheelScroll = Mouse.getDWheel();
		wasMouseWheelScrolling = isMouseWheelScrolling;
		isMouseWheelScrolling = distanceOfCurrentMouseWheelScroll != 0;
		if (isMouseWheelScrolling) {
			distanceOfLastMouseWheelScroll = distanceOfCurrentMouseWheelScroll;
			timeOfLastMouseWheelScroll = System.currentTimeMillis();
			if (!wasMouseWheelScrolling)
				timeOfLastMouseWheelScrollStart = System.currentTimeMillis();
		}
			
	}
	
	public static void checkKeyEvents() {
		for (int i = 0; i < keyListeners.size(); i++)
			if (Keyboard.isKeyDown(keyListeners.get(i).key) && !keyListeners.get(i).isKeyDown()) {
				keyListeners.get(i).keyPressed();
				keyListeners.get(i).setKeyDown(true);
			}
			else if (!Keyboard.isKeyDown(keyListeners.get(i).key) && keyListeners.get(i).isKeyDown()) {
				keyListeners.get(i).keyReleased();
				keyListeners.get(i).setKeyDown(false);
			}
			else
				if (keyListeners.get(i).isKeyDown())
					keyListeners.get(i).keyIsDown();
				else 
					keyListeners.get(i).keyIsUp();
	}
	
	public static void trackWithMouse(Rect rect) {
		rectsTrackingWithMouse.add(rect);
		float w = 100;
		float h = 100;
		Rect.CONTAINER.add(rect, new CoordinateConstraints(mouseX - w / 2, mouseY - h / 2, w, h));
	}

	public static void addKeyListener(KeyListener keyListener) {
		keyListeners.add(keyListener);
		keyListener.setKeyDown(Keyboard.isKeyDown(keyListener.key));
	}

	public static void removeKeyListener(KeyListener keyListener) {
		int index = keyListeners.indexOf(keyListener);
		keyListeners.remove(index);
	}

	//	public static void refreshDisplayHeight() {
	//		DISPLAY_HEIGHT = Display.getHeight();
	//	}

	public static int getKeyListenerIndex(KeyListener keyListener) {
		return keyListeners.indexOf(keyListener);
	}

	public static ArrayList<MouseListener> getMouseListeners() {
		return mouseListeners;
	}

	public static int getLastMouseLeftClickedX() {
		return lastMouseLeftClickedX;
	}

	public static int getLastMouseLeftClickedY() {
		return lastMouseLeftClickedY;
	}

	public static int getLastMouseRightClickedX() {
		return lastMouseRightClickedX;
	}

	public static int getLastMouseRightClickedY() {
		return lastMouseRightClickedY;
	}

	public static int getOldMouseX() {
		return oldMouseX;
	}

	public static int getOldMouseY() {
		return oldMouseY;
	}

	public static int getMouseX() {
		return mouseX;
	}

	public static int getMouseY() {
		return mouseY;
	}

	public static int getDistanceOfLastMouseWheelScroll() {
		return distanceOfLastMouseWheelScroll;
	}

	public static long getTimeSinceLastMouseMovement() {
		return System.currentTimeMillis() - timeOfLastMouseMovement;
	}

	public static long getTimeSinceLastMouseLeftClick() {
		return System.currentTimeMillis() - timeOfLastMouseLeftClick;
	}

	public static long getTimeSinceLastMouseRightClick() {
		return System.currentTimeMillis() - timeOfLastMouseRightClick;
	}

	public static long getTimeSinceLastMouseLeftRelease() {
		return System.currentTimeMillis() - timeOfLastMouseLeftRelease;
	}

	public static long getTimeSinceLastMouseRightRelease() {
		return System.currentTimeMillis() - timeOfLastMouseRightRelease;
	}

	public static long getTimeSinceLastMouseWheelScroll() {
		return System.currentTimeMillis() - timeOfLastMouseWheelScroll;
	}

	public static boolean isMouseLeftButtonDown() {
		return isMouseLeftButtonDown;
	}

	public static boolean isMouseRightButtonDown() {
		return isMouseRightButtonDown;
	}

	public static long getTimeOfLastMouseMovement() {
		return timeOfLastMouseMovement;
	}

	public static long getTimeOfLastMouseLeftClick() {
		return timeOfLastMouseLeftClick;
	}

	public static long getTimeOfLastMouseRightClick() {
		return timeOfLastMouseRightClick;
	}

	public static long getTimeOfLastMouseLeftRelease() {
		return timeOfLastMouseLeftRelease;
	}

	public static long getTimeOfLastMouseRightRelease() {
		return timeOfLastMouseRightRelease;
	}

	public static long getTimeOfLastMouseWheelScroll() {
		return timeOfLastMouseWheelScroll;
	}

	public static boolean isMouseWheelScrolling() {
		return isMouseWheelScrolling;
	}

	public static int getDistanceOfCurrentMouseWheelScroll() {
		return distanceOfCurrentMouseWheelScroll;
	}

	public static boolean wasMouseLeftButtonDown() {
		return wasMouseLeftButtonDown;
	}

	public static boolean wasMouseRightButtonDown() {
		return wasMouseRightButtonDown;
	}

	public static boolean wasMouseWheelScrolling() {
		return wasMouseWheelScrolling;
	}

	public static boolean isMouseMoving() {
		return isMouseMoving;
	}

	public static boolean isWasMouseMoving() {
		return wasMouseMoving;
	}

	public static long getTimeOfLastMouseMovementStart() {
		return timeOfLastMouseMovementStart;
	}

	public static long getTimeOfLastMouseWheelScrollStart() {
		return timeOfLastMouseWheelScrollStart;
	}

	public static ArrayList<KeyListener> getKeyListeners() {
		return keyListeners;
	}

	public static ArrayList<Rect> getRectsTrackingWithMouse() {
		return rectsTrackingWithMouse;
	}
}
