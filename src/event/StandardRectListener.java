package event;

import aamain.Rect;

public class StandardRectListener extends RectListener {
	
	public StandardRectListener(Rect client) {
		super(client);
	}

	@Override
	public void mouseEntered() {
		if(client.isInteracting())
			client.setToHighlightedStyle();
	}
	
	@Override
	public void mouseExited() {
		if(client.isInteracting())
			client.setToNormalStyle();
	}

	@Override
	public void leftClickedOn() {
		if(client.isInteracting())
			client.setToSelectedStyle();
	}

	@Override
	public void leftClickedThenReleasedOn() {
		if(client.isInteracting())
			client.setToHighlightedStyle();
	}

	public Rect getClient() {
		return client;
	}
	
	public void setClient(Rect client) {
		this.client = client;
	}
}
