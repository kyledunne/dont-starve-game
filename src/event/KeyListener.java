package event;

import aamain.Rect;

public abstract class KeyListener implements EventListener {
	public Rect client;
	public int key;
	private boolean isKeyDown;
	private long timeOfLastKeyDepression = 0, timeOfLastKeyRelease = 0;

	public KeyListener(Rect client, int key) {
		this.client = client;
		this.key = key;
	}
	
	public void keyIsDown() {}
	public void keyIsUp() {}
	public void keyPressed() { keyIsDown(); }	
	public void keyReleased() { keyIsUp(); }
	
	public boolean isKeyDown() {
		return isKeyDown;
	}
	
	protected void setKeyDown(boolean isKeyDown) {
		this.isKeyDown = isKeyDown;
		if (isKeyDown) {
			timeOfLastKeyDepression = System.currentTimeMillis();
		}
		else {
			timeOfLastKeyRelease = System.currentTimeMillis();
		}
	}
	
	public long getTimeSinceLastKeyDepression() {
		return System.currentTimeMillis() - timeOfLastKeyDepression;
	}

	public long getTimeSinceLastKeyRelease() {
		return System.currentTimeMillis() - timeOfLastKeyRelease;
	}
}
