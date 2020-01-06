package event;

import aamain.Rect;

public class ActionEvent implements Event {
	private Rect actionSource;

	public ActionEvent(Rect actionSource) {
		this.actionSource = actionSource;
	}
	
	public Rect getActionSource() {
		return actionSource;
	}
}
