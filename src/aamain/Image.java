package aamain;
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Image extends Style {
	private Texture texture;
	private Color backgroundColor;
	private Color bindingColor;
	
	public Image(String texturePath, Color backgroundColor, Color bindingColor) {
		try {
			texture = TextureLoader.getTexture(texturePath.substring(texturePath.length() - 3), ResourceLoader.getResourceAsStream(texturePath));
		} catch(IOException e) { e.printStackTrace(); }
		
		this.backgroundColor = backgroundColor;
		this.bindingColor = bindingColor;
	}
	
	private Image(Texture texture, Color backgroundColor) {
		this.texture = texture;
		this.backgroundColor = backgroundColor;
		this.bindingColor = Color.TRANSPARENT_WHITE;
	}
	
	public Image(String texturePath, Color backgroundColor) {
		this(texturePath, backgroundColor, Color.TRANSPARENT_WHITE);
	}
	
	public Image(String texturePath) {
		this(texturePath, null);
	}

	@Override
	public void draw(Rect rect) {
		if (this.hasBackgroundColor())
			backgroundColor.draw(rect);
		
		float textureWidth = texture.getWidth();
		float textureHeight = texture.getHeight();
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		bindingColor.bind();
		texture.bind();
		GL11.glBegin(GL11.GL_POLYGON);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(rect.getAbsoluteX(), rect.getAbsoluteY());
			GL11.glTexCoord2f(0, textureHeight);
			GL11.glVertex2f(rect.getAbsoluteX(), rect.getAbsoluteBottomY());
			GL11.glTexCoord2f(textureWidth, textureHeight);
			GL11.glVertex2f(rect.getAbsoluteRightX(), rect.getAbsoluteBottomY());
			GL11.glTexCoord2f(textureWidth, 0);
			GL11.glVertex2f(rect.getAbsoluteRightX(), rect.getAbsoluteY());
		GL11.glEnd();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}
	
	@Override
	public Style getHighlightedStyle() {
		if (hasBackgroundColor())
			return new Image(this.texture, (Color)backgroundColor.getHighlightedStyle());
		else
			return this;
	}

	@Override
	public Style getSelectedStyle() {
		if (hasBackgroundColor())
			return new Image(this.texture, (Color)backgroundColor.getSelectedStyle());
		else
			return this;
	}
	
	@Override
	public float getPreferredWidth() {
		return texture.getImageWidth();
	}
	
	@Override
	public float getPreferredHeight() {
		return texture.getImageHeight();
	}

	public boolean hasBackgroundColor() {
		return (backgroundColor != null);
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	
	public Color getBindingColor() {
		return bindingColor;
	}

	public void setBindingColor(Color bindingColor) {
		this.bindingColor = bindingColor;
	}

	public Texture getTexture() {
		return texture;
	}
}
