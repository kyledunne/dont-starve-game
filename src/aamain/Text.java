package aamain;
import java.awt.Font;
import org.newdawn.slick.TrueTypeFont;
import static org.lwjgl.opengl.GL11.*;

public class Text extends Rect {

	public static final Font DEFAULT_FONT = new Font("Verdana", Font.PLAIN, 16);
	public static final TrueTypeFont DEFAULT_TTFONT = new TrueTypeFont(DEFAULT_FONT, true);
	private TrueTypeFont ttfont;
	private Font font;
	private String chars;
	private Color fontColor;
	
	public Text(Font font, String chars, Color fontColor, LayoutManager layout, Style style, boolean interacting) {
		super(layout, style, interacting); // TODO interacting
		if(font == null)
			this.font = DEFAULT_FONT;
		else
			this.font = font;
		
		this.ttfont = new TrueTypeFont(this.font, true);
		this.chars = chars;
		if(fontColor == null)
			this.fontColor = Color.BLACK;
		else
			this.fontColor = fontColor;
		
		this.ph = this.ttfont.getHeight();
		this.pw = this.ttfont.getWidth(chars);
		this.h = ph;
		this.w = pw;
	}
	
	public Text(String chars) {
		this(DEFAULT_FONT, chars, Color.BLACK);
	}
	
	public Text(String chars, int fontSize) {
		this(adjustedFont(DEFAULT_FONT, fontSize), chars, Color.BLACK);
	}
	
	public Text(String chars, Color fontColor) {
		this(DEFAULT_FONT, chars, fontColor);
	}

	public Text(String chars, int fontSize, Color fontColor) {
		this(adjustedFont(DEFAULT_FONT, fontSize), chars, fontColor);
	}
	
	public Text(Font font, String chars, Color fontColor) {
		this(font, chars, fontColor, null, null, false);
	}

	public Text(String string, boolean interacting) {
		this(null, string, null, null, null, interacting);
	}

	public Text(String string, boolean interacting, Style style) {
		this(null, string, null, null, style, interacting);
	}

	public Text(Font font, String chars, Color fontColor, boolean interacting, Style style) {
		this(font, chars, fontColor, null, style, interacting);
	}

	public void draw() {
		if(currentStyle != null)
			currentStyle.draw(this);
		
		glEnable(GL_TEXTURE_2D);
		ttfont.drawString(absoluteX, absoluteY, chars, Util.slickColor(fontColor));
		glDisable(GL_TEXTURE_2D);
		
		drawComponentsAndLayers();
	}
	
	public static Font adjustedFont(Font font, int newSize) {
		return new Font(font.getFontName(), font.getStyle(), newSize);
	}
	
	public static float centerTextX(Rect container, Text t) {
		return (container.getWidth() - t.getWidth()) / 2;
	}
	
	public static float centerTextY(Rect container, Text t) {
		return (container.getHeight() - t.getHeight()) / 2;
	}
	
	public void setCoordinates(float x, float y) {
		setCoordinates(x, y, getPreferredWidth(), getPreferredHeight());
	}
	
	@Override
	public void setToNormalStyle() {
		super.setToNormalStyle();
	}
	
//	@Override
//	public void setToHighlightedStyle() {
//		super.setToHighlightedStyle();
//	}
//	
//	@Override
//	public void setToSelectedStyle() {
//		super.setToSelectedStyle();
//	}

	public Font getFont() {
		return font;
	}

	public TrueTypeFont getTTFont() {
		return ttfont;
	}

	public void setTTFont(TrueTypeFont ttfont) {
		this.ttfont = ttfont;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public String getChars() {
		return chars;
	}

	public void setChars(String chars) {
		this.chars = chars;
	}

	public Color getFontColor() {
		return fontColor;
	}

	public void setFontColor(Color fontColor) {
		this.fontColor = fontColor;
	}
}
