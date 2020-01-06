package entities;

import aamain.Image;

public class Axe extends CraftableEntity implements Storeable {
	public static final Image axeImage = new Image("res/images/entities/axe.png");
	
	public Axe() {
		super(axeImage, CraftingRecipe.AXE_RECIPE);
	}

	@Override
	public int getMaxStackSize() { return 1; }
}
