package entities;

import aamain.LayoutManager;
import aamain.Style;

public abstract class CraftableEntity extends Entity {
	
	public CraftingRecipe craftingRecipe;

	public CraftableEntity(LayoutManager layout, Style style, boolean maintainAspectRatio, boolean interacting, float pw, float ph, boolean listenTo) {
		super(layout, style, maintainAspectRatio, interacting, pw, ph, listenTo);
	}

	public CraftableEntity(LayoutManager layout, Style style, boolean maintainAspectRatio, boolean interacting, float pw, float ph) {
		super(layout, style, maintainAspectRatio, interacting, pw, ph);
	}

	public CraftableEntity(Style style, boolean interacting) {
		super(style, interacting);
	}

	public CraftableEntity(Style style) {
		super(style);
	}
	
	public CraftableEntity(Style style, CraftingRecipe craftingRecipe) {
		super(style);
		this.craftingRecipe = craftingRecipe;
	}
	
}
