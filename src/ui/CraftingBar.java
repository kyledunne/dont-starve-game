package ui;

import entities.*;
import event.*;
import aamain.*;

public class CraftingBar extends Rect {
	public Axe axe = new Axe();
	public Campfire campfire = new Campfire();

	public CraftingBar() {
		super(new FlowLayout(FlowLayout.Orientation.TOP_TO_BOTTOM, 0), Color.adjustAColorsAlpha(Color.TRANSPARENT_WHITE, .2f), false);
		
		axe = new Axe();
		campfire = new Campfire();
		axe.setRectListener(new StandardRectListener(axe) {
			
			@Override
			public void leftClickedOn() {
				if (axe.craftingRecipe.isCurrentlyCraftable()) {
					axe.craftingRecipe.consumeIngredients();
					Main.MAIN_GAME_INSTANCE.inventoryBar.attemptToAddToInventory(new Axe());
				}
			}
		});
		
		campfire.setRectListener(new StandardRectListener(campfire) {
			
			@Override
			public void leftClickedOn() {
				if (campfire.craftingRecipe.isCurrentlyCraftable()) {
					campfire.craftingRecipe.consumeIngredients();
					Main.MAIN_GAME_INSTANCE.inventoryBar.attemptToAddToInventory(new Campfire());
				}
			}
		});
		
		this.add(axe);
		this.add(campfire);
	}
	
}
