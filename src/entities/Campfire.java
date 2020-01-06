package entities;

import event.*;
import aamain.*;

public class Campfire extends CraftableEntity implements Placeable {
	public static final Image campfireImage = new Image("res/images/entities/campfire.png");
	public static final Image lightFromCampfire = new Image("res/images/entities/lightFromCampfire.png");
	public static final float WIDTH = 150;
	public static final float HEIGHT = 150;
	
	public static final float LIGHT_FROM_CAMPFIRE_WIDTH = 1000;
	public static final float LIGHT_FROM_CAMPFIRE_HEIGHT = 1000;

	public Campfire() {
		super(campfireImage);
			
		craftingRecipe = CraftingRecipe.CAMPFIRE_RECIPE;
		
		rectListener = new StandardRectListener(this) { 
			
			@Override
			public void leftClickedOn() {
				if (container.getContainer() instanceof Map) {
//					container.remove(getThis());
//					Main.MAIN_GAME_INSTANCE.map.campfireRemoved();
					for (int i = 0; i < Input.getRectsTrackingWithMouse().size(); i++) //TODO check to see if null causes an error with instanceof
						if (Input.getRectsTrackingWithMouse().get(i) instanceof Cookable)
							((Cookable)Input.getRectsTrackingWithMouse().get(i)).cook();
				}
			}
		};
		
	}

	@Override
	public int getMaxStackSize() { return 1; }
}

/**
 * 		container.remove(this);
		Main.MAIN_GAME_INSTANCE.map.campfire = (Campfire) getThis();
		Main.MAIN_GAME_INSTANCE.map.entityLayer.add(Main.MAIN_GAME_INSTANCE.map.campfire, new CoordinateConstraints(Main.MAIN_GAME_INSTANCE.map.characterEntity.getX(), 
				Main.MAIN_GAME_INSTANCE.map.characterEntity.getY(), WIDTH, HEIGHT));
		return false;
 */
