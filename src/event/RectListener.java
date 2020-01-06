package event;

import aamain.Rect;

public abstract class RectListener {
	protected Rect client;
	
	/**
	 * Specifies whether this RectListener is active (whether this RectListener's mouseListener is currently listening to the client Rect).
	 */
	private boolean active = false;
	
	protected MouseListener mouseListener = new MouseListener() {

		@Override
		public void mouseIsMoving() {
			if(client.isMousedOver() ^ client.wasMousedOver())
				if(client.isMousedOver())
					if(Input.isMouseLeftButtonDown()) {
						if(client.isMousedOver(Input.getLastMouseLeftClickedX(), Input.getLastMouseLeftClickedY())) {
							mouseEntered();
							leftClickedOn();
						}
					}
					else
						mouseEntered();
				else
					mouseExited();
		}

		@Override
		public void leftClicked() {
			if(client.isMousedOver())
				leftClickedOn();
		}

		@Override
		public void leftReleased() {
			if(client.isMousedOver() && client.isMousedOver(Input.getLastMouseLeftClickedX(), Input.getLastMouseLeftClickedY()))
				leftClickedThenReleasedOn();
		}

		@Override
		public void rightClicked() {
			// TODO Auto-generated method stub
			super.rightClicked();
		}

		@Override
		public void rightReleased() {
			if(client.isMousedOver() && client.isMousedOver(Input.getLastMouseRightClickedX(), Input.getLastMouseRightClickedY()))
				rightClickedThenReleasedOn();
		}

		@Override
		public void wheelIsScrolling() {
			// TODO Auto-generated method stub
			super.wheelIsScrolling();
		}

		@Override
		public void leftButtonIsDown() {
			// TODO Auto-generated method stub
			super.leftButtonIsDown();
		}

		@Override
		public void leftButtonIsUp() {
			// TODO Auto-generated method stub
			super.leftButtonIsUp();
		}

		@Override
		public void rightButtonIsDown() {
			// TODO Auto-generated method stub
			super.rightButtonIsDown();
		}

		@Override
		public void rightButtonIsUp() {
			// TODO Auto-generated method stub
			super.rightButtonIsUp();
		}

		@Override
		public void mouseIsNotMoving() {
			// TODO Auto-generated method stub
			super.mouseIsNotMoving();
		}

		@Override
		public void wheelIsNotScrolling() {
			// TODO Auto-generated method stub
			super.wheelIsNotScrolling();
		}
		
	};
	
	public RectListener(Rect client) {
		this.client = client;
	}
	
	public void mouseEntered() {}
	public void mouseExited() {}
	public void leftClickedOn() {}
	public void leftReleasedOn() {}
	public void rightClickedOn() {}
	public void rightReleasedOn() {}
	public void wheelScrolledOver() {}
	
	public void leftClickedThenReleasedOn() {}
	public void rightClickedThenReleasedOn() {}
	
//	public void mouseIsInside() {}
//	public void mouseIsOutside() {}
//	public void isLeftClickedOn() {}
//	public void isRightClickedOn() {}
	
//	public void coordinatesChanged() {
//		if(client.isMousedOver() ^ client.wasMousedOver()) //TODO absoluteX is changing, not mouseX
//			if(client.isMousedOver())
//				if(Mouse.isButtonDown(0)) {
//					if(client.isMousedOver(Input.getLastMouseLeftClickedX(), Input.getLastMouseLeftClickedY())) {
//						mouseEntered();
//						leftClickedOn();
//					}
//				}
//				else
//					mouseEntered();
//			else
//				mouseExited();
//	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		if(active ^ this.active) {
			if(active)
				Input.getMouseListeners().add(mouseListener);
			else
				Input.getMouseListeners().remove(mouseListener);
			this.active = active;
		}
	}
}

/*

*/