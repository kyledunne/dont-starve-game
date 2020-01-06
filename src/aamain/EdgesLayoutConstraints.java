package aamain;

public class EdgesLayoutConstraints {
	private EdgesLayout.Location location;
	private float size;
	
	public EdgesLayoutConstraints(EdgesLayout.Location location, float size) {
		this.location = location;
		this.size = size;
	}

	public EdgesLayout.Location getLocation() {
		return location;
	}

	public float getSize() {
		return size;
	}
}
