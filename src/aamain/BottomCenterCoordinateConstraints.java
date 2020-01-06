package aamain;

public class BottomCenterCoordinateConstraints extends CoordinateConstraints {

	public BottomCenterCoordinateConstraints(float x, float y, float w, float h) {
		this(x, y, w, h, false);
	}

	public BottomCenterCoordinateConstraints(float x, float y, float w, float h, boolean center) {
		this.x = x - w * .5f;
		this.y = y - h;
		this.w = w;
		this.h = h;
		this.center = center;
	}
}
