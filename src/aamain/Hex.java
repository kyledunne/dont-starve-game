package aamain;

public class Hex extends Rect {
	public static final float HEX_ASPECT_RATIO = (float)(1 / (Math.sqrt(3)/2));
	public static final int DEFAULT_HEX_WIDTH = 100;
	
	/**
	 * true: isMousedOver() will only be true only if the cursor is inside the bounds of the hexagon
	 * false: isMousedOver() will be true if the cursor is inside the bounding rectangle of the hexagon
	 */
	private boolean hexStyleMouseOver = true;
	
	public Hex(LayoutManager layout, Style style, boolean interacting, float pw, boolean listenTo) {
		super(layout, style, true, interacting, pw, (pw * HEX_ASPECT_RATIO), listenTo);
	}

	public Hex(LayoutManager layout, Style style, boolean interacting, float pw) {
		this(layout, style, interacting, pw, true);
	}

	public Hex(LayoutManager layout, Style style, boolean interacting) {
		this(layout, style, interacting, DEFAULT_HEX_WIDTH);
	}
	
	@Override
	public float[] getDrawingBoundaryXCoords() {
		float[] xCoords = new float[6];
		xCoords[0] = this.getAbsoluteX() + this.getWidth() * .5f;
		xCoords[1] = this.getAbsoluteX() + this.getWidth();
		xCoords[2] = this.getAbsoluteX() + this.getWidth();
		xCoords[3] = this.getAbsoluteX() + this.getWidth() * .5f;
		xCoords[4] = this.getAbsoluteX();
		xCoords[5] = this.getAbsoluteX();
		return xCoords;
	}
	
	@Override
	public float[] getDrawingBoundaryYCoords() {
		float[] yCoords = new float[6];
		yCoords[0] = this.getAbsoluteY();
		yCoords[1] = this.getAbsoluteY() + this.getHeight() * .25f;
		yCoords[2] = this.getAbsoluteY() + (this.getHeight()) * .75f;
		yCoords[3] = this.getAbsoluteY() + this.getHeight();
		yCoords[4] = this.getAbsoluteY() + (this.getHeight()) * .75f;
		yCoords[5] = this.getAbsoluteY() + this.getHeight() * .25f;
		return yCoords;
	}
	
	@Override
	public boolean isMousedOver(int mouseX, int mouseY) {
		if (!hexStyleMouseOver)
			return (super.isMousedOver(mouseX, mouseY));
		
		if (!(mouseX >= absoluteX && mouseX < getAbsoluteRightX() && mouseY >= absoluteY && mouseY < getAbsoluteBottomY()))
			return false;
		else {
			float mX = mouseX - absoluteX;
			float mY = mouseY - absoluteY;
			float rightMX = getWidth() - mX;
			float bottomMY = getHeight() - mY;
			float yMultiplier = (float)Math.tan(Math.PI / 3);
			
			if ((mX + yMultiplier * mY) < (w * .5f))
				return false;
			
			if ((rightMX + yMultiplier * mY) < (w * .5f))
				return false;
			
			if ((rightMX + yMultiplier * bottomMY) < (w * .5f))
				return false;
			
			if ((mX + yMultiplier * bottomMY) < (w * .5f))
				return false;
		}
		return true;
	}

	public boolean getHexStyleMouseOver() {
		return hexStyleMouseOver;
	}

	public void setHexStyleMouseOver(boolean hexStyleMouseOver) {
		this.hexStyleMouseOver = hexStyleMouseOver;
	}

}
