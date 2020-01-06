package entities;

import aamain.*;

/**
 * Entities have one skin, and if they can be put in the inventory then they will have the same texture, just sized for the inventory
 */
public abstract class Entity extends Rect {
	public static final int DEFAULT_MAX_STACK_SIZE = 20;
	
	public Entity(LayoutManager layout, Style style, boolean maintainAspectRatio, boolean interacting, float pw, float ph, boolean listenTo) {
		super(layout, style, maintainAspectRatio, interacting, pw, ph,
				listenTo);
	}

	public Entity(LayoutManager layout, Style style, boolean maintainAspectRatio, boolean interacting, float pw, float ph) {
		super(layout, style, maintainAspectRatio, interacting, style.getPreferredWidth(), style.getPreferredHeight());
	}

	public Entity(Style style, boolean interacting) {
		this(null, style, true, interacting, 0, 0);
	}
	
	public Entity(Style style) {
		this(style, true);
	}
	
	float radius;
	public int getMaxStackSize() { return DEFAULT_MAX_STACK_SIZE; }
}
