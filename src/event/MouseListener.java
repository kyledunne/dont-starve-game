package event;

public abstract class MouseListener implements EventListener {
	public void mouseStartedMoving() { mouseIsMoving(); }
	public void mouseStoppedMoving() { mouseIsNotMoving(); }
	public void leftClicked() { leftButtonIsDown(); }
	public void leftReleased() { leftButtonIsUp(); }
	public void rightClicked() { rightButtonIsDown(); }
	public void rightReleased() { rightButtonIsUp(); }
	public void wheelStartedScrolling() { wheelIsScrolling(); }
	public void wheelStoppedScrolling() { wheelIsNotScrolling(); }
	
	public void mouseIsMoving() {}
	public void mouseIsNotMoving() {}
	public void leftButtonIsDown() {}
	public void leftButtonIsUp() {}
	public void rightButtonIsDown() {}
	public void rightButtonIsUp() {}
	public void wheelIsScrolling() {}
	public void wheelIsNotScrolling() {}
}