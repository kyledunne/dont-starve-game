package aamain;

/**
 * Every Rect has a Style, which defines how it will be drawn (when rect.isVisible()).
 * The rect.draw() method calls style.draw(Rect rect), where the Rect is actually rendered.
 * 
 * <p>If no style is chosen, the Rect's style will be Color.TRANSPARENT.
 */
public abstract class Style {
	
	public abstract void draw(Rect rect);
	public abstract Style getHighlightedStyle();
	public abstract Style getSelectedStyle();
	public float getPreferredWidth() { return 0; }
	public float getPreferredHeight() { return 0; }
}
