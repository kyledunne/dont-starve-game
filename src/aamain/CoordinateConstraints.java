package aamain;

public class CoordinateConstraints {
	public float x, y, w, h;
	public boolean center;
	
	protected CoordinateConstraints() {}

	public CoordinateConstraints (float x, float y, float w, float h) {
		this(x, y, w, h, false);
	}
	
	public CoordinateConstraints (float x, float y, float w, float h, boolean center) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.center = center;
	}
	
}
